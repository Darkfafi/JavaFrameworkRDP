package gameEngine.ramses.events;

import java.lang.reflect.Method;

public class EventMethodData {
	
	private Method _method;
	private EventDispatcher _holder;
	public EventDispatcher objectAddedTo;
	
	public EventMethodData(Method method, EventDispatcher holderMethod){
		_method = method;
		_holder = holderMethod;
	}
	
	public Method getMethod(){
		return _method;
	}
	public EventDispatcher getMethodHolder(){
		return _holder;
	}
}
