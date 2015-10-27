package gameEngine.ramses.mathUtils;

public class VectorOperations {
	
	public static float dotProduct(Vector2D vec1, Vector2D vec2){
		return (vec1.getX() * vec2.getX()) + (vec1.getY() * vec2.getY());
	}
}
