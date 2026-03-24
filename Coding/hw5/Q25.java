package Coding.hw5;
// 25. completable future:
// 1. Homework 1: Write a simple program that uses CompletableFuture to asynchronously get the sum
// and product of two integers, and print the results.
// 2. Homework 2: Assume there is an online store that needs to fetch data from three APIs: products,
// reviews, and inventory. Use CompletableFuture to implement this scenario and merge the fetched
// data for further processing. (需要找public api去模拟，)
// 1. Sign In to Developer.BestBuy.com
// 2. Best Buy Developer API Documentation (bestbuyapis.github.io)
// 3. 可以⽤fake api https://jsonplaceholder.typicode.com/
// 4. Github public api: https://api.github.com/users/your-user-name/repos
// 3. Homework 3: For Homework 2, implement exception handling. If an exception occurs during any API
// call, return a default value and log the exception information.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class Q25 {

    public static void main(String[] args) {

        // Homework 1
        System.out.println("=== HW1 ===");

        CompletableFuture<Integer> sum =
                CompletableFuture.supplyAsync(() -> 2 + 3);

        CompletableFuture<Integer> product =
                CompletableFuture.supplyAsync(() -> 2 * 3);

        CompletableFuture.allOf(sum, product)
                .thenRun(() -> {
                    System.out.println("Sum: " + sum.join());
                    System.out.println("Product: " + product.join());
                }).join();


        // Homework 2
        System.out.println("\n=== HW2 ===");

        CompletableFuture<String> products =
                fetch("https://jsonplaceholder.typicode.com/posts");

        CompletableFuture<String> reviews =
                fetch("https://jsonplaceholder.typicode.com/comments");

        CompletableFuture<String> inventory =
                fetch("https://jsonplaceholder.typicode.com/todos");

        CompletableFuture.allOf(products, reviews, inventory)
                .thenRun(() -> {
                    System.out.println("Products length: " + products.join().length());
                    System.out.println("Reviews length: " + reviews.join().length());
                    System.out.println("Inventory length: " + inventory.join().length());
                }).join();


        // Homework 3 (Exception Handling)
        System.out.println("\n=== HW3 ===");

        CompletableFuture<String> safeProducts =
                fetchWithException("https://jsonplaceholder.typicode.com/posts");

        CompletableFuture<String> safeReviews =
                fetchWithException("https://jsonplaceholder.typicode.com/comments");

        // test wrong url
        CompletableFuture<String> safeInventory =
                fetchWithException("https://wrong-url");

        CompletableFuture.allOf(safeProducts, safeReviews, safeInventory)
                .thenRun(() -> {
                    System.out.println("Products: " + safeProducts.join().length());
                    System.out.println("Reviews: " + safeReviews.join().length());
                    System.out.println("Inventory: " + safeInventory.join());
                }).join();
    }


    // Java 8 HTTP request
    public static CompletableFuture<String> fetch(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL u = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                return response.toString();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    // handle exception
    public static CompletableFuture<String> fetchWithException(String url) {
        return fetch(url).exceptionally(e -> {
            System.out.println("Error fetching: " + url);
            System.out.println("Exception: " + e.getMessage());
            return "DEFAULT_VALUE";
        });
    }
}