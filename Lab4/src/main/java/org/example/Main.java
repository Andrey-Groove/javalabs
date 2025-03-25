package org.example;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void processCsvFromResources(String csvFilePath) throws IOException, CsvException {

        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(csvFilePath)) {
            if (in == null) {
                throw new IOException("Файл не найден: " + csvFilePath);
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(in))) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {

                    System.out.println(Arrays.toString(nextLine));
                }
            }
        }
    }

    public static void main(String[] args) {
        String csvFilePath = "foreign_names.csv";


        try {
            processCsvFromResources(csvFilePath);

        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        } catch (CsvException e) {
            System.err.println("Ошибка при чтении CSV: " + e.getMessage());
        }
    }
}