package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.statements.BlockStatement;
import org.fodor.browser.js.AST.statements.FunctionDeclaration;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

import java.util.ArrayList;

public class CallExpression extends Expression {
    private Expression expression;
    private ArrayList<ASTNode> arguments = new ArrayList<>();

    public CallExpression(Identifier expression) {
        this.expression = expression;
    }

    public CallExpression(Expression expression, ArrayList<ASTNode> arguments) {
        this.expression = expression;
        this.arguments = arguments;
    }

    @Override
    public Value execute(Interpreter interpreter) {
        ASTNode callee;

        if (expression instanceof Identifier) {
            callee = findValueInScope(interpreter, (Identifier) expression);
        } else if (expression instanceof MemberExpression) {
            callee = expression;
        } else {
            throw new RuntimeException("Cannot call expression: " + expression.toString());
        }


        if (callee == null) {
            // TODO: not defined
        }

        // if it's a function
        if (callee instanceof FunctionDeclaration) {
            BlockStatement body = ((FunctionDeclaration) callee).getBody();
            body.associateArguments(getArguments());
            return interpreter.run(body);
        }

        // We execute the expression
        var res = callee.execute(interpreter);

        // If the result is a Function, we execute the function
        if (res.getType() == Value.Type.Function) {
            var body = (BlockStatement) res.getValue();
            body.associateArguments(getArguments());
            return interpreter.run(body);
        }

        // Otherwise we return the value
        return res;
    }

    public ArrayList<ASTNode> getArguments() {
        return arguments;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        this.expression.dump(indent + 1);
        printIndent(indent + 1);
        if (arguments != null && arguments.size() > 0) {
            System.out.println("Arguments");
            for (ASTNode arg : arguments) {
                arg.dump(indent + 2);
            }
        }
    }
}