package gameEngine.ramses.controlls.keyboard;

import gameEngine.ramses.events.Event;

public class EventKeyboard extends Event {
	
	//private KeyEvent _keyEvent;
	
	private int _keyCode;
	private char _keyChar;
	
	public EventKeyboard(String type, int keyCode) {
		super(type,true);
		_keyCode = keyCode;
		_keyChar =(char)keyCode;
	}
	
	public int getKeyCode(){
		return _keyCode;
	}
	
	public char getKeyChar(){
		return _keyChar;
	}
}
