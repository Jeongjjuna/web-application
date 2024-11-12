package model;

public class User {

    private final String username;
    private final String email;
    private final String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
