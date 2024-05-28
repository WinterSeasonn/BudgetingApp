package com.app.api.appapi.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;;

/**
 * Account model
 * @author John Luong
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

    public String jsonEncode() throws IOException{
        try{
            return(new ObjectMapper()).writeValueAsString(this);
        }catch(Exception e){}
        return null;
    }
}
