package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.enums.Precedence;
import org.fodor.browser.JS.AST.nodes.*;
import org.fodor.browser.JS.AST.utils.ExpressionEvaluator;

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
        }
    };

    private ArrayList<Token> tokens;
    private int cursor = 0;

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
        ReturnStatement returnStatement = new ReturnStatement();

        nextToken();

        // TODO: skipping expression

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

        // TODO: skipping expression

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

    // ---------------------------------------
    public Parser() {

    }

    private ASTNode walk() {
        // Stop walking when no more tokens
        if (cursor >= tokens.size()) {
            return null;
        }
        // Stop walking this child/branch when reached close curly brace
        if (tokens.get(cursor).getType() == Token.Type.RBRACE) {
            cursor++;
            return null;
        }
        if (isNamedFunctionDeclaration(cursor)) {
            String functionName = tokens.get(cursor + 1).getValue();
            cursor += 5;
            BlockStatement blockStatement = new BlockStatement();
            blockStatement.append(walk());
            FunctionDeclaration functionDeclaration = new FunctionDeclaration(functionName, blockStatement);
            return functionDeclaration;
        }
        if (isCallExpression(cursor)) {
            CallExpression callExpression = new CallExpression(tokens.get(cursor).getValue());
            cursor += 4;
            return callExpression;
        }
        if (isReturnStatement(cursor)) {
            cursor++;
            return new ReturnStatement(walkExpression());
        }
        if (isExpressionStatement(cursor)) {
            return new ExpressionStatement(walkExpression());
        }

        cursor++;
        return walk();
    }

    /*
    Gather all tokens of an expression and evaluate it.
    Will return the root node of the expression tree.
     */
    private ASTNode walkExpression() {
        ArrayList<Token> expressionTokens = new ArrayList<>();
        while (
                cursor < tokens.size() &&
                        tokens.get(cursor).getType() != Token.Type.SEMICOLON &&
                        tokens.get(cursor).getType() != Token.Type.RBRACE
        ) {
            expressionTokens.add(tokens.get(cursor));
            cursor++;
        }
        return ExpressionEvaluator.eval(expressionTokens);
    }

    public Program parse(Lexer lexer) {
        Program program = new Program();
        Token token;

        tokens = new ArrayList<>();

        // TODO: get tokens one by one instead of all at once
        while ((token = lexer.nextToken()).getType() != Token.Type.EOF) {
            tokens.add(token);
        }

        while (cursor < tokens.size()) {
            program.append(walk());
        }

        return program;
    }

    private boolean isCallExpression(int index) {
        return (index + 2) < tokens.size() &&
                        tokens.get(index).getType() == Token.Type.IDENT &&
                        tokens.get(index + 1).getType() == Token.Type.LPAREN &&
                        tokens.get(index + 2).getType() == Token.Type.RPAREN &&
                        ((index + 3) < tokens.size() ? (tokens.get(index + 3).getType() != Token.Type.LBRACE) : true);
    }

    private boolean isNamedFunctionDeclaration(int index) {
        return (index + 4) < tokens.size() &&
                tokens.get(index).getType() == Token.Type.FUNCTION &&
                        tokens.get(index + 1).getType() == Token.Type.IDENT &&
                        tokens.get(index + 2).getType() == Token.Type.LPAREN &&
                        tokens.get(index + 3).getType() == Token.Type.RPAREN &&
                        tokens.get(index + 4).getType() == Token.Type.LBRACE;
    }

    private boolean isExpressionStatement(int index) {
        return tokens.get(index).getType() == Token.Type.IDENT ||
                        tokens.get(index).getType() == Token.Type.NUM ||
                        tokens.get(index).getType() == Token.Type.STR;
    }

    private boolean isReturnStatement(int index) {
        return tokens.get(index).getType() == Token.Type.RETURN;
    }
}
