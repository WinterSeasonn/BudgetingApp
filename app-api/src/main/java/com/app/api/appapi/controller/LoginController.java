package com.app.api.appapi.controller;

import com.app.api.appapi.persistence.AccountDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;


import com.app.api.appapi.model.Account;


@RestController
@RequestMapping("Login")

public class LoginController{
    private AccountDAO ADAO;

    public LoginController(AccountDAO ADAO){
      this.ADAO = ADAO;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> verify(@RequestParam("user") String user, @RequestParam("pass") String pass){
        try{
            Account verifyAccount = ADAO.getAccount(user);
            if(verifyAccount != null){
                if(verifyAccount.getPassword().equals(pass)){
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<Account> login(@RequestParam("user") String user, @RequestParam("pass") String pass){
        try{
            System.out.println("Testing Login...");
            ResponseEntity<String> verifyStatus = verify(user,pass);
            if(verifyStatus.getStatusCode() == HttpStatus.OK){
                System.out.println("Works!");
                return new ResponseEntity<>(ADAO.getAccount(user),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(verifyStatus.getStatusCode());
            }
        } catch (IOException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<Boolean> signup(@RequestParam("user") String user, @RequestParam("pass") String pass){
        try{
            System.out.println("Testing Signup...");
            if(ADAO.getAccount(user) != null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else{
                ADAO.createAccount(new Account(user,pass,0));
                System.out.println("Works!");
                return new ResponseEntity<>(HttpStatus.CREATED);
            }

        }
        catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

