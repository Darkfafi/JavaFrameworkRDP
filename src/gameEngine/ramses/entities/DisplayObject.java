package gameEngine.ramses.entities;

import gameEngine.ramses.collisionDetection.AxisProjection;
import gameEngine.ramses.collisionDetection.SmartBoundingBox;
import gameEngine.ramses.events.EventDispatcher;
import gameEngine.ramses.screen.Screen;
import gameEngine.ramses.utils.math.Vector2D;

public class DisplayObject extends EventDispatcher {
	
	protected DisplayObjectContainer parentObject;
	
	public int x = 0;
	public int y = 0;
	
	private int _width = 0;
	private int _height = 0;
	
	public float scaleX = 1;
	public float scaleY = 1;
	
	public double rotation = 0;
	
	private float _pivotPointX = 0.5f;
	private float _pivotPointY = 0.5f;
	
	//Rendering
	protected int pivotPositionRevX = 0;
	protected int pivotPositionRevY = 0;
	protected int xPosStart = 0;
	protected int yPosStart = 0;
	
	public void renderObject(Screen gameScreen){
		
		pivotPositionRevX = Math.round((int)(-getPivotX() * getWidth(false)));
		pivotPositionRevY = Math.round((int)(-getPivotY() *  getHeight(false)));
		xPosStart = this.getWorldPositionX() + pivotPositionRevX;
		yPosStart = this.getWorldPositionY() + pivotPositionRevY;
	}
	
	public boolean hitTestObject(DisplayObject other){
		
		boolean result = true;
		
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
	        	result = false;
	        	break;
	        }
		}
		
		return result;
	}
	
	protected void setWidthAndHeight(int width, int height){
		
		_width = width;
		_height = height;
	}
	
	
	// Getters 
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
		
		int xPos = Math.round(getWorldPosition().getX());
		
		return xPos;
	}
	public int getWorldPositionY(){
		
		int yPos = Math.round(getWorldPosition().getY());
		
		return yPos;
	}
	
	public Vector2D getWorldPosition(){
		Vector2D worldPosVec = new Vector2D(x,y);
	
		if(parentObject != null){
			Vector2D parentCalcVec = new Vector2D(x, y);
			parentCalcVec.setAngle(parentCalcVec.getAngle() + Math.toRadians(parentObject.getWorldRotation()));
			worldPosVec = parentObject.getWorldPosition().clone();
			worldPosVec.setX(Math.round(worldPosVec.getX()));
			worldPosVec.setY(Math.round(worldPosVec.getY()));
			worldPosVec.add(parentCalcVec);
			//System.out.println(parentObject + " " + worldPosVec);		
		}
		
		return worldPosVec;
	}
	
	public double getWorldRotation(){
		double rotationObj = rotation;
		
		if(parentObject != null){
			rotationObj += parentObject.getWorldRotation();
		}
		
		return rotationObj;
	}
	
	public float getWorldScaleX(){
		float thisScaleX = scaleX;
		
		if(parentObject != null){
			thisScaleX *= parentObject.getWorldScaleX();
		}
		return thisScaleX;
 	}
	public float getWorldScaleY(){
		float thisScaleY = scaleY;
		
		if(parentObject != null){
			thisScaleY *= parentObject.getWorldScaleY();
		}
		return thisScaleY;
	}
	
	public int getWidth(){
		return getWidth(true);
	}
	
	public int getHeight(){
		return getHeight(true);
	}
	
	public int getWidth(boolean positive){
		int widthR = (int) (this.getWorldScaleX() * _width);
		if(positive){
			widthR = Math.abs(widthR);
		}
		return (int)widthR;
	}
	
	public int getHeight(boolean positive){
		int heightR = (int) (this.getWorldScaleY() * _height);
		if(positive){
			heightR = Math.abs(heightR);
		}
		return (int)heightR;
	}
	
	public DisplayObjectContainer getParent(){
		return parentObject;
	}
}
