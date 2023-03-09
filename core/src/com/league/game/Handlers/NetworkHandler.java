package com.league.game.Handlers;


import com.league.game.LeagueOfHorrors;
import com.league.game.models.AbilityEntity;
import com.league.game.models.HeroGameEntity;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class NetworkHandler {

    private LeagueOfHorrors gameManager;
    private Socket socket;

    private int MAX_STATE_QUEUE_SIZE = 1;

    public NetworkHandler (LeagueOfHorrors gameManager) {
       this.gameManager = gameManager;
    }

    public void getAndConfigureSocket() {
        try {
            socket = IO.socket("http://localhost:3000");
            configureSocket();
            socket.open();
        } catch (Exception e) {
            e.printStackTrace();
//            log.error(e.getMessage());
        }
    }

    public void createHero() {
        String heroName = gameManager.selectedHeroName;
        if (gameManager.heroes.get(socket.id()) == null) {
            HeroGameEntity heroGameEntity = new HeroGameEntity();
            heroGameEntity.setHeroId(socket.id());
            heroGameEntity.setHeroName(heroName);
            heroGameEntity.setAbilities(getAbilities(heroName, gameManager.abilityEntityMap));
            gameManager.heroes.put(socket.id(), new HeroGameEntity());
        }
        socket.emit("createHero", heroName);
    }

    private void configureSocket() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    log.info("Connected to Game Server");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        socket.on("heroCreated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    if (args[0].equals(socket.id())) {
                        gameManager.isHeroCreated = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                log.info("Disconnected from Game Server");
            }
        });
        socket.on("updateState", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONParser parser = new JSONParser();
                    JSONObject gameState = (JSONObject) parser.parse(String.valueOf(args[0]));
                    JSONArray connectedPlayers = (JSONArray) gameState.get("connected");
                    if (gameManager.heroStateQueue.size() < MAX_STATE_QUEUE_SIZE) {
                        gameManager.heroStateQueue.add(StateHandler.replicateServerState(connectedPlayers));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    log.error(e.getMessage());
                }
            }
        });
    }


    private static List<AbilityEntity> getAbilities(String heroName, Map<String, List<AbilityEntity>> abilityMap) {
        List<AbilityEntity> abilityEntitiesList = abilityMap.get(heroName);
        return abilityEntitiesList;
    }
}
