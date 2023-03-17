package com.league.game.models;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LivingGameEntity extends GameEntity{
    private long health = 1000;
    private final long MAX_HEALTH = 1000;
    private TextureRegion healthBarImage;

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.getEntityImage(), this.getXPos(), this.getYPos(), this.getWidth(), this.getHeight());
        float healthWidth = this.getWidth() * relu((float) this.health / MAX_HEALTH);
        float healthHeight = this.getHeight()/20;
        spriteBatch.draw(healthBarImage, this.getXPos(), this.getYPos() + (this.getHeight() * 1.02f), healthWidth, healthHeight);
    }

    private float relu(float value) {
       if (value < 0)  {
           return 0f;
       }
       else {
           return value;
       }
    }
}
