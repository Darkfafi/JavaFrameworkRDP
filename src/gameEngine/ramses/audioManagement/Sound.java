package gameEngine.ramses.audioManagement;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private AudioInputStream _audioInputStream;
	private AudioFormat _audioFormat;  
	private int _audioSize;
	private byte[] _audioBytes;
	private DataLine.Info _audioInfo;  
	
	public Sound(String audioLocation){
		try {
			_audioInputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(audioLocation));
			_audioFormat = _audioInputStream.getFormat();
			_audioSize = (int) (_audioFormat.getFrameSize() * _audioInputStream.getFrameLength());
			_audioBytes = new byte[_audioSize];
			_audioInfo = new DataLine.Info(Clip.class, _audioFormat, _audioSize);
			_audioInputStream.read(_audioBytes, 0, _audioSize);
		} catch (IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Clip getAudioClip() throws LineUnavailableException{
		Clip clip = (Clip)AudioSystem.getLine(_audioInfo);
		clip.open(_audioFormat,_audioBytes,0,_audioSize);
		return clip;
	}
	/*
	public ByteArrayOutputStream getCloneBytes(){
		return _cloneBytes;
	}*/
}
