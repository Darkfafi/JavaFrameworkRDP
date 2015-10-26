package gameEngine.ramses.events;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class EventQueueRoom {
	//static class die een add en resolved event functie heeft.
	//als een event word geadd word er bij alle eventHandlers gekeken of het hun event is. Zo ja activeer het.
	//private static ArrayList<Event> _allEvents = new ArrayList<Event>();
	
	public static void addQueueItem(Event event,EventDispatcher dispatcher) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ArrayList<ListenerItem> listListeners = dispatcher.getAllListeners();
		EventDispatcher currentParent;
		event.dispatcher = dispatcher;
		event.caster = dispatcher;
		callMethodsInListOfEvent(listListeners,event);
		if(event.isBubbles()){
			currentParent = dispatcher.getParentListener();
			while(currentParent != null){
				event.caster = currentParent;
				listListeners = currentParent.getAllListeners();
				callMethodsInListOfEvent(listListeners,event);
				currentParent = currentParent.getParentListener();
			}
		}
	}
	
	private static void callMethodsInListOfEvent(ArrayList<ListenerItem> list, Event event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ListenerItem currentItem;
		if(list.size() > 0){
			for(int i = list.size() - 1; i >= 0 ; i--){
				currentItem = list.get(i);
				if(currentItem.getType() == event.getType()){
					currentItem.getMethodData().getMethod().invoke(currentItem.getMethodData().getMethodHolder(), event);
				}
			}
		}
	}
	
	/*EventDispatcher currentObject;
		ArrayList<ListenerItem> listListeners;
		ListenerItem currentListenItem;
		for(int i = _allListeners.size() - 1; i >= 0; i--){
			currentObject = _allListeners.get(i);
			if(currentObject.hasEventListener(event.getType())){
				listListeners = currentObject.getAllListeners();
				for(int j = listListeners.size() - 1; j >= 0; j--){
					currentListenItem = listListeners.get(i);
					if(currentListenItem.getType() == event.getType()){
						event.dispatcher = currentObject;
						currentListenItem.getMethod().invoke(currentObject, event);
					}
				}
			}
		}*/
}
