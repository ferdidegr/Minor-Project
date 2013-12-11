package Utils;

import java.io.IOException;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private static Audio background_menu, background_game;
	private static Audio button, jump, hurt;
	private static boolean on = true;

	// private static SoundStore sounds;

	/**
	 * initialize and load all sounds and music
	 */
	public static void init() {

		try {
			background_menu = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sound/background_menu.ogg"));
			background_game = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sound/background_x.ogg"));
			
			hurt = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sound/hurt.ogg"));
			button = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/button-3.wav"));
			// jump = AudioLoader.getAudio("WAV",
			// ResourceLoader.getResourceAsStream("res/sound/jump.wav"));
			
			//start background music
			if(on)
			background_menu.playAsMusic(1f, 1f, true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * play a sound effect
	 * 
	 * @param string
	 *            name of sound effect to be played
	 */
	public static void playEffect(String string) {
		if (on) {
			switch (string) {
			case "button":
				button.playAsSoundEffect(1f, 0.1f, false);
				// case "jump": jump.playAsSoundEffect(1f, 1f, false);
			case "hurt":
				hurt.playAsSoundEffect(1f, 1f, false);
			}
		}
	}

	/**
	 * play background music
	 * 
	 * @param string
	 *            name of music to be played
	 */
	public static void playMusic(String string) {
		if (on) {
			switch (string) {
			case "background_menu":
				background_menu.playAsMusic(1f, 1f, true);
			case "background_game":
				background_game.playAsMusic(1f, 1f, true);
			}
		}
	}

	/**
	 * exit and cleanup AL
	 */
	public static void exit() {
		AL.destroy();
	}

	/**
	 * pause the current background music
	 */
	public static void pause() {
		SoundStore.get().pauseLoop();
	}

	/**
	 * restart the paused music
	 */
	public static void resume() {
		SoundStore.get().restartLoop();
	}

}
