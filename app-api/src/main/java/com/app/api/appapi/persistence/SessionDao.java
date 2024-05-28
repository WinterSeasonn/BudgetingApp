package com.app.api.appapi.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public interface SessionDao {
    static final String USER_INFO_TAG = "Login";
    static final String SPLIT_STRING = ":";

    public class LoginInfo{
        public String Username;
        public String Password;

        public LoginInfo(@JsonProperty("Username") String Username, @JsonProperty("Password") String Password){
            this.Username = Username;
            this.Password = Password;
        }
    }
    public void ClearSessionInfo();

    public void SetSessionInfo(String Username, String Password);

    public LoginInfo GetSessionInfo();
}
