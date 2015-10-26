package gameEngine.ramses.events;

import java.lang.reflect.Method;

public class ListenerItem {
	private String _typeItem;
	private EventMethodData _methodData;
		
	public ListenerItem(String type, EventMethodData methodData){
		_typeItem = type;
		_methodData = methodData;
	}
	
	public String getType(){
		return _typeItem;
	}
	public EventMethodData getMethodData(){
		return _methodData;
	}
}
