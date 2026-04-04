package repository;

import annotations.MyComponent;

@MyComponent
public class UserRepository {
    public String getUserName() {
        return "Alice";
    }
}