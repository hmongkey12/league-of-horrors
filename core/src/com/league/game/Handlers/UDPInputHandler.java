package com.league.game.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.league.game.LeagueOfHorrors;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UDPInputHandler {

    /**
     * Handles input received from the user's keyboard and sends commands to the game server based on the keyboard keys pressed.
     * @param gameManager the main game manager that contains the reference to the UDP network handler. NetworkHandler will be the connection between client and server.
     * The method sends commands to move the player character left, right, up, down, and to use the first skill, as specified by the keyboard keys A, D, W, S, and J respectively.</p>
     * If the UDP network handler is null, no command is sent.</p>
     */
    public static void handleInput(LeagueOfHorrors gameManager) {
        if (gameManager.udpNetworkHandler == null) {
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sendCommand(gameManager, "left");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sendCommand(gameManager, "right");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            sendCommand(gameManager, "up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            sendCommand(gameManager, "down");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            sendCommand(gameManager, "skill1");
        }
    }

    /**
     * Sends a command to the game server via UDP, which is the user's input from the keyboard.
     * @param gameManager the main game manager that contains the reference to the UDP network handler. NetworkHandler will be the connection between client and server.
     * @param commandType the type of the command to be sent to the game server. It can be one of the following values: "left", "right", "up", "down", or "skill1".
     * If the UDP network handler is null, the method returns without sending any command.</p>
     */
    private static void sendCommand(LeagueOfHorrors gameManager, String commandType) {
        Map<String, String> command = new HashMap<>();
        command.put("command", commandType + "_" + gameManager.getPlayerId());
        gameManager.udpNetworkHandler.sendData(JSONObject.toJSONString(command));
    }
}
