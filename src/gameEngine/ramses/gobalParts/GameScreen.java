package gameEngine.ramses.gobalParts;

import gameEngine.ramses.controlls.keyboard.EventKeyboard;
import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.events.Event;

import java.awt.Graphics2D;
import java.awt.Image;

public class GameScreen extends DisplayObject{

	public GameScreen(){
		this.setParentListener(null);
	}
	
	private Graphics2D _g;
	
	public void drawSprite(Image spriteToDraw,int x,int y,int width,int height, double rotation){
		_g.rotate(Math.toRadians(rotation), x + spriteToDraw.getWidth(null) / 2, y + spriteToDraw.getHeight(null) / 2);
		_g.drawImage(spriteToDraw, x, y,width,height, null);
		_g.rotate(Math.toRadians(-rotation),x + spriteToDraw.getWidth(null) / 2, y + spriteToDraw.getHeight(null) / 2);
		
	}
	
	public void renderObject(Graphics2D g){
		
		_g = g;
		
		int l = childerenObjects.size();
		for(int i = l - 1; i >= 0; i--){
			if(childerenObjects.get(i) instanceof SpriteEntity){
				SpriteEntity sprEntity = (SpriteEntity)childerenObjects.get(i);
				sprEntity.renderObject(this, 0, 0,1,1,0);
			}
		}
	}
	
	public void secUpdate(){
		dispatchEvent(new Event(FrameworkConsts.ENTER_SECOND,true));
	}
	
	public void update(){
		dispatchEvent(new Event(FrameworkConsts.ENTER_FRAME,true));
	}
}
