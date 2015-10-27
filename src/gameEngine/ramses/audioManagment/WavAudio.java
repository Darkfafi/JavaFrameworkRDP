package gameEngine.ramses.audioManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class WavAudio {
	
	//private static Map<String,AudioInputStream> _audioList = new HashMap<String,AudioInputStream>();
	private static Map<String,AudioInputStream> _audioList = new HashMap<String,AudioInputStream>();
	private static Map<String,AudioChannel> _audioChannelsList = new HashMap<String,AudioChannel>();

	public static void load() throws IOException, UnsupportedAudioFileException, LineUnavailableException{
		
		createChannel("musicChannel");
		
		createAudio("Test","tetSong.wav");
		createAudio("Test2","isaacSong.wav");
	}
	
	
	private static void createAudio(String name, String path) throws UnsupportedAudioFileException, IOException{
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(WavAudio.class.getResource("/audio/" + path));
		_audioList.put(name,audioInputStream);
	}
	
	private static void createChannel(String name) throws LineUnavailableException{
		AudioChannel channel = new AudioChannel();
		_audioChannelsList.put(name, channel);
	}
	
	public static void playAudio(String nameChannel,String nameAudio){
		playAudio(nameChannel,nameAudio,100,0);
	}
	public static void playAudio(String nameChannel,String nameAudio,float volume){
		playAudio(nameChannel,nameAudio,volume,0);	
	}
	public static void playAudio(String nameChannel,String nameAudio,float volume, int repeatAmount){
		try {
			_audioChannelsList.get(nameChannel).play(_audioList.get(nameAudio),repeatAmount,volume);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void stopChannel(String nameChannel){
		_audioChannelsList.get(nameChannel).stop();
	}
	
	public static void resumeChannel(String nameChannel){
		_audioChannelsList.get(nameChannel).resume();
	}
	
	public static void setChannelVolume(String nameChannel,float volume){
		_audioChannelsList.get(nameChannel).setVolume(volume);
	}
}
