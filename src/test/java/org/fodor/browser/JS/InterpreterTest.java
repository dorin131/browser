package org.fodor.browser.JS;

import static org.junit.jupiter.api.Assertions.*;

import org.fodor.browser.JS.AST.enums.Operator;
import org.fodor.browser.JS.AST.nodes.*;
import org.fodor.browser.JS.AST.Value;
import org.junit.jupiter.api.Test;

class InterpreterTest {

    /*
    function test() {
        return (1 + 2) + 3;
    }
    test();

    --------------------------------

    {
        "type": "Program",
        "body": [
            {
                "type": "FunctionDeclaration",
                "id": {
                    "type": "Identifier",
                    "name": "test"
                },
                "params": [],
                "body": {
                    "type": "BlockStatement",
                    "body": [
                        {
                            "type": "ReturnStatement",
                            "argument": {
                                "type": "BinaryExpression",
                                "operator": "+",
                                "left": {
                                    "type": "BinaryExpression",
                                    "operator": "+",
                                    "left": {
                                        "type": "Literal",
                                        "value": 1,
                                        "raw": "1"
                                    },
                                    "right": {
                                        "type": "Literal",
                                        "value": 2,
                                        "raw": "2"
                                    }
                                },
                                "right": {
                                    "type": "Literal",
                                    "value": 3,
                                    "raw": "3"
                                }
                            }
                        }
                    ]
                },
                "generator": false,
                "expression": false,
                "async": false
            },
            {
                "type": "ExpressionStatement",
                "expression": {
                    "type": "CallExpression",
                    "callee": {
                        "type": "Identifier",
                        "name": "test"
                    },
                    "arguments": []
                }
            }
        ],
        "sourceType": "script"
    }
     */
    @Test
    void run() {
        BlockStatement blockStatement = new BlockStatement();
        ReturnStatement returnStatement = new ReturnStatement(
                new BinaryExpression(
                        "+",
                        new Literal(new Value(Value.Type.Number, 3)),
                        new BinaryExpression(
                                "-",
                                new Literal(new Value(Value.Type.Number, 100)),
                                new Literal(new Value(Value.Type.Number, 2))
                        )
                )
        );
        blockStatement.append(returnStatement);
        FunctionDeclaration functionDeclaration = new FunctionDeclaration("foo", blockStatement);

        Program program = new Program();
        program.append(functionDeclaration);
        program.append(new CallExpression("foo"));

        Value result = new Interpreter().run(program);

        assertEquals(Value.Type.Number, result.getType());
        assertEquals(101, result.getValue());
    }
}