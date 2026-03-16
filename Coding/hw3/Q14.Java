interface IStatusCode {
 int getCode();
 String getDescription();
}

enum OrderStatus implements IStatusCode{
    PENDING(0, "Order is pending"),
    PAID(1, "Payment received"),
    SHIPPED(2, "Order has been shipped"),
    DELIVERED(3, "Order delivered"),
    CANCELLED(-1, "Order cancelled");
    private int code; private String description;
    public OrderStatus(int code, String description){
        this.code = code;this.description=description;
    }
    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
public class EnumTest {

    public static void main(String[] args) {

        for (OrderStatus status : OrderStatus.values()) {
            System.out.println(
                    status.name() + " -> " +
                    status.getCode() + " -> " +
                    status.getDescription()
            );
        }
        OrderStatus paid = OrderStatus.valueOf("PAID");

        System.out.println("\nGet by name:");
        System.out.println(paid + " code = " + paid.getCode());
    }
}