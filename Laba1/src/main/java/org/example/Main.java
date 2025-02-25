package org.example;

public class Main {
     public static void main(String[] args){
         Container container = new Container();

         container.add(10);
         container.add(20);
         container.add(30);

         System.out.println("Содержимое контейнера: " + container);
         System.out.println("Размер контейнера: " + container.size());

         System.out.println("Элемент по индексу 1: " + container.get(1));

         container.remove(1);
         System.out.println("Содержимое контейнера после удаление 2 элемента : " + container);
         System.out.println("Размер контейнера: " + container.size());
     }
}
