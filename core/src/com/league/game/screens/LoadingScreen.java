package com.league.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.league.game.Handlers.UDPCreationHandler;
import com.league.game.Handlers.UDPNetworkHandler;
import com.league.game.LeagueOfHorrors;
import com.league.game.utils.ImageProcessor;

public class LoadingScreen extends ScreenAdapter {
    private final static String LOADING_IMAGE_NAME = "loading.png";
    private LeagueOfHorrors gameManager;
    private final float ANIMATION_DURATION = .2f;

    private Animation<TextureRegion> loadingAnimation;

    public LoadingScreen(LeagueOfHorrors gameManager) {
       this.gameManager = gameManager;
    }
    @Override
    public void show() {
        if (gameManager.udpNetworkHandler == null) {
            gameManager.udpNetworkHandler = new UDPNetworkHandler();
            if (gameManager.udpNetworkHandler.getClientSocket() != null) {
                gameManager.setScreen(new HeroSelectionScreen(gameManager));
            }
        }
        else if (!gameManager.isHeroCreated) {
            UDPCreationHandler.handleCreation(gameManager);
        }
        loadingAnimation = ImageProcessor.getImageAnimation(LOADING_IMAGE_NAME, gameManager.assetManager);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        if (gameManager.isHeroCreated) {
            gameManager.setScreen(new GameRenderScreen(gameManager));
        }
        gameManager.spriteBatch.begin();
        gameManager.spriteBatch.draw(loadingAnimation.getKeyFrame(ANIMATION_DURATION, true),
                gameManager.VIEW_PORT_WIDTH/2, gameManager.VIEW_PORT_HEIGHT/2,
                gameManager.VIEW_PORT_WIDTH/2, gameManager.VIEW_PORT_HEIGHT/2);
        gameManager.spriteBatch.end();
    }

    @Override
    public void hide() {}
}
