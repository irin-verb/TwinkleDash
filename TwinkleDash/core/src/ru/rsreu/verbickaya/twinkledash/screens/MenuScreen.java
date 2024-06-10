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

public class MenuScreen extends ScreenAdapter {
    private TwinkleDash game;
    private Stage stage;
    private Label title;
    private TextButton playButton;
    private TextButton recordsButton;
    private TextButton exitButton;
    private TextButton authorButton;
    private Image background;

    public MenuScreen(TwinkleDash game) {
        Assets.playMenuMusic();
        this.game = game;
        stage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT));
        initBackground();
        initTitle();
        initButtons();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initBackground() {
        background = new Image(Assets.main_bckgrnd);
        background.setWidth(TwinkleDash.WIDTH);
        background.setHeight(TwinkleDash.WIDTH);
        background.setPosition(0,TwinkleDash.HEIGHT-TwinkleDash.WIDTH + 110);
        stage.addActor(background);
    }

    private void initTitle() {
        title = new Label("Twinkle Dash", new Label.LabelStyle(Assets.big_font, Color.BLACK));
        title.setPosition(TwinkleDash.CENTER_X, TwinkleDash.HEIGHT * 0.85f, Align.center);
        stage.addActor(title);
    }
    private void initButtons() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = Assets.font;
        buttonStyle.fontColor = Color.BLACK;

        playButton = new TextButton("Play", buttonStyle);
        recordsButton = new TextButton("Records", buttonStyle);
        exitButton = new TextButton("Exit", buttonStyle);
        authorButton = new TextButton("About author", buttonStyle);

        playButton.setPosition(TwinkleDash.CENTER_X, TwinkleDash.HEIGHT * 0.7f, Align.center);
        recordsButton.setPosition(TwinkleDash.CENTER_X, TwinkleDash.HEIGHT * 0.6f, Align.center);
        exitButton.setPosition(TwinkleDash.WIDTH * 0.91f, 108.0f, Align.center);
        authorButton.setPosition(TwinkleDash.WIDTH * 0.15f, TwinkleDash.HEIGHT * 0.1f, Align.center);

        initButtonListeners();

        stage.addActor(playButton);
        stage.addActor(recordsButton);
        stage.addActor(exitButton);
        stage.addActor(authorButton);
    }

    private void initButtonListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Assets.stopMenuMusic();
                game.setScreen(new GameplayScreen(game));
                Assets.playButtonSound();
            }
        });
        recordsButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new RecordsScreen(game));
                Assets.playButtonSound();
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        authorButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new AuthorScreen(game));
                Assets.playButtonSound();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

}