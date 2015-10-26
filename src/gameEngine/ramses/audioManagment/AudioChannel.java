package gameEngine.ramses.audioManagment;

import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class AudioChannel extends EventDispatcher {
	
	private float _channelVolume = 100f;
	private ArrayList<ClipInfo> _currentAudioClips = new ArrayList<ClipInfo>();
	
	public AudioChannel(){
		addEventListener(FrameworkConsts.ENTER_SECOND, getEventMethodData("checkSounds"));
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
	
	public void play(AudioInputStream audio,int timesToRepeat,float volume) throws LineUnavailableException, IOException{
		Clip newClip = AudioSystem.getClip();
		ClipInfo clipInfo;
		newClip.open(audio);
		setClipVolume(newClip,volume);
		newClip.start();
		clipInfo = new ClipInfo(newClip,timesToRepeat,volume);
		_currentAudioClips.add(clipInfo);
	}
	
	public void setVolume(float volume){
		ClipInfo currentClip;	
		_channelVolume = volume;
		for(int i = _currentAudioClips.size() - 1; i >= 0; i--){
			currentClip = _currentAudioClips.get(i);
			setClipVolume(currentClip.getClip(),currentClip.getVolume());
			currentClip.getClip().start();
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
