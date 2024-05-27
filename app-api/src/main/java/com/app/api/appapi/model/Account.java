package com.app.api.appapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Account model
 * @author John Luong
 */
public class Account {
    static final String STRING_FORMAT = "Account [username=%s]";
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("id") private int id;

    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("id") int id){
        this.username = username;
        this.password = password;
        this.id = id;
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
     * @return the account username
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
     * @return the account password
     */
    public String getPassword(){
        return password;
    }

    /**
     * gets the Account id
     * @return the account id
     */
    public int getId(){
        return id;
    }

    /**
     * @inheritDoc
     */
    public String toString(){
        return String.format(STRING_FORMAT,username);
    }

    /**
     * @inheritDoc
     */
    public boolean equals(Object other){
        return ((Account)other).username == this.username;
    }

    /**
     * @inheritDoc
     */
    public Account clone(){
        return new Account(this.username, this.password, this.id);
    }
}
