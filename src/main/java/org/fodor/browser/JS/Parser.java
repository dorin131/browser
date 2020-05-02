package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.nodes.*;
import org.fodor.browser.JS.AST.utils.ExpressionEvaluator;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;
    private int cursor = 0;

    private ASTNode walk() {
        // Stop walking when no more tokens
        if (cursor >= tokens.size()) {
            return null;
        }
        // Stop walking this child/branch when reached close curly brace
        if (tokens.get(cursor).getType() == Token.Type.RBrace) {
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
                        tokens.get(cursor).getType() != Token.Type.Semicolon &&
                        tokens.get(cursor).getType() != Token.Type.RBrace
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
                        tokens.get(index).getType() == Token.Type.Identifier &&
                        tokens.get(index + 1).getType() == Token.Type.LParen &&
                        tokens.get(index + 2).getType() == Token.Type.RParen &&
                        ((index + 3) < tokens.size() ? (tokens.get(index + 3).getType() != Token.Type.LBrace) : true);
    }

    private boolean isNamedFunctionDeclaration(int index) {
        return (index + 4) < tokens.size() &&
                tokens.get(index).getType() == Token.Type.Function &&
                        tokens.get(index + 1).getType() == Token.Type.Identifier &&
                        tokens.get(index + 2).getType() == Token.Type.LParen &&
                        tokens.get(index + 3).getType() == Token.Type.RParen &&
                        tokens.get(index + 4).getType() == Token.Type.LBrace;
    }

    private boolean isExpressionStatement(int index) {
        return tokens.get(index).getType() == Token.Type.Identifier ||
                        tokens.get(index).getType() == Token.Type.Numeric ||
                        tokens.get(index).getType() == Token.Type.String;
    }

    private boolean isReturnStatement(int index) {
        return tokens.get(index).getType() == Token.Type.Return;
    }
}
