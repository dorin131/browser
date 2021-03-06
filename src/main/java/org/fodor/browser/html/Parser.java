package org.fodor.browser.html;

import org.fodor.browser.html.elements.*;
import org.fodor.browser.html.stucts.Token;

import java.util.HashMap;
import java.util.Stack;

public class Parser {
    DOM dom;
    Tokenizer tokenizer;
    Token currentToken;
    Stack<Element> elementStack = new Stack<>();

    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public DOM parse() {
        dom = new DOM();
        elementStack.push(dom);
        nextToken();

        while (currentToken.getType() != Token.Type.EOF) {
            parseToken();
        }
        return dom;
    }

    private void nextToken() {
        currentToken = tokenizer.next();
    }

    private void parseToken() {
        switch (currentToken.getType()) {
            case TEXT:
                parseText();
                break;
            case OPEN_TAG:
                parseOpenTag();
                break;
            case CLOSE_TAG:
                parseCloseTag();
                break;
            case ILLEGAL:
                //System.out.println("Parser error: " + currentToken.getContent());
                nextToken();
            case EOF:
            default:
        }
    }

    private void parseText() {
        elementStack.peek().appendChild(new TextElement(currentToken.getContent()));
        nextToken();
    }

    private void parseOpenTag() {
        switch (currentToken.getContent()) {
            case "div":
                pushToElementStack(new DivElement(getTagAttributes()));
                break;
            case "script":
                pushToElementStack(new ScriptContentElement(getTagContent()));
                break;
            default:
        }
        nextToken();
    }

    private void parseCloseTag() {
        switch (currentToken.getContent()) {
            case "div":
                popOffStackIfMatchesType(ElementType.DIV);
                break;
            default:
        }
        nextToken();
    }

    private void popOffStackIfMatchesType(ElementType type) {
        var lastElement = elementStack.peek();
        if (lastElement.getType() == type) {
            elementStack.pop();
        }
    }

    private void pushToElementStack(Element el) {
        var lastElement = elementStack.peek();
        var newElement = el;
        lastElement.appendChild(newElement);
        elementStack.push(newElement);
    }

    private String getTagContent() {
        nextToken();
        return currentToken.getContent();
    }

    private HashMap<String, String> getTagAttributes() {
        return currentToken.getAttributes();
    }
}
