package com.dice.weatherApp.service;

import com.dice.weatherApp.utils.Util;
import org.json.JSONObject;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

public class JwtService {

    private Properties properties = Util.getApplicationProperties();
    private final String SECRET_KEY = properties.getProperty("jwt.secret.key");
    private final String ALGORITHM = "HmacSHA256";
    private final long EXPIRATION_TIME_MS = properties.getProperty("jwt.expiration.time.ms") != null ? Long.parseLong(properties.getProperty("jwt.expiration.time.ms")) : 3600000;

    public String createJwt(Map<String, Object> claims) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = currentTimeMillis + EXPIRATION_TIME_MS;

        // Create a header and payload
        String header = "{\"alg\":\"" + ALGORITHM + "\",\"typ\":\"JWT\"}";
        String payload = "{\"exp\":" + expirationTimeMillis + ",";
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            payload += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
        }
        payload = payload.substring(0, payload.length() - 1) + "}";

        // Encode header and payload
        String encodedHeader = base64UrlEncode(header);
        String encodedPayload = base64UrlEncode(payload);

        // Create the signature
        String signatureInput = encodedHeader + "." + encodedPayload;
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        Key key = new javax.crypto.spec.SecretKeySpec(secretKeyBytes, ALGORITHM);
        String signature = base64UrlEncode(signTheData(key, signatureInput));

        // Create the JWT by concatenating the encoded parts
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    public boolean verifyJwt(String token) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        String header = parts[0];
        String payload = parts[1];
        String signature = parts[2];

        // Verify the signature
        String signatureInput = header + "." + payload;
        byte[] secretKeyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        Key key = new javax.crypto.spec.SecretKeySpec(secretKeyBytes, ALGORITHM);
        String expectedSignature = base64UrlEncode(signTheData(key, signatureInput));

        return expectedSignature.equals(signature);
    }

    public JSONObject getData(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        String header = parts[0];
        String payload = parts[1];

        // Decode header and payload
        String decodedHeader = base64UrlDecode(header);
        String decodedPayload = base64UrlDecode(payload);

        JSONObject data = new JSONObject();
        data.put("header", new JSONObject(decodedHeader));
        data.put("payload", new JSONObject(decodedPayload));

        return data;
    }

    private String base64UrlEncode(String input) {
        byte[] bytes = Base64.getUrlEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String base64UrlDecode(String input) {
        byte[] bytes = Base64.getUrlDecoder().decode(input.getBytes(StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String signTheData(Key key, String data) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(key);
        byte[] signatureBytes = hmacSha256.doFinal(data.getBytes());
        return new String(signatureBytes, StandardCharsets.UTF_8);
    }
}

