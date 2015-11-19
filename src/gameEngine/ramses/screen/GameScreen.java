package gameEngine.ramses.screen;

import gameEngine.ramses.engine.Framework;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.entities.DisplayObjectContainer;
import gameEngine.ramses.events.Event;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class GameScreen extends DisplayObjectContainer{
	
	private Graphics2D _g;
	//private ArrayList<Camera> _allCameras = new ArrayList<Camera>(); 
	private Camera _camera;
	
	public GameScreen(){
		this.setPivotPoint(0, 0);
		_camera = new Camera(Framework.MAIN_CAMERA,0,0,GameEngine.getScreenWidth(), GameEngine.getScreenHeight());
		this.addChild(_camera);
		//_allCameras.add(camera);
	}
	
	public void drawSprite(Image spriteToDraw,int x,int y,int width,int height, double rotation,int pivotX, int pivotY){
		// all cameras draw this in their perspective.
		/*
		for(int i = 0; i < _allCameras.size(); i++){
			_allCameras.get(i);
			ImageIO.rea	
		}*/
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
	
	public void secUpdate(){
		dispatchEvent(new Event(Framework.ENTER_SECOND,true));
	}
	
	public void update(){
		dispatchEvent(new Event(Framework.ENTER_FRAME,true));
	}
	public Camera getCamera(){
		return _camera;
	}
}
