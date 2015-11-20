package gameEngine.ramses.entities;

import gameEngine.ramses.engine.FrameEvents;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.screen.Screen;

import java.util.ArrayList;


public class DisplayObjectContainer extends DisplayObject {
	protected ArrayList<DisplayObject> childerenObjects = new ArrayList<DisplayObject>();
	
	@Override
	public void renderObject(Screen gameScreen){
		super.renderObject(gameScreen);
		int l = childerenObjects.size();
		for(int i = l - 1; i >= 0; i--){
			childerenObjects.get(i).renderObject(gameScreen);
		}
	}
	
	
	public void addChild(DisplayObject displayObject){
		if(displayObject != this){
			if(displayObject.parentObject == this || displayObject.parentObject == null){
				displayObject.parentObject = this;
				childerenObjects.add(displayObject);
			}else{
				displayObject.parentObject.removeChild(displayObject);
				addChild(displayObject);
			}
			displayObject.setParentListener(this);
			displayObject.dispatchEvent(new Event(FrameEvents.ADDED_TO_STAGE,true));
		}else{
			System.err.println("Cannot add DisplayObject to itself");
		}
	}
	
	public void removeChild(DisplayObject displayObject){
		removeChild(displayObject,false);
	}
	
	public void removeChild(DisplayObject displayObject, boolean removeChilderen){
		
		int index = childerenObjects.indexOf(displayObject);
		if(index != -1){
			displayObject.parentObject = null;
			childerenObjects.remove(index);
			if(removeChilderen){
				if(displayObject instanceof DisplayObjectContainer){
					DisplayObjectContainer conDisp = (DisplayObjectContainer)displayObject;
					for(int i = conDisp.childerenObjects.size() - 1; i >= 0; i--){
						conDisp.removeChild(conDisp.childerenObjects.get(i),true);
					}
				}
			}
			displayObject.dispatchEvent(new Event(FrameEvents.REMOVED_FROM_STAGE,true));
		}else{
			System.err.println("DisplayObject does not contain object: " + displayObject.toString());
		}
		
	}
	public boolean containsChild(DisplayObject displayObject){
		boolean result = false;
		int l = childerenObjects.size();
		for(int i = l - 1; i >= 0; i--){
			if(childerenObjects.get(i) == displayObject){
				result = true;
				break;
			}
		}
		return result;
	}
	
	public boolean hitTestObject(DisplayObject other,boolean checkChilderen){
		boolean result;
		
		result = hitTestObject(other);
		
		if(!result && childerenObjects.size() > 0){
			for(int i = childerenObjects.size() - 1; i >= 0; i--){
				result = childerenObjects.get(i).hitTestObject(other);
				if(result){
					break;
				}
			}
		}
		return result;
	}
	@Override
	public void destroy(){
		DisplayObject child;
		for(int i = childerenObjects.size() - 1; i >= 0; i--){
			child = childerenObjects.get(i);
			child.destroy();
			child = null;
		}
		super.destroy();
	}
}
