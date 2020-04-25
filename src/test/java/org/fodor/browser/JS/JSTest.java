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
}