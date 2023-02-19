package com.itcraftsolution.raido.Models;

public class ResponceModel {

    String agentid, status, username, journey;

    public ResponceModel(String agentid, String status, String username, String journey) {
        this.agentid = agentid;
        this.status = status;
        this.username = username;
        this.journey = journey;
    }

    public ResponceModel() {
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJourney() {
        return journey;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }
}
