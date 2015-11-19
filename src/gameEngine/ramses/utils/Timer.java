package gameEngine.ramses.utils;

import gameEngine.ramses.engine.Framework;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;

public class Timer extends EventDispatcher{
	
	//PUBLIC FINALS
	public static final String TIMER_ON_END = "timerEndedEvent";
	public static final String TIMER_ON_TIK = "timerTikEvent";
	public static final String TIMER_ON_REPEAT = "timerRepeatedEvent";
	public static final String TIMER_ON_RESUME = "timerRepeatedEvent";
	public static final String TIMER_ON_PAUSE = "timerRepeatedEvent";
	
	private boolean _running = false;
	private boolean _paused = false;
	
	private float _timeGivenInSeconds = 0;
	private int _givenRepeatAmount = 0;
	
	private double _timePassedInSec = 0; 
	private int _timesRepeated = 0;
	
	public void start(float timeInSeconds, int timesToRepeat){
		if(timeInSeconds > 0 && timesToRepeat >= 0){
			if(_timeGivenInSeconds != 0){
				stop();
			}
			GameEngine.getCoreListener().addEventListener(Framework.ENTER_FRAME, getEventMethodData("tik"));
			_timeGivenInSeconds = timeInSeconds;
			_givenRepeatAmount = timesToRepeat;
			_running = true;
		}else{
			System.err.println("ERROR: Timer can not be given a negative value or a zero time value to execute");
		}
	}
	public void stop(){
		if(_timeGivenInSeconds != 0){
			GameEngine.getCoreListener().removeEventListener(Framework.ENTER_FRAME, getEventMethodData("tik"));
			_timesRepeated = 0;
			_timePassedInSec = 0;
			_running = false;
			_paused = false;
		}
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
			if(_timePassedInSec >= _timeGivenInSeconds){
				if(_timesRepeated < _givenRepeatAmount){
					_timePassedInSec = 0;
					_timesRepeated ++;
					this.dispatchEvent(new Event(TIMER_ON_REPEAT));
				}else{
					stop();
					this.dispatchEvent(new Event(TIMER_ON_END));
				}
				this.dispatchEvent(new Event(TIMER_ON_TIK));
			}
		}
	}
}
