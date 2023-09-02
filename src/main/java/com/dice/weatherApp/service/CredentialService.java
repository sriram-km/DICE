package com.dice.weatherApp.service;

import com.dice.weatherApp.utils.CsvUtil;
import com.dice.weatherApp.utils.Util;
import com.dice.weatherApp.model.Credential_ele;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class CredentialService {

    private final CsvUtil csvUtil;

    private static final int CLIENT_ID_LENGTH = 16;
    private static final int CLIENT_SECRET_LENGTH = 32;

    @Autowired
    public CredentialService() {
        this.csvUtil = new CsvUtil();
    }

    public void storeClient(String clientId, String clientSecret) throws NoSuchAlgorithmException {
        String clientIdHash = Util.hash(clientId);
        String clientSecretHash = Util.hash(clientSecret);

        Credential_ele credentialResponse = new Credential_ele();
        credentialResponse.setClientIdHash(clientIdHash);
        credentialResponse.setClientSecretHash(clientSecretHash);

        csvUtil.writeClient(credentialResponse);
    }

    public String getClientSecret(String clientId) throws NoSuchAlgorithmException {
        List<Credential_ele> credentialResponses = csvUtil.readClients();
        String clientIdHash = Util.hash(clientId);

        for (Credential_ele credentialResponse : credentialResponses) {
            if (credentialResponse.getClientIdHash().equals(clientIdHash)) {
                return credentialResponse.getClientSecretHash();
            }
        }

        return null;
    }


    private String[] createCredentials(){
        String[] credentials = new String[2];
        //client id
        credentials[0] = Util.generateRandomString(CLIENT_ID_LENGTH);
        //client secret
        credentials[1] = Util.generateRandomString(CLIENT_SECRET_LENGTH);
        return credentials;
    }

    public String[] getNewCredentials() throws NoSuchAlgorithmException {
        String[] credentials = createCredentials();
        storeClient(credentials[0], credentials[1]);
        return credentials;
    }

    public boolean validateAuthentication(String clientId, String clientSecret) throws NoSuchAlgorithmException {
        String clientSecretHash = getClientSecret(clientId);
        String clientSecretHashCheck = Util.hash(clientSecret);
        return clientSecretHashCheck.equals(clientSecretHash);
    }

}

