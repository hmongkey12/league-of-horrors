package com.league.game.Handlers;

import com.league.game.enums.FacingDirection;
import com.league.game.models.AbilityEntity;
import com.league.game.models.HeroGameEntity;
import com.serializers.SerializableAbilityEntity;
import com.serializers.SerializableGameState;
import com.serializers.SerializableHeroEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UDPStateHandler {
    public static Map<String, HeroGameEntity> replicateServerState(SerializableGameState gameState) {
        Map<String, HeroGameEntity> newPlayersOnClient = new HashMap<String, HeroGameEntity>();
        for (SerializableHeroEntity serializableHeroEntity : gameState.getConnectedPlayers().values()) {
            HeroGameEntity heroGameEntity = new HeroGameEntity();
            heroGameEntity.setHeroName(serializableHeroEntity.getHeroName());
            heroGameEntity.setHeroId(serializableHeroEntity.getId());
            heroGameEntity.setXPos(serializableHeroEntity.getXPos());
            heroGameEntity.setYPos(serializableHeroEntity.getYPos());
            heroGameEntity.setMoving(serializableHeroEntity.isMoving());
            heroGameEntity.setAttacking(serializableHeroEntity.isAttacking());
            heroGameEntity.setWidth(serializableHeroEntity.getWidth());
            heroGameEntity.setHeight(serializableHeroEntity.getHeight());
            heroGameEntity.setFacingDirection(getFacingDirection(serializableHeroEntity.getFacingDirection()));
            heroGameEntity.setAbilities(mapAbilityToHero(serializableHeroEntity.getAbilities()));
            newPlayersOnClient.put(serializableHeroEntity.getId(), heroGameEntity);
        }
        return newPlayersOnClient;
    }

    private static FacingDirection getFacingDirection(String facingDirection) {
        return FacingDirection.valueOf(facingDirection.toUpperCase());
    }

    private static List<AbilityEntity> mapAbilityToHero(List<SerializableAbilityEntity> serializableAbilityEntities) {
        List<AbilityEntity> abilityEntities = new ArrayList<AbilityEntity>();
        for (SerializableAbilityEntity serializableAbilityEntity : serializableAbilityEntities) {
            AbilityEntity abilityEntity = new AbilityEntity();
            abilityEntity.setAbilityName(serializableAbilityEntity.getAbilityName());
            abilityEntity.setXPos(serializableAbilityEntity.getXPos());
            abilityEntity.setYPos(serializableAbilityEntity.getYPos());
            abilityEntity.setWidth(serializableAbilityEntity.getWidth());
            abilityEntity.setHeight(serializableAbilityEntity.getHeight());
            abilityEntity.setDamage((long) serializableAbilityEntity.getDamage());
            abilityEntities.add(abilityEntity);
        }
        return abilityEntities;
    }
}
