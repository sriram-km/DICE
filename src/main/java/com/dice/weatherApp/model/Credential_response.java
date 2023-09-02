package com.dice.weatherApp.model;

public class Credential_response {
    String clientId;
    String clientSecret;

    public String getClientId(){
        return clientId;
    }

    public String getClientSecret(){
        return clientSecret;
    }

    public void setClientId(String clientId){
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret){
        this.clientSecret = clientSecret;
    }
}