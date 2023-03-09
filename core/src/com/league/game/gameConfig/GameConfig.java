package com.league.game.gameConfig;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.league.game.models.AbilityEntity;
import com.league.game.utils.ImageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "com.league.game")
public class GameConfig {

    @Bean
    public AssetManager assetManager() {
        AssetManager assetManager = new AssetManager();
        String[] assetNames = {"background.png", "pumpkin_idle.png",
                "pumpkin_moving.png", "pumpkin_1.png",
                "pumpkin_2.png", "reaper_1.png",
                "reaper_2.png", "reaper_moving.png", "reaper_idle.png",
                "reaper_selection.png", "pumpkin_selection.png", "loading.png", "health.png"
        };
        for (String assets : assetNames) {
            assetManager.load(assets, Texture.class);
        }
        assetManager.finishLoading();
        return assetManager;
    }



    @Bean
    @Autowired
    public Map<String, List<AbilityEntity>> abilityEntityMap(AssetManager assetManager) {
        List<AbilityEntity> abilityEntityList = new ArrayList<AbilityEntity>();
        Map<String, List<AbilityEntity>> abilityEntityMap = new HashMap<String, List<AbilityEntity>>();
        String[][] heroAbilitiesList = {{"pumpkin_1.png", "pumpkin_2.png"}, {"reaper_1.png", "reaper_2.png"}};
        AbilityEntity abilityEntity;
        for ( String[] heroAbilities : heroAbilitiesList) {
            String heroName = null;
            for (String heroAbility : heroAbilities) {
                heroName = heroAbility.split("_", -2)[0];
                abilityEntity = new AbilityEntity();
                abilityEntity.setAbilityName(heroAbility);
                abilityEntityList.add(abilityEntity);
            }
            abilityEntityMap.put(heroName, abilityEntityList);
        }
        return abilityEntityMap;
    }

    @Bean
    @Autowired
    public Map<String, Map<String, Animation<TextureRegion>>> animationMap(AssetManager assetManager) {
        Map<String, Map<String, Animation<TextureRegion>>> animMap = new HashMap<String, Map<String, Animation<TextureRegion>>>();
        String[][] heroImages = {{"pumpkin_1.png", "pumpkin_2.png", "pumpkin_idle.png", "pumpkin_moving.png"},
                {"reaper_1.png", "reaper_2.png", "reaper_idle.png", "reaper_moving.png"}};
        for (String[] heroImage : heroImages) {
            String heroName = "";
            Map<String, Animation<TextureRegion>> heroAnimations = new HashMap<String, Animation<TextureRegion>>();
            for (String name : heroImage) {
                heroName = name.split("_", -2)[0];
                Animation<TextureRegion> animation = ImageProcessor.getImageAnimation(name, assetManager);
                heroAnimations.put(name, animation);
            }
            animMap.put(heroName, heroAnimations);
        }
        return animMap;
    }
}
