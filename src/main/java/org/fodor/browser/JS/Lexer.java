package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;

import java.util.*;

public class Lexer {
    private final String code;
    private int position = 0;
    private int readPosition = 0; // next character or one ahead
    private char ch;

    public Lexer(String code) {
        this.code = code;
        // loading first character
        this.readChar();
    }

    public ArrayList<Token> parse() {
        return null;
    }

    private void readChar() {
        if (readPosition >= code.length()) {
            ch = 0;
        } else {
            ch = code.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }

    public Token nextToken() {
        Token t;

        skipWhitespace();

        switch (ch) {
            case '=':
                t = new Token(Token.Type.Assign, ch);
                break;
            case ';':
                t = new Token(Token.Type.Semicolon, ch);
                break;
            case '(':
                t = new Token(Token.Type.LParen, ch);
                break;
            case ')':
                t = new Token(Token.Type.RParen, ch);
                break;
            case ',':
                t = new Token(Token.Type.Comma, ch);
                break;
            case '+':
                t = new Token(Token.Type.Plus, ch);
                break;
            case '{':
                t = new Token(Token.Type.LBrace, ch);
                break;
            case '}':
                t = new Token(Token.Type.RBrace, ch);
                break;
            case 0:
                t = new Token(Token.Type.EOF, ch);
                break;
            default:
                if (isLetter(ch)) {
                    String value = readIdentifier();
                    Token.Type type = Token.lookupIdent(value);
                    return new Token(type, value);
                } else if (isDigit(ch)) {
                    return new Token(Token.Type.Numeric, readNumber());
                }
                t = new Token(Token.Type.Illegal, ch);
        }

        readChar();
        return t;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private void skipWhitespace() {
        while (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
            readChar();
        }
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_');
    }

    private String readIdentifier() {
        int startPos = position;
        while (this.isLetter(ch)) {
            readChar();
        }
        return code.substring(startPos, position);
    }

    private String readNumber() {
        int startPos = position;
        while (isDigit(ch)) {
            readChar();
        }
        return code.substring(startPos, position);
    }
}
