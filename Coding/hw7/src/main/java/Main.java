import core.SimpleBeanFactory;
import repository.UserRepository;
import service.OrderService;
import service.UserService;

public class Main {
    public static void main(String[] args) {
        SimpleBeanFactory factory = new SimpleBeanFactory();

        // register beans
        factory.registerBean("userRepository", UserRepository.class);
        factory.registerBean("userService", UserService.class);
        factory.registerBean("orderService", OrderService.class);

        // test dependency injection
        UserService userService = (UserService) factory.getBean("userService");
        userService.printUser();

        // test singleton scope
        UserService userService1 = (UserService) factory.getBean("userService");
        UserService userService2 = (UserService) factory.getBean("userService");
        System.out.println("userService singleton? " + (userService1 == userService2));

        // test prototype scope
        OrderService orderService1 = (OrderService) factory.getBean("orderService");
        OrderService orderService2 = (OrderService) factory.getBean("orderService");
        System.out.println("orderService prototype? " + (orderService1 != orderService2));
    }
}