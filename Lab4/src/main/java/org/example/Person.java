package org.example;
import java.time.LocalDate;




    public class Person {
        private int id;
        private String name;
        private String gender;
        private Department department;
        private double salary;
        private LocalDate birthDate;

        public Person(int id, String name, String gender, Department department, double salary, LocalDate birthDate) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.department = department;
            this.salary = salary;
            this.birthDate = birthDate;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public Department getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

    }
