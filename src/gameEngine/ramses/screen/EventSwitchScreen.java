package gameEngine.ramses.screen;

import gameEngine.ramses.events.Event;

public class EventSwitchScreen extends Event {
	
	private String _screenName;
	
	public EventSwitchScreen(String type,String screenName) {
		super(type,true);
		_screenName = screenName;
	}
	
	public String getScreenName(){
		return _screenName;
	}
}
