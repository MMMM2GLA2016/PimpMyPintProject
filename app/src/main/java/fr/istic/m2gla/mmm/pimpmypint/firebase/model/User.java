package gla.istic.pimpmypint.firebase.model;

import java.util.List;

/**
 * Created by Antoine Brezillon on 21/03/16.
 */
public class User {

    private String uid;
    private String name;
    private List<User> contacts;

    public User() {
    }

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public List<User> getContacts() {
        return contacts;
    }
}
