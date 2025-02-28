package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    private Map<String, Double> variables = new HashMap<>();

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true; // Является числом
        } catch (NumberFormatException e) {
            return false; // Не является числом
        }
    }


    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^"); // Проверяем, является ли токен одним из поддерживаемых операторов
    }


    private boolean isVariable(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9]*"); // Проверяем, начинается ли токен с буквы и состоит из букв и цифр
    }


    private boolean isFunction(String token) {
        return token.equals("sqrt"); // Проверяем, является ли токен функцией "sqrt"
    }


    private double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2; // Сложение
            case "-":
                return operand1 - operand2; // Вычитание
            case "*":
                return operand1 * operand2; // Умножение
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль"); // Деление на ноль
                }
                return operand1 / operand2;
            case "^":
                return Math.pow(operand1, operand2); // Возведение в степень
            default:
                throw new IllegalArgumentException("Неподдерживаемый оператор: " + operator);
        }
    }
}
