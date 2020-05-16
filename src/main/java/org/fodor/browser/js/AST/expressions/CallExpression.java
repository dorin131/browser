package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.statements.BlockStatement;
import org.fodor.browser.js.AST.statements.FunctionDeclaration;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

import java.util.ArrayList;

public class CallExpression extends Expression {
    private Identifier identifier;
    private ArrayList<ASTNode> arguments = new ArrayList<>();

    public CallExpression(Identifier identifier) {
        this.identifier = identifier;
    }

    public CallExpression(Identifier identifier, ArrayList<ASTNode> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public Value execute(Interpreter interpreter) {
        ASTNode callee = findValueInScope(interpreter, identifier);

        if (callee == null) {
            // TODO: not defined
        }

        // if it's a function
        if (callee instanceof FunctionDeclaration) {
            BlockStatement body = ((FunctionDeclaration) callee).getBody();
            body.associateArguments(getArguments());
            return interpreter.run(body);
        }

        // or if it's just an expression
        return callee.execute(interpreter);
    }

    public ArrayList<ASTNode> getArguments() {
        return arguments;
    }

    public String name() {
        return this.identifier.getName();
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.printf("%s \"%s\"\n", this.getClass().getSimpleName(), name());
        printIndent(indent + 1);
        if (arguments != null && arguments.size() > 0) {
            System.out.println("Arguments");
            for (ASTNode arg : arguments) {
                arg.dump(indent + 2);
            }
        }
    }
}