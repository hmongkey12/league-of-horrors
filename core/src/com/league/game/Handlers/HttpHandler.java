package com.league.game.Handlers;

import com.badlogic.gdx.Net;
import com.league.game.auth.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpHandler {

    public static int requestUserData(String username, String password, String requestType) {
        Authentication authentication = new Authentication();
        int responseCode = HttpURLConnection.HTTP_UNAVAILABLE;
            try {
                HttpURLConnection urlConnection;
                if (requestType.equalsIgnoreCase("login")) {
                    URL url = authentication.login(username, password);
                    urlConnection = configureUrlConnection(url, "GET");
                    responseCode = performGetRequest(urlConnection);
                } else if (requestType.equalsIgnoreCase("register")) {
                    URL url = authentication.register(username, password);
                    urlConnection = configureUrlConnection(url, "POST");
                    responseCode = performPostRequest(urlConnection);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        return responseCode;
    }

    private static HttpURLConnection configureUrlConnection (URL url, String method) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);
        if (method.equals(Net.HttpMethods.GET)) {
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            urlConnection.setRequestMethod("GET");
        } else if (method.equals(Net.HttpMethods.POST)) {
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
        }
        return urlConnection;
    }

    private static int performGetRequest(HttpURLConnection urlConnection) throws IOException {
        if (urlConnection != null){
            BufferedReader bufferedReader;
            InputStreamReader inputStreamReader;
            int responseCode = urlConnection.getResponseCode();
            String responseData;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                responseData = bufferedReader.readLine();
                System.out.println(responseData);
                bufferedReader.close();
                inputStreamReader.close();
                return HttpURLConnection.HTTP_OK;
            }
            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
        return HttpURLConnection.HTTP_BAD_REQUEST;
    }

    private static int performPostRequest(HttpURLConnection urlConnection) throws IOException, JSONException {
        if (urlConnection != null) {
            OutputStream outputStream = urlConnection.getOutputStream();
            byte[] outgoingData;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username","alice");
            jsonObject.put("password","1234");
            outgoingData = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(outgoingData);
            outputStream.flush();
            outputStream.close();
            return urlConnection.getResponseCode();
        }
        return HttpURLConnection.HTTP_BAD_REQUEST;
    }
}
