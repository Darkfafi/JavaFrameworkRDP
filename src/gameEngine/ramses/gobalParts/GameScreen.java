package gameEngine.ramses.gobalParts;

import gameEngine.ramses.controlls.keyboard.EventKeyboard;
import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.events.Event;

import java.awt.Graphics2D;
import java.awt.Image;

public class GameScreen extends DisplayObject{
	private Graphics2D _g;
	
	
	public void drawSprite(Image spriteToDraw,int x,int y,int width,int height, double rotation,int pivotX, int pivotY){
		_g.rotate(Math.toRadians(rotation), x + pivotX, y + pivotY);
		_g.drawImage(spriteToDraw, x, y,width,height, null);
		_g.rotate(Math.toRadians(-rotation),x + pivotX, y + pivotY);
		
	}
	
	public void renderObject(Graphics2D g){
		
		_g = g;
		
		// add een global object en render alles daar in. (cat in cat werkt wel maar 2 cats in gamescreen geeft lag)
		//stage
		
		int l = childerenObjects.size();
		for(int i = l - 1; i >= 0; i--){
			if(childerenObjects.get(i) instanceof SpriteEntity){
				//System.out.println(i);
				SpriteEntity sprEntity = (SpriteEntity)childerenObjects.get(i);
				sprEntity.renderObject(this, 0, 0,1,1);
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
