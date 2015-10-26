package gameEngine.ramses.events;

public class Event {
	public EventDispatcher caster;
	public EventDispatcher dispatcher;
	
	private String _type;
	private boolean _bubbles;
	
	public Event(String type){
		this(type,false);
	}
	
	public Event(String type, boolean bubbles){
		_type = type;
		_bubbles = bubbles;
	}
	
	public String getType(){
		return _type;
	}
	public boolean isBubbles(){
		return _bubbles;
	}
}
