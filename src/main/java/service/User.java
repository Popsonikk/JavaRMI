package service;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    boolean isAdmin;

    public User() {
    }

    public User(String name, boolean isAdmin) {
        this.name = name;

        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
