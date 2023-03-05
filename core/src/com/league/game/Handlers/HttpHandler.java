package com.league.game.Handlers;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class HttpHandler {
    public static void requestUserData(String username) throws IOException {
        Socket socket = new Socket("localhost", 8089);
        log.info("Requesting user data from account service.");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream.writeUTF("data " + username);
        dataOutputStream.flush();
        String userData = dataInputStream.readUTF();
        dataOutputStream.close();
        socket.close();
    }
}
