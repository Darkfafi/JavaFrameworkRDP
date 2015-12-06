package gameEngine.ramses.audioManagement;

import gameEngine.ramses.assetsManagement.IResources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class WavAudio implements IResources{
	
	//createChannel("musicChannel");
	
	//createAudio("Test","tetSong.wav");
	//createAudio("Test2","isaacSong.wav");
	
	//private static Map<String,AudioInputStream> _audioList = new HashMap<String,AudioInputStream>();
	private Map<String,Sound> _audioList = new HashMap<String,Sound>();
	private Map<String,AudioChannel> _audioChannelsList = new HashMap<String,AudioChannel>();
	
	private String _baseAudioFolder = "/audio/";
	
	public WavAudio(){
		this("/audio/");
	}
	
	public WavAudio(String baseAudioFolder){
		_baseAudioFolder = baseAudioFolder;
	}
	
	protected void createAudio(String name, String path) throws UnsupportedAudioFileException, IOException{
		//AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(WavAudio.class.getResource(_baseAudioFolder + path));
		Sound sound = new Sound(_baseAudioFolder + path);
		_audioList.put(name,sound);
	}
	
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
		} catch (LineUnavailableException | IOException
				| UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
