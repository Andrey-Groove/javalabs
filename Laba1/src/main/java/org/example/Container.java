package org.example;

public class Container { private final int[] intnum;  // массив целых чисел
    private final int size;
    private static final int DEFAULT_CAPACITY = 5; // final чтобы создать константу, static- копия переменной для всего класса

    //  контейнер по умолчанию
    public Container() {
        this(DEFAULT_CAPACITY);
    }

    public Container(int intCapacity) {
        intnum = new int[intCapacity];
        size = 0;
    }
}
