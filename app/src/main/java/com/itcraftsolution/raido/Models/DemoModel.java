package com.itcraftsolution.raido.Models;

public class DemoModel {

   String status, userid, userimage, username, userbookseat, userdate, usersource, userdestination;

    public DemoModel(String status, String userid, String userimage, String username, String userbookseat, String userdate, String usersource, String userdestination) {
        this.status = status;
        this.userid = userid;
        this.userimage = userimage;
        this.username = username;
        this.userbookseat = userbookseat;
        this.userdate = userdate;
        this.usersource = usersource;
        this.userdestination = userdestination;
    }

    public DemoModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserbookseat() {
        return userbookseat;
    }

    public void setUserbookseat(String userbookseat) {
        this.userbookseat = userbookseat;
    }

    public String getUserdate() {
        return userdate;
    }

    public void setUserdate(String userdate) {
        this.userdate = userdate;
    }

    public String getUsersource() {
        return usersource;
    }

    public void setUsersource(String usersource) {
        this.usersource = usersource;
    }

    public String getUserdestination() {
        return userdestination;
    }

    public void setUserdestination(String userdestination) {
        this.userdestination = userdestination;
    }
}