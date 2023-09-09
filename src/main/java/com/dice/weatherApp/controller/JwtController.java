package com.dice.weatherApp.controller;

import com.dice.weatherApp.model.Jwt_response;
import com.dice.weatherApp.service.JwtService;
import com.dice.weatherApp.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class JwtController {

    @GetMapping("/get-new-jwt")
    public ResponseEntity<? extends Object> generateJwt() {
        try {
            JwtService jwtService = new JwtService();
            Map<String, Object> claims = new HashMap<>();
            claims.put("allowed", "true");
            String jwt = jwtService.createJwt(claims);
            Jwt_response result = new Jwt_response();
            result.setJwt(jwt);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.INTERNAL_SERVER_ERROR;
        }

    }

}
