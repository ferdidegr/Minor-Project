package Editor;

import java.io.IOException;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	private Audio background;
	private Audio button;

	public void init() {

		try {
			background = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/background.ogg"));
			background.playAsMusic(1f, 1f, true);
			button = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/button-3.wav"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playButton(){
		button.playAsSoundEffect(1f,0.1f,false);
	}
	
	public void exit(){
		AL.destroy();
	}
	
	

}
