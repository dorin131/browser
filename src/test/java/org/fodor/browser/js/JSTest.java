package org.fodor.browser.js;

import org.fodor.browser.shared.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSTest {

    @Test
    void evalFunctionWithReturnValue() {
        String input = "var test = function() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "};\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(6, result.getValue());
    }

    @Test
    void evalFunctionWithReturnValue2() {
        String input = "var test = function() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "};\n" +
                "\n" +
                "1 + test();";
        Value result = new JS().eval(input);
        assertEquals(7, result.getValue());
    }

    @Test
    void evalFunctionWithReturnValue3() {
        String input = "var test = function() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "};\n" +
                "\n" +
                "var test2 = function() {\n" +
                "\treturn 4;\n" +
                "};\n" +
                "\n" +
                "test2() + test();";
        Value result = new JS().eval(input);
        assertEquals(10, result.getValue());
    }

    @Test
    void evalFunctionWithReturnValue4() {
        String input = "var test = function() {\n" +
                "};\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(Value.jsUndefined(), result);
    }

    @Test
    void evalFunctionWithMixedPrecedence1() {
        String input = "var test = function() {\n" +
                "\treturn 1 + 2 * 3;\n" +
                "};\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(7, result.getValue());
    }

    @Test
    void evalFunctionWithReturnUndefined() {
        String input = "var test = function() {\n" +
                "\t1 + 2 + 3;\n" +
                "};\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Undefined, result.getType());
    }

    @Test
    void evalExpressionAddition() {
        String input = "1 + 2 + 3;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(6, result.getValue());
    }

    @Test
    void evalExpressionMixed() {
        String input = "10 + 2 - 3;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(9, result.getValue());
    }

    @Test
    void evalExpressionWithoutSemicolon1() {
        String input = "777";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(777, result.getValue());
    }

    @Test
    void evalExpressionWithoutSemicolon2() {
        String input = "777 + 1000";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(1777, result.getValue());
    }

    @Test
    void evalExpressionWithSemicolon3() {
        String input = "666 - 66 + 1";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(601, result.getValue());
    }

    @Test
    void evalExpression() {
        String input = "var x = 1; x;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(1, result.getValue());
    }

    @Test
    void evalExpression2() {
        String input = "var x = 10 + 20; x;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(30, result.getValue());
    }

    @Test
    void evalExpression3() {
        String input = "var a = 1;\n" +
                "var b = a;\n" +
                "b;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(1, result.getValue());
    }

    @Test
    void evalExpression4() {
        String input = "var a = 1;\n" +
                "var b = 2;\n" +
                "var c = a + b;\n" +
                "c;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(3, result.getValue());
    }

    @Test
    void evalIllegal1() {
        String input = "\"";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Error, result.getType());
    }

    @Test
    void evalParams1() {
        String input = "var dorin = function(x) { return x; }; dorin(10);";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(10, result.getValue());
    }

    @Test
    void evalParams2() {
        String input = "var dorin = function(x, y) { return x + y; }; dorin(10, 20);";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(30, result.getValue());
    }

    @Test
    void evalParams3() {
        String input = "var dorin = function(x, y) { return x + y; }; var yo = function() { return 3; }; dorin(10, yo());";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(13, result.getValue());
    }

    @Test
    void evalParams4() {
        String input = "var a = 1; var www = function(a,b) {return a*b;}; www(9,9);";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(81, result.getValue());
    }

    @Test
    void evalParams5() {
        String input = "var a = 1; var www = function(b) {return a*b;}; www(9,9);";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(9, result.getValue());
    }

    @Test
    void evalObject0() {
        String input = "var a = {};";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Undefined, result.getType());
    }

    @Test
    void evalObject01() {
        String input = "var a = {}; a;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Object, result.getType());
        assertEquals("{ }", result.getValue());
    }

    @Test
    void evalObject1() {
        String input = "var a = { b: 1 };";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Undefined, result.getType());
    }

    @Test
    void evalObject2() {
        String input = "var a = { b: 1 }; a;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Object, result.getType());
        assertEquals("{ b: 1, }", result.getValue());
    }

    @Test
    void evalObject2_1() {
        String input = "var a = { b: 1, c: 1 + 2 }; a;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Object, result.getType());
        assertEquals("{ b: 1, c: 1 + 2, }", result.getValue());
    }

    @Test
    void evalObject3() {
        String input = "var a = { b: 1 }; a.b;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(1, result.getValue());
    }

    @Test
    void evalObject4() {
        String input = "var a = { b: 1 + 2 }; a.b;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(3, result.getValue());
    }

    @Test
    void evalObject5() {
        String input = "var d = function() { return 5; }; var a = { b: 1 + 2, c: d() * 2 }; a.b + a.c;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(13, result.getValue());
    }

//    @Test
//    void evalObject6() {
//        String input = "var a = { b: { c: 3 } }; a.b.c;";
//        Value result = new JS().eval(input);
//        assertEquals(Value.Type.Number, result.getType());
//        assertEquals(3, result.getValue());
//    }
}