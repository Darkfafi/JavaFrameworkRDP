package gameEngine.ramses.audioManagment;

import gameEngine.ramses.assetsManagement.IResources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class WavAudio implements IResources{
	
	//createChannel("musicChannel");
	
	//createAudio("Test","tetSong.wav");
	//createAudio("Test2","isaacSong.wav");
	
	//private static Map<String,AudioInputStream> _audioList = new HashMap<String,AudioInputStream>();
	private Map<String,String> _audioList = new HashMap<String,String>();
	private Map<String,AudioChannel> _audioChannelsList = new HashMap<String,AudioChannel>();
	
	private String _baseAudioFolder = "/audio/";
	
	public WavAudio(){
		this("/audio/");
	}
	
	public WavAudio(String baseAudioFolder){
		_baseAudioFolder = baseAudioFolder;
	}
	
	@SuppressWarnings("unused")
	protected void createAudio(String name, String path) throws UnsupportedAudioFileException, IOException{
		//AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(WavAudio.class.getResource(_baseAudioFolder + path));
		_audioList.put(name,(_baseAudioFolder + path));
	}
	
	@SuppressWarnings("unused")
	protected void createChannel(String name) throws LineUnavailableException{
		AudioChannel channel = new AudioChannel();
		_audioChannelsList.put(name, channel);
	}
	
	public void playAudio(String nameChannel,String nameAudio){
		playAudio(nameChannel,nameAudio,0,100);
	}
	public void playAudio(String nameChannel,String nameAudio,int repeatAmount){
		playAudio(nameChannel,nameAudio,repeatAmount,100);	
	}
	public void playAudio(String nameChannel,String nameAudio, int repeatAmount,float volume){
		try {
			_audioChannelsList.get(nameChannel).play(_audioList.get(nameAudio),repeatAmount,volume);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopChannel(String nameChannel){
		_audioChannelsList.get(nameChannel).stop();
	}
	
	public void resumeChannel(String nameChannel){
		_audioChannelsList.get(nameChannel).resume();
	}
	
	public void setChannelVolume(String nameChannel,float volume){
		_audioChannelsList.get(nameChannel).setVolume(volume);
	}
}
