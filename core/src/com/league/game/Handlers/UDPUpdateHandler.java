package com.league.game.Handlers;

import com.league.game.LeagueOfHorrors;
import com.serializers.BasicSerializer;
import com.serializers.SerializableGameState;
import com.serializers.SerializableGameStateDecorator;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

public class UDPUpdateHandler {

    public static void handleUpdate(LeagueOfHorrors gameManager) {
        byte[] incomingDatagramPacketBuffer = new byte[16000];
        DatagramPacket incomingDatagramPacket = new DatagramPacket(incomingDatagramPacketBuffer, incomingDatagramPacketBuffer.length);
        try {
            gameManager.udpNetworkHandler.getClientSocket().setSoTimeout(1000);
            gameManager.udpNetworkHandler.getClientSocket().receive(incomingDatagramPacket);
            SerializableGameStateDecorator serializableGameStateDecorator =
                    new SerializableGameStateDecorator(new BasicSerializer());
            SerializableGameState serializableGameState =
                    (SerializableGameState) serializableGameStateDecorator.deserialize(incomingDatagramPacket.getData());
            if (serializableGameState != null) {
                gameManager.heroStateQueue.add(UDPStateHandler.replicateServerState(serializableGameState));
            }
        } catch (SocketTimeoutException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
