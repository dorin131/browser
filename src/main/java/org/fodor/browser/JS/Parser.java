package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private ArrayList<Token> tokens;
    private int cursor = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    private ASTNode walk() {
        if (cursor >= tokens.size()) {
            return new ASTNode();
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
            ReturnStatement returnStatement;
            returnStatement = new ReturnStatement(walk());
            return returnStatement;
        }
        if (isBinaryExpression(cursor)) {
            BinaryExpression.Op op = BinaryExpression.Op.Plus;
            Expression leftExpression = new Literal(new Value(tokens.get(cursor).getValue()));
            cursor += 2;
            BinaryExpression binaryExpression = new BinaryExpression(op, leftExpression, walk());

            return binaryExpression;
        }
        if (isLiteral(cursor)) {
            Literal literal = new Literal(new Value(tokens.get(cursor).getValue()));
            cursor++;
            return literal;
        }
        cursor++;
        return walk();
    }

    public Program parse() {
        Program program = new Program();

        while (cursor < tokens.size()) {
            program.append(walk());
        }

        return program;
    }

    private boolean isCallExpression(int index) {
        if (
                (index + 3) < tokens.size() &&
                        tokens.get(index).getType() == Token.Type.Identifier &&
                        tokens.get(index + 1).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 1).getValue().equals("(") &&
                        tokens.get(index + 2).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 2).getValue().equals(")") &&
                        tokens.get(index + 3).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 3).getValue().equals(";")
        ) {
            return true;
        }
        return false;
    }

    private boolean isLiteral(int index) {
        if (tokens.get(index).getType() == Token.Type.Numeric) {
            return true;
        }
        return false;
    }

    private boolean isNamedFunctionDeclaration(int index) {
        if (
                (index + 4) < tokens.size() &&
                tokens.get(index).getType() == Token.Type.Keyword &&
                        tokens.get(index).getValue().equals("function") &&
                        tokens.get(index + 1).getType() == Token.Type.Identifier &&
                        tokens.get(index + 2).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 2).getValue().equals("(") &&
                        tokens.get(index + 3).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 3).getValue().equals(")") &&
                        tokens.get(index + 4).getType() == Token.Type.Punctuator &&
                        tokens.get(index + 4).getValue().equals("{")
        ) {
            return true;
        }
        return false;
    }

    private boolean isBinaryExpression(int index) {
        List<Character> operators = Arrays.asList('+', '-', '/', '%', '*', '<', '>');
        if (
                (index + 1) < tokens.size() &&
                // Check if next character is an operator
                tokens.get(index + 1).getType() == Token.Type.Punctuator &&
                        operators.contains(tokens.get(index + 1).getValue().charAt(0))
        ) {
            return true;
        }
        return false;
    }

    private boolean isReturnStatement(int index) {
        if (
                tokens.get(index).getType() == Token.Type.Keyword &&
                        tokens.get(index).getValue().equals("return")
        ) {
            return true;
        }
        return false;
    }
}
