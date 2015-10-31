package gameEngine.ramses.utils;

import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;

public class Timer extends EventDispatcher{
	
	public static final String TIMER_ENDED = "timerEndedEvent";
	public static final String TIMER_TIK = "timerTikEvent";
	public static final String TIMER_REPEAT = "timerRepeatEvent";
	
	
	private boolean _running = false;
	private boolean _paused = false;
	
	private float _timeGivenInSeconds = 0;
	private int _givenRepeatAmount = 0;
	
	private double _timePassedInSec = 0; 
	private int _timesRepeated = 0;
	
	public void start(float timeInSeconds, int timesToRepeat){
		if(_timeGivenInSeconds != 0){
			stop();
		}
		GameEngine.getCoreListener().addEventListener(FrameworkConsts.ENTER_FRAME, getEventMethodData("tik"));
		_timeGivenInSeconds = timeInSeconds;
		_givenRepeatAmount = timesToRepeat;
		_running = true;
	}
	public void stop(){
		GameEngine.getCoreListener().removeEventListener(FrameworkConsts.ENTER_FRAME, getEventMethodData("tik"));
		_timePassedInSec = 0;
		_running = false;
		_paused = false;
	}
	public void pause(){
		_paused = true;
		_running = false;
	}
	public void reset(){
		stop();
		start(_timeGivenInSeconds,_givenRepeatAmount);
	}
	public void resume(){
		if(_paused){
			_running = true;
			_paused = false;
		}
	}
	
	@SuppressWarnings("unused")
	private void tik(Event e){
		if(_running){
			_timePassedInSec += GameEngine.getDeltaTime();
			//System.out.println(_timePassedInSec);
			if(_timePassedInSec >= _timeGivenInSeconds){
				this.dispatchEvent(new Event(TIMER_TIK));
				if(_timesRepeated <_givenRepeatAmount){
					_timePassedInSec = 0;
					_timesRepeated ++;
					this.dispatchEvent(new Event(TIMER_REPEAT));
				}else{
					stop();
					this.dispatchEvent(new Event(TIMER_ENDED));
				}
			}
		}
	}
}
