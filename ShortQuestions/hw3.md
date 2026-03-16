
## Question 1. Draw and explain the Java Exception Hierarchy. What are the two main branches under Throwable? Give two examples for each branch
Java Exception Hierarchy:
                    Throwable
            /                       \
        Error                        Exception
       /  |  \                       /        \
      1   2   3     (checked exceptions)     RuntimeException
        (not a class, 456 extend Exception)  (789 extend RuntimeException)
                         /  |  \               /  |  \
                        4   5   6             7   8   9
1: StackOverflowError
2：OutOfMemoryError
3: VirtualMachineError
4: IOException     5:SQLException     6:FileNotFountException
7: NullPointerException      8: IndexOutOfBounds      9:ClassCast

##  Question 2. What is the difference between Checked Exception and Unchecked Exception? Which one does the compiler force you to handle? Give two examples of each type.
Checked Exception：Checked at compile time. Must be handled by compiler. Parent class is Exception. Examples: IOException, SQLException
Unchecked Exception: Occurs at run time. Parent class is RuntimeException. Examples: NullPointerException, ArithmeticException.

## Question 3. What will be the output of the following code? Explain why.
```
public class ExceptionTest {
    public static int test() {
        try {
            System.out.println("try");
            return 1;
        } catch (Exception e) {
            System.out.println("catch");
            return 2;
        } finally {
            System.out.println("finally");
            return 3;
        }
    }
    public static void main(String[] args) {
        System.out.println(test());
    }
}
```
Output: 
try
finally
3
Because: Firstly, it executes try block and prints "try" and the current return value is 1; Then, it executes finally block and prints "finally" and the return value '3' in finally block overrides the previous return value.

## Question 4. What is the difference between throw and throws? Write a short code example demonstrating both.
Throw: Used in a method body to explicitly throw an exception. Throw only one exception.
Throws: Used in the method signature to show which exceptions it may throw. Can decclare multiple exceptions.
```
public void withdraw(int amount){
    if(amount < 0){
        throw new IllegalArgumentException("Amount cant be negative");
    }
}
```
```
public void readFile(String fileName) throws IOException{
    FileReader file = new FileReader(fileName);
}
```

## Question 5. What will happen when you try to compile and run this code?
```
public class Test {
 public static void main(String[] args) {
    try {
        throw new RuntimeException("Error 1");
    } catch (Exception e) {
        throw new RuntimeException("Error 2");
    } finally {
        throw new RuntimeException("Error 3");
    }
 }
}
```
It will throw "Error 3". 
Because: try block will throw "Error 1". Then catch block will capture the error and throws "Error 2". Then finally block will throw "Error 3" which overrides all the previous exceptions.

## Question 6. Can an Enum extend another class? Can an Enum implement an interface? Explain why
Enum cant extend another class because it implicitly extends java.lang.enum class.
It can implement other interfaces.

## Question 7. What is the output of the following code?
```
enum Status {
    PENDING(0),
    PROCESSING(1),
    COMPLETED(2);
    private int code;
    Status(int code) {
    this.code = code;
 }
 public int getCode() {
    return code;
 }
}
public class EnumTest {
 public static void main(String[] args) {
    for (Status s : Status.values()) {
    System.out.println(s.name() + " -> " + s.getCode() + " -> " + s.ordinal());
 }
 }
}

```
Outout:
PENDING -> 0 -> 0
PROCESSING -> 1 -> 1
COMPLETED -> 2 -> 2
ordinal() returns the position of enum constant starting from 0

## Question 8. What is the difference between Aggregation and Composition? Complete the table below:
| Aspect                   | Aggregation                | Composition                   |
| ------------------------ | -------------------------- | ----------------------------- |
| Relationship Type        | Weak "has-a" relationship  | Strong "part-of" relationship |
| Lifecycle                | Independent                | Dependent                     |
| Object Creation Location | Created outside the parent | Created inside the parent     |
| UML Symbol               | Hollow diamond             | Filled diamond                |
| Example                  | University has Professors  | Computer has CPU              |
## Question 9. Look at the following code. Is the relationship between Library and Book an Aggregation or Composition? Explain your reasoning.
```
class Book {
    private String title;
    public Book(String title) { this.title = title; }
}
class Library {
    private List<Book> books = new ArrayList<>();
    public void addBook(Book book) {
    books.add(book);
 }
}
// Usage
Book b1 = new Book("Java Programming");
Library lib = new Library();
lib.addBook(b1);
```
It's aggregation. Because library and book's life cycle are independant. And the relationship is "library has books". The book object is created outside the library class and then added.

## Question 10. List the three key elements of the Singleton pattern. Why must the constructor be private?
1. private constructor: prevent other classes from creating instances
2. private static final instance variable: hold the single instance
3. public static getInstance() method: provide global access to the instance

## Question 11. What is the difference between Eager Initialization Singleton and Lazy Initialization Singleton? Which one creates the instance first?
Eager Initialization Singleton: The instance is created when the class is loaded. It's thread-safe but may create the object even if it's not used.
Lazy Initialization Singleton: The instance is created only when it's first used. It saves memory but need synchronization for thread safe.
## Question 12. Is this Singleton implementation correct? If not, explain the problem and how to fix it
```
public class Singleton {
 private static Singleton instance;
 public Singleton() { }
 public static Singleton getInstance() {
    if (instance == null) {
        instance = new Singleton();
    }
    return instance;
 }
}
```
1.constructor should be private 2.should make sure it's thread-safe
If two threads enter the "if (instance == null)" block at the same time and both evaluate the condition as true. They will create two separate instances which violate the principal of singleton pattern.

