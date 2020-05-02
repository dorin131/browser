package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Value;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSTest {

    @Test
    void evalFunctionWithReturnValue() {
        String input = "function test() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "}\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(6, result.getValue());
    }

//    @Test
//    void evalFunctionWithReturnValue2() {
//        String input = "function test() {\n" +
//                "\treturn 1 + 2 + 3;\n" +
//                "}\n" +
//                "\n" +
//                "1 + test();";
//        Value result = new JS().eval(input);
//        assertEquals(7, result.getValue());
//    }

//    @Test
//    void evalFunctionWithReturnValue3() {
//        String input = "function test() {\n" +
//                "\treturn 1 + 2 + 3;\n" +
//                "}\n" +
//                "\n" +
//                "function test2() {\n" +
//                "\treturn 4;\n" +
//                "}\n" +
//                "\n" +
//                "test2() + test();";
//        Value result = new JS().eval(input);
//        assertEquals(10, result.getValue());
//    }

    @Test
    void evalFunctionWithReturnValue4() {
        String input = "function test() {\n" +
                "}\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(Value.jsUndefined(), result);
    }

//    @Test
//    void evalFunctionWithMixedPrecedence1() {
//        String input = "function test() {\n" +
//                "\treturn 1 + 2 * 3;\n" +
//                "}\n" +
//                "\n" +
//                "test();";
//        Value result = new JS().eval(input);
//        assertEquals(7, result.getValue());
//    }

    @Test
    void evalFunctionWithReturnValueWithoutSemicolons() {
        String input = "function test() {\n" +
                "\treturn 1 + 2 + 3\n" +
                "}\n" +
                "\n" +
                "test()";
        Value result = new JS().eval(input);
        assertEquals(6, result.getValue());
    }

    @Test
    void evalFunctionWithReturnUndefined() {
        String input = "function test() {\n" +
                "\t1 + 2 + 3;\n" +
                "}\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Undefined, result.getType());
    }

    @Test
    void evalFunctionWithReturnUndefinedWithoutSemicolon() {
        String input = "function test() {\n" +
                "\t1 + 2 + 3\n" +
                "}\n" +
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
        // TODO: should be 601 - incorrect order
        assertEquals(777, result.getValue());
    }

    @Test
    void evalExpressionWithoutSemicolon2() {
        String input = "777 + 1000";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        // TODO: should be 601 - incorrect order
        assertEquals(1777, result.getValue());
    }

    @Test
    void evalExpressionWithoutSemicolon3() {
        String input = "666 - 66 + 1";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(601, result.getValue());
    }

    // var x = 1;
    // x;
    // result: 1

    // var x = 10 + 20;
    // x;
    // result: 30

    // var a = 1;
    // var b = a;
    // b;
    // result: 1

    // var a = 1;
    // var b = 2;
    // var c = a + b;
    // c;
    // result: 3
}