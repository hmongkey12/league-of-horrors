package com.league.game.auth;


import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Authentication {
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String DATABASE_URI = "http://localhost:8089";

    public URL login(String username, String password) {
        try {
            return createURLWithQueryParameters(username, password);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL register(String username, String password) {
//      MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
//      byte[] hashedPassword = messageDigest.digest(password.getBytes());
//      createURLWithPassword(username, hashedPassword.toString());
        return null;
    }

    private URL createURLWithQueryParameters(String username, String password) throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder(DATABASE_URI);
        urlBuilder.append("?")
                .append("username=").append(URLEncoder.encode(username, "UTF-8"))
                .append("&")
                .append("password=").append(URLEncoder.encode(password, "UTF-8"));
        return new URL(urlBuilder.toString());
    }

    private URL createURLWithRequestBody(String username, String password) throws MalformedURLException {
        URL url = new URL(DATABASE_URI);
        return new URL(DATABASE_URI);
    }
}
