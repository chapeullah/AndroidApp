package ru.mirea.sda.retrofit;

public class User {
    public int id;
    public String name;
    public String username;
    public String email;

    @Override
    public String toString() {
        return id + ". " + name + " (" + username + ") — " + email;
    }
}
