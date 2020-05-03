package org.fodor.browser.JS.AST.utils;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.nodes.ASTNode;
import org.fodor.browser.JS.AST.nodes.BinaryExpression;
import org.fodor.browser.JS.AST.nodes.Literal;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    @Test
    /*
    1 + 2 + 3
     */
    void eval() {
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token(Token.Type.NUM, "1"));
        tokens.add(new Token(Token.Type.ADD, "+"));
        tokens.add(new Token(Token.Type.NUM, "2"));
        tokens.add(new Token(Token.Type.ADD, "+"));
        tokens.add(new Token(Token.Type.NUM, "3"));

        ASTNode result = ExpressionEvaluator.eval(tokens);

        assertEquals("BinaryExpression", result.getClass().getSimpleName());

        BinaryExpression top = (BinaryExpression) result;

        assertEquals("Literal", top.getRhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", top.getLhs().getClass().getSimpleName());

        Literal left = (Literal) top.getRhs();
        BinaryExpression right = (BinaryExpression) top.getLhs();

        assertEquals("Literal", right.getLhs().getClass().getSimpleName());
        assertEquals("Literal", right.getRhs().getClass().getSimpleName());
        assertEquals(1, right.getLhs().execute(null).getValue());
        assertEquals(2, right.getRhs().execute(null).getValue());
        assertEquals(3, left.execute(null).getValue());
    }
}