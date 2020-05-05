package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Function;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

public class CallExpression extends Expression {
    private String name;
    private ArrayList<ASTNode> parameters = new ArrayList<>();

    public CallExpression(String name) {
        this.name = name;
    }

    public CallExpression(String name, ArrayList<ASTNode> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public Value execute(Interpreter interpreter) {
        Value callee = interpreter.getGlobal().get(this.name);
        if (callee.getValue() instanceof Function) {
            return interpreter.run(((Function) callee.getValue()).body());
        }
        throw new RuntimeException("Not Function: " + callee);
    }

    public String name() {
        return this.name;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.printf("%s \"%s\"\n", this.getClass().getSimpleName(), name());
        printIndent(indent + 1);
        System.out.println("Arguments");
        for (ASTNode p : parameters) {
            p.dump(indent + 2);
        }
    }
}