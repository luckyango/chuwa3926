package chuwa3926.Coding.hw4;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Q11 {
    public interface OrderProcessor {
        default BigDecimal calculateTotal(Order order){
            return order.getItems().stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        public static String formatPrice(BigDecimal price){
            return "$"+price;
        }
        public abstract void processOrder(Order order);
    }
    static class Product{
        String id; String name; private BigDecimal price; private String category;
        boolean available;
        public Product(String name, BigDecimal price, String category) {
            this.name = name; this.price = price; this.category = category;
        }
        public BigDecimal getPrice(){
            return price;
        }
        public String getCategory(){
            return category;
        }
        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + "}";
        }
    }
    static class Order{
        String orderId; LocalDateTime orderDate; 
        private List<Product> items;
        String customerEmail;
        public Order(List<Product> items) { this.items = items; }
        public List<Product> getItems(){
            return items;
        }
        @Override
        public String toString() {
            return "Order{id='" + orderId + "', totalItems=" + items.size() + "}";
        }
    }
    static class OrderService implements OrderProcessor{
        List<Order> filterOrders(List<Order> orders, Predicate<Order> condition){
            return orders.stream().filter(condition).collect(Collectors.toList());
        }
        Map<String, List<Order>> groupOrdersByCategory(List<Order> orders){
            return orders.stream().collect(Collectors.groupingBy(o->o.getItems().get(0).getCategory()));
        }
        Optional<Order> findMostExpensiveOrder(List<Order> orders){
            return orders.stream().max(Comparator.comparing(this::calculateTotal));
        }
        @Override
        public void processOrder(Order order){

        }
    }
    public static void main(String[] args) {
        Product p1 = new Product("Laptop", new BigDecimal("1200"), "Electronics");
        Product p2 = new Product("Mouse", new BigDecimal("20"), "Electronics");
        Product p3 = new Product("Book", new BigDecimal("15"), "Education");

        Order o1 = new Order(Arrays.asList(p1, p2));
        Order o2 = new Order(Arrays.asList(p3));
        List<Order> orders = Arrays.asList(o1, o2);

        OrderService os = new OrderService();
        // 1. filter
        System.out.println("-----filter-----");
        BigDecimal threshold = new BigDecimal(100);
        Predicate<Order> predicate = o->os.calculateTotal(o).compareTo(threshold)>0;
        List<Order> filteredOrder = os.filterOrders(orders, predicate);
        filteredOrder.stream().forEach(System.out::println);
        //2. group
        System.out.println("-----group-----");
        Map<String, List<Order>> groupedOrders = os.groupOrdersByCategory(orders);
        groupedOrders.forEach((category, orderList) -> System.out.println(category + ": " + orderList));
        // 3.find the most expensive order
        System.out.println("-----Most expensive total-----");
        Optional<Order> mostExpOrder = os.findMostExpensiveOrder(orders);
        mostExpOrder.ifPresent(o -> 
            System.out.println("Most expensive total: " + OrderProcessor.formatPrice(os.calculateTotal(o)))
        );
    }
}
