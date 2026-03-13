import java.lang.Math;
package Shape;
public class TestShape {

    public static void main(String[] args){

        Shape[] shapes = {new Rectangle("Red",4,5),new Circle("Blue",3)};

        for(Shape s : shapes){
            System.out.println("Color: " + s.getColor());
            System.out.println("Area: " + s.getArea());
            System.out.println("Perimeter: " + s.getPerimeter());
            if(s instanceof Drawable){
                ((Drawable)s).draw();
            }
            System.out.println();
        }
    }
}