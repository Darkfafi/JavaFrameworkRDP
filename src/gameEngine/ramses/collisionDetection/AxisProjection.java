package gameEngine.ramses.collisionDetection;
public class AxisProjection {
	private float _min;
	private float _max;
	
	public AxisProjection(float min, float max){
		_min = min;
		_max = max;
	}
	
	public float getMin(){
		return _min;
	}
	public float getMax(){
		return _max;
	}
}
