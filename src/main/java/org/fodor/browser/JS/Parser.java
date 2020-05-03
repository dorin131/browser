package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.enums.Precedence;
import org.fodor.browser.JS.AST.nodes.*;
import org.fodor.browser.JS.AST.utils.ExpressionEvaluator;

import java.util.*;

public class Parser {
    private Token currentToken;
    private Token peekToken;
    private Lexer lexer;
    private ArrayList<String> errors;

    private ArrayList<Token> tokens;
    private int cursor = 0;

    public Parser(Lexer lexer) {
        this.errors = new ArrayList<>();
        this.lexer = lexer;
        nextToken();
        nextToken();
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

    private ASTNode parseExpression(Precedence p) {
        switch (currentToken.getType()) {
            case IDENT:
                return parseIdentifier();
            default:
                return null;
        }
    }

    private ASTNode parseIdentifier() {
        return new Identifier(currentToken);
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

    private Expression prefixParseFn() {
        return null;
    }

    private Expression infixParseFn(Expression exp) {
        return null;
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
