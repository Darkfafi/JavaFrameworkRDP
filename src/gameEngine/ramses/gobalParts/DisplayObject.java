package gameEngine.ramses.gobalParts;

import gameEngine.ramses.collisionDetection.AxisProjection;
import gameEngine.ramses.collisionDetection.SmartBoundingBox;
import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.events.EventDispatcher;
import gameEngine.ramses.mathUtils.Vector2D;

import java.util.ArrayList;

public class DisplayObject extends EventDispatcher{

	protected DisplayObject parentObject;
	protected ArrayList<DisplayObject> childerenObjects = new ArrayList<DisplayObject>();
	
	public int x = 0;
	public int y = 0;
	
	private int _width = 0;
	private int _height = 0;
	
	public float scaleX = 1;
	public float scaleY = 1;
	
	public double rotation = 0;
	
	private float _pivotPointX = 0.5f;
	private float _pivotPointY = 0.5f;
	
	private boolean _shouldRender = false; // Als het niet in de camera is dan render het object niet. Ook niet buiten het scherm. Dit geeft meer performance.
	
	public void addChild(DisplayObject displayObject){
		if(displayObject != this && !(displayObject instanceof GameScreen)){
			if(displayObject.parentObject == this || displayObject.parentObject == null){
				displayObject.parentObject = this;
				childerenObjects.add(displayObject);
			}else{
				displayObject.parentObject.removeChild(displayObject);
				addChild(displayObject);
			}
			displayObject.setParentListener(this);
			displayObject.dispatchEvent(new Event(FrameworkConsts.ADDED_TO_STAGE,true));
		}else{
			System.err.println("Cannot add DisplayObject to itself and cannot add a Screen to a displayObject");
		}
	}
	
	protected void setWidthAndHeight(int width, int height){
		
		_width = width;
		_height = height;
	}
	
	public void removeChild(DisplayObject displayObject){
		
		int index = childerenObjects.indexOf(displayObject);
		if(index != -1){
			childerenObjects.remove(index);
		}else{
			System.err.println("DisplayObject does not contain object: " + displayObject.toString());
		}
		
	}
	
	public void remove(){
		if(parentObject != null){
			dispatchEvent(new Event(FrameworkConsts.REMOVED_FROM_STAGE,true));
			parentObject.removeChild(this);
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
	
	public boolean hitTestObject(DisplayObject other){
		Vector2D[] allAxis = new Vector2D[4];
		AxisProjection thisProj;
		AxisProjection otherProj;
		
		SmartBoundingBox thisSB = new SmartBoundingBox(this);
		SmartBoundingBox otherSB = new SmartBoundingBox(other);
		
		allAxis[0] = new Vector2D(thisSB.getRightTop().getX() - thisSB.getLeftTop().getX(),thisSB.getRightTop().getY() - thisSB.getLeftTop().getY());
		allAxis[1] = new Vector2D(thisSB.getRightTop().getX() - thisSB.getRightBottom().getX(),thisSB.getRightTop().getY() - thisSB.getRightBottom().getY());
		allAxis[2] = new Vector2D(otherSB.getLeftTop().getX() - otherSB.getLeftBottom().getX(),otherSB.getLeftTop().getY() - otherSB.getLeftBottom().getY());
		allAxis[3] = new Vector2D(otherSB.getLeftTop().getX() - otherSB.getRightTop().getX(), otherSB.getLeftTop().getY() - otherSB.getRightTop().getY());
		
		for (int i = 0; i < 4; i++) {
			thisProj = thisSB.projectOntoAxis(allAxis[i]);
	        otherProj = otherSB.projectOntoAxis(allAxis[i]);

	            // ... and check whether the outermost projected points of both OBBs overlap.
	            // If this is not the case, the Seperating Axis Theorem states that there can be no collision between the rectangles
	        if (!((otherProj.getMin()<=thisProj.getMax())&&(otherProj.getMax()>=thisProj.getMin()))){
	            return false;
	        }
		}
		
		return true;
	}
	
	// Getters and Setters
	
	public boolean getShouldRender(){
		return _shouldRender;
	}
	
	public void setPivotPoint(float xRatio, float yRatio){
		_pivotPointX = xRatio;
		_pivotPointY = yRatio;
	}
	
	public float getPivotX(){
		return _pivotPointX;
	}
	public float getPivotY(){
		return _pivotPointY;
	}
	
	public int getWorldPositionX(){
		
		int xPos = x;
		
		if(parentObject != null){
			xPos += parentObject.getWorldPositionX();
		}
		
		return xPos;
	}
	public int getWorldPositionY(){
		
		int yPos = y;
		
		if(parentObject != null){
			yPos += parentObject.getWorldPositionY();
		}
		
		return yPos;
	}
	
	public int getWidth(){
		return getWidth(true);
	}
	
	public int getHeight(){
		return getHeight(true);
	}
	
	public int getWidth(boolean positive){
		int widthR = (int) (scaleX * _width);
		if(positive){
			widthR = Math.abs(widthR);
		}
		return (int)widthR;
	}
	
	public int getHeight(boolean positive){
		int heightR = (int) (scaleY * _height);
		if(positive){
			heightR = Math.abs(heightR);
		}
		return (int)heightR;
	}
}
