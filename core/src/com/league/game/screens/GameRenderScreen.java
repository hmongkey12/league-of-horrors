package com.league.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.league.game.Handlers.StateHandler;
import com.league.game.Handlers.UDPInputHandler;
import com.league.game.Handlers.UDPUpdateHandler;
import com.league.game.LeagueOfHorrors;
import com.league.game.Handlers.InputHandler;
import com.league.game.enums.FacingDirection;
import com.league.game.models.HeroGameEntity;
import com.league.game.utils.ImageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GameRenderScreen extends ScreenAdapter {
    private static final String BACKGROUND_IMAGE_NAME = "background.png";
    private static final String ABILITY_ONE_SUFFIX = "_1.png";
    private static final String IDLE_SUFFIX = "_idle.png";
    private static final String MOVING_SUFFIX = "_moving.png";
    private static final String HEALTH_BAR_NAME = "health.png";

    private TextureRegion abilityFrame;
    private final LeagueOfHorrors gameManager;
    private final Viewport viewport;
    private final Camera playerCamera = new OrthographicCamera();
    private final TextureRegion backgroundTextureRegion;
    private float animationDuration;

    private BitmapFont font;



    public GameRenderScreen(LeagueOfHorrors gameManager) {
        this.gameManager = gameManager;
        backgroundTextureRegion = new TextureRegion(gameManager.assetManager.get(BACKGROUND_IMAGE_NAME, Texture.class));
        viewport = new FitViewport(gameManager.VIEW_PORT_WIDTH, gameManager.VIEW_PORT_HEIGHT, playerCamera);
    }

    @Override
    public void show() {
        playerCamera.position.set(new Vector3(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0));

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        gameManager.spriteBatch.setProjectionMatrix(playerCamera.combined);
        UDPInputHandler.handleInput(gameManager);
        Map<String, String> command = new HashMap<String, String>();
        command.put("getUpdate", gameManager.getPlayerId());
        gameManager.udpNetworkHandler.sendData(JSONObject.toJSONString(command));
        UDPUpdateHandler.handleUpdate(gameManager);
        gameManager.getSpriteBatch().begin();
        gameManager.getSpriteBatch().draw(backgroundTextureRegion, 0, 0);
        renderHeroes();
        gameManager.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
       viewport.update(width, height);
    }

    @Override
    public void hide() {
    }

    private void renderHeroes() {
        animationDuration += Gdx.graphics.getDeltaTime();
        String heroName = "";
        Map<String, HeroGameEntity> localHeroGameState = gameManager.heroStateQueue.poll();
        if (localHeroGameState != null) {
            for (Map.Entry<String, HeroGameEntity> hero : localHeroGameState.entrySet()) {
                heroName = hero.getValue().getHeroName();
                try {
                    if (hero.getKey().equals(gameManager.playerId)) {
                        playerCamera.position.x = hero.getValue().getXPos();
                        playerCamera.update();
                    }
                    TextureRegion currentFrame;
                    if (hero.getValue().isMoving()) {
                        currentFrame = gameManager.animationMap.get(heroName).get(heroName + MOVING_SUFFIX).getKeyFrame(animationDuration/2, true);
                    } else if (hero.getValue().isAttacking()) {
                        currentFrame = gameManager.animationMap.get(heroName).get(heroName + IDLE_SUFFIX).getKeyFrame(animationDuration/2, true);
                        abilityFrame = gameManager.animationMap.get(heroName).get(heroName + ABILITY_ONE_SUFFIX).getKeyFrame(animationDuration/2, true);
                    } else {
                        currentFrame = gameManager.animationMap.get(heroName).get(heroName + IDLE_SUFFIX).getKeyFrame(animationDuration/2, true);
                        abilityFrame = null;
                    }
                    hero.getValue().setEntityImage(currentFrame);
                    if (hero.getValue().getHealthBarImage() == null) {
                        hero.getValue().setHealthBarImage(ImageProcessor.getImageStill(HEALTH_BAR_NAME, gameManager.assetManager));
                    }
                    hero.getValue().draw(gameManager.spriteBatch);

                    if (abilityFrame != null && hero.getValue().getFacingDirection().equals(FacingDirection.LEFT)) {
                        hero.getValue().getAbilities().get(0).setEntityImage(abilityFrame);
                        hero.getValue().getAbilities().get(0).draw(gameManager.spriteBatch);
                    } else if ( abilityFrame != null && hero.getValue().getFacingDirection().equals(FacingDirection.RIGHT)) {
                        hero.getValue().getAbilities().get(0).setEntityImage(abilityFrame);
                        hero.getValue().getAbilities().get(0).draw(gameManager.spriteBatch);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }


        }
    }

}
