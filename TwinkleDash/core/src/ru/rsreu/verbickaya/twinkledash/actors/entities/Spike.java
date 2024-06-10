package ru.rsreu.verbickaya.twinkledash.actors.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;
import com.badlogic.gdx.math.Polygon;

import static ru.rsreu.verbickaya.twinkledash.utils.Utils.getTrianglePeaks;

public class Spike extends Actor {

    private static final int WIDTH = TwinkleDash.SPIKE_WIDTH;
    private static final int HEIGHT = TwinkleDash.SPIKE_HEIGHT;
    private SpikeDirection direction;
    private TextureRegion region;
    private Polygon bounds; // границы для отслеживания коллизий


    public enum SpikeDirection {up, down, left, right};


    public Spike(SpikeDirection direction, int shift_x, int shift_y) {
        this.direction = direction;
        region = new TextureRegion(Assets.getSpikeTextureRegion(0, direction));
        float[] peaks = getTrianglePeaks(WIDTH, HEIGHT, shift_x, shift_y, direction);
        bounds = new Polygon(peaks);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setOrigin(Align.center);
    }

    public Spike(SpikeDirection direction, int shift_x, int shift_y, int level) {
        this.direction = direction;
        region = new TextureRegion(Assets.getSpikeTextureRegion(level, direction));
        float[] peaks = getTrianglePeaks(WIDTH, HEIGHT, shift_x, shift_y, direction);
        bounds = new Polygon(peaks);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setOrigin(Align.center);
    }


    public void changeColor(int level) {
        region = new TextureRegion(Assets.getSpikeTextureRegion(level, direction));
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


    public Polygon getBounds() {
        return bounds;
    }
    public void setBounds(Polygon bounds) {
        this.bounds = bounds;
    }


}