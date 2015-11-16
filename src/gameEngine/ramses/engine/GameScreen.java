package gameEngine.ramses.engine;

import gameEngine.ramses.entities.DisplayObjectContainer;
import gameEngine.ramses.events.Event;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class GameScreen extends DisplayObjectContainer{
	private Graphics2D _g;
	
	public GameScreen(){
		this.setPivotPoint(0, 0);
	}
	
	public void drawSprite(Image spriteToDraw,int x,int y,int width,int height, double rotation,int pivotX, int pivotY){
		_g.rotate(Math.toRadians(rotation), x + pivotX, y + pivotY);
		_g.drawImage(spriteToDraw, x, y,width,height, null);
		_g.rotate(Math.toRadians(-rotation),x + pivotX, y + pivotY);
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
		dispatchEvent(new Event(FrameworkConsts.ENTER_SECOND,true));
	}
	
	public void update(){
		dispatchEvent(new Event(FrameworkConsts.ENTER_FRAME,true));
	}
}
