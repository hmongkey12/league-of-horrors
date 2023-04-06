package com.league.game.Handlers;

import com.league.game.LeagueOfHorrors;
import com.serializers.*;
import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

public class UDPCreationHandler {
    private static final int BUFFER_SIZE = 16000;
    private static final int SERVER_PORT = 8086;
    private static byte[] incomingBuffer;
    private static byte[] outgoingBuffer;
    private static DatagramPacket outgoingPacket;
    private static DatagramPacket incomingPacket;
    private static Map<String, String> creationCommand;
    private static String playerId;

    public static void handleCreation(LeagueOfHorrors gameManager) {
        initialize(gameManager);
        sendCreationRequest(gameManager);
        receiveAndProcessResponse(gameManager);
    }

    private static void initialize(LeagueOfHorrors gameManager) {
        playerId = gameManager.getPlayerId();
        incomingBuffer = new byte[BUFFER_SIZE];
        outgoingBuffer = new byte[BUFFER_SIZE];
        incomingPacket = new DatagramPacket(incomingBuffer, incomingBuffer.length);
        creationCommand = new HashMap<>();
        creationCommand.put("createHero", gameManager.getSelectedHeroName() + "_" + playerId);
        outgoingBuffer = JSONObject.toJSONString(creationCommand).getBytes();
    }

    private static void sendCreationRequest(LeagueOfHorrors gameManager) {
        try {
            gameManager.udpNetworkHandler.getClientSocket().setSoTimeout(5000);
            outgoingPacket = new DatagramPacket(outgoingBuffer, outgoingBuffer.length,
                    InetAddress.getByName("127.0.0.1"), SERVER_PORT);
            gameManager.udpNetworkHandler.getClientSocket().send(outgoingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void receiveAndProcessResponse(LeagueOfHorrors gameManager) {
        try {
            gameManager.udpNetworkHandler.getClientSocket().receive(incomingPacket);
            SerializableGameStateDecorator stateDecorator = new SerializableGameStateDecorator(new BasicSerializer());
            SerializableGameState gameState = (SerializableGameState) stateDecorator.deserialize(incomingPacket.getData());
            if (gameState != null && gameState.getConnectedPlayers().get(playerId) != null) {
                gameManager.isHeroCreated = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
