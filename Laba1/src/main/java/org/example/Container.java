package org.example;

public class Container {
    private int[] intnum;  // массив целых чисел
    private int size;
    private static final int DEFAULT_CAPACITY = 5; // final чтобы создать константу, static- копия переменной для всего класса

    //  контейнер по умолчанию
    public Container() {
        this(DEFAULT_CAPACITY);
    }
    // конструктор для задание ёмкости
    public Container(int initialCapacity) {
        intnum = new int[initialCapacity];
        size = 0;
    }
    // метод для увелечения размера массива если он заполнится
    private void ensureCapacity(){
        if (size == intnum.length) {
            int newCapacity = intnum.length * 2;
            int[] newArr = new int[newCapacity];
            // копирование данных из старого массива в новый с начальной позиции
            System.arraycopy(intnum,0, newArr, 0,size);
            intnum = newArr;
        }
    }
    // добавление в массив
    public void add(int element) {
        ensureCapacity();
        intnum[size] = element;
        size++;
    }
    private void errorCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + "находится вне заданного диапазона: " + size);
        }
    }

    public int get (int index) {
        errorCheck(index);
        return intnum[index];
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        errorCheck(index);
        for (int i = index; i < size - 1; i++) {
            intnum[i] = intnum[i+1];
        }
        intnum[size - 1] = 0; // не обязательно
        size--;
    }
}
