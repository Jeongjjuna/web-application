package db;

import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataBase {
    private static Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public static Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public static List<User> findAll() {
        return List.copyOf(users.values());
    }
}