package gameEngine.ramses.assetsManagement;

public class FrameInfo {
	private int _cutX;
	private int _cutY;
	private int _cutWidth;
	private int _cutHeight;
	
	public FrameInfo(int cutX,int cutY,int cutWidth,int cutHeight){
		_cutX = cutX;
		_cutY = cutY;
		_cutWidth = cutWidth;
		_cutHeight = cutHeight;
	}
	
	public int x(){
		return _cutX;
	}
	public int y(){
		return _cutY;
	}
	public int width(){
		return _cutWidth;
	}
	public int height(){
		return _cutHeight;
	}
}
