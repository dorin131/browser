package org.fodor.browser.window;

import org.fodor.browser.gui.components.Console;
import org.fodor.browser.js.AST.expressions.Identifier;
import org.fodor.browser.js.AST.expressions.ObjectExpression;
import org.fodor.browser.js.AST.statements.BlockStatement;
import org.fodor.browser.js.AST.statements.FunctionDeclaration;
import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.js.Interpreter;
import org.fodor.browser.js.AST.structs.Value;
import org.fodor.browser.window.console.Log;

import java.util.ArrayList;

public class ConsoleObject extends ObjectExpression {
   public ConsoleObject(Console console) {
        var blockStatement = new BlockStatement();
        blockStatement.append(new Log(blockStatement, console));

        var parameters = new ArrayList<Identifier>();
        parameters.add(new Identifier(new Token(Token.Type.IDENT, "message")));

        var log = new FunctionDeclaration(blockStatement, parameters);

        this.put(new Identifier(new Token(Token.Type.IDENT, "log")), log);
    }
}
