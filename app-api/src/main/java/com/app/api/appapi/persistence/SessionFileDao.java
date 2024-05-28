package com.app.api.appapi.persistence;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;
import org.apache.catalina.startup.RealmRuleSet;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Source;

@Component
public class SessionFileDao implements SessionDao {
    HttpServletRequest Request;
    HttpServletResponse Response;
    ObjectMapper Mapper;
    ArrayList<Cookie> CookieStorage;
    static final String USER_INFO_TAG = "Login";
    static final String SPLIT_TAG = ":";


    public SessionFileDao(HttpServletRequest Request, HttpServletResponse Response) throws IOException{
        this.Request = Request;
        this.Response = Response;
        this.Mapper = new ObjectMapper();
    }

    private void AddSessionCookie(Cookie cookie){
        CookieStorage.add(cookie);
        Response.addCookie(cookie);
    }

    private void SessionCookie(String name, String value){
        getCookies();
        Cookie SessionData = new Cookie(name,value);
        SessionData.setMaxAge(-1);
        SessionData.setSecure(true);
        SessionData.setHttpOnly(true);
        SessionData.setPath("/");
        AddSessionCookie(SessionData);
    }

    private void getCookies(){
        if(CookieStorage == null){
            this.CookieStorage = new ArrayList<>();
            Cookie[] CookieArray = Request.getCookies();
            if(CookieArray != null){
                for(Cookie cookie : CookieArray){
                    CookieStorage.add(cookie);
                }
            }
        }
    }

    private Cookie getCookie(String tag){
        getCookies();
        for(Cookie cookie : CookieStorage){
           if(cookie.getName().equals(tag)){
               if(cookie.getValue() != ""){return cookie;}
               break;
           }
        }
        return null;
    }

    private void removeCookie(String tag){
        Cookie cookie = getCookie(tag);
        if(cookie != null){
            cookie.setMaxAge(0);
            cookie.setValue("");
            Response.addCookie(cookie);
        }
    }

    public void ClearSessionInfo() {
        removeCookie(USER_INFO_TAG);
    }


    public void SetSessionInfo(String Username, String Password) {
        Cookie cookie = getCookie(USER_INFO_TAG);
        if(cookie != null){
            cookie.setValue(Username+SPLIT_TAG+Password);
        } else{
            SessionCookie(USER_INFO_TAG, Username+SPLIT_TAG+Password);
        }
        System.out.println("Login is" + Username + " !");
    }



    public LoginInfo GetSessionInfo() {
        Cookie SessionData = getCookie(USER_INFO_TAG);
        if(SessionData != null){
            String[] CookieStorage = SessionData.getValue().split(SPLIT_STRING);
            return new LoginInfo(CookieStorage[0],CookieStorage[1]);
        }
        return null;
    }
}
