package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    private Map<String, Double> variables = new HashMap<>();


    public double evaluate(String expression) {
        try {
            Queue<Token> rpnQueue = parseToRPN(expression); // Преобразуем в RPN
            return evaluateRPN(rpnQueue); // Вычисляем RPN-выражение
        } catch (Exception e) {
            System.out.println("Ошибка в выражении: " + e.getMessage());
            return Double.NaN; // Возвращаем NaN в случае ошибки
        }
    }

    // Преобразование в обратную нотацию (тоесть выражение (2 + 3) стане (2 3 +)
    private Queue<Token> parseToRPN(String expression) {
        Queue<Token> outputQueue = new LinkedList<>(); // очередь для выражения
        Stack<Token> operatorStack = new Stack<>(); // стек для операторов

        // Регулярное выражение для разбора токенов
        String regex = "(\\d+\\.?\\d*|[+\\-*/\\^\\(\\)]|[a-zA-Z][a-zA-Z0-9]*|sqrt)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String token = matcher.group().trim(); // Получаем токен из строки
            if (token.isEmpty()) continue; // Пропускаем пустые токены

            if (isNumber(token)) {
                outputQueue.add(new Token(Token.TokenType.NUMBER, token)); // Добавляем число в очередь
            } else if (isVariable(token)) {
                outputQueue.add(new Token(Token.TokenType.VARIABLE, token)); // Добавляем переменную в очередь
            } else if (isFunction(token)) {
                operatorStack.push(new Token(Token.TokenType.FUNCTION, token)); // Добавляем функцию в стек
            } else if (isOperator(token)) {
                Token operator = new Token(Token.TokenType.OPERATOR, token); // Создаем токен оператора

                // Обрабатываем приоритет операторов
                while (!operatorStack.isEmpty() &&
                        operatorStack.peek().type == Token.TokenType.OPERATOR &&
                        (operator.precedence <= operatorStack.peek().precedence)) {
                    outputQueue.add(operatorStack.pop()); // Перемещаем операторы из стека в очередь
                }
                operatorStack.push(operator); // Добавляем текущий оператор в стек
            } else if (token.equals("(")) {
                operatorStack.push(new Token(Token.TokenType.LEFT_PAREN, token)); // Добавляем левую скобку в стек
            } else if (token.equals(")")) {
                // Обрабатываем закрывающую скобку
                while (!operatorStack.isEmpty() && operatorStack.peek().type != Token.TokenType.LEFT_PAREN) {
                    outputQueue.add(operatorStack.pop()); // Перемещаем операторы из стека в очередь до левой скобки
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().type == Token.TokenType.LEFT_PAREN) {
                    operatorStack.pop(); // Удаляем левую скобку
                } else {
                    throw new IllegalArgumentException("Неправильно расставлены скобки");
                }

                if (!operatorStack.isEmpty() && operatorStack.peek().type == Token.TokenType.FUNCTION) {
                    outputQueue.add(operatorStack.pop()); // Выталкиваем функцию из стека в очередь
                }
            } else {
                throw new IllegalArgumentException("Неизвестный токен: " + token);
            }
        }

        // Перемещаем оставшиеся операторы из стека в очередь
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().type == Token.TokenType.LEFT_PAREN || operatorStack.peek().type == Token.TokenType.RIGHT_PAREN) {
                throw new IllegalArgumentException("Неправильно расставлены скобки");
            }
            outputQueue.add(operatorStack.pop());
        }

        return outputQueue; // Возвращаем очередь с RPN-выражением
    }

    // Вычисление выражения в обратной нотации
    private double evaluateRPN(Queue<Token> rpnQueue) {
        Stack<Token> valueStack = new Stack<>(); // Стек для чисел

        while (!rpnQueue.isEmpty()) {
            Token token = rpnQueue.remove(); // Извлекаем токен из очереди

            switch (token.type) {
                case NUMBER:
                    valueStack.push(token); // Добавляем число в стек
                    break;
                case VARIABLE:
                    Double value = getVariableValue(token.value); // Получаем значение переменной
                    valueStack.push(new Token(Token.TokenType.NUMBER, String.valueOf(value))); // Добавляем значение в стек
                    break;
                case OPERATOR:
                    // Выполняем операцию
                    if (valueStack.size() < 2) {
                        throw new IllegalArgumentException("Недостаточно операндов для оператора " + token.value);
                    }
                    double operand2 = Double.parseDouble(valueStack.pop().value); // Извлекаем второй операнд
                    double operand1 = Double.parseDouble(valueStack.pop().value); // Извлекаем первый операнд
                    double result = performOperation(operand1, operand2, token.value); // Выполняем операцию
                    valueStack.push(new Token(Token.TokenType.NUMBER, String.valueOf(result))); // Добавляем результат в стек
                    break;
                case FUNCTION:
                    // Применяем функцию
                    if (valueStack.isEmpty()) {
                        throw new IllegalArgumentException("Недостаточно аргументов для функции " + token.value);
                    }
                    double argument = Double.parseDouble(valueStack.pop().value); // Извлекаем аргумент
                    double functionResult = applyFunction(argument, token.value); // Применяем функцию
                    valueStack.push(new Token(Token.TokenType.NUMBER, String.valueOf(functionResult))); // Добавляем результат в стек
                    break;
                default:
                    throw new IllegalArgumentException("Неожиданный токен: " + token);
            }
        }

        // Проверяем, что в стеке остался только результат
        if (valueStack.size() != 1) {
            throw new IllegalArgumentException("Неправильное выражение");
        }

        return Double.parseDouble(valueStack.pop().value); // Возвращаем результат
    }
    // получение значений от пользователя
    private double getVariableValue(String variableName) {
        if (variables.containsKey(variableName)) {
            return variables.get(variableName); // Возвращаем значение, если оно уже известно
        }

        Scanner scanner = new Scanner(System.in);
        double value;

        while (true) {
            try {
                System.out.print("Введите значение для переменной " + variableName + ": ");
                value = Double.parseDouble(scanner.nextLine()); // Считываем значение от пользователя
                break; // Выходим из цикла, если ввод успешен
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите число.");
            }
        }

        variables.put(variableName, value); // Сохраняем значение для дальнейшего использования
        return value; // Возвращаем значение
    }

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
    // применение функций
    private double applyFunction(double argument, String functionName) {
        switch (functionName) {
            case "sqrt":
                if (argument < 0) {
                    throw new IllegalArgumentException("Нельзя извлечь квадратный корень из отрицательного числа"); // Квадратный корень из отрицательного числа
                }
                return Math.sqrt(argument); // Квадратный корень
            default:
                throw new IllegalArgumentException("Неподдерживаемая функция: " + functionName);
        }
    }
}
