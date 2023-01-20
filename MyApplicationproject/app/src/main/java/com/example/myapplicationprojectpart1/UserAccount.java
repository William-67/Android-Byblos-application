package com.example.myapplicationprojectpart1;

import java.io.Serializable;

public class UserAccount implements Serializable {
    private String key;
    private String userName;
    private String password;
    private Identity identity;

    public UserAccount(){

    }

    public UserAccount(String key, String userName, String password, Identity identity) {
        this.key=key;
        this.userName = userName;
        this.password = password;
        this.identity = identity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "key='" + key + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + identity +
                '}';
    }
}
