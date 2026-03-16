package chuwa3926.Coding.hw3;

import java.util.ArrayList;
import java.util.List;

public class Q17 {
    static class Professor {

        private String name;
        private String specialization;

        public Professor(String name, String specialization) {
            this.name = name;
            this.specialization = specialization;
        }

        public String toString() {
            return name + " (" + specialization + ")";
        }
    }
    static class Department {

        private String name;
        private String building;

        public Department(String name, String building) {
            this.name = name;
            this.building = building;
        }

        public String toString() {
            return name + " - " + building;
        }
    }
    static class University {

        private String name;
        private List<Department> departments = new ArrayList<>();
        private List<Professor> professors = new ArrayList<>();

        public University(String name) {
            this.name = name;
            departments.add(new Department("Computer Science", "Engineering Building"));
            departments.add(new Department("Mathematics", "Science Building"));
            departments.add(new Department("Physics", "Research Center"));
        }

        public void addProfessor(Professor p) {
            professors.add(p);
        }

        public void listProfessors() {

            System.out.println("Professors at " + name + ":");
            for (Professor p : professors) {
                System.out.println(p);
            }
        }
    }
    public static void main(String[] args) {

        Professor p1 = new Professor("Alice", "AI");
        Professor p2 = new Professor("Bob", "Data Science");

        University uni = new University("Tech University");

        uni.addProfessor(p1);
        uni.addProfessor(p2);

        uni.listProfessors();

        uni = null;

        System.out.println("\nProfessors still exist:");
        System.out.println(p1);
        System.out.println(p2);
    }
}
