package chuwa3926.Coding.hw4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.*;

public class Q12 {
    static class Student {
        String id, name, major;
        int age;
        List<Double> scores;

        public Student(String id, String name, int age, String major, List<Double> scores) {
            this.id = id; this.name = name; this.age = age;
            this.major = major; this.scores = scores;
        }

        public List<Double> getScores() { return scores; }
        public String getMajor() { return major; }
        public String getName() { return name; }
        @Override
        public String toString() {
            return String.format("Student{name='%s', major='%s', avg=%.2f}", 
                                name, major, StudentAnalyzer.avg(this));
        }
    }
    static class StudentAnalyzer{
        private static double avg(Student s){
            return s.getScores().stream()
            .mapToDouble(Double::doubleValue)
            .average().orElse(0.0);
        }
        List<String> getTopStudentNames(List<Student> students, int n){
            return students.stream()
            .sorted((a,b)->Double.compare(avg(b), avg(a)))
            .limit(n)
            .map(Student::getName)
            .collect(Collectors.toList());
        }
        Map<String, Double> getAverageScoreByMajor(List<Student> students){
            return students.stream()
            .collect(Collectors.groupingBy(
                Student::getMajor,Collectors.averagingDouble(StudentAnalyzer::avg)));
        }
        Optional<Student> findStudentWithHighestSingleScore(List<Student> students){
            return students.stream()
            .max(Comparator.comparing(
                s->s.getScores().stream().max(Double::compare).orElse(0.0)
            ));
        }
        public List<Student> getStudentsAboveAverageInMajor(List<Student> students, String major) {
            double avgMajor = students.stream()
                .filter(s -> s.getMajor().equals(major))
                .mapToDouble(StudentAnalyzer::avg)
                .average().orElse(0.0);

            return students.stream()
                .filter(s -> s.getMajor().equals(major) && Double.compare(avg(s), avgMajor)>0)
                .collect(Collectors.toList());
        }

        public Map<Boolean, List<Student>> partitionByPassFail(List<Student> students, double passingScore) {
            return students.stream()
            .collect(Collectors.partitioningBy(s->avg(s) >= passingScore));
        }
    }
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("1", "Alice", 20, "CS", Arrays.asList(95.0, 88.0, 92.0)),
            new Student("2", "Bob", 21, "CS", Arrays.asList(70.0, 65.0, 72.0)),
            new Student("3", "Charlie", 22, "Math", Arrays.asList(100.0, 98.0)),
            new Student("4", "David", 20, "Math", Arrays.asList(60.0, 55.0)),
            new Student("5", "Eve", 23, "Art", Arrays.asList(85.0, 90.0)),
            new Student("6", "Frank", 21, "Art", Arrays.asList(40.0, 50.0))
        );

        StudentAnalyzer analyzer = new StudentAnalyzer();

        System.out.println("--- 1. Top 3 Students ---");
        System.out.println(analyzer.getTopStudentNames(students, 3));

        System.out.println("\n--- 2. Average Score By Major ---");
        analyzer.getAverageScoreByMajor(students).forEach((major, avg) -> 
            System.out.printf("%s: %.2f\n", major, avg));

        System.out.println("\n--- 3. Student with Highest Single Score ---");
        analyzer.findStudentWithHighestSingleScore(students).ifPresent(s -> 
            System.out.println("Highest single score belongs to: " + s.getName()));

        System.out.println("\n--- 4. Students Above Average in CS ---");
        analyzer.getStudentsAboveAverageInMajor(students, "CS").forEach(System.out::println);

        System.out.println("\n--- 5. Pass/Fail Partition (Pass >= 60) ---");
        Map<Boolean, List<Student>> partitioned = analyzer.partitionByPassFail(students, 60.0);
        System.out.println("Passed: " + partitioned.get(true));
        System.out.println("Failed: " + partitioned.get(false));
    }
}
