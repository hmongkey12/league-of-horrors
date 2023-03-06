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
import java.util.Scanner;

@Slf4j
public class HttpHandler {

    public static void requestUserData(String username, String password) {
        String responseData = null;
        Scanner scanner = new Scanner(System.in);
        String requestType = null;
        Authentication authentication = new Authentication();
        while(responseData == null) {
            try {
                requestType = scanner.nextLine();
                HttpURLConnection urlConnection = null;
                if (requestType.equalsIgnoreCase("login")) {
                    URL url = authentication.login(username, password);
                    System.out.println(url.toString());
                    urlConnection = configureUrlConnection(url, "GET");
                } else if (requestType.equalsIgnoreCase("register")) {
                    URL url = authentication.register(username, password);
                    System.out.println(url.toString());
                    urlConnection = configureUrlConnection(url, "POST");
                }
                if (urlConnection != null){
                    BufferedReader bufferedReader = null;
                    InputStreamReader inputStreamReader = null;
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                        bufferedReader = new BufferedReader(inputStreamReader);
                        responseData = bufferedReader.readLine();
                        bufferedReader.close();
                        inputStreamReader.close();
                    }
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
            }
        }
        System.out.println("reponse data: " + responseData);
    }

    private static HttpURLConnection configureUrlConnection (URL url, String method) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);
        urlConnection.setRequestProperty("Content-Type", "text/plain");
        if (method.equals(Net.HttpMethods.GET)) {
            urlConnection.setRequestMethod("GET");
        } else if (method.equals(Net.HttpMethods.POST)) {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
//            JSONObject json = new JSONObject();
//            json.put("name", "bob");
//            json.put("password", "1234");
        }
        return urlConnection;
    }
}
