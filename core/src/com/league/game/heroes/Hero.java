package com.league.game.heroes;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Builder;
import org.json.simple.JSONObject;

@Builder
public class Hero {
    private long xPos;
    private long yPos;
    private int size;

    public void update(JSONObject gameState) {
        xPos = (long) gameState.get("xPos");
        yPos = (long) gameState.get("yPos");
    }

    public  void draw(ShapeRenderer shape) {
        shape.rect(xPos, yPos, size/2, size);
    }

    public void animate(SpriteBatch spriteBatch, TextureRegion movementFrame) {
        spriteBatch.draw(movementFrame, xPos, yPos);
    }

}
