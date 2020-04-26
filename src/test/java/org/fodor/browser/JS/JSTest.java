package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSTest {

    @Test
    void eval() {
        String input = "function test() {\n" +
                "\treturn 1 + 2 + 3;\n" +
                "}\n" +
                "\n" +
                "test();";
        Value result = new JS().eval(input);
        assertTrue(result.getValue().equals("6"));
    }

    // 1 + 2 + 3 + 4;
    // result: 10

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