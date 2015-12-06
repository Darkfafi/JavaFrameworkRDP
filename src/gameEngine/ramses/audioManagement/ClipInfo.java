package gameEngine.ramses.audioManagement;

import javax.sound.sampled.Clip;

public class ClipInfo {
	
	public int timesRepeated = 0;
	
	private Clip _clip;
	private int _repeatAmount;
	private float _volume;
	
	public ClipInfo(Clip clip,int repeatAmount, float volume){
		_clip = clip;
		_repeatAmount = repeatAmount;
		_volume = volume;
	}
	
	public Clip getClip(){
		return _clip;
	}
	public int getRepeatAmount(){
		return _repeatAmount;
	}
	public float getVolume(){
		return _volume;
	}
}
