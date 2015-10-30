package gameEngine.ramses.collisionDetection;

import gameEngine.ramses.gobalParts.DisplayObject;
import gameEngine.ramses.utils.math.Vector2D;

public class SmartBoundingBox {
	
	private Vector2D _upL;
	private Vector2D _upR;
	private Vector2D _downL;
	private Vector2D _downR;
	
	private Vector2D[] _corners = new Vector2D[4];
	
	public SmartBoundingBox(DisplayObject object){
		_upL = new Vector2D(-(object.getWidth() * object.getPivotX()),-(object.getHeight() * object.getPivotY()));
		_upR = _upL.clone(); _upR.setX(_upR.getX() + object.getWidth());
		_downL = _upL.clone(); _downL.setY(_downL.getY() + object.getHeight());
		_downR = _downL.clone(); _downR.setX(_upR.getX());
		
		_corners[0] = _upL;
		_corners[1] = _upR;
		_corners[2] = _downR;
		_corners[3] = _downL;
		
		for(int i = 0; i < 4; i++){
			_corners[i].setAngle(Math.toRadians(Math.toDegrees(_corners[i].getAngle()) + object.getWorldRotation()));
			//_corners[i].setAngle(Math.toRadians(Math.toDegrees(_corners[i].getAngle()) + object.rotation));
			_corners[i].setX(_corners[i].getX() + object.getWorldPositionX());
			_corners[i].setY(_corners[i].getY() + object.getWorldPositionY());
		}
		
		//System.out.println("UL " + _upL.toString() + " UR " + _upR.toString() + " DL " +  _downL.toString() + " DR " +_downR.toString());
	}
	
	public AxisProjection projectOntoAxis (Vector2D axis) // Project all four points of the OBB onto the given axis and return the dotproducts of the two outermost points
    {
		
        float min = (_upL.getX()*axis.getX()+_upL.getY()*axis.getY());
        float max = min;
        // start at 1 because we already had 0 in the declaration
        for (int i = 1;  i < _corners.length; i++)
        {
            float projection = (_corners[i].getX()*axis.getX()+_corners[i].getY()*axis.getY());

            if (projection < min)
                min=projection;
            if (projection > max)
                max=projection;
        }
        
        return new AxisProjection(min,max);
    }
	
	public Vector2D getLeftTop(){
		return _upL;
	}
	public Vector2D getLeftBottom(){
		return _downL;
	}
	public Vector2D getRightTop(){
		return _upR;
	}
	public Vector2D getRightBottom(){
		return _downR;
	}
}
