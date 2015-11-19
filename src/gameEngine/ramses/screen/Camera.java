package gameEngine.ramses.screen;

import gameEngine.ramses.entities.DisplayObjectContainer;

import java.awt.Graphics2D;

public class Camera extends DisplayObjectContainer {
	
	private String _name;
	
	public Camera(String name,int xStart, int yStart, int width, int height){
		_name = name;
		x = xStart;
		y = yStart;
		this.setWidthAndHeight(width, height);
	}
	
	
	
	public void renderCamera(Graphics2D g){
		
	}
	
}
