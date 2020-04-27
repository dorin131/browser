package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.nodes.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        ArrayList<Token> input = new ArrayList<>();
        input.add(new Token(Token.Type.Keyword, Token.Keyword.function.toString()));
        input.add(new Token(Token.Type.Identifier, "test"));
        input.add(new Token(Token.Type.Punctuator, "("));
        input.add(new Token(Token.Type.Punctuator, ")"));
        input.add(new Token(Token.Type.Punctuator, "{"));
        input.add(new Token(Token.Type.Keyword, Token.Keyword.returns.toString()));
//        input.add(new Token(Token.Type.Punctuator, "("));
        input.add(new Token(Token.Type.Numeric, "1"));
        input.add(new Token(Token.Type.Punctuator, "+"));
        input.add(new Token(Token.Type.Numeric, "2"));
//        input.add(new Token(Token.Type.Punctuator, ")"));
        input.add(new Token(Token.Type.Punctuator, "+"));
        input.add(new Token(Token.Type.Numeric, "3"));
        input.add(new Token(Token.Type.Punctuator, ";"));
        input.add(new Token(Token.Type.Punctuator, "}"));
        input.add(new Token(Token.Type.Identifier, "test"));
        input.add(new Token(Token.Type.Punctuator, "("));
        input.add(new Token(Token.Type.Punctuator, ")"));
        input.add(new Token(Token.Type.Punctuator, ";"));

        Program result = new Parser(input).parse();

        assertEquals(2, result.getChildren().size());
        ASTNode sub1 = result.getChildren().get(0);
        Expression sub2 = (Expression) result.getChildren().get(1);
        assertEquals("FunctionDeclaration", sub1.getClass().getSimpleName());
        assertEquals("CallExpression", sub2.getClass().getSimpleName());
        ScopeNode sub1_1 = ((FunctionDeclaration) sub1).getBody();
        assertEquals("BlockStatement", sub1_1.getClass().getSimpleName());
        ASTNode sub1_1_1 = (ASTNode) sub1_1.getChildren().get(0);
        assertEquals("ReturnStatement", sub1_1_1.getClass().getSimpleName());
        ASTNode sub1_1_1_1 = ((ReturnStatement) sub1_1_1).getArgument();
        assertEquals("BinaryExpression", sub1_1_1_1.getClass().getSimpleName());
        assertEquals("Add", ((BinaryExpression) sub1_1_1_1).getOp().toString());
        assertEquals("Literal", ((BinaryExpression) sub1_1_1_1).getLhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) sub1_1_1_1).getRhs().getClass().getSimpleName());
        BinaryExpression sub1_1_1_1_1 = (BinaryExpression) ((BinaryExpression) sub1_1_1_1).getRhs();
        assertEquals("Add", sub1_1_1_1_1.getOp().toString());
        assertEquals("Literal", sub1_1_1_1_1.getLhs().getClass().getSimpleName());
        assertEquals("Literal", sub1_1_1_1_1.getRhs().getClass().getSimpleName());
    }
}