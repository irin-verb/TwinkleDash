package ru.rsreu.verbickaya.twinkledash.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;

public class GreetingScreen extends ScreenAdapter {
    private TwinkleDash game;
    private Stage stage;
    private Image greeting;

    public GreetingScreen(TwinkleDash game) {
        Assets.playGameMusic();
        this.game = game;
        stage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT));
        greeting = new Image(Assets.logo);
        greeting.setPosition(TwinkleDash.CENTER_X,TwinkleDash.CENTER_Y, Align.center);
        stage.addActor(greeting);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new NameScreen(game));
            }
        }, 2.5f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(247/255f, 240/255f, 234/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
}
