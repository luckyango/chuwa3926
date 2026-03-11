
class Student{
    private String name; private int age; private double grade;
    public Student(String name, int age, double grade){
        this.name = name;         
        setAge(age);
        setGrade(grade);
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public double getGrade(){
        return grade;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        if(age >= 1 && age <= 150) this.age = age;
    }
    public void setGrade(double grade){
        if(grade >= 0.0 && grade <= 100.0 ) this.grade = grade;
    }
    public static void main(String[] args){
        Student stu = new Student("shu",20,70.8);
        System.out.println("A new student joins: name: " + stu.getName() + "; age: " + stu.getAge() + "; grade: "+stu.getGrade());
        stu.setName("yang");
        System.out.println("The student's name changes to " + stu.getName());
        stu.setAge(151);
        System.out.println("Try to set an invalid age (151) but will fail, and the current age is still "+stu.getAge());
        stu.setAge(22);
        System.out.println("Try to set a valid age, so the current age becomes "+stu.getAge());
        stu.setGrade(101.0);
        System.out.println("Try to set an invalid grade (101.0) but will fail, and the current grade is still "+stu.getGrade());
        stu.setGrade(88.6);
        System.out.println("Try to set a valid grade, so the current grade becomes to "+stu.getGrade());
    }
}
