package gameEngine.ramses.gobalParts;

import gameEngine.ramses.engine.GameEngine;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AnimEntity extends SpriteEntity{
	
	public float animationSpeed = 1f;
	//public int animationFrameRate = 30;
	
	private ArrayList<Image> _animationSpriteSheet;
	
	private int _currentFrame = 0;
	private boolean _repeat = true;
	private boolean _playingAnimation = false;
	
	private Timer _animTimer = new Timer();
	
	//Idea: DrawWidth = currentSprite.width * (width / biggestImageWidth) // This also for height
	
	private void nextFrame(){
		if(_animationSpriteSheet != null && _animationSpriteSheet.size() > 0){
			_sprite = _animationSpriteSheet.get(_currentFrame);
			if(parentObject != null && getSprite() != null){
				if(_playingAnimation){
					if(_currentFrame < _animationSpriteSheet.size() - 1){
						_currentFrame ++;
					}else if(_repeat){
						_currentFrame = 0;
					}else{
						stop();
					}
				}
			}
			
			if(_playingAnimation){
				correctRunTimer();
			}
		}
	}
	
	@Override
	protected void setWidthAndHeight(int width, int height){
		
		Image currentSprite;
		int largestWidth = 0;
		int largestHeight = 0;
		
		if(_animationSpriteSheet != null){
			for(int i = 0; i < _animationSpriteSheet.size(); i++){
				currentSprite = _animationSpriteSheet.get(i);
				if(currentSprite.getWidth(null) > largestWidth){
					largestWidth = currentSprite.getWidth(null);
				}
				if(currentSprite.getHeight(null) > largestHeight){
					largestHeight = currentSprite.getHeight(null);
				}
			}
			//currentSprite =  _animationSpriteSheet.get(indexBiggestFrame); // biggest frame in size.
			
			super.setWidthAndHeight(largestWidth, largestHeight);
		}
	}
	
	public void setAnimationSheet(ArrayList<Image> spriteSheet){
		_animationSpriteSheet = spriteSheet;
		setSprite(_animationSpriteSheet.get(0));
		gotoAndPlay(0);
		
		/*
		if(_playingAnimation){
			gotoAndPlay(0);
		}else{
			gotoAndStop(0);
		}*/
	}
	
	public void gotoAndPlay(int frameIndex){
		gotoAndPlay(frameIndex,_repeat);
	}
	public void gotoAndPlay(int frameIndex, boolean repeat){
		_currentFrame = frameIndex;
		play(repeat);
	}
	
	public void gotoAndStop(int frameIndex){
		_currentFrame = frameIndex;
		_sprite = _animationSpriteSheet.get(_currentFrame);
		stop();
	}
	public void stop(){
		correctStopTimer();
		_playingAnimation = false;
	}
	
	public void play(){
		play(_repeat);
	}
	public void play(boolean repeat){
		correctStopTimer();
		_playingAnimation = true;
		_repeat = repeat;
		correctRunTimer();
	}
	public boolean isPlaying(){
		return _playingAnimation;
	}
	public boolean isRepeating(){
		return _repeat;
	}
	public int getCurrentFrame(){
		return _currentFrame + 1;
	}
	public int getTotalFrames(){
		return _animationSpriteSheet.size();
	}
	
	private void correctStopTimer(){
		if(_playingAnimation){
			_animTimer.cancel();
			_animTimer = new Timer();
		}
	}
	private void correctRunTimer(){
		_animTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				nextFrame();
		}}, 1000/(int)(GameEngine.getCurrentFrameRate() * animationSpeed));
	}
}
