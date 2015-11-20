package gameEngine.ramses.screen;

import gameEngine.ramses.engine.FrameEvents;
import gameEngine.ramses.engine.FrameNames;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.entities.DisplayObject;
import gameEngine.ramses.entities.DisplayObjectContainer;
import gameEngine.ramses.events.Event;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public abstract class Screen extends DisplayObjectContainer implements IScreen{
	
	private Graphics2D _g;
	private Camera _camera;
	
	private boolean _removing = false;
	
	public void drawSprite(Image spriteToDraw,int x,int y,int width,int height, double rotation,int pivotX, int pivotY){
		_g.rotate(Math.toRadians(rotation), x - _camera.x + pivotX, y - _camera.y + pivotY);
		_g.drawImage(spriteToDraw, x - _camera.x, y - _camera.y,width,height, null);
		_g.rotate(Math.toRadians(-rotation),x - _camera.x + pivotX, y - _camera.y + pivotY);
	}
	
	public void drawText(String text, Color color, Font font, int x, int y){
		_g.setFont(font);
		_g.setColor(color);
		_g.drawString(text, x, y);
	}
	
	public void renderScreen(Graphics2D g){
		_g = g;
		renderObject(this);
	}
	public void update(){
		dispatchEvent(new Event(FrameEvents.ENTER_FRAME,true));
	}
	public void secUpdate(){
		dispatchEvent(new Event(FrameEvents.ENTER_SECOND,true));
	}
	public void setScreenBuildUp(){
		this.setPivotPoint(0, 0);
		_camera = new Camera(FrameNames.MAIN_CAMERA,0,0,GameEngine.getScreenWidth(), GameEngine.getScreenHeight());
		this.addChild(_camera);
		this.screenSet();
	}
	public void removeScreenBuildDown(){
		_removing = true;
		DisplayObject currentChild;
		for(int i = this.childerenObjects.size() - 1; i >= 0; i--){
			currentChild = childerenObjects.get(i);
			this.removeChild(currentChild,true);
			currentChild = null;
		}
		_removing = false;
		this.screenRemoved();
	}
	
	public Camera getCamera(){
		return _camera;
	}
	public boolean isBeingRemoved(){
		return _removing;
	}
}
