package org.example;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVReaderExample {
    private static final char SEPARATOR = ';';

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final Map<String, Department> departmentMap = new HashMap<>();


    public static List<Person> readPeopleFromCSV(String csvFilePath) throws Exception {
        List<Person> people = new ArrayList<>();


        try (InputStream in = CSVReaderExample.class.getClassLoader().getResourceAsStream(csvFilePath)) {

            if (in == null) {
                throw new FileNotFoundException("Файл не найден: " + csvFilePath);
            }


            var csvParser = new CSVParserBuilder()
                    .withSeparator(SEPARATOR)
                    .build();


            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(in))
                    .withCSVParser(csvParser)
                    .build();


            reader.readNext();


            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {

                if (nextLine.length < 6) {

                    continue;
                }

                long id = Long.parseLong(nextLine[0]);
                String name = nextLine[1];
                String gender = nextLine[2];
                LocalDate birthDate = LocalDate.parse(nextLine[3], DATE_FORMATTER);
                String departmentName = nextLine[4];
                double salary = Double.parseDouble(nextLine[5]);


                Department department = departmentMap.get(departmentName);
                if (department == null) {
                    department = new Department(departmentName);
                    departmentMap.put(departmentName, department);
                }

                Person person = new Person(id, name, gender, department, salary, birthDate);
                people.add(person);
            }

        }
        return people;
    }
    public static void main(String[] args) {
        try {
            List<Person> people = CSVReaderExample.readPeopleFromCSV("foreign_names.csv");
            System.out.println("Считано людей: " + people.size());

            for (Person person : people) {
                System.out.println(person);
            }

        } catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        }
    }
}