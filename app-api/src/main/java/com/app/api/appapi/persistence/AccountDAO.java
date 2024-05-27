package com.app.api.appapi.persistence;

import java.io.IOException;
import com.app.api.appapi.model.Account;

/**
 * Defines the interface for the Account object persistence
 * @author John Luong
 */
public interface AccountDAO {
    /**
     * Retrieves all Accounts
     * 
     * @return an array of Account objects, may be empty
     * @throws IOException if an issue with underlying storage
     */
    Account[] getAccounts() throws IOException;

    /**
     * Retrieves an Account with the given id
     * 
     * @param id the id of the Account
     * @return the Account with matching id or null if the id is not found
     * @throws IOException if an issue with underlying storage
     */
    Account getAccount(String Username) throws IOException;

    /**
     * Creates and saves an Account
     * 
     * @param account Object to be created and saved
     * @return new Account if successful, false otherwise
     * @throws IOException if an issue with underlying storage
     */
    Account createAccount(Account account) throws IOException;

    /**
     * Updates and saves an Account
     * 
     * @param account Object to be updated and saved
     * @return Updated Account if successful, null otherwise
     * @throws IOException if underlying storage cannot be accessed
     */
    Account updateAccount(Account account) throws IOException;

    /**
     * Deletes an Account
     * 
     * @param id The id of the Account
     * @return True if Account was deleted, false otherwise
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAccount(int id) throws IOException;
}
