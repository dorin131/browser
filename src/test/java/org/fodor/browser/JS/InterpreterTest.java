package org.fodor.browser.JS;

import static org.junit.jupiter.api.Assertions.*;

import org.fodor.browser.JS.AST.nodes.*;
import org.fodor.browser.JS.AST.Value;
import org.junit.jupiter.api.Test;

class InterpreterTest {

    @Test
    void run() {
        BlockStatement blockStatement = new BlockStatement();
        ReturnStatement returnStatement = new ReturnStatement(
                new BinaryExpression(
                        BinaryExpression.Op.Plus,
                        new Literal(new Value<>(1)),
                        new Literal(new Value<>(2))
                )
        );

        blockStatement.append(returnStatement);

        Program program = new Program();
        FunctionDeclaration functionDeclaration = new FunctionDeclaration("foo", blockStatement);

        //System.out.println("+ " + program.getClass().getSimpleName());

        program.append(functionDeclaration);
        program.append(blockStatement);
        program.append(new CallExpression("foo"));

        Value result = new Interpreter().run(program);
        assertEquals(3, result.getValue());
    }
}