package org.fodor.browser.JS.AST.utils;

import org.fodor.browser.JS.AST.enums.Operator;
import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.ASTNode;
import org.fodor.browser.JS.AST.nodes.BinaryExpression;
import org.fodor.browser.JS.AST.nodes.Literal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
https://www.klittlepage.com/2013/12/22/twelve-days-2013-shunting-yard-algorithm/
 */
public class ExpressionEvaluator {
    private static void addNode(Stack<ASTNode> stack, String operator) {
        final ASTNode right = stack.pop();
        final ASTNode left = stack.pop();

//        switch (operator) {
//            case "+":
//                stack.push(new BinaryExpression(Operator.Add, left, right));
//                break;
//            case "-":
//                stack.push(new BinaryExpression(Operator.Sub, left, right));
//                break;
//            default:
//                throw new RuntimeException("Unknown operator");
//        }
        stack.push(new BinaryExpression(operator, left, right));

    }

    public static ASTNode eval(ArrayList<Token> tokens) {
        Stack<String> operatorStack = new Stack<>();
        Stack<ASTNode> operandStack = new Stack<>();
        List<Character> operators = Arrays.asList('+', '-', '/', '%', '*', '<', '>');

        for (Token token : tokens) {
            String popped;
            String value = token.getValue();

            switch (value) {
                case "(":
                    operatorStack.push("(");
                case ")":
                    while (!operatorStack.isEmpty()) {
                        popped = operatorStack.pop();
                        if (popped.equals("(")) {
                            continue;
                        } else {
                            addNode(operandStack, popped);
                        }
                        throw new IllegalStateException("Unballanced right paren");
                    }
                default:
                    String o1 = value;
                    String o2;
                    if (operators.contains(o1.charAt(0))) {
                        while (!operatorStack.isEmpty() && null != (o2 = operatorStack.peek())) {
                            // here we check precedence
                            if (true) {
                                operatorStack.pop();
                                addNode(operandStack, o2);
                            } else {
                                break;
                            }
                        }
                        operatorStack.push(o1);
                    } else {
                        // TODO: assuming its a number
                        //Literal literal;
                        //if (tokens.get(cursor).getType() == Token.Type.String) {
                        //    literal = new Literal(new Value(Value.Type.String, tokens.get(cursor)));
                        //} else {
                        //    literal = new Literal(new Value(Value.Type.Number, Integer.parseInt(tokens.get(cursor).getValue())));
                        //}
                        operandStack.push(new Literal(new Value(Value.Type.Number, Integer.parseInt(o1))));
                    }
            }
        }
        while (!operatorStack.isEmpty()) {
            addNode(operandStack, operatorStack.pop());
        }

        return operandStack.pop();
    }
}
