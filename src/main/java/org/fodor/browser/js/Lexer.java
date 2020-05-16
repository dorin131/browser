package org.fodor.browser.js;

import org.fodor.browser.js.AST.structs.Token;

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

    private void readChar() {
        if (readPosition >= code.length()) {
            ch = 0;
        } else {
            ch = code.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }

    private char peekChar() {
        if (readPosition >= code.length()) {
            return 0;
        }
        return code.charAt(readPosition);
    }

    public Token nextToken() {
        Token t;

        skipWhitespace();

        switch (ch) {
            case '=':
                if (peekChar() == '=') {
                    readChar();
                    t = new Token(Token.Type.EQ, "==");
                } else {
                    t = new Token(Token.Type.ASSIGN, ch);
                }
                break;
            case '<':
                if (peekChar() == '=') {
                    readChar();
                    t = new Token(Token.Type.LE, "<=");
                } else {
                    t = new Token(Token.Type.LT, ch);
                }
                break;
            case '>':
                if (peekChar() == '=') {
                    readChar();
                    t = new Token(Token.Type.GE, ">=");
                } else {
                    t = new Token(Token.Type.GT, ch);
                }
                break;
            case '!':
                if (peekChar() == '=') {
                    readChar();
                    t = new Token(Token.Type.NEQ, "!=");
                } else {
                    t = new Token(Token.Type.BANG, ch);
                }
                break;
            case ':':
                t = new Token(Token.Type.COLON, ch);
                break;
            case ';':
                t = new Token(Token.Type.SEMICOLON, ch);
                break;
            case '(':
                t = new Token(Token.Type.LPAREN, ch);
                break;
            case ')':
                t = new Token(Token.Type.RPAREN, ch);
                break;
            case ',':
                t = new Token(Token.Type.COMMA, ch);
                break;
            case '+':
                t = new Token(Token.Type.PLUS, ch);
                break;
            case '-':
                t = new Token(Token.Type.MINUS, ch);
                break;
            case '*':
                t = new Token(Token.Type.ASTERISK, ch);
                break;
            case '/':
                t = new Token(Token.Type.SLASH, ch);
                break;
            case '{':
                t = new Token(Token.Type.LBRACE, ch);
                break;
            case '}':
                t = new Token(Token.Type.RBRACE, ch);
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
                    return new Token(Token.Type.NUM, readNumber());
                }
                t = new Token(Token.Type.ILLEGAL, ch);
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
        while (this.isLetter(ch) || this.isDigit(ch)) {
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
