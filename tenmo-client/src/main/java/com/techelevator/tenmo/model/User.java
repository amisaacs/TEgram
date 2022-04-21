package com.techelevator.tenmo.model;
/*
Model of User that will come back from the RESTful api (our server in this case)
 */
public class User {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof User) {
            User otherUser = (User) other;
            return otherUser.getId().equals(id)
                    && otherUser.getUsername().equals(username);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return id + "         " + username;
    }
}
