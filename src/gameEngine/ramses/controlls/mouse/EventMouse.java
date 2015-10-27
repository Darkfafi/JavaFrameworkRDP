package gameEngine.ramses.controlls.mouse;

import gameEngine.ramses.events.Event;

import java.awt.event.MouseEvent;

public class EventMouse extends Event {

	private MouseEvent _mouseEvent;
	
	public EventMouse(String type,MouseEvent event) {
		super(type,true);
		_mouseEvent = event;
	}
	
	public MouseEvent getMouseEvent(){
		return _mouseEvent;
	}
}
