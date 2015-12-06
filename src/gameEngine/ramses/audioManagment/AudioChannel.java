package gameEngine.ramses.audioManagment;

import gameEngine.ramses.engine.FrameEvents;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioChannel extends EventDispatcher {
	
	private float _channelVolume = 100f;
	private ArrayList<ClipInfo> _currentAudioClips = new ArrayList<ClipInfo>();
	
	public AudioChannel(){
		GameEngine.getCoreListener().addEventListener(FrameEvents.ENTER_SECOND, getEventMethodData("checkSounds"));
	}
	
	@SuppressWarnings("unused")
	private void checkSounds(Event e){
		ClipInfo currentClip;
		for(int i = _currentAudioClips.size() - 1; i >= 0; i--){
			currentClip = _currentAudioClips.get(i);
			if(!currentClip.getClip().isRunning()){
				if(currentClip.timesRepeated >= currentClip.getRepeatAmount()){
				  currentClip.getClip().close();
				  _currentAudioClips.remove(i);
				}else{
					currentClip.timesRepeated ++;
					currentClip.getClip().setFramePosition(0);
					currentClip.getClip().start();
				}
			}
		}
	}
	
	public void play(String audio,int timesToRepeat,float volume) throws LineUnavailableException, IOException{
		Clip newClip;// = AudioSystem.getClip();
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(WavAudio.class.getResource(audio));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioFormat format = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		newClip = (Clip) AudioSystem.getLine(info);
		
		ClipInfo clipInfo;
		newClip.open(audioInputStream);
		setClipVolume(newClip,volume);
		newClip.start();
		clipInfo = new ClipInfo(newClip,timesToRepeat,volume);
		_currentAudioClips.add(clipInfo);
		//System.out.println(_currentAudioClips.size());
	}
	
	public void setVolume(float volume){
		ClipInfo currentClip;	
		_channelVolume = volume;
		for(int i = _currentAudioClips.size() - 1; i >= 0; i--){
			currentClip = _currentAudioClips.get(i);
			setClipVolume(currentClip.getClip(),currentClip.getVolume());
			if(currentClip.getClip().isRunning()){
				currentClip.getClip().start();
			}
		}
	}
	
	private void setClipVolume(Clip clip, float volume){
		FloatControl audioController = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		audioController.setValue((float)(Math.log((volume *(_channelVolume / 100)) / 100d) / Math.log(10.0) * 20.0));
	}
	
	public void stop(){
		for(int i = _currentAudioClips.size() - 1; i >= 0; i--){
			_currentAudioClips.get(i).getClip().stop();
		}
	}
	public void resume(){
		for(int i = _currentAudioClips.size() - 1; i >= 0; i--){
			_currentAudioClips.get(i).getClip().start();
		}
	}
}
