package ru.rsreu.verbickaya.twinkledash.actors.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;

import static java.lang.Math.abs;

public class Twinkle extends Actor {

    private static final int RADIUS = TwinkleDash.TWINKLE_RADIUS;
    private static final float GRAVITY = 900f;
    private static final float JUMP = 450f; // скорость прыжка
    private static float VELOCITY = 450f; // скорость движения влево/вправо
    private static float MAX_VELOCITY = 1000f; // предельная скорость
    private static float VELOCITY_STEP = 5f; // шаг увеличения скорости
    private float speed_y = JUMP;
    private float speed_x = VELOCITY;
    private Vector2 speed; // скорость
    private Vector2 speedUp; // ускорение
    private TextureRegion region;
    private Circle bounds; // границы (окружность) для коллизий
    private State state;


    public enum State { PREGAME, ALIVE, DYING, DEAD }



    public Twinkle() {
        region = new TextureRegion(Assets.getTwinkleTextureRegion(0));
        setWidth(RADIUS);
        setHeight(RADIUS);
        state = State.ALIVE;
        speed = new Vector2(VELOCITY, 0);
        speedUp = new Vector2(0, -GRAVITY);
        bounds = new Circle(getX() + getWidth() / 2, getY() + getHeight() / 2, RADIUS);
        setOrigin(Align.center);
    }


    public void changeColor(int level) {
        region = new TextureRegion(Assets.getTwinkleTextureRegion(level));
    }


    public void jump() {
        speed.y = speed_y;
        Assets.playJumpSound();
    }


    public void reflect() {
        speed_x *= -1;
        if (abs(speed_x) + VELOCITY_STEP <= MAX_VELOCITY) {
            if (speed_x < 0) speed_x -= VELOCITY_STEP; else speed_x += VELOCITY_STEP;
            speed_y = abs(speed_x);
            speedUp = new Vector2(0, -abs(speed_x)*2);
        }
        speed = new Vector2(speed_x, 0);
        Assets.playLevelSound();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        switch (state){
            case PREGAME:
                break;
            case ALIVE:
                actAlive(delta);
                break;
            case DEAD:
            case DYING:
                actDying(delta);
                break;
        }

        updateBounds();
    }




    private void actDying(float delta) {
        speedUp.y = -GRAVITY;
        applyAccel(delta);
        updatePosition(delta);
        controlBorders();
    }

    private void updateBounds() {
        bounds.setX(getX() + getWidth() / 2);
        bounds.setY(getY() + getHeight() / 2);
       // bounds.x = getX();
        //bounds.y = getY();
    }

    private void actAlive(float delta) {
        applyAccel(delta);
        updatePosition(delta);
        controlBorders();
    }



    private void controlBorders() {
        if (isBelowGround()) {
            setY(TwinkleDash.GROUND_HEIGHT);
            setState(State.DEAD);
        }
        if (isAboveCeiling()) {
            setY(TwinkleDash.HEIGHT - TwinkleDash.GROUND_HEIGHT - getHeight());
        }
        if (isBehindLeftWall()) {
            setX(TwinkleDash.WALL_WIDTH);
        }
        if (isBehindRightWall()) {
            setX(TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH - getWidth());
        }
    }

    public boolean isAboveCeiling() {
        return (getY(Align.top) >= TwinkleDash.HEIGHT-TwinkleDash.GROUND_HEIGHT);
    }
    public boolean isBelowGround() {
        return (getY(Align.bottom) <= TwinkleDash.GROUND_HEIGHT);
    }
    public boolean isBehindWalls() { return (getX(Align.left) <= TwinkleDash.WALL_WIDTH || getX(Align.right) >= TwinkleDash.WIDTH-TwinkleDash.WALL_WIDTH); }
    public boolean isBehindLeftWall() {
        return getX(Align.left) <= TwinkleDash.WALL_WIDTH;
    }
    public boolean isBehindRightWall() {
        return getX(Align.right) >= TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH;
    }



    private void updatePosition(float delta) {
        setX(getX() + speed.x * delta);
        setY(getY() + speed.y * delta);
    }

    private void applyAccel(float delta) {
        speed.add(speedUp.x * delta, speedUp.y * delta);
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.WHITE);
        switch (state){
            case ALIVE:
            case PREGAME:
                drawAlive(batch);
                break;
            case DEAD:
            case DYING:
                drawDead(batch);
                break;
        }

    }

    private void drawAlive(Batch batch) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    private void drawDead(Batch batch) {
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
        // Тут может измениться освещение (интенсивность) ((если будет))
    }

    public void setToDying() {
        Assets.playHitSound();

        addAction(Actions.delay(.25f,Actions.run(new Runnable() {
            @Override
            public void run() {
                Assets.playDieSound();
            }
        })));

        state = State.DYING;
        speed.y = 0;
    }


    public Circle getBounds() {
        return bounds;
    }

    public void setBounds(Circle bounds) {
        this.bounds = bounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
