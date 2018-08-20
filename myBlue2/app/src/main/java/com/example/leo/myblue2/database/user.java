package com.example.leo.myblue2.database;

import java.util.Objects;

/**
 * Created by leo on 2017/6/18.
 */

public class user implements dbitem{
    private String userid;
    private String account;
    private String password;
    public user(String userid,String account,String password){
        this.userid=userid;
        this.account=account;
        this.password=password;
    }
    public user(){

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
