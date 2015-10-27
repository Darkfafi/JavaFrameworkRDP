package gameEngine.ramses.gobalParts;

import java.awt.Graphics2D;
import java.awt.Image;

public class SpriteEntity extends DisplayObject{
	
	protected Image _sprite;
	
	public void renderObject(GameScreen gameScreen, int xStart, int yStart,float xScaleStart, float yScaleStart,double startRotation){
		if(_sprite != null){
			gameScreen.drawSprite(_sprite, xStart + x +(int)(-getPivotX() * getWidth(false)), yStart + y +(int)(-getPivotY() *  getHeight(false)), (int)(_sprite.getWidth(null) * (xScaleStart * scaleX)), (int)(_sprite.getHeight(null) * (yScaleStart * scaleY)),startRotation + rotation);
		}
		
		int l = childerenObjects.size();
		
		for(int i = l - 1; i >= 0; i--){
			if(childerenObjects.get(i) instanceof SpriteEntity){
				SpriteEntity sprEntity = (SpriteEntity) childerenObjects.get(i);
				sprEntity.renderObject(gameScreen, x +(int)(-getPivotX() * getWidth(false)), y +(int)(-getPivotY() *  getHeight(false)),scaleX,scaleY,rotation);
			}
		}
	}
	
	public void setSprite(Image sprite){
		_sprite = sprite;
		setWidthAndHeight(_sprite.getWidth(null),_sprite.getHeight(null));
		
	}
	
	public Image getSprite(){
		return _sprite;
	}
	
	public Graphics2D getGraphics2D(){
		return (Graphics2D)_sprite.getGraphics();
	}
}
