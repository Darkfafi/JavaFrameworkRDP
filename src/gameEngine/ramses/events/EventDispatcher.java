package gameEngine.ramses.events;

import gameEngine.ramses.engine.GameEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class EventDispatcher {
	
	// een lijst van alle listeners die dit object heeft aangemaakt op andere objecten. (IDEE)
	private ArrayList<ListenerItem> _allListeners = new ArrayList<ListenerItem>();
	
 	private EventDispatcher _parentListener = GameEngine.getCoreListener();
 	
	public void addEventListener(String type, EventMethodData methodData){
		
		_allListeners.add(new ListenerItem(type, methodData));
	}
	
	public void dispatchEvent(Event event){
		try {
			EventQueueRoom.addQueueItem(event,this);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeEventListener(String type, EventMethodData methodData){
		ListenerItem currentItem;
		if(_allListeners.size() > 0){
			for(int i = 0; i < _allListeners.size(); i++){
				currentItem = _allListeners.get(i);
				if(currentItem.getType().equals(type) && currentItem.getMethodData().getMethod().equals(methodData.getMethod())){
					_allListeners.remove(currentItem);
					break;
				}
			}
		}
	}
	
	public boolean hasEventListener(String type){
		ListenerItem currentItem;
		boolean result = false;
		if(_allListeners.size() > 0){
			for(int i = 0; i < _allListeners.size(); i++){
				currentItem = _allListeners.get(i);
				if(currentItem.getType() == type){
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	public ArrayList<ListenerItem> getAllListeners(){
		return _allListeners;
	}
	
	@SuppressWarnings("unchecked")
	public EventMethodData getEventMethodData(String eventMethodName){
		Method mthd = null;
		@SuppressWarnings("rawtypes")
		Class currentClass = this.getClass();
		while(currentClass != Object.class && mthd == null){
			try {
				mthd = currentClass.getDeclaredMethod(eventMethodName,Event.class);
				mthd.setAccessible(true);
			} catch (NoSuchMethodException | SecurityException e) {
				currentClass = currentClass.getSuperclass();
				if(currentClass == Object.class){
					e.printStackTrace();
				}
			}
		}
		return new EventMethodData(mthd,this);
	}
	
	public void destroyAllListeners(){
		if(_allListeners.size() > 0){
			for(int i = _allListeners.size() - 1; i >= 0; i--){
				_allListeners.remove(_allListeners.get(i));
			}
		}
	}
	
	public EventDispatcher getParentListener(){
		return _parentListener;
	}
	public void setParentListener(EventDispatcher listener){
		_parentListener = listener;
	}
}
