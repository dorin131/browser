package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Lexer {
    private String code;
    private List<Character> specialChars = Arrays.asList(';', '(', ')', '{', '}', '+', '-', '/', '%', '=', '*', '<', '>');
    private List<Character> ignoredChars = Arrays.asList(' ', '\n', '\t');

    public Lexer(String code) {
        this.code = code;
    }

    public ArrayList<Token> parse() {
        ArrayList<Token> result = new ArrayList<>();

        String tokenValue = "";

        for (int i = 0; i < code.length(); i++) {
            char current = code.charAt(i);
            char next = ' ';
            if (i < code.length() - 1) {
                next = code.charAt(i+1);
            }
            if (specialChars.contains(current)) {
                result.add(new Token(getType(Character.toString(current)), Character.toString(current)));
                tokenValue = "";
                continue;
            }
            if (ignoredChars.contains(current)) {
                tokenValue = "";
                continue;
            }
            if (isAlphanumeric(current)) {
                tokenValue += current;
                if (isAlphanumeric(next)) {
                    continue;
                } else {
                    Token.Type type = getType(tokenValue);
                    result.add(new Token(type, tokenValue));
                    tokenValue = "";
                    continue;
                }
            }
            throw new RuntimeException("Unknown character found " + current);
        }

        return result;
    }

    private Token.Type getType(String s) {
        try {
            Integer.parseInt(s);
            return Token.Type.Numeric;
        } catch (NumberFormatException e) {
            // not a number
        }

        switch (s) {
            case "function":
            case "return":
                return Token.Type.Keyword;
            case "(":
            case ")":
            case "{":
            case "}":
            case ";":
            case "+":
            case "-":
            case "/":
            case "*":
            case "%":
                return Token.Type.Punctuator;
            default:
                return Token.Type.Identifier;
        }
    }

    private boolean isAlphanumeric(char c) {
        Pattern alphanumeric = Pattern.compile("^[a-zA-Z0-9]+$");
        return alphanumeric.matcher("" + c).matches();
    }
}
