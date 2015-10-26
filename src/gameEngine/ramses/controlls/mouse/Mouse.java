package gameEngine.ramses.controlls.mouse;

import java.awt.event.MouseListener;

public class Mouse {
	private static int _x;
	private static int _y;
	
	public static int getX(){
		return _x;
	}
	
	public static int getY(){
		return _y;
	}
	public static void setX(int value, MouseListener m){
		if(m.getClass().equals(MouseManager.class)){
			_x = value;
		}else{
			System.err.println("Only the MouseManager can change this value");
		}
	}
	public static void setY(int value, MouseListener m){
		if(m.getClass().equals(MouseManager.class)){
			_y = value;
		}else{
			System.err.println("Only the MouseManager can change this value");
		}
	}
}
