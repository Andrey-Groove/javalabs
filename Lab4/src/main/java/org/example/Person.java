package org.example;
import java.time.LocalDate;




    public class Person {
        private long id;
        private String name;
        private String gender;
        private Department department;
        private double salary;
        private LocalDate birthDate;

        public Person(long id, String name, String gender, Department department, double salary, LocalDate birthDate) {
            this.id = id;
            this.name = name;
            this.gender = gender;
            this.department = department;
            this.salary = salary;
            this.birthDate = birthDate;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }


        public Department getDepartment() {
            return department;
        }

        public void setDepartment(Department department) {
            this.department = department;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        @Override
        public String toString() {
            return String.format("Person{id=%d, name='%s', gender='%s', birthDate=%s, department=%s, salary=%.2f}",
                    id, name, gender, birthDate, department != null ? department.getName() : "null", salary);
        }

    }
