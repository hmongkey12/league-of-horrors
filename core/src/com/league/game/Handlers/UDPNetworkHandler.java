package com.league.game.Handlers;

import lombok.Data;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@Data
public class UDPNetworkHandler {
    private DatagramSocket clientSocket;
    private DatagramPacket incomingDatagramPacket;
    private DatagramPacket outgoingDatagramPacket;
    private InetAddress clientIpAddress;

    private InetAddress serverIpAddress;
    private byte[] incomingDatagramPacketBuffer = new byte[16000];
    private byte[] outgoingDatagramPacketBuffer = new byte[16000];
    public static final int CLIENT_PORT = 8085;
    public static final int SERVER_PORT = 8086;
   public UDPNetworkHandler() {
       try{
           clientSocket = new DatagramSocket() ;
           clientIpAddress = InetAddress.getByName("127.0.0.1");
           serverIpAddress = InetAddress.getByName("127.0.0.1");
           incomingDatagramPacket = new DatagramPacket(incomingDatagramPacketBuffer, incomingDatagramPacketBuffer.length,
                   serverIpAddress, SERVER_PORT);
       } catch (Exception e) {
            e.printStackTrace();
       }
   }

   public void sendData(String message) {
       try {
           outgoingDatagramPacketBuffer = message.getBytes();
           clientSocket.send(new DatagramPacket(outgoingDatagramPacketBuffer, outgoingDatagramPacketBuffer.length, serverIpAddress, SERVER_PORT));
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
