package gameEngine.ramses.text;

import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.engine.GameScreen;
import gameEngine.ramses.entities.DisplayObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class TextField extends DisplayObject {
		
	private String _text = "";
	private Color _color = Color.BLACK;
	private Font _font = new Font("Serif", Font.BOLD, 12);
	private int _size = 12;
	
	@Override
	public void renderObject(GameScreen gameScreen){
		super.renderObject(gameScreen);
		gameScreen.drawText(_text, _color, _font, xPosStart, yPosStart);
	}
	
	public void setFont(String fontLocation){
		//_font = font;
		File fontFile = new File(fontLocation);
		//File fontFile = new File("gameEngine/ramses/text/fonts/Oxygen-Bold.ttf")
		try {
			_font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_font = _font.deriveFont(Font.PLAIN,20);
	}
	public Font getFont(){
		return _font;
	}
	
	
	public void setSize(int size){
		_size = size;
	}
	public int getSize(){
		return _size;
	}
	
	
	public void setColor(Color color){
		_color = color;
	}
	public Color getColor(){
		return _color;
	}
	public void setText(String text){
		_text = text;
	}
	public String getText(){
		return _text;
	}
	public void addText(String text){
		_text += text;
	}
}
