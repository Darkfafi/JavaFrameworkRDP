import gameEngine.ramses.assetsManagement.Assets;
import gameEngine.ramses.audioManagment.WavAudio;
import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.gobalParts.AnimEntity;
import gameEngine.ramses.gobalParts.GameScreen;
import gameEngine.ramses.utils.Timer;


public class Main {
	public static final String NAME = "GameFramework";
	public static final int FRAME_RATE = 30;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static GameScreen testScreen = new GameScreen(); // voorbeeld.
	
	public static void main(String[] args){
		
		
		GameEngine engine = new GameEngine(NAME,WIDTH,HEIGHT,FRAME_RATE);
		engine.start();
		
		engine.setScreen(testScreen); // voorbeeld.	
		
		// voorbeeld.
		AnimEntity sprtEntity = new AnimEntity();
		AnimEntity sprtEntity2 = new AnimEntity();
		AnimEntity sprtEntity3 = new AnimEntity();
		sprtEntity.setAnimationSheet(Assets.getSpriteSheet("Test2"));
		sprtEntity2.setAnimationSheet(Assets.getSpriteSheet("Test2"));
		sprtEntity3.setAnimationSheet(Assets.getSpriteSheet("Test2"));
		testScreen.addChild(sprtEntity);
		sprtEntity.x = 400;
		sprtEntity.y = 300;
		testScreen.addChild(sprtEntity2);
		sprtEntity.addChild(sprtEntity3);
		sprtEntity2.x = (sprtEntity.getWidth());
		sprtEntity2.y = 0;
		//sprtEntity2.scaleX = 0.5f;
		sprtEntity3.x = (sprtEntity.getWidth()  + 1);
		sprtEntity3.y = 300;	
		//sprtEntity.setPivotPoint(0, 0.5f);
		
		Timer timer = new Timer();
		timer.start(5, 100);
		
		sprtEntity.rotation = 0;
		sprtEntity2.rotation = 45;
		sprtEntity3.rotation = 90;
		WavAudio.setChannelVolume("musicChannel", 50);
		//WavAudio.playAudio("musicChannel","Test",50,1);
		WavAudio.playAudio("musicChannel","Test2",50);
		
	//	WavAudio.stopAudio("Test2");
		
		//EventListener Tests
		GameEngine.getCoreListener().addEventListener(FrameworkConsts.KEY_PRESSED, testScreen.getEventMethodData("testMethod"));
		//sprtEntity.addEventListener("testEvent", testScreen.getEventMethodData("testMethod"));
		//sprtEntity.dispatchEvent(new Event("testEvent"));
		
		
		//System.out.println(sprtEntity.hitTestObject(sprtEntity3));
	}
}