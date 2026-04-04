package service;


import annotations.MyAutowired;
import annotations.MyComponent;
import repository.*;

@MyComponent
public class UserService {

    @MyAutowired
    private UserRepository userRepository;

    public void printUser() {
        System.out.println("User: " + userRepository.getUserName());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
