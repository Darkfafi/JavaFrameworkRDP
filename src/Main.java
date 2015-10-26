import gameEngine.ramses.assetsManagement.Assets;
import gameEngine.ramses.audioManagment.WavAudio;
import gameEngine.ramses.engine.GameEngine;
import gameEngine.ramses.events.Event;
import gameEngine.ramses.gobalParts.GameScreen;
import gameEngine.ramses.gobalParts.SpriteEntity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Main {
	
	public static final int FRAME_RATE = 30;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	private static GameScreen testScreen = new GameScreen(); // voorbeeld.
	
	public static void main(String[] args){
		JFrame window = new JFrame("Games");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		canvas.setBackground(Color.PINK);
		
		window.add(canvas);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		GameEngine engine = new GameEngine(canvas,FRAME_RATE);
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
		
		testScreen.addEventListener("testEvent", testScreen.getEventMethodData("testMethod"));
		sprtEntity.addEventListener("testEvent", testScreen.getEventMethodData("testMethod"));
		sprtEntity.dispatchEvent(new Event("testEvent"));
		
		
		System.out.println(sprtEntity.hitTestObject(sprtEntity2));
	}
}