import gameEngine.ramses.assetsManagement.Assets;
import gameEngine.ramses.audioManagment.WavAudio;
import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.gobalParts.GameScreen;
import gameEngine.ramses.gobalParts.SpriteEntity;


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
		SpriteEntity sprtEntity = new SpriteEntity();
		SpriteEntity sprtEntity2 = new SpriteEntity();
		sprtEntity.setSprite(Assets.getImage("Test"));
		sprtEntity2.setSprite(Assets.getImage("Test"));
		testScreen.addChild(sprtEntity);
		sprtEntity.x = sprtEntity.getWidth() / 2;
		sprtEntity.y = 300;
		sprtEntity.addChild(sprtEntity2);
		sprtEntity2.x = sprtEntity.x + sprtEntity.getHeight() + 1;
		sprtEntity2.scaleX = 1f;
		
		sprtEntity.rotation = 92;
		sprtEntity2.rotation = 90;
		
		WavAudio.setChannelVolume("musicChannel", 50);
		//WavAudio.playAudio("musicChannel","Test",50,1);
		WavAudio.playAudio("musicChannel","Test2",50);
		
	//	WavAudio.stopAudio("Test2");
		
		//EventListener Tests
		
		//testScreen.addEventListener(FrameworkConsts.KEY_PRESSED, testScreen.getEventMethodData("testMethod"));
		//sprtEntity.addEventListener("testEvent", testScreen.getEventMethodData("testMethod"));
		//sprtEntity.dispatchEvent(new Event("testEvent"));
		
		
		System.out.println(sprtEntity.hitTestObject(sprtEntity2));
	}
}