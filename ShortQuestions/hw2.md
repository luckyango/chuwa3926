# Conceptual Questions

## Question 1. What is the difference between method overloading and method overriding? In which type of polymorphism does each belong?
Overloading happens in the same class. The method has the same name but different parameters. It's compile-time polymorphism.
Overriding happens in subclasses. Child class could provide different implementaion of a parent method. It's runtime polymorphism.

## Question 2. What will be the output of the following code? Explain why
```
class Animal {
 public void makeSound() {
 System.out.println("Some sound");
 }
}
class Dog extends Animal {
 @Override
 public void makeSound() {
 System.out.println("Bark!");
 }
}
public class Test {
 public static void main(String[] args) {
 Animal a = new Dog();
 a.makeSound();
 }
}
```
Output:
Bark!
The reference type is Animal, and the actual type is Dog. So in runtime, the method called is from the actual type (Dog).

## Question 3. Why does Java NOT support multiple inheritance with classes? What is the "Diamond Problem"? How does Java solve this issue?
Prevent inheriting the same methods from different classes (diamond problem).
Diamond problem: class B and C inherit the same parent class A. And the new class D inherits class B and C. As class B and C overrides the same method, Java doesn't know which to use in the new class D.
How to solve: 1. allow multiple inheritance with interfaces. 2. Use methods with explicit override

## Question 4. What will happen when you try to compile and run the following code? Explain your answer.
```
abstract class Shape {
 public abstract double getArea();
}
public class Test {
 public static void main(String[] args) {
 Shape s = new Shape();
 System.out.println(s.getArea());
 }
}
```
Compile will fail. Because abstract class cant be instantiated.

## Question 5. What is the difference between an abstract class and an interface? Give one scenario where you would prefer using an interface over an abstract class.
Abstract class: 1. can have fields and constructor 2. can have abstract methods and concrete methods 3.only single inheritance
Interface: 1. only define method contract 2. supports multiple inheritance
One scenarios prefering interface: when multiple unrelated classes want the same capability

## Question 6. What will be the output of the following code? Explain the concept of upcasting and downcasting
```
class Animal {
 public void eat() {
 System.out.println("Animal eating");
 }
}
class Cat extends Animal {
 public void meow() {
 System.out.println("Meow!");
 }
}
public class Test {
 public static void main(String[] args) {
 Animal a = new Cat(); // Line 1
 a.eat(); // Line 2
 // a.meow(); // Line 3 (commented out)
 if (a instanceof Cat) {
 Cat c = (Cat) a; // Line 4
 c.meow(); // Line 5
 }
 }
}
```
output:
Animal eating
Meow!
Upcasting:  Animal a = new Cat(); convert a subclass object to a superclass reference
Downcasting: Cat c = (Cat) a; convert a superclass reference to a subclass reference


## Question 7. What are the rules for overriding the equals() method? Why must we also override hashCode() when we override equals()
Rules for overriding: (1)reflexive: x.equals(x)==true; (2)symmetric: x.equals(y)==y.equals(x); (3)Transitive: x.equals(y)==true, z.equals(y)==true->x.equals(y)==true (4)Consistent: multiple calls to equals must return the same reuslt as long as the object doesnt change  （5）x.equals(null)==false
If two objects are equal according to equals(), they must return the same hashcode.


## Question 8. What is the difference between shallow copy and deep copy? Given an object Person with a field Address address, explain what happens to the address field in each type of copy.
Shallow copy: It copies object fields as references.
Deep copy: It creates new copies of all referenced objects.
For person with a field Address address, let's say p1 -> addr, if shallow copy -> p2 will refer to the same Address object as p1; if deep copy, p2 will create a new copy, p2->addr2

## Question 9. What will be the output of the following code? Explain why.
```
interface Flyable {
 default void takeOff() {
 System.out.println("Taking off from Flyable");
 }
}
interface Swimmable {
 default void takeOff() {
 System.out.println("Diving in from Swimmable");
  }
}
class Duck implements Flyable, Swimmable {
 @Override
 public void takeOff() {
 Flyable.super.takeOff();
 }
}
public class Test {
 public static void main(String[] args) {
 Duck d = new Duck();
 d.takeOff();
 }
}
```
Output:
Taking off from Flyable.
The two interfaces define the same contract and causes a conflict. The implenmeting class must override the method and explictly selects the specific implementation.

## Question 10.  Consider the following code. Which methods are valid overloads of calculate(int a, int b)? Which are NOT valid and why?
```
public class Calculator {
    public int calculate(int a, int b) {
    return a + b;
    }
    // Method A
    public int calculate(int a, int b, int c) {
    return a + b + c;
    }
    // Method B
    public double calculate(double a, double b) {
    return a + b;
    }
    // Method C
    public double calculate(int a, int b) {
    return (double)(a + b);
    }
    // Method D
    private int calculate(int x, int y) {
    return x * y;
    }
}
```
Valid overloads: Method A; Method B
Invalid overloads: Method C - because only the return type differs, which cant distinguish overloaded methods ; Method D - only the parameter names differ, but the parameter types stay the same.
