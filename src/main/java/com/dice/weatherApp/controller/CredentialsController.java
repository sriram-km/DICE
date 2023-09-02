package com.dice.weatherApp.controller;

import com.dice.weatherApp.model.Credential_response;
import com.dice.weatherApp.service.CredentialService;
import com.dice.weatherApp.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/weather")
public class CredentialsController {

    @GetMapping("/get-new-credentials")
    public ResponseEntity<? extends Object> generateCredentials() {
        try {
            CredentialService credentialService = new CredentialService();
            String[] credential;
            credential = credentialService.getNewCredentials();
            Credential_response result = new Credential_response();
            result.setClientId(credential[0]);
            result.setClientSecret(credential[1]);

            return ResponseEntity.ok(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Constants.INTERNAL_SERVER_ERROR;
        }

    }

}
