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
    void evalExpression() {
        String input = "1 + 2 + 3;";
        Value result = new JS().eval(input);
        assertEquals(Value.Type.Number, result.getType());
        assertEquals(6, result.getValue());
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