package com.dice.weatherApp.utils;

import org.springframework.http.ResponseEntity;

public class Constants {
    public static final ResponseEntity<String> INTERNAL_SERVER_ERROR = ResponseEntity.status(500).body("Internal Server Error");

}
