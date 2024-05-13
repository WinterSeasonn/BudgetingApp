package com.app.api.appapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Account model
 * @Author John Luong
 */
public class Account {
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
    }

    /**
     * sets the Account username to the provided username
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * gets the Account username
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * sets the Account password to the provided password
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * gets the Account password
     * @return the password
     */
    public String getPassword(){
        return password;
    }
}
