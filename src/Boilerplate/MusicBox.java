package Boilerplate;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicBox {

	static Clip Audio;
	public static FloatControl audioControl;
	
	MusicBox() {}
	
	public void PLAY (File SelectedAudio) {
		try {
			Audio = AudioSystem.getClip();
			Audio.open(AudioSystem.getAudioInputStream(SelectedAudio));
			Audio.start();
			audioControl = (FloatControl) Audio.getControl(FloatControl.Type.MASTER_GAIN);
			Audio.loop(20);
			//Thread.sleep(Audio.getMicrosecondLength()/1000);
		}
		catch (Exception e) {}
	}
	
	public void STOP () {
		Audio.stop();
		Audio.close();
		Audio.drain();
	}
	
	public static void Mute()   {
		audioControl.setValue(-80.0f);
	}
	
	public static void Unmute()   {
		audioControl.setValue(0.0f);
	}
	
	public void main (String [] args) {
		File TestAudio = new File("res/music/TestAudio.wav");
		PLAY(TestAudio);
	}
	
}