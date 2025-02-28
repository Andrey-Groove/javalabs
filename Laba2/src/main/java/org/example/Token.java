package org.example;

public class Token {
    public enum TokenType {
        NUMBER,
        OPERATOR,
        LEFT_PAREN,
        RIGHT_PAREN,
        VARIABLE,
        FUNCTION
    }

    public TokenType type;
    public String value; // Строковое представление
    public int precedence; // Приоритет оператора

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
        this.precedence = getPrecedence(type, value);
    }
    private int getPrecedence(TokenType type, String value) {
        switch (type) {
            case OPERATOR:
                switch (value) {
                    case "+":
                    case "-":
                        return 1;
                    case "*":
                    case "/":
                        return 2;
                    case "^": // Возведение в степень
                        return 3;
                }
            case FUNCTION: //Приоритет функции.
                return 4;
            default:
                return 0;
        }
    }




}
