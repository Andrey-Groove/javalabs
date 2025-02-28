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


}
