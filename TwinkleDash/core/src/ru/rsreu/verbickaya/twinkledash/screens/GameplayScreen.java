package ru.rsreu.verbickaya.twinkledash.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.actors.controllers.SpikesController;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Ground;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Spike;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Twinkle;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Wall;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;
import ru.rsreu.verbickaya.twinkledash.utils.RecordsProcessor;
import ru.rsreu.verbickaya.twinkledash.utils.Utils;

public class GameplayScreen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected TwinkleDash game;
    private Stage gameplayStage;
    private Stage stage;


    private State screenState = State.PREGAME;
    private enum State {PREGAME, PLAYING, DYING, DEAD}

    private boolean justTouched;
    private boolean allowRestart = false;
    private int score = 0;
    private int level = 0;


    private TextButton exitButton;
    private TextButton menuButton;
    private Label current_score_label;
    private Label current_score;
    private Label best_score_label;
    private Label best_score;
    private Label record_score_label;


    private Ground bottom_ground;
    private Ground upper_ground;
    private Wall right_wall;
    private Wall left_wall;
    private Image background;
    private Image whitePixel;


    private Twinkle twinkle;
    private SpikesController spikes;



    public GameplayScreen(TwinkleDash game) {

        this.game = game;
        Assets.playGameMusic();
        camera = new OrthographicCamera(TwinkleDash.WIDTH, TwinkleDash.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT, camera));
        stage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT));

        initWhitePixel();
        initBackground();
        initTwinkle();
        initBorders();
        initSpikes();
        initButtons();
        initLabels();
        initInputProcessor();

        gameplayStage.addActor(background);
        gameplayStage.addActor(twinkle);
        gameplayStage.addActor(bottom_ground);
        gameplayStage.addActor(upper_ground);
        gameplayStage.addActor(left_wall);
        gameplayStage.addActor(right_wall);
        for (Spike spike : spikes.getConstantSpikes())
            gameplayStage.addActor(spike);
        stage.addActor(exitButton);
        stage.addActor(menuButton);
        stage.addActor(current_score);
        stage.addActor(best_score);
        stage.addActor(best_score_label);
        stage.addActor(current_score_label);
    }

    private void initLabels() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.little_font, Assets.getTextColor(0));
        best_score_label = new Label("Best\nscore",labelStyle);
        best_score_label.setAlignment(Align.center);
        best_score_label.setPosition(TwinkleDash.WIDTH * 0.08f, TwinkleDash.HEIGHT * 0.8f, Align.center);
        current_score_label = new Label("Current\nscore",labelStyle);
        current_score_label.setAlignment(Align.center);
        current_score_label.setPosition(TwinkleDash.WIDTH * 0.92f, TwinkleDash.HEIGHT * 0.8f, Align.center);

        Label.LabelStyle messageLabelStyle = new Label.LabelStyle(Assets.font, Color.BLACK);
        record_score_label = new Label(game.getUserName() + ", your record is in top 10!\nCheck the records table.",messageLabelStyle);
        record_score_label.setAlignment(Align.center);
        record_score_label.setPosition(TwinkleDash.CENTER_X,TwinkleDash.CENTER_Y,Align.center);

        Label.LabelStyle numLabelStyle = new Label.LabelStyle(Assets.big_font, Assets.getTextColor(0));
        best_score = new Label(Integer.toString(game.getBestScore()), numLabelStyle);
        best_score.setPosition(TwinkleDash.WIDTH * 0.08f, TwinkleDash.HEIGHT * 0.7f, Align.center);
        best_score.setAlignment(Align.center);
        current_score = new Label(Integer.toString(score), numLabelStyle);
        current_score.setAlignment(Align.center);
        current_score.setPosition(TwinkleDash.WIDTH * 0.92f, TwinkleDash.HEIGHT * 0.7f, Align.center);
    }

    private void initWhitePixel() {
        whitePixel = new Image(Assets.white_pixel);
        whitePixel.setWidth(TwinkleDash.WIDTH);
        whitePixel.setHeight(TwinkleDash.HEIGHT);
    }

    private void initTwinkle() {
        twinkle = new Twinkle();
        twinkle.setPosition(TwinkleDash.WIDTH * .25f, TwinkleDash.HEIGHT / 2, Align.center);
        twinkle.addAction(Utils.getFloatyAction());
        twinkle.setState(Twinkle.State.PREGAME);
    }

    private void initBackground() {
        background = new Image(Assets.getBackgroundTexture(0));
        int square_side;
        if (TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH * 2 >
                TwinkleDash.HEIGHT - TwinkleDash.GROUND_HEIGHT * 2)
            square_side = TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH * 2;
        else square_side = TwinkleDash.HEIGHT - TwinkleDash.GROUND_HEIGHT * 2;
        background.setWidth(square_side);
        background.setHeight(square_side);
        background.setPosition(TwinkleDash.CENTER_X - square_side / 2, TwinkleDash.CENTER_Y - square_side / 2);
    }

    private void initBorders() {
        bottom_ground = new Ground();
        bottom_ground.setPosition(0, 0);
        upper_ground = new Ground();
        upper_ground.setPosition(0, TwinkleDash.HEIGHT - TwinkleDash.GROUND_HEIGHT);
        left_wall = new Wall();
        left_wall.setPosition(0, 0);
        right_wall = new Wall();
        right_wall.setPosition(TwinkleDash.WIDTH - TwinkleDash.WALL_WIDTH, 0);
    }

    private void initSpikes() {
        spikes = new SpikesController();
    }

    private void initButtons() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = Assets.font;
        buttonStyle.fontColor = Assets.getTextColor(0);
        exitButton = new TextButton("Exit", buttonStyle);
        menuButton = new TextButton("Menu", buttonStyle);
        exitButton.setPosition(TwinkleDash.WIDTH * 0.92f, TwinkleDash.HEIGHT * 0.1f, Align.center);
        menuButton.setPosition(TwinkleDash.WIDTH * 0.08f, TwinkleDash.HEIGHT * 0.1f, Align.center);
        initButtonListeners();
    }

    private void initButtonListeners() {
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Assets.stopGameMusic();
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




    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch (screenState) {
            case PREGAME:
                updateAndDrawStages();
                break;
            case PLAYING:
                renderPlaying();
                break;
            case DYING:
            case DEAD:
                renderDeadOrDying();
                break;
        }
    }

    private void renderDeadOrDying() {
        if (twinkle.getState() == Twinkle.State.DEAD) {
            screenState = State.DEAD;
            allowRestart = true;
        }
        updateAndDrawStages();
    }

    private void showWhiteScreen() {
        gameplayStage.addActor(whitePixel);
        whitePixel.addAction(Actions.fadeOut(5));
    }

    private void showRecordsMessage() {
        gameplayStage.addActor(record_score_label);
        record_score_label.addAction(Actions.fadeOut(5));
    }

    private void renderPlaying() {
        if (justTouched) {
            twinkle.jump();
            justTouched = false;
        }
        gameplayStage.act();
        stage.act();
        controlGameplay();
        gameplayStage.draw();
        stage.draw();
    }

    private void updateAndDrawStages() {
        gameplayStage.act();
        gameplayStage.draw();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        stage.dispose();
    }

    private void controlGameplay() {
        for (Spike spike : spikes.getConstantSpikes()) {
            if (Utils.isCollision(spike.getBounds(), twinkle.getBounds())) {
                stopTheWorld();
            }
        }
        for (Spike spike : spikes.getDynamicSpikes()) {
            if (Utils.isCollision(spike.getBounds(), twinkle.getBounds())) {
                stopTheWorld();
            }
        }
        if (twinkle.isBehindWalls() && twinkle.getState().equals(Twinkle.State.ALIVE)) {
            score++;
            twinkle.reflect();
            for (Spike spike : spikes.getDynamicSpikes()) spike.remove();
            spikes.changeSide(score);
            for (Spike spike : spikes.getDynamicSpikes()) gameplayStage.addActor(spike);
            if (getLevel(score) != level) {
                level = getLevel(score);
                updateLevelColors(level % TwinkleDash.LEVEL_COUNT);
            }
            updateCurrentScoreLabel();
            if (score > game.getBestScore()) {
                game.setBestScore(score);
                updateBestScoreLabel();
            }
        }
    }

    private void updateBestScoreLabel() {
        best_score.setText(game.getBestScore());
    }

    private void updateCurrentScoreLabel() {
        current_score.setText(score);
    }

    private int getLevel(int score) {
        return score / TwinkleDash.LEVEL_STEP;
    }

    private void stopTheWorld() {
        twinkle.setToDying();
        showWhiteScreen();
        screenState = State.DYING;
        if (RecordsProcessor.isInTop(score) && score != 0 && score == game.getBestScore()) {
            showRecordsMessage();
            RecordsProcessor.addRecord(game.getUserName(), game.getBestScore());
        }
    }

    private void initInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(gameplayStage);
        multiplexer.addProcessor(new InputAdapter() {
            // We only care about the touch down event
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                switch (screenState) {
                    case DYING:
                        justTouched = true;
                        break;

                    case DEAD:
                        if (allowRestart) {
                            Assets.playResetSound();
                            game.setScreen(new GameplayScreen(game));
                        }
                        justTouched = true;
                        break;

                    case PLAYING:
                        justTouched = true;
                        break;

                    case PREGAME:
                        justTouched = true;
                        screenState = State.PLAYING;
                        twinkle.setState(Twinkle.State.ALIVE);
                        twinkle.clearActions();
                        //gameplayStage.addActor(twinkle);
                        break;
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateLevelColors(int color_level) {
        //фон
        TextureRegion newBackgroundRegion;
        newBackgroundRegion = new TextureRegion(Assets.getBackgroundTexture(color_level));
        background.setDrawable(new TextureRegionDrawable(newBackgroundRegion));
        // сам шарик
        twinkle.changeColor(color_level);
        // стены и пики
        right_wall.changeColor(color_level);
        left_wall.changeColor(color_level);
        bottom_ground.changeColor(color_level);
        upper_ground.changeColor(color_level);
        spikes.changeColor(color_level);
        // шрифт на кнопках
        TextButton.TextButtonStyle buttonStyle = exitButton.getStyle();
        buttonStyle.fontColor = Assets.getTextColor(color_level);
        exitButton.setStyle(buttonStyle);
        menuButton.setStyle(buttonStyle);
        // label-ы
        Label.LabelStyle numLabelStyle = new Label.LabelStyle(Assets.big_font,Assets.getTextColor(color_level));
        best_score.setStyle(numLabelStyle);
        current_score.setStyle(numLabelStyle);
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.little_font,Assets.getTextColor(color_level));
        best_score_label.setStyle(labelStyle);
        current_score_label.setStyle(labelStyle);
    }

}
