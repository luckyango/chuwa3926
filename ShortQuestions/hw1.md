# Conceptual Questions

## Question 1. What is the difference between JDK, JRE, and JVM? Which one do you need to run a compiled Java program?
JVM (Java Virtual Machine): it's the runtime engine to execute bytecode. It converts bytecode to machine code so the could run on different platforms.
JRE (Java runtime environment): includes JVM and Java libraries to run Java applications.
JDK (Java Development Kit): includs JRE and Java development tools (like compiler).
JRE is needed to run a compiles Java program.

## Question 2. Given the following code, what will be printed and why?
```
int a = 10;
int b = a;
b = 20;
System.out.println(a);
```
It will print 10.
Because primitive types are passed by value in Java. int b receives the copy of a, so a will not be affected when you change b.

## Question 3.Given the following code, what will be printed and why?
```
int[] arr1 = {1, 2, 3};
int[] arr2 = arr1;
arr2[0] = 100;
System.out.println(arr1[0]);
```
It will print 100.
The array is reference type. arr1 and arr2 refer to the same array. when arr2 changes the first element, the underlying array is changed. so arr1 is updated.

## Question 4. What is the output of the following code? Explain your answer.
```
String s1 = "hello";
String s2 = "hello";
String s3 = new String("hello");
System.out.println(s1 == s2);
System.out.println(s1 == s3);
System.out.println(s1.equals(s3));
```
It will print: true; false; true
s1 and s2 refer to the same object in the string pool. s3 refers to a new object created in heap memory.
The address of s1 and s3 is not the same, and the content of them is the same. '==' compares the address of two objetcs, 'equals' compares the content.

## Question 5. What is the difference between final variable, final method, and final class?
final variable: the field cant be modified after initialization.
final method: the method cant be overridden in subclasses.
final class: the class cant be extended.
## Question 6. What is the difference between static variable and instance variable? Give a simple example of when you would use a static variable.
static variable: variable belongs to a class and is shared across all the objects.
instance variable: each object has its own copy and the variable belongs to an instance.
you can use a static variable when you want to count how many objects are created.
class Student{
    private static int count = 0;
}
## Question 7. What will happen when you try to compile and run this code?
```
public class Test {
    public static void main(String[] args) {
        final int x = 10;
        x=20;
        System.out.println(x);
    }
}
```
You will fail compiling the code. Because you cant change the final variable after initializing it.
## Question 8. List the four pillars of Object-Oriented Programming and briefly explain each one in one sentence.
Encapsulation: bundling the fields and methods in a class and restrict the access to data.
Inheritance: one class could inherit fields and methods from another class.
Polymophism: one method could behave differently depending on the onject.
Abstraction: hide the implementation details and only expose the necessary features.
## Question 9. What is encapsulation? Why do we make instance variables private and provide public getter/setter methods?
Encapsulation bundles fields and methods so we can restrict the access to the data. It's the practice of keeping the fields private and providing controlled access through public methods. 
In this case, we could prevent the data from unintended modification and validate before updating values.
## Question 10. What is the output of the following code?
```
public class Counter {
 static int count = 0;
 public Counter() {
 count++;
 }
 public static void main(String[] args) {
 Counter c1 = new Counter();
 Counter c2 = new Counter();
 Counter c3 = new Counter();
 System.out.println(Counter.count);
 }
}
```
Output is 3.
because count is a static variable and shared across all the objects. So it will be incremented by one every time the constuctor is called.
