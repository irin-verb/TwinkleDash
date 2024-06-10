package ru.rsreu.verbickaya.twinkledash;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
//		config.setWindowedMode(TwinkleDash.WIDTH,TwinkleDash.HEIGHT);
		config.setResizable(false);
		config.setTitle("Twinkle Dash");
		new Lwjgl3Application(new TwinkleDash(), config);
	}
}
