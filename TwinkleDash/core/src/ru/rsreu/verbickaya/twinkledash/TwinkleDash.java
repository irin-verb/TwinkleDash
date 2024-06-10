package ru.rsreu.verbickaya.twinkledash;

import com.badlogic.gdx.*;
import ru.rsreu.verbickaya.twinkledash.screens.GreetingScreen;
import ru.rsreu.verbickaya.twinkledash.utils.Assets;

public class TwinkleDash extends Game {

	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int CENTER_X = WIDTH/2;
	public static final int CENTER_Y = HEIGHT/2;
	public static final int TWINKLE_RADIUS = 30;
	public static final int GROUND_HEIGHT = 20;
	public static final int GROUND_WIDTH = WIDTH;
	public static final int WALL_HEIGHT = HEIGHT;
	public static final int WALL_WIDTH = 300;
	public static final int SPIKE_WIDTH = 88;
	public static final int SPIKE_HEIGHT = SPIKE_WIDTH;
	public static final int LEVEL_STEP = 4;
	public static final int LEVEL_COUNT = 8;


	private int bestScore = 0;
	private String userName;


	@Override
	public void create () {
		Assets.load();
		setScreen(new GreetingScreen(this));
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}


	public void setBestScore(int score) {
		this.bestScore = score;
	}
	public int getBestScore() {
		return this.bestScore;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}

