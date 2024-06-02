package com.app.api.appapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.app.api.appapi.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functions in AccountDAO
 * @author John Luong
 */
@Component
public class AccountFileDAO implements AccountDAO{
    Map<String,Account> accounts;
    private ObjectMapper objectMapper;
    private String filename;

    /**
     * Creates an Account File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the accounts from the file
    }

    /**
     * Generates an array of Accounts from the tree map
     * 
     * @return  The array of Accounts, may be empty
     */
    private Account[] getAccountArray() {
        return getAccountArray(null);
    }

    /**
     * Generates an array of Accounts from the tree map for any Account that
     * contains the text specified by containsText. If containsText is null,
     * the array contains all of the Accounts in the tree map.
     * 
     * @return  The array of Accounts, may be empty
     */
    private Account[] getAccountArray(String containsText) {
        ArrayList<Account> accountArrayList = new ArrayList<>();
    
        for (Account account : accounts.values()) {
            String accountNameLowerCase = account.getUsername().toLowerCase();
            if (containsText == null || accountNameLowerCase.contains(containsText)) {
                accountArrayList.add(account);
            }
        }
    
        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);
        return accountArray;
    }

    /**
     * Saves the Accounts from the map into the file as an array of JSON objects
     * 
     * @return true if the Accounts were written successfully
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename),accountArray);
        return true;
    }

    /**
     * Loads Accounts from the JSON file into the map.
     * Also sets next id to one more than the greatest id found in the file.
     * 
     * @return true if the file was read successfully
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        accounts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of accounts
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Account[] accountArray = objectMapper.readValue(new File(filename),Account[].class);

        // Add each account to the tree map
        for (Account account : accountArray) {
            accounts.put(account.getUsername(),account);
        }
        return true;
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account[] getAccounts() throws IOException{
        synchronized(accounts) {
            return getAccountArray();
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account getAccount(String username) {
        synchronized(accounts) {
          if(accounts.containsKey(username)){
            return accounts.get(username);
          }
          else{
            return null;
          }
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account createAccount(Account account) throws IOException {
        synchronized(accounts) {
            if(getAccount(account.getUsername()) != null){
                throw new IOException("Username in use");
            }
            Account newAccount = new Account(account.getUsername().toLowerCase(), account.getPassword());
            accounts.put(newAccount.getUsername(),newAccount);
            save(); // may throw an IOException
            return newAccount;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public Account updateAccount(Account account) throws IOException {
        synchronized(accounts) {
            if (!accounts.containsKey(account.getUsername()))
                return null;
    
            accounts.put(account.getUsername(), account);
            save(); // may throw an IOException
            return account;
        }
    }

    /**
    ** {@inheritDoc}
     */
    @Override
    public boolean deleteAccount(String username) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(username)) {
                accounts.remove(username);
                return save();
            }
            else
                return false;
        }
    }
}
