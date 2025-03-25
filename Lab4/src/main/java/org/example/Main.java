package org.example;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String csvFilePath = "foreign_names.csv"; // Путь к файлу в ресурсах
        char separator = ';'; // Разделитель

        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(csvFilePath);
             CSVReader reader = (in == null) ? null : new CSVReader(new InputStreamReader(in), separator)) {

            if (reader == null) {
                throw new FileNotFoundException("Файл не найден: " + csvFilePath);
            }

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Выводим каждую строку на экран
                System.out.println(Arrays.toString(nextLine));
            }

        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: Файл не найден: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}