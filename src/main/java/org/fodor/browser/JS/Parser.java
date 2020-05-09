package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.JS.AST.structs.Token;
import org.fodor.browser.JS.AST.expressions.*;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.AST.enums.Precedence;
import org.fodor.browser.JS.AST.statements.*;

import java.util.*;

public class Parser {
    private Token currentToken;
    private Token peekToken;
    private Lexer lexer;
    private ArrayList<String> errors;
    private HashMap<Token.Type, Precedence> precedences = new HashMap<>() {
        {
            put(Token.Type.EQ, Precedence.EQUALS);
            put(Token.Type.NEQ, Precedence.EQUALS);
            put(Token.Type.LT, Precedence.LESSGREATER);
            put(Token.Type.GT, Precedence.LESSGREATER);
            put(Token.Type.PLUS, Precedence.SUM);
            put(Token.Type.MINUS, Precedence.SUM);
            put(Token.Type.SLASH, Precedence.PRODUCT);
            put(Token.Type.ASTERISK, Precedence.PRODUCT);
            put(Token.Type.LPAREN, Precedence.CALL);
        }
    };
    private interface parsePrefixFunction {
        ASTNode parse();
    }
    private interface parseInfixFunction {
        ASTNode parse(ASTNode expression);
    }
    HashMap<Token.Type, parsePrefixFunction> parsePrefixFunctions = new HashMap<>() {
        {
            put(Token.Type.IDENT, () -> parseIdentifier());
            put(Token.Type.NUM, () -> parseIntegerLiteral());
            put(Token.Type.STR, () -> parseStringLiteral());
            put(Token.Type.TRUE, () -> parseBoolean());
            put(Token.Type.FALSE, () -> parseBoolean());
            put(Token.Type.LPAREN, () -> parseGroupedExpression());
            put(Token.Type.BANG, () -> parsePrefixExpression());
            put(Token.Type.MINUS, () -> parsePrefixExpression());
            put(Token.Type.IF, () -> parseIfStatement());
            put(Token.Type.FUNCTION, () -> parseFunctionDeclaration());
        }
    };

    HashMap<Token.Type, parseInfixFunction> parseInfixFunctions = new HashMap<>() {
        {
            put(Token.Type.PLUS, (exp) -> parseInfixExpression(exp));
            put(Token.Type.MINUS, (exp) -> parseInfixExpression(exp));
            put(Token.Type.SLASH, (exp) -> parseInfixExpression(exp));
            put(Token.Type.ASTERISK, (exp) -> parseInfixExpression(exp));
            put(Token.Type.EQ, (exp) -> parseInfixExpression(exp));
            put(Token.Type.NEQ, (exp) -> parseInfixExpression(exp));
            put(Token.Type.GT, (exp) -> parseInfixExpression(exp));
            put(Token.Type.LT, (exp) -> parseInfixExpression(exp));
            put(Token.Type.LPAREN, (exp) -> parseCallExpression(exp));
        }
    };

    public Parser(Lexer lexer) {
        this.errors = new ArrayList<>();
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    private Precedence peekPrecedence() {
        if (precedences.containsKey(peekToken.getType())) {
            return precedences.get(peekToken.getType());
        }
        return Precedence.LOWEST;
    }

    private Precedence curPrecedence() {
        if (precedences.containsKey(currentToken.getType())) {
            return precedences.get(currentToken.getType());
        }
        return Precedence.LOWEST;
    }

    private void nextToken() {
        this.currentToken = this.peekToken;
        this.peekToken = lexer.nextToken();
    }

    public Program parseProgram() {
        Program program = new Program();

        while (!currentTokenIs(Token.Type.EOF)) {
            ASTNode statement = parseStatement();
            if (statement != null) {
                program.append(statement);
            }
            nextToken();
        }

        return program;
    }

    private ASTNode parseStatement() {
        switch (currentToken.getType()) {
            case VAR:
                return parseVarStatement();
            case RETURN:
                return parseReturnStatement();
            case IF:
                return parseIfStatement();
            case ILLEGAL:
                return new ErrorLiteral("Illegal token: " + currentToken.getValue());
            default:
                return parseExpressionStatement();
        }
    }

    private ExpressionStatement parseExpressionStatement() {
        ExpressionStatement expressionStatement = new ExpressionStatement();

        ASTNode expression = parseExpression(Precedence.LOWEST);

        expressionStatement.setExpression(expression);

        if (peekTokenIs(Token.Type.SEMICOLON)) {
            nextToken();
        }
        return expressionStatement;
    }

    private ASTNode parseExpression(Precedence precedence) {
        parsePrefixFunction prefixFn = parsePrefixFunctions.get(currentToken.getType());
        if (prefixFn == null) {
            return null;
        }
        ASTNode left = prefixFn.parse();

        while (!peekTokenIs(Token.Type.SEMICOLON) && precedence.ordinal() < peekPrecedence().ordinal()) {
            parseInfixFunction infixFn = parseInfixFunctions.get(peekToken.getType());
            if (infixFn == null) {
                return left;
            }
            nextToken();
            left = infixFn.parse(left);
        }

        return left;
    }

    private ASTNode parseGroupedExpression() {
        nextToken();
        ASTNode exp = parseExpression(Precedence.LOWEST);
        if (!expectPeek(Token.Type.RPAREN)) {
            return null;
        }
        return exp;
    }

    private PrefixExpression parsePrefixExpression() {
        PrefixExpression prefixExpression = new PrefixExpression(currentToken.getValue());
        nextToken();
        prefixExpression.setRight(parseExpression(Precedence.PREFIX));
        return prefixExpression;
    }

    private BinaryExpression parseInfixExpression(ASTNode left) {
        Token token = currentToken;
        Precedence precedence = curPrecedence();

        nextToken();

        return new BinaryExpression(token.getValue(), left, parseExpression(precedence));
    }

    private Expression parseCallExpression(ASTNode function) {
        return new CallExpression(((Identifier) function).getName(), parseCallArguments());
    }

    private ArrayList<ASTNode> parseCallArguments() {
        ArrayList<ASTNode> arguments = new ArrayList<>();
        if (peekTokenIs(Token.Type.RPAREN)) {
            nextToken();
            return arguments;
        }
        nextToken();
        arguments.add(parseExpression(Precedence.LOWEST));
        while (peekTokenIs(Token.Type.COMMA)) {
            nextToken();
            nextToken();
            arguments.add(parseExpression(Precedence.LOWEST));
        }
        if (!expectPeek(Token.Type.RPAREN)) {
            return null;
        }
        return arguments;
    }

    private FunctionDeclaration parseFunctionDeclaration() {
        if (!expectPeek(Token.Type.LPAREN)) {
            return null;
        }

        ArrayList<Identifier> parameters = parseFunctionParameters();

        if (!expectPeek(Token.Type.LBRACE)) {
            return null;
        }

        BlockStatement body = parseBlockStatement();

        return new FunctionDeclaration(body, parameters);
    }

    private ArrayList<Identifier> parseFunctionParameters() {
        ArrayList<Identifier> identifiers = new ArrayList<>();

        if (peekTokenIs(Token.Type.RPAREN)) {
            nextToken();
            return identifiers;
        }

        nextToken();

        Identifier ident = new Identifier(currentToken);
        identifiers.add(ident);

        while (peekTokenIs(Token.Type.COMMA)) {
            nextToken();
            nextToken();
            ident = new Identifier(currentToken);
            identifiers.add(ident);
        }

        if (!expectPeek(Token.Type.RPAREN)) {
            return null;
        }

        return identifiers;
    }

    private Identifier parseIdentifier() {
        return new Identifier(currentToken);
    }

    private Literal parseIntegerLiteral() {
        return new Literal(new Value(Value.Type.Number, Integer.parseInt(currentToken.getValue())));
    }

    private Literal parseStringLiteral() {
        return new Literal(new Value(Value.Type.String, currentToken.getValue()));
    }

    private Literal parseBoolean() {
        return new Literal(new Value(Value.Type.Boolean, currentTokenIs(Token.Type.TRUE)));
    }

    private IfStatement parseIfStatement() {
        IfStatement ifStatement = new IfStatement();

        if (!expectPeek(Token.Type.LPAREN)) {
            return null;
        }
        nextToken();
        ifStatement.setTest(parseExpression(Precedence.LOWEST));
        if (!expectPeek(Token.Type.RPAREN)) {
            return null;
        }
        if (!expectPeek(Token.Type.LBRACE)) {
            return null;
        }
        ifStatement.setConsequent(parseBlockStatement());

        if (peekTokenIs(Token.Type.ELSE)) {
            nextToken();
            if (!expectPeek(Token.Type.LBRACE)) {
                return null;
            }
            ifStatement.setAlternate(parseBlockStatement());
        }

        return ifStatement;
    }

    private BlockStatement parseBlockStatement() {
        BlockStatement block = new BlockStatement();
        nextToken();

        while (!currentTokenIs(Token.Type.RBRACE)) {
            ASTNode stmt = parseStatement();
            if (stmt != null) {
                block.append(stmt);
            }
            nextToken();
        }
        return block;
    }

    private ReturnStatement parseReturnStatement() {
        nextToken();

        ReturnStatement returnStatement = new ReturnStatement(parseExpression(Precedence.LOWEST));

        while (!currentTokenIs(Token.Type.SEMICOLON)) {
            nextToken();
        }

        return returnStatement;
    }

    private VariableDeclaration parseVarStatement() {
        VariableDeclaration declaration = new VariableDeclaration(currentToken);

        if (!expectPeek(Token.Type.IDENT)) {
            return null;
        }

        declaration.setName(currentToken);

        if (!expectPeek(Token.Type.ASSIGN)) {
            return null;
        }

        nextToken();
        declaration.setBody(parseExpression(Precedence.LOWEST));

        while (!currentTokenIs(Token.Type.SEMICOLON)) {
            nextToken();
        }
        
        return declaration;
    }

    private boolean currentTokenIs(Token.Type type) {
        return currentToken.getType() == type;
    }

    private boolean expectPeek(Token.Type type) {
        if (peekTokenIs(type)) {
            nextToken();
            return true;
        }
        peekError(type);
        return false;
    }

    private boolean peekTokenIs(Token.Type type) {
        return peekToken.getType() == type;
    }

    private ArrayList<String> getErrors() {
        return this.errors;
    }

    private void peekError(Token.Type type) {
        String msg = String.format("Expected next token to be %s, got %s instead", type, peekToken.getType());
        System.out.println(msg);
        this.errors.add(msg);
    }
}
