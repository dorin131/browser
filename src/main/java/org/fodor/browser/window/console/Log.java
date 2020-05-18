package org.fodor.browser.window.console;

import org.fodor.browser.gui.components.Console;
import org.fodor.browser.js.AST.expressions.Identifier;
import org.fodor.browser.js.AST.statements.BlockStatement;
import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.js.Interpreter;
import org.fodor.browser.js.AST.structs.Value;

public class Log extends ASTNode {
    private BlockStatement blockStatement;
    private Console console;

    public Log(BlockStatement blockStatement, Console console) {
        this.blockStatement = blockStatement;
        this.console = console;
    }

    @Override
    public Value execute(Interpreter i) {
        var message = blockStatement.getLocalScope().get(new Identifier(new Token(Token.Type.IDENT, "message")));
        var value = new Value(Value.Type.String, message);
        this.console.printOutput(value);
        return value;
    }
}
