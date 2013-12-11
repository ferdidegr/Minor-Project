package Game;

import java.io.IOException;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private static Audio background_menu, background_game;
	private static Audio button, jump;
//	private static SoundStore sounds;

	public static void init() {

		try {
			background_menu = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sound/background_menu.ogg"));
			background_game = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sound/background_x.ogg"));
			background_menu.playAsMusic(1f, 1f, true);
			button = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/button-3.wav"));
//			jump = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sound/jump.wav"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void playButton() {
		button.playAsSoundEffect(1f, 0.1f, false);
	}

	public static void playEffect(String string) {
		switch (string) {
		case "button": button.playAsSoundEffect(1f, 0.1f, false);
//		case "jump": jump.playAsSoundEffect(1f, 1f, false);
		}
	}

	public static void playMusic(String string) {
		switch (string) {
		case "background_menu": background_menu.playAsMusic(1f, 1f, true);
		case "background_game": background_game.playAsMusic(1f, 1f, true);
		}
	}

	public static void exit() {
		AL.destroy();
	}
	
	public static void pause(){
		SoundStore.get().pauseLoop();
	}
	
	public static void resume(){
		SoundStore.get().restartLoop();
	}

}
