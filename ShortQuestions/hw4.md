## Question 1. What is a Functional Interface? What annotation is used to mark a Functional Interface, and is this annotation mandatory? Give two examples of built-in Functional Interfaces in Java 8 and explain their abstract method signatures.
Functional interface is an interface with exactly one abstract method. It can have multiple default and static methods.
Annotation: @FunctionalInterface. It's not mandatory but recommended beacause it's easier for the compiler to detect errors.
Examples:
Interface: Predict<T>; Method: boolean test(T t); used for boolean conditions
Interface: Function<T,R>; Method: R apply(T t); take an input and returns a result

## Question 2. What will be the output of the following code? Explain why.
```
interface Greeting {
    default void sayHello() {
        System.out.println("Hello from Greeting");
    }
}
interface Farewell {
    default void sayHello() {
        System.out.println("Hello from Farewell");
    }
}
class Person implements Greeting, Farewell {
    @Override
    public void sayHello() {
        Greeting.super.sayHello();
        System.out.println("And hello from Person");
    }
}
public class Test {
    public static void main(String[] args) {
    Person p = new Person();
    p.sayHello();
 }
}

```
Output: 
Hello from Greeting
And hello from Person
Beacuse:
person implements two interface with the same default method. So we need to override the default method to resolve the conflict. The sayHello method in class Person explicitly calls the Greeting's version.

## Question 3. Convert the following anonymous class to a lambda expression. Explain each step of the conversion process.
```
Comparator<String> comparator = new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.length() - s2.length();
    }
};
```
Answer:
STEP 0: Start with Anonymous Class
STEP 1: Remove boilerplate (class declaration, method name, @Override); Keep ONLY: parameters and method body
```
Comparator<String> comparator = (String s1, String s2) -> {
        return s1.length() - s2.length();
    }
```
STEP 2: Remove parameter types 
```
Comparator<String> comparator = (s1, s2) -> {
        return s1.length() - s2.length();
    }
```
STEP 3: Single statement? Remove braces {} and 'return' keyword
```
Comparator<String> comparator = (s1, s2) -> s1.length() - s2.length();
```
## Question 4. Which of the following lambda expressions are valid? For invalid ones, explain the reason.
```
// A
Runnable r = () -> System.out.println("Running");
// B
Predicate<String> p = s -> return s.isEmpty();
// C
Function<Integer, Integer> f = x -> { x * 2; };
// D
Consumer<String> c = (String s) -> System.out.println(s);
// E
BiFunction<Integer, Integer, Integer> bi = (a, b) -> a + b;
// F
Supplier<String> sup = () -> { return "Hello"; };
```
A: valid
B: invalid. should remove "return" keyword. It's a single statement. Predicate<String> p = s ->  s.isEmpty();
C: invalid. should add "return" keyword. Function<Integer, Integer> f = x -> { return x * 2; };
D: valid. It's better for Consumer<String> c = s -> System.out.println(s);
E: valid 
F: valid
## Question 5. Match each lambda expression with its corresponding method reference. Explain the type of each method reference (Static, Bound Instance, Unbound Instance, or Constructor).
```
// Lambda expressions:
// 1. x -> System.out.println(x)
** bound instance **
// 2. s -> s.toUpperCase()
** unbound instance **
// 3. x -> Math.abs(x)
** static** 
// 4. () -> new ArrayList<>()
** constructor** 
// 5. (s1, s2) -> s1.compareTo(s2)
**Unbound Instance**
// Method references:
// A. ArrayList::new
// B. System.out::println
// C. Math::abs
// D. String::toUpperCase
// E. String::compareTo
```
static: Lambda calls a static method of a class
bound instance: Lambda calls a method on a specific object (already exists)
Unbound Instance: Lambda calls a method on the parameter itself (no pre-existing object)
## Question 6. What is the difference between Optional.of() and Optional.ofNullable()? What will happen when executing the following code?
```
String value = null;
Optional<String> opt1 = Optional.of(value);
Optional<String> opt2 = Optional.ofNullable(value);
System.out.println(opt1.isPresent());
System.out.println(opt2.isPresent());
```
Optional: a container class that may or may not contain a non-null value. Used to solve the NullPointerException problem.
Optional.of(value) - value is guaranteed to be non-null. If it's null -> throw NPE
Optional.ofNullable(value) - value might be null. If value is null -> return empty Optional
Output: the code will throw NullPointerException when executing 'Optional<String> opt1 = Optional.of(value);'. Because Optional.of() is strict. It doesnt allow null value and throw an exception immediately if the input is null.
## Question 7. What will be the output of the following code? Explain the difference between orElse() and orElseGet().
```
public class Test {
    public static String createDefault() {
        System.out.println("Creating default value");
        return "Default";
    }
    public static void main(String[] args) {
        Optional<String> opt = Optional.of("Hello");
        System.out.println("--- Using orElse ---");
        String result1 = opt.orElse(createDefault());
        System.out.println("Result: " + result1);
        System.out.println("--- Using orElseGet ---");
        String result2 = opt.orElseGet(() -> createDefault());
        System.out.println("Result: " + result2);
    }
}
```
OrElse(default): Always execute. The evaluation type is eager. The createDefault method is executed regardless of whether opt is empty or not. When value is present -> return value; Default is ALWAYS evaluated, even if Optional has value
OrElseGet(Supplier): The code inside the lambda is only executed if and only if optional is emtpy. when value is present -> return value; when value is absent -> call supplier.
Output:
--- Using orElse ---
Creating default value
Result: Hello
--- Using orElseGet ---
 Result: Hello
## Question 8. Explain the difference between map() and flatMap() in Stream API. Given the following class structure, write code to get a list of all product names from all orders.
```
class Order {
    private List<Product> products;
    public List<Product> getProducts() { return products; }
}
class Product {
    private String name;
    public String getName() { return name; }
}
// Given: List<Order> orders
// Write code to get: List<String> allProductNames
```
map(): one to one. transform one element in the stream into excatly one other element
flatMap(): one to many. transform one element into a stream of other elements and then flattens all those individual streams into a single, continuous stream
Code:
List<String> allProductNames = 
orders.stream()
.flatMap(order->order.getProducts().stream())
.map(Product::getName())
.collect(Collectors.toList());

## Question 9. What will be the output of the following code? Explain the concept of lazy evaluation in Stream API.
```
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Stream<Integer> stream = numbers.stream()
    .filter(n -> {
    System.out.println("Filtering: " + n);
    return n % 2 == 0;
    })
    .map(n -> {
    System.out.println("Mapping: " + n);
    return n * 10;
    });
System.out.println("Stream created");
System.out.println("Calling findFirst...");
Optional<Integer> result = stream.findFirst();
System.out.println("Result: " + result.orElse(-1));
```
Output:
Stream created
Calling findFirst...
Filtering: 1
Filtering: 2
Mapping: 2
Result: 20
Because:
stream is lazy. The operations only run when the terminal operation is called. And it stops after the first match.


## Question 10. Analyze the following code. What is wrong with it? How would you fix it?
```
public class ProductService {
 public void processProducts(Optional<List<Product>> productsOpt) {
    if (productsOpt.isPresent()) {
        List<Product> products = productsOpt.get();
        for (Product p : products) {
            process(p);
        }
    }
 }
 public Optional<BigDecimal> calculateTotal(List<Product> products) {
    if (products == null || products.isEmpty()) {
        return null;
    }
    BigDecimal total = products.stream()
    .map(Product::getPrice)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
    return Optional.of(total);
 }
}
```
1.Passing Optional as a parameter. Use Optional as a return type. Optional is intended for return types, not method parameters.
2.Returning null instead of Optional.empty(). The caller expects an Optional box specifically so they don't have to check for null
3.use .isPresent() followed by .get(). It doesnt leverage the functional power of Optional.
```
public class ProductService {

    // Fix 1: Accept a plain List. If it might be null, handle it inside or via the caller.
    public void processProducts(List<Product> products) {
        // Use Optional.ofNullable to handle null safely, then use ifPresent for a functional approach
        Optional.ofNullable(products)
            .ifPresent(list -> list.forEach(this::process));
    }

    public Optional<BigDecimal> calculateTotal(List<Product> products) {
        // Fix 2: If the result is missing, return Optional.empty(), NEVER null.
        if (products == null || products.isEmpty()) {
            return Optional.empty();
        }
        
        BigDecimal total = products.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        return Optional.of(total);
    }
}
```