package com.example.javafxx;

abstract class AbstractUser {
    private int userID;
    private String email;
    private String password;
    private String name;

    public AbstractUser(int userID, String email, String password, String name) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public abstract void displayUserInfo();

    // Getters and Setters
    public int getUserID() { return userID; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}