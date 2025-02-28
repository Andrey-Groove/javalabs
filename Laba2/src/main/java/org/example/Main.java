package org.example;

import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        ExpressionEvaluator evaluator = new ExpressionEvaluator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String expression = scanner.nextLine();

            if (expression.equalsIgnoreCase("exit")) {
                break;
            }

            double result = evaluator.evaluate(expression);

            if (!Double.isNaN(result)) {
                System.out.println("Результат: " + result);
            }
        }

        System.out.println("Программа завершена.");
    }
}