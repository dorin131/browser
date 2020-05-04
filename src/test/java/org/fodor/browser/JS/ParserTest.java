package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.nodes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse1() {
        String input = "function test() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "}\n" +
                "\n" +
                "test();";

        Lexer lexer = new Lexer(input);

        Program result = new Parser().parse(lexer);

        assertEquals(2, result.getChildren().size());
        ASTNode sub1 = result.getChildren().get(0);
        Expression sub2 = (Expression) result.getChildren().get(1);
        assertEquals("FunctionDeclaration", sub1.getClass().getSimpleName());
        assertEquals("CallExpression", sub2.getClass().getSimpleName());
        ScopeNode sub1_1 = ((FunctionDeclaration) sub1).getBody();
        assertEquals("BlockStatement", sub1_1.getClass().getSimpleName());
        ASTNode sub1_1_1 = (ASTNode) sub1_1.getChildren().get(0);
        assertEquals("ReturnStatement", sub1_1_1.getClass().getSimpleName());
        ASTNode sub1_1_1_1 = ((ReturnStatement) sub1_1_1).getExpression();
        assertEquals("BinaryExpression", sub1_1_1_1.getClass().getSimpleName());
        assertEquals("+", ((BinaryExpression) sub1_1_1_1).getOp().toString());
        assertEquals("Literal", ((BinaryExpression) sub1_1_1_1).getRhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) sub1_1_1_1).getLhs().getClass().getSimpleName());
        BinaryExpression sub1_1_1_1_1 = (BinaryExpression) ((BinaryExpression) sub1_1_1_1).getLhs();
        assertEquals("+", sub1_1_1_1_1.getOp().toString());
        assertEquals("Literal", sub1_1_1_1_1.getRhs().getClass().getSimpleName());
        assertEquals("Literal", sub1_1_1_1_1.getLhs().getClass().getSimpleName());
    }

    @Test
    void parseProgram1() {
        String input = "var dorin = 1;\n" +
                "var browser = 777;";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(2, result.getChildren().size());
        ASTNode sub1 = result.getChildren().get(0);
        ASTNode sub2 = result.getChildren().get(1);
        assertEquals("VariableDeclaration", sub1.getClass().getSimpleName());
        assertEquals("VariableDeclaration", sub2.getClass().getSimpleName());
        assertEquals("dorin", ((VariableDeclaration) sub1).getName());
        assertEquals("browser", ((VariableDeclaration) sub2).getName());
    }

    @Test
    void parseProgram2() {
        String input = "dorin;";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode identifier = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("Identifier", identifier.getClass().getSimpleName());
    }

    @Test
    void parseProgram3() {
        String input = "7;";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode literal = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("Literal", literal.getClass().getSimpleName());
        assertEquals(7, ((Literal) literal).execute(null).getValue());
    }

    @Test
    void parseProgram4() {
        String input = "-888;";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode literal = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("PrefixExpression", literal.getClass().getSimpleName());
    }

    @Test
    void parseProgram5() {
        String input = "1 + 2;";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode literal = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", literal.getClass().getSimpleName());
    }

    @Test
    void parseProgram6() {
        String input = "1 + 2";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode literal = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", literal.getClass().getSimpleName());
    }

    @Test
    void parseProgram7() {
        String input = "1 + 2 * 3";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode binaryExpression = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", binaryExpression.getClass().getSimpleName());
        assertEquals("Literal", ((BinaryExpression) binaryExpression).getLhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) binaryExpression).getRhs().getClass().getSimpleName());
    }

    @Test
    void parseProgram8() {
        String input = "1 + 2 * 3 - 2";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode binaryExpression = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", binaryExpression.getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) binaryExpression).getLhs().getClass().getSimpleName());
        assertEquals("Literal", ((BinaryExpression) binaryExpression).getRhs().getClass().getSimpleName());
    }

    @Test
    void parseProgram9() {
        String input = "5 > 2 == false";

        Lexer lexer = new Lexer(input);

        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode binaryExpression = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", binaryExpression.getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) binaryExpression).getLhs().getClass().getSimpleName());
        assertEquals("Literal", ((BinaryExpression) binaryExpression).getRhs().getClass().getSimpleName());
        assertEquals(false, ((BinaryExpression) binaryExpression).getRhs().execute(null).getValue());
    }
}