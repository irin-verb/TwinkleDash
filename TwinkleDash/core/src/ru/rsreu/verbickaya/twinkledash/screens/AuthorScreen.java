package ru.rsreu.verbickaya.twinkledash.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;
import ru.rsreu.verbickaya.twinkledash.utils.Utils;

public class AuthorScreen extends ScreenAdapter {
    private TwinkleDash game;
    private Stage stage;
    private Label title;
    private Label text;
    private TextButton exitButton;
    private TextButton menuButton;
    private Image background;

    public AuthorScreen(TwinkleDash game) {
        this.game = game;
        stage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT));

        initBackground();
        initText();
        initTitle();
        initButtons();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initButtons() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = Assets.font;
        buttonStyle.fontColor = Color.BLACK;

        exitButton = new TextButton("Exit", buttonStyle);
        menuButton = new TextButton("Menu", buttonStyle);

        exitButton.setPosition(TwinkleDash.WIDTH * 0.91f, 108.0f, Align.center);
        menuButton.setPosition(TwinkleDash.WIDTH * 0.09f, TwinkleDash.HEIGHT * 0.1f, Align.center);

        initButtonListeners();

        stage.addActor(exitButton);
        stage.addActor(menuButton);
    }

    private void initButtonListeners() {
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                Assets.playButtonSound();
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void initTitle() {
        title = new Label("About author", new Label.LabelStyle(Assets.font, Color.BLACK));
        title.setPosition(TwinkleDash.CENTER_X, TwinkleDash.HEIGHT * 0.85f, Align.center);
        stage.addActor(title);
    }

    private void initText() {
        text = new Label(Utils.getAuthorText(), new Label.LabelStyle(Assets.little_font, Color.BLACK));
        text.setWrap(true);
        text.setWidth(TwinkleDash.WIDTH * 0.5f);
        text.setPosition(TwinkleDash.CENTER_X, TwinkleDash.HEIGHT * 0.65f, Align.center);
        stage.addActor(text);
    }

    private void initBackground() {
        background = new Image(Assets.author_bckgrnd);
        background.setWidth(TwinkleDash.WIDTH);
        background.setHeight(TwinkleDash.WIDTH);
        background.setPosition(0,TwinkleDash.HEIGHT-TwinkleDash.WIDTH + 120);
        stage.addActor(background);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

}
