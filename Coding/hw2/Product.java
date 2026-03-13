import java.util.Objects;

class Product {

    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String toString(){
        return "Product{id='" + id + "', name='" + name + "', price=" + price + "}";
    }

    public boolean equals(Object obj){

        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Product other = (Product)obj;
        return Objects.equals(id, other.id);
    }

    public int hashCode(){
        return Objects.hash(id);
    }
    public static void main(String[] args){

        Product p1 = new Product("P001","Laptop",999.99);
        Product p2 = new Product("P001","Phone",499.99);

        System.out.println(p1.equals(p2));

        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());

        System.out.println(p1);
        System.out.println(p2);
    }
}