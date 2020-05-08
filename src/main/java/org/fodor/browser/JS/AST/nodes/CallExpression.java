package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.shared.Value;
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
        ASTNode callee = interpreter.getGlobal().get(this.name);

        // if it's a function
        if (callee instanceof FunctionDeclaration) {
            callee = ((FunctionDeclaration) callee).getBody();
        }

        // if it's a block
        if (callee instanceof ScopeNode) {
            return interpreter.run((ScopeNode) callee);
        }

        // or if it's just an expression
        return callee.execute(interpreter);
    }

    public String name() {
        return this.name;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.printf("%s \"%s\"\n", this.getClass().getSimpleName(), name());
        printIndent(indent + 1);
        if (parameters != null && parameters.size() > 0) {
            System.out.println("Arguments");
            for (ASTNode p : parameters) {
                p.dump(indent + 2);
            }
        }
    }
}