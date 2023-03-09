package com.league.game.Handlers;

import com.league.game.LeagueOfHorrors;
import com.league.game.enums.FacingDirection;
import com.league.game.models.AbilityEntity;
import com.league.game.models.HeroGameEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateHandler {
    /**
     * PlayersOnServer is an JSONArray of JSONArrays
     * playersOnServer Structure:
     *      [[player1id , player1State], [player2id , player2State], ... [playerNid , playerNState]]
     * JSONObject is the playerNState
     * This is because we can't serialize node objects to become Java Maps, it would be easier if the server were Java
     * That way we can just send the object over and not need to copy anything
     * **/
    public static Map<String, HeroGameEntity> replicateServerState(JSONArray playersOnServer) {
        JSONObject playerState;
        String playerId;
        Map<String, HeroGameEntity> newPlayersOnClient = new HashMap<String, HeroGameEntity>();

        /**
         Each player would represent the inner JSONArray, and playersOnServer would be the outer JSONArray
         playersOnServer = [[player1id , player1State], [player2id , player2State]]
         playerOnServer = [player1d , player1State], it is a JSONArray
         player1State = { "xPos" : 100L, "yPos" : 100L }, it is a json object
        **/
        for (Object playerOnServer : playersOnServer) {
            playerId = (String) ((JSONArray) playerOnServer).get(0);
            playerState = (JSONObject) ((JSONArray) playerOnServer).get(1);
            newPlayersOnClient.put(playerId, mapJSONHeroStateToHeroObject(playerId, playerState));
        }
        return newPlayersOnClient;
    }


//    This maps the JSONObject values to the Hero Object values
    private static HeroGameEntity mapJSONHeroStateToHeroObject (String playerId, JSONObject jsonObjectState) {
        String direction = (String) jsonObjectState.get("facingDirection");
        HeroGameEntity heroGameEntity = new HeroGameEntity();
        heroGameEntity.setHeroId(playerId);
        heroGameEntity.setHeroName((String) jsonObjectState.get("heroName"));
        heroGameEntity.setWidth(Long.parseLong(jsonObjectState.get("width").toString()));
        heroGameEntity.setHeight(Long.parseLong(jsonObjectState.get("height").toString()));
        heroGameEntity.setHealth(Long.parseLong(jsonObjectState.get("health").toString()));
        heroGameEntity.setAbilities(mapAbilityToHero((JSONArray) jsonObjectState.get("abilities")));
        heroGameEntity.setAttacking((Boolean) jsonObjectState.get("isAttacking"));
        heroGameEntity.setFacingDirection(getFacingDirection(direction));
        heroGameEntity.setXPos((Long) jsonObjectState.get("xPos"));
        heroGameEntity.setYPos((Long) jsonObjectState.get("yPos"));
        heroGameEntity.setMoving((Boolean) jsonObjectState.get("isMoving"));
        return heroGameEntity;
    }

    private static FacingDirection getFacingDirection(String facingDirection) {
       return (facingDirection.equals(FacingDirection.LEFT.getDirection()) ?
               FacingDirection.LEFT : (facingDirection.equals(FacingDirection.RIGHT.getDirection())) ?
               FacingDirection.RIGHT : FacingDirection.NONE);
    }

    private static List<AbilityEntity> mapAbilityToHero(JSONArray jsonStateArray) {
        List<AbilityEntity> abilityEntities = new ArrayList<AbilityEntity>();
        JSONObject jsonAbilityObject;
        for (int i = 0; i < jsonStateArray.size(); i++) {
            jsonAbilityObject = (JSONObject) jsonStateArray.get(i);
            AbilityEntity abilityEntity = new AbilityEntity();
            abilityEntity.setAbilityName((String) jsonAbilityObject.get("abilityName"));
            abilityEntity.setXPos(Long.parseLong(jsonAbilityObject.get("xPos").toString()));
            abilityEntity.setYPos(Long.parseLong(jsonAbilityObject.get("yPos").toString()));
            abilityEntity.setWidth(Long.parseLong(jsonAbilityObject.get("width").toString()));
            abilityEntity.setHeight(Long.parseLong(jsonAbilityObject.get("height").toString()));
            abilityEntity.setDamage(Long.parseLong(jsonAbilityObject.get("damage").toString()));
            abilityEntities.add(abilityEntity);
        }
        return abilityEntities;
    }
}
