package chuwa3926.Coding.hw3;

public class Q16 {
    public static class DatabaseConnection {

        private DatabaseConnection() {
            System.out.println("Database connection created");
        }

        private static class Holder {
            private static final DatabaseConnection INSTANCE = new DatabaseConnection();
        }

        public static DatabaseConnection getInstance() {
            return Holder.INSTANCE;
        }

        public void executeQuery(String sql) {
            System.out.println("Executing: " + sql);
        }

    public static void main(String[] args) {
            DatabaseConnection db1 = DatabaseConnection.getInstance();
            DatabaseConnection db2 = DatabaseConnection.getInstance();
            System.out.println("Same instance? " + (db1 == db2));
            db1.executeQuery("SELECT * FROM users");
        }
    }
}
