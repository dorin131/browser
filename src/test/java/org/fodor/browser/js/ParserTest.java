package org.fodor.browser.js;

import org.fodor.browser.js.AST.expressions.Literal;
import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.expressions.BinaryExpression;
import org.fodor.browser.js.AST.expressions.CallExpression;
import org.fodor.browser.js.AST.statements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse1() {
        String input = "var test = function() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "};\n" +
                "\n" +
                "test();";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(2, result.getChildren().size());
        ASTNode sub1 = result.getChildren().get(0);
        Statement sub2 = (Statement) result.getChildren().get(1);
        assertEquals("VariableDeclaration", sub1.getClass().getSimpleName());
        assertEquals("ExpressionStatement", sub2.getClass().getSimpleName());
        assertEquals("CallExpression", ((ExpressionStatement) sub2).getExpression().getClass().getSimpleName());
        ASTNode sub1_1 = ((VariableDeclaration) sub1).getBody();
        assertEquals("FunctionDeclaration", sub1_1.getClass().getSimpleName());
        ASTNode sub1_1_1 = ((FunctionDeclaration) sub1_1).getBody();
        assertEquals("BlockStatement", sub1_1_1.getClass().getSimpleName());
        ASTNode sub1_1_1_1 = ((BlockStatement) sub1_1_1).getChildren().get(0);
        assertEquals("ReturnStatement", sub1_1_1_1.getClass().getSimpleName());
        assertEquals("BinaryExpression", ((ReturnStatement) sub1_1_1_1).getExpression().getClass().getSimpleName());
        assertEquals("+", ((BinaryExpression) ((ReturnStatement) sub1_1_1_1).getExpression()).getOp());
        assertEquals("Literal", ((BinaryExpression) ((ReturnStatement) sub1_1_1_1).getExpression()).getRhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) ((ReturnStatement) sub1_1_1_1).getExpression()).getLhs().getClass().getSimpleName());
        BinaryExpression sub1_1_1_1_1 = (BinaryExpression) ((BinaryExpression) ((ReturnStatement) sub1_1_1_1).getExpression()).getLhs();
        assertEquals("+", sub1_1_1_1_1.getOp());
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
        assertEquals(2, ((BinaryExpression) ((BinaryExpression) binaryExpression).getRhs()).getLhs().execute(null).getValue());
        assertEquals(3, ((BinaryExpression) ((BinaryExpression) binaryExpression).getRhs()).getRhs().execute(null).getValue());
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

    @Test
    void parseProgram10() {
        String input = "(1 + 2) * 3";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        ASTNode binaryExpression = ((ExpressionStatement) expressionStatement).getExpression();
        assertEquals("BinaryExpression", binaryExpression.getClass().getSimpleName());
        assertEquals("Literal", ((BinaryExpression) binaryExpression).getRhs().getClass().getSimpleName());
        assertEquals("BinaryExpression", ((BinaryExpression) binaryExpression).getLhs().getClass().getSimpleName());
        assertEquals(1, ((BinaryExpression) ((BinaryExpression) binaryExpression).getLhs()).getLhs().execute(null).getValue());
        assertEquals(2, ((BinaryExpression) ((BinaryExpression) binaryExpression).getLhs()).getRhs().execute(null).getValue());
    }

    @Test
    void parseProgram11() {
        String input = "if (a > b) {\n" +
                "\t2;\n" +
                "}";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("IfStatement", expressionStatement.getClass().getSimpleName());
        IfStatement ifStatement = ((IfStatement) expressionStatement);
        assertEquals("BinaryExpression", ifStatement.getTest().getClass().getSimpleName());
        assertEquals("BlockStatement", ifStatement.getConsequent().getClass().getSimpleName());
        assertEquals("ExpressionStatement", ((BlockStatement) ifStatement.getConsequent()).getChildren().get(0).getClass().getSimpleName());
    }

    @Test
    void parseProgram12() {
        String input = "if (a > b) {\n" +
                "\t2;\n" +
                "} else {\n" +
                "\t1;\n" +
                "}";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("IfStatement", expressionStatement.getClass().getSimpleName());
        IfStatement ifStatement = ((IfStatement) expressionStatement);
        assertEquals("BinaryExpression", ifStatement.getTest().getClass().getSimpleName());
        assertEquals("BlockStatement", ifStatement.getConsequent().getClass().getSimpleName());
        assertEquals("ExpressionStatement", ((BlockStatement) ifStatement.getConsequent()).getChildren().get(0).getClass().getSimpleName());
        assertEquals(2, ((ExpressionStatement)((BlockStatement) ifStatement.getConsequent()).getChildren().get(0)).execute(null).getValue());
        assertEquals("BlockStatement", ifStatement.getAlternate().getClass().getSimpleName());
        assertEquals("ExpressionStatement", ((BlockStatement) ifStatement.getAlternate()).getChildren().get(0).getClass().getSimpleName());
        assertEquals(1, ((ExpressionStatement)((BlockStatement) ifStatement.getAlternate()).getChildren().get(0)).execute(null).getValue());
    }

    @Test
    void parseProgram13() {
        String input = "function(x,y) { x + y; };";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        assertEquals("FunctionDeclaration", ((ExpressionStatement) expressionStatement).getExpression().getClass().getSimpleName());
    }

    @Test
    void parseProgram14() {
        String input = "yo(1, 3 * 5);";

        Lexer lexer = new Lexer(input);
        Program result = new Parser(lexer).parseProgram();

        assertEquals(1, result.getChildren().size());
        ASTNode expressionStatement = result.getChildren().get(0);
        assertEquals("ExpressionStatement", expressionStatement.getClass().getSimpleName());
        assertEquals("CallExpression", ((ExpressionStatement) expressionStatement).getExpression().getClass().getSimpleName());
        assertEquals("yo", ((CallExpression)((ExpressionStatement) expressionStatement).getExpression()).name());
    }
}