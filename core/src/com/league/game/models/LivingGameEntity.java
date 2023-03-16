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
        if (healthBarImage == null) {
            System.err.println("HealthBarImage not loaded!");
            return;
        }
        System.out.println("this.getWidth: " + this.getWidth());
        System.out.println("this.health: " + this.health);
        System.out.println("Max health: " + MAX_HEALTH);
        System.out.println("relu: " + relu(this.health/MAX_HEALTH));
        float healthBarWidth = this.getWidth() * relu(this.health / MAX_HEALTH);
        if (healthBarWidth < 0) {
            System.err.println("Health bar width is negative: " + healthBarWidth);
            return;
        }
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
