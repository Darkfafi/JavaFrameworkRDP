package gameEngine.ramses.controlls.keyboard;

import gameEngine.ramses.events.Event;

import java.awt.event.KeyEvent;

public class EventKeyboard extends Event {
	
	private KeyEvent _keyEvent;
	
	public EventKeyboard(String type, KeyEvent keyEvent) {
		super(type,true);
		_keyEvent = keyEvent;
	}
	
	public KeyEvent getKeyEvent(){
		return _keyEvent;
	}
}
