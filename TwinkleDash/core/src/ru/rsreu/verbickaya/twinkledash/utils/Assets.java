package ru.rsreu.verbickaya.twinkledash.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import ru.rsreu.verbickaya.twinkledash.TwinkleDash;
import ru.rsreu.verbickaya.twinkledash.actors.entities.Spike;

import static ru.rsreu.verbickaya.twinkledash.utils.Utils.getTrianglePeaks;

public class Assets {

    private static final int RADIUS = TwinkleDash.TWINKLE_RADIUS;
    private static final int GROUND_WIDTH = TwinkleDash.GROUND_WIDTH;
    private static final int GROUND_HEIGHT = TwinkleDash.GROUND_HEIGHT;
    private static final int WALL_WIDTH = TwinkleDash.WALL_WIDTH;
    private static final int WALL_HEIGHT = TwinkleDash.WALL_HEIGHT;
    private static final int SPIKE_HEIGHT = TwinkleDash.SPIKE_HEIGHT;
    private static final int SPIKE_WIDTH = TwinkleDash.SPIKE_WIDTH;


    private static SpriteBatch batch;

    private static Sound hit_sound;
    private static Sound jump_sound;
    private static Sound die_sound;
    private static Sound newLevel_sound;
    private static Sound buttonClick_sound;
    private static Sound reset_sound;
    private static Music menu_music;
    private static Music game_music;


    public static TextureRegion white_pixel;
    public static Texture main_bckgrnd;
    public static Texture records_bckgrnd;
    public static Texture author_bckgrnd;
    public static Texture logo;


    private static Array<Texture> game_level_backgrounds;
    private static Array<Color> game_level_twinkleColors;
    private static Array<Color> game_level_bordersAndSpikesColors;
    private static Array<Color> game_level_textColors;


    public static BitmapFont big_font;
    public static BitmapFont font;
    public static BitmapFont little_font;


    public static final String RECORD_PATH = "text/records.txt";
    public static final String AUTHOR_PATH = "text/author.txt";


    public static void playButtonSound() { buttonClick_sound.play(); }
    public static void playHitSound(){
        hit_sound.play();
    }
    public static void playDieSound(){
        die_sound.play();
    }
    public static void playJumpSound() {
        jump_sound.play();
    }
    public static void playLevelSound() {
        newLevel_sound.play();
    }
    public static void playResetSound() { reset_sound.play(); }


    public static void playMenuMusic() { menu_music.setLooping(true); menu_music.play(); }
    public static void stopMenuMusic() { menu_music.stop(); }
    public static void playGameMusic() { game_music.setLooping(true); game_music.play(); }
    public static void stopGameMusic() { game_music.stop(); }



    public static TextureRegion getTwinkleTextureRegion(int level) {
        int i;
        if (level < game_level_twinkleColors.size) i = level;
        else i = game_level_twinkleColors.size - 1;
        return createCircleTexture(game_level_twinkleColors.get(i));
    }
    public static TextureRegion getGroundTextureRegion(int level) {
        int i;
        if (level < game_level_bordersAndSpikesColors.size) i = level;
        else i = game_level_bordersAndSpikesColors.size - 1;
        return createRectangleTexture(game_level_bordersAndSpikesColors.get(i), GROUND_WIDTH, GROUND_HEIGHT);
    }
    public static TextureRegion getWallTextureRegion(int level) {
        int i;
        if (level < game_level_bordersAndSpikesColors.size) i = level;
        else i = game_level_bordersAndSpikesColors.size - 1;
        return createRectangleTexture(game_level_bordersAndSpikesColors.get(i), WALL_WIDTH, WALL_HEIGHT);
    }
    public static TextureRegion getSpikeTextureRegion(int level, Spike.SpikeDirection direction) {
        int i;
        if (level < game_level_bordersAndSpikesColors.size) i = level;
        else i = game_level_bordersAndSpikesColors.size - 1;
        return createTriangleTexture(game_level_bordersAndSpikesColors.get(i), SPIKE_WIDTH, SPIKE_HEIGHT, direction);
    }
    public static Texture getBackgroundTexture(int level) {
        int i;
        if (level < game_level_backgrounds.size) i = level;
        else i = game_level_backgrounds.size - 1;
        return game_level_backgrounds.get(i);
    }
    public static Color getTextColor(int level) {
        int i;
        if (level < game_level_textColors.size) i = level;
        else i = game_level_textColors.size - 1;
        return game_level_textColors.get(i);
    }



    public static void load() {
        batch = new SpriteBatch();
        initFonts();
        initSoundsAndMusic();
        initBackgrounds();
    }





    private static void initFonts() {
        big_font = new BitmapFont(Gdx.files.internal("fonts/big_font.fnt"));
        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        little_font = new BitmapFont(Gdx.files.internal("fonts/little_font.fnt"));
    }

    private static void initSoundsAndMusic() {
        die_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/die.mp3"));
        hit_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
        jump_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        newLevel_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/level.wav"));
        buttonClick_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/button.wav"));
        reset_sound = Gdx.audio.newSound(Gdx.files.internal("sounds/reset.mp3"));

        game_music = Gdx.audio.newMusic(Gdx.files.internal("music/game.mp3"));
        menu_music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
    }

    private static void initBackgrounds() {
        main_bckgrnd = new Texture(Gdx.files.internal("backgrounds/menu.jpg"));
        author_bckgrnd = new Texture(Gdx.files.internal("backgrounds/author.jpg"));
        records_bckgrnd = new Texture(Gdx.files.internal("backgrounds/records.jpg"));
        logo = new Texture(Gdx.files.internal("backgrounds/logo.jpg"));
        initLevelColors();
    }


    private static void initLevelColors() {

        white_pixel = createRectangleTexture(Color.WHITE, WALL_HEIGHT, GROUND_WIDTH);

        game_level_backgrounds = new Array<Texture>();
        game_level_twinkleColors = new Array<Color>();
        game_level_bordersAndSpikesColors = new Array<Color>();
        game_level_textColors = new Array<Color>();

        String path = "backgrounds/game_levels/";

        // уровень 0
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "light/white_gray.jpg")));
        //game_level_twinkleColors.add(new Color(230/255f,182/255f,170/255f,1)); // ОЧЕНЬ КРАСИВЫЙ ЦВЕТ
        game_level_twinkleColors.add(new Color(64/255f,69/255f,75/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(104/255f,109/255f,115/255f,1));
        game_level_textColors.add(new Color(230/255f,230/255f,232/255f,1));

        // уровень 1
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "light/blue_pink.jpg")));
        game_level_twinkleColors.add(new Color(83/255f,118/255f,130/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(110/255f,156/255f,172/255f,1));
        game_level_textColors.add(new Color(218/255f,234/255f,237/255f,1));

        // уровень 2
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "dark/black_white.jpg")));
        game_level_twinkleColors.add(new Color(1,1,1,1));
        game_level_bordersAndSpikesColors.add(new Color(94/255f,105/255f, 0.45882353f,1));
        game_level_textColors.add(new Color(1,1,1,1));

        // уровень 3
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "light/yellow_pink.jpg")));
        game_level_twinkleColors.add(new Color(117/255f,112/255f,94/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(255/255f,105/255f,86/255f,1));
        game_level_textColors.add(new Color(255/255f,240/255f,212/255f,1));

        // уровень 4
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "dark/blue_pink.jpg")));
        game_level_twinkleColors.add(new Color(245/255f,161/255f,150/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(250/255f,133/255f,139/255f,1));
        game_level_textColors.add(new Color(14/255f,42/255f,54/255f,1));

        // уровень 5
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "light/pink_blue.jpg")));
        game_level_twinkleColors.add(new Color(14/255f,25/255f,33/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(35/255f,62/255f,82/255f,1));
        game_level_textColors.add(new Color(243/255f,166/255f,158/255f,1));

        // уровень 6
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "dark/green_yellow.jpg")));
        game_level_twinkleColors.add(new Color(255/255f,242/255f,170/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(90/255f,125/255f,93/255f,1));
        game_level_textColors.add(new Color(15/255f,23/255f,20/255f,1));

        // уровень 7
        game_level_backgrounds.add(new Texture(Gdx.files.internal(path + "dark/red_blue.jpg")));
        game_level_twinkleColors.add(new Color(95/255f,235/255f,222/255f,1));
        game_level_bordersAndSpikesColors.add(new Color(247/255f,31/255f,34/255f,1));
        game_level_textColors.add(new Color(72/255f,1/255f,31/255f,1));
    }




    private static TextureRegion createCircleTexture(Color color) {
        Pixmap pixmap = new Pixmap(RADIUS * 2, RADIUS * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(RADIUS, RADIUS, RADIUS);
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        pixmap.dispose();
        return textureRegion;
    }

    private static TextureRegion createRectangleTexture(Color color, int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        pixmap.dispose();
        return textureRegion;
    }

    private static TextureRegion createTriangleTexture(Color color, int width, int height, Spike.SpikeDirection direction) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        float[] peaks = getTrianglePeaks(width, height, direction);
        pixmap.fillTriangle((int) peaks[0],(int) peaks[1],(int) peaks[2],
                (int) peaks[3],(int) peaks[4],(int) peaks[5]);
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        pixmap.dispose();
        return textureRegion;
    }





    public static void dispose(){
        if (batch != null) {
            batch.dispose();
        }

        hit_sound.dispose();
        jump_sound.dispose();
        die_sound.dispose();
        newLevel_sound.dispose();
        buttonClick_sound.dispose();
        reset_sound.dispose();

        menu_music.dispose();
        game_music.dispose();

        main_bckgrnd.dispose();
        author_bckgrnd.dispose();
        records_bckgrnd.dispose();
        logo.dispose();
        for (Texture texture: game_level_backgrounds) {
            texture.dispose();
        }

        big_font.dispose();
        font.dispose();
        little_font.dispose();
    }


}