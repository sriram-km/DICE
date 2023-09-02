package com.dice.weatherApp.model;

import com.dice.weatherApp.utils.Util;
import lombok.Data;

import java.security.NoSuchAlgorithmException;

@Data
public class Credential_ele {
    private String clientIdHash;
    private String clientSecretHash;

    public Credential_ele(String clientId, String clientSecret) throws NoSuchAlgorithmException {
        this.clientIdHash = Util.hash(clientId);
        this.clientSecretHash = Util.hash(clientSecret);
    }

    public Credential_ele() {
    }
    public void setClientIdHash(String clientIdHash) {
        this.clientIdHash = clientIdHash;
    }

    public void setClientSecretHash(String clientSecretHash) {
        this.clientSecretHash = clientSecretHash;
    }

    public void setClientId(String clientId) throws NoSuchAlgorithmException {
        this.clientIdHash = Util.hash(clientId);
    }

}

