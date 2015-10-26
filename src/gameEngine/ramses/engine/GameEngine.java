package gameEngine.ramses.engine;

import gameEngine.ramses.assetsManagement.Assets;
import gameEngine.ramses.audioManagment.WavAudio;
import gameEngine.ramses.controlls.mouse.MouseManager;
import gameEngine.ramses.gobalParts.GameScreen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class GameEngine implements Runnable {

	private static volatile int currentFrameRate = 10;
	private static int currentWidth = 1200;
	private static int currentHeight = 800;
	
	private boolean _running = false;
	
	private int _setFrameRate;
	
	private Canvas _canvas;
	private BufferedImage _image;
	private int[] _pixels;
	
	private GameScreen _currentScreen;
	private static GameScreen _currentGameScreen;
	
	//Test
	
	Image imageTest;
	
	public GameEngine(Canvas canvas,int frameRate){
		
		loadAssets();
		loadAudio();
		
		MouseManager mouseManager = new MouseManager();
		
		_canvas = canvas;
		
		currentFrameRate = frameRate;
		
		_image = new BufferedImage(_canvas.getWidth(),_canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
		_pixels = ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
		
		_setFrameRate = frameRate;
		
		//imageTest = Assets.getImage("Test"); // Test
	}
	
	private void loadAudio(){
		try {
			WavAudio.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadAssets(){
		try{
			Assets.load();
		}catch(IOException e){
			System.out.println("ERROR: Unable To Load Images in 'Assets'");
			System.exit(1);
		}
	}
	
	public void start(){
		_running = true;
		Thread t =  new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	
	public void stop(){
		_running = false;
	}
	
	public void run(){
		
		currentWidth = _canvas.getWidth();
		currentHeight = _canvas.getHeight();
		
		long lastTime = System.nanoTime(); 
		double nsPerTick = 1000000000D / _setFrameRate;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		long now = 0;
		
		while(_running){
			
			now = System.nanoTime();
			
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while(delta >= 1){
				ticks ++;
				tick();
				delta -= 1;
			}
			
			try{
				Thread.sleep(2);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			frames ++;
			render();
			
			//this if statement is for debugging.
			if(System.currentTimeMillis() - lastTimer > 1000){
				lastTimer = System.currentTimeMillis();
				//System.out.println(ticks + " , " + frames);
				
				if(_currentScreen != null){
					_currentScreen.secUpdate();
				}
				
				currentFrameRate = ticks;
				ticks = 0;
				frames = 0;
			}
		}
	}
	
	private void tick(){
		if(_currentScreen != null){
			_currentScreen.update();
		}
	}
	
	private void render(){
		BufferStrategy bs = _canvas.getBufferStrategy();
		
		if(bs == null){
			_canvas.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		g.setColor(Color.PINK);
		g.fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
		
		g.drawImage(_image, 0, 0, _canvas.getWidth(),_canvas.getHeight(),null);
		
		if(_currentScreen != null){
			_currentScreen.renderObject(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void setScreen(GameScreen screen){
		_currentScreen = screen;
		_currentGameScreen = _currentScreen;
	}
	public GameScreen getScreen(GameScreen screen){
		return _currentScreen;
	}
	
	public static int getCurrentFrameRate(){
		return currentFrameRate;
	}
	public static int getScreenWidth(){
		return currentWidth;
	}
	public static int getScreenHeight(){
		return currentHeight;
	}
	public static GameScreen currentGameScreen(){
		return _currentGameScreen;
	}
}
