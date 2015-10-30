package gameEngine.ramses.utils.math;

public class Vector2D {
	
	private float _x = 0;
	private float _y = 0;
	private float _length = 0;
	private double _angle = 0; 
	
	public Vector2D(){
		setX(0);
		setY(0);
	}
	
	public Vector2D(float x, float y){
		setX(x);
		setY(y);
	}
	
	
	public void normalize(){
		setLength(1);
	}
	
	public Vector2D clone(){
		return new Vector2D(_x,_y);
	}
	
	public boolean equals(Vector2D vec){
		boolean result = false;
		if(_x == vec.getX() && _y == vec.getY()){
			result = true;
		}
		return result;
	}
	
	// Setters
	public void setX(float x){
		_x = x;
		_angle = (float)Math.atan2(_y, _x);
		_length = (float)Math.sqrt((_x * _x) + (_y * _y));
	}
	public void setY(float y){
		_y = y;
		_angle = (float)Math.atan2(_y, _x);
		_length = (float)Math.sqrt((_x * _x) + (_y * _y));
	}
	public void setLength(float length){
		_length = length;
		_x = (float)(_length * Math.cos(_angle));
		_y = (float)(_length * Math.sin(_angle));
	}
	public void setAngle(double d){
		_angle = d;
		_x = (float)(_length * Math.cos(_angle));
		_y = (float)(_length * Math.sin(_angle));
	}
	
	// Getters
	public float getX(){
		return _x;
	}
	public float getY(){
		return _y;
	}
	public float getLength(){
		return _length;
	}
	public double getAngle(){
		return _angle;
	}
	
	public String toString(){
		return ("x = " + _x + " y = " + _y);
	}
	
	// Calculators
	public void add(Vector2D vec){
		setX(_x + vec.getX());
		setY(_y + vec.getY());
	}
	public void substract(Vector2D vec){
		setX(_x - vec.getX());
		setY(_y - vec.getY());
	}
	public void multiply(float number){
		setX(_x * number);
		setY(_y * number);
	}
}
