package ru.rsreu.verbickaya.twinkledash.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;

public class NameScreen extends ScreenAdapter {
    private TwinkleDash game;
    private Stage stage;
    private Label title;
    private TextButton cancelButton;
    private TextButton acceptButton;
    private TextField nameField;

    public NameScreen(TwinkleDash game) {
        this.game = game;
        stage = new Stage(new StretchViewport(TwinkleDash.WIDTH, TwinkleDash.HEIGHT));

        initTitle();
        initButtons();
        initTextField();

        stage.setKeyboardFocus(nameField);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void initButtons() {
        TextButton.TextButtonStyle buttonStyle1 = new TextButton.TextButtonStyle();
        buttonStyle1.font = Assets.font;
        buttonStyle1.fontColor = Color.BLACK;
        cancelButton = new TextButton("Cancel", buttonStyle1);

        TextButton.TextButtonStyle buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.font = Assets.font;
        buttonStyle2.fontColor = Color.GRAY;
        acceptButton = new TextButton("OK", buttonStyle2);

        cancelButton.setPosition(TwinkleDash.CENTER_X + 500, TwinkleDash.CENTER_Y - 200, Align.center);
        acceptButton.setPosition(TwinkleDash.CENTER_X - 500, TwinkleDash.CENTER_Y - 200, Align.center);

        initButtonListeners();

        stage.addActor(cancelButton);
        stage.addActor(acceptButton);
    }

    private void initButtonListeners() {
        acceptButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if (!(nameField.getText().equals(null) || nameField.getText().equals(""))) {
                    game.setUserName(nameField.getText());
                    game.setScreen(new MenuScreen(game));
                    Assets.stopGameMusic();
                    Assets.playButtonSound();
                }
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void initTitle() {
        title = new Label("Please enter your name before the playing", new Label.LabelStyle(Assets.font, Color.BLACK));
        title.setPosition(TwinkleDash.CENTER_X, TwinkleDash.CENTER_Y + 200, Align.center);
        stage.addActor(title);
    }

    private void initTextField() {

        TextureRegion whitePixel = Assets.white_pixel;
        TextureRegionDrawable fieldBackground = new TextureRegionDrawable(whitePixel);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = Assets.big_font;
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = fieldBackground;

        nameField = new TextField("", textFieldStyle);
        nameField.setAlignment(Align.center);
        nameField.setSize(TwinkleDash.WIDTH*0.6f, TwinkleDash.HEIGHT*0.15f);
        nameField.setMaxLength(10);
        nameField.setPosition(TwinkleDash.CENTER_X, TwinkleDash.CENTER_Y,Align.center);

        initTextFieldFilter();
        stage.addActor(nameField);
    }

    private void initTextFieldFilter() {
        final char[] allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        nameField.setTextFieldFilter((textField, c) -> {
            for (char allowedChar : allowedCharacters) {
                if (c == allowedChar) {
                    return true;
                }
            }
            return false;
        });
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(247/255f, 240/255f, 234/255f, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        updateAcceptButton();
        stage.act();
        stage.draw();

    }
     private void updateAcceptButton() {
         TextButton.TextButtonStyle buttonStyle = acceptButton.getStyle();
         if (nameField.getText().equals(null) || nameField.getText().equals(""))
             buttonStyle.fontColor = Color.GRAY;
         else buttonStyle.fontColor = Color.BLACK;
         acceptButton.setStyle(buttonStyle);
     }

}
