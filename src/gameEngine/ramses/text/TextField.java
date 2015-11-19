package gameEngine.ramses.text;

import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.entities.DisplayObject;
import gameEngine.ramses.screen.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;

public class TextField extends DisplayObject {
		
	private String _text = "";
	private Color _color = Color.BLACK;
	private Font _font = new Font("Serif", Font.BOLD, 12);
	private int _size = 12;
	
	@Override
	public void renderObject(Screen gameScreen){
		super.renderObject(gameScreen);
		gameScreen.drawText(_text, _color, _font, xPosStart, yPosStart);
	}
	
	public void setCustomFont(String fontLocation){
		//_font = font;
		File fontFile = new File(fontLocation);
		//File fontFile = new File("gameEngine/ramses/text/fonts/Oxygen-Bold.ttf")
		try {
			_font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	// Getters
	public Font getFont(){
		return _font;
	}
	public int getSize(){
		return _size;
	}
	public Color getColor(){
		return _color;
	}
	
	public String getText(){
		return _text;
	}
	
	// Setters
	public void addText(String text){
		setText(_text + text);
	}
	public void setText(String text){
		_text = text;
		setPerfectFitSize();
	}
	public void setColor(Color color){
		_color = color;
	}
	public void setSize(int size){
		_size = size;
		setFontSettings();
	}
	
	public void setFont(String fontName,int fontType){
		setFont(fontName,fontType,_size);
	}
	public void setFont(String fontName,int fontType, int size){
		_font = new Font(fontName,fontType,size);
		setFontSettings();
	}
	
	// Textfield personal correctors
	private void setFontSettings(){
		_font = _font.deriveFont(Font.PLAIN,_size);
		setPerfectFitSize();
	}
	private void setPerfectFitSize(){
		FontMetrics fontMath = GameEngine.getGraphics2D().getFontMetrics(_font);
		this.setWidthAndHeight(fontMath.stringWidth(_text),fontMath.getHeight());
	}
}
