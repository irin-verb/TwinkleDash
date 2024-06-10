package ru.rsreu.verbickaya.twinkledash.actors.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;

public class Wall  extends Actor {

    private static final float WIDTH = TwinkleDash.WALL_WIDTH;
    private static final float HEIGHT = TwinkleDash.WALL_HEIGHT;
    private TextureRegion region;

    public Wall() {
        region = new TextureRegion(Assets.getWallTextureRegion(0));
        setWidth(WIDTH);
        setHeight(HEIGHT);
    }

    public void changeColor(int level) {
        region = new TextureRegion(Assets.getWallTextureRegion(level));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }
    public TextureRegion getRegion() {
        return region;
    }
}