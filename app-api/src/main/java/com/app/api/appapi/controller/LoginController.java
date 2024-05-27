package com.app.api.appapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;


package com.app.api.appapi.model.Account;


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
                    return new ResponseEntity<>(verifyAccount.jsonEncode(),HttpStatus.OK);
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
            ResponseEntity<String> verify = verifyAccount(user,pass);
            if(verify.getStatusCode() ==HttpStatus.OK){
                return new ResponseEntity<>(ADAO.getAccount(user),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(verify.getStatusCode());
            }
            System.out.println("Works!")
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
                ADAO.createAccount(new Account(username,password,0));
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            System.out.println("Works!");
        }
        catch(IOException e){
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }
}

