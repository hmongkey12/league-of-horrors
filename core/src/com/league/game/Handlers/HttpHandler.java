package com.league.game.Handlers;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.league.game.auth.Authentication;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.internal.http.HttpMethod;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class HttpHandler {

    public static void requestUserData(String username, String password) {
        String responseData = null;
        Scanner scanner = new Scanner(System.in);
        String requestType = null;
        Authentication authentication = new Authentication();
        int responseCode = 0;
        while(responseCode != HttpURLConnection.HTTP_OK) {
            try {
                requestType = scanner.nextLine();
                HttpURLConnection urlConnection = null;
                if (requestType.equalsIgnoreCase("login")) {
                    URL url = authentication.login(username, password);
                    urlConnection = configureUrlConnection(url, "GET");
                    responseCode = performGetRequest(urlConnection);
                } else if (requestType.equalsIgnoreCase("register")) {
                    URL url = authentication.register(username, password);
                    urlConnection = configureUrlConnection(url, "POST");
                    responseCode = performPostRequest(urlConnection);
                }
            } catch (SocketTimeoutException e) {
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                break;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                break;
            } catch (ProtocolException e) {
                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
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
            BufferedReader bufferedReader = null;
            InputStreamReader inputStreamReader = null;
            int responseCode = urlConnection.getResponseCode();
            String responseData;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                responseData = bufferedReader.readLine();
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
            byte[] outgoingData = null;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username","alice");
            jsonObject.put("password","1234");
            outgoingData = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(outgoingData);
            outputStream.flush();
            outputStream.close();
            int responseCode = urlConnection.getResponseCode();
            return responseCode;
        }
        return HttpURLConnection.HTTP_BAD_REQUEST;
    }
}
