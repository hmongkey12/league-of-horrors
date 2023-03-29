package com.league.game.Handlers;

import com.league.game.LeagueOfHorrors;
import com.serializers.*;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class UDPCreationHandler {

    private static byte[] incomingDatagramPacketBuffer;

    private static byte[] outgoingDatagramPacketBuffer;
    private static DatagramPacket outgoingDatagramPacket;
    private static DatagramPacket incomingDatagramPacket;
    private static Map<String, String> creationCommand;

    private static String playerId;

    public static final int SERVER_PORT = 8086;

    public static void handleCreation(LeagueOfHorrors gameManager) {
        playerId = gameManager.getPlayerId();
        incomingDatagramPacketBuffer = new byte[16000];
        outgoingDatagramPacketBuffer = new byte[16000];
        incomingDatagramPacket = new DatagramPacket(incomingDatagramPacketBuffer, incomingDatagramPacketBuffer.length);
        creationCommand = new HashMap<String, String>();
        creationCommand.put("createHero", gameManager.getSelectedHeroName() + "_" + playerId);
        outgoingDatagramPacketBuffer = JSONObject.toJSONString(creationCommand).getBytes();
        try {
            gameManager.udpNetworkHandler.getClientSocket().setSoTimeout(5000);
            outgoingDatagramPacket = new DatagramPacket(outgoingDatagramPacketBuffer, outgoingDatagramPacketBuffer.length,
                    InetAddress.getByName("127.0.0.1"), SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
            try {
                gameManager.udpNetworkHandler.getClientSocket().send(outgoingDatagramPacket);
                gameManager.udpNetworkHandler.getClientSocket().receive(incomingDatagramPacket);
                SerializableGameStateDecorator serializableGameStateDecorator = new SerializableGameStateDecorator(new BasicSerializer());
                SerializableGameState serializableGameState = (SerializableGameState) serializableGameStateDecorator.deserialize(incomingDatagramPacket.getData());
                if (serializableGameState != null) {
                    if (serializableGameState.getConnectedPlayers().get(playerId) != null) {
                        gameManager.isHeroCreated = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}
