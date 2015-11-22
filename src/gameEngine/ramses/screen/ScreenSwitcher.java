package gameEngine.ramses.screen;

import gameEngine.ramses.engine.FrameEvents;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;
import gameEngine.ramses.events.EventQueueRoom;

import java.util.HashMap;
import java.util.Map;

public class ScreenSwitcher extends EventDispatcher {
	
	private Map<String,Screen> _allScreens = new HashMap<String	,Screen>();
	
	private GameEngine _engine;
	
	public ScreenSwitcher(GameEngine engine){
		GameEngine.getCoreListener().addEventListener(FrameEvents.SWITCH_SCREEN, this.getEventMethodData("switchScreenEvent"));
		_engine = engine;
	}
	
	public void addScreen(String screenName, Screen screen){
		_allScreens.put(screenName, screen);
	}
	
	public void removeScreen(String screenName){
		_allScreens.remove(screenName);
	}
	
	@SuppressWarnings("unused")
	private void switchScreenEvent(Event e){
		EventSwitchScreen es = (EventSwitchScreen)e;
		switchScreen(es.getScreenName());
		
	}
	
	
	public void switchScreen(String screenName){
		_engine.setScreen(_allScreens.get(screenName));
	}
	
}
