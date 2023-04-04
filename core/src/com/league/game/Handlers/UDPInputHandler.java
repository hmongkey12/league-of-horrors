package com.league.game.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.league.game.LeagueOfHorrors;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UDPInputHandler {
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

    private static void sendCommand(LeagueOfHorrors gameManager, String commandType) {
        Map<String, String> command = new HashMap<>();
        command.put("command", commandType + "_" + gameManager.getPlayerId());
        gameManager.udpNetworkHandler.sendData(JSONObject.toJSONString(command));
    }
}
