package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final int Iter = 2000;

    public static void main(String[] args) {

        System.out.println("ArrayList:");
        testList(new ArrayList<>());

        System.out.println("LinkedList:");
        testList(new LinkedList<>());

    }

    private static void testList(List<Integer> list) {
        // Добавление элементов
        long startTime = System.nanoTime();
        for (int i = 0; i < Iter; i++) {
            list.add(i);
        }
        long endTime = System.nanoTime();
        long addTime = endTime - startTime;

        // Получение элементов
        startTime = System.nanoTime();
        for (int i = 0; i < Iter; i++) {
            list.get(i % list.size());
        }
        endTime = System.nanoTime();
        long getTime = endTime - startTime;

        // Удаление элементов
        startTime = System.nanoTime();
        for (int i = Iter - 1; i >= 0; i--) {
            list.remove(i);
        }
        endTime = System.nanoTime();
        long removeTime = endTime - startTime;

        // Вывод результатов в виде таблицы
        System.out.printf("%-10s %-15s %-15s%n", "Метод", "Итерации", "Время (наносек)");
        System.out.printf("%-10s %-15d %-15d%n", "add", Iter, addTime);
        System.out.printf("%-10s %-15d %-15d%n", "get", Iter, getTime);
        System.out.printf("%-10s %-15d %-15d%n", "remove", Iter, removeTime);
    }
}