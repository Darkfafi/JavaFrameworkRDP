package gameEngine.ramses.entities;

import gameEngine.ramses.engine.GameScreen;

import java.awt.Image;

public class SpriteObject extends DisplayObjectContainer {

	protected Image _sprite;
	
	@Override
	public void renderObject(GameScreen gameScreen){
		super.renderObject(gameScreen);
		if(_sprite != null){
			int widthDraw = (int)(_sprite.getWidth(null) * (this.getWorldScaleX()));
			int heightDraw = (int)(_sprite.getHeight(null) * (this.getWorldScaleY()));
			double rotationDrawing = this.getWorldRotation();//startRotation + rotation;
		
		
			gameScreen.drawSprite(_sprite, xPosStart, yPosStart, widthDraw, heightDraw, rotationDrawing,-pivotPositionRevX,-pivotPositionRevY);
		}
	}
	
	public void setSprite(Image sprite){
		_sprite = sprite;
		setWidthAndHeight(_sprite.getWidth(null),_sprite.getHeight(null));	
	}
	
	public Image getSprite(){
		return _sprite;
	}
}
