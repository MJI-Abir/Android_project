package com.app.hotel.viewModels;

import android.view.View;

public class User {
    private String fname, lname, mobile, mailId,pImageUrl;
    private String id;

    public User() {

    }

    public User(String fname, String lname, String mobile, String mailId, View imageview, String id) {
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.mailId = mailId;
        this.id=id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public void setId(String id) {this.id=id;}

    public String getId() {return id;}

    public String getpImageUrl() {
        return pImageUrl;
    }

    public void setpImageUrl(String pImageUrl) {
        this.pImageUrl = pImageUrl;
    }
}
