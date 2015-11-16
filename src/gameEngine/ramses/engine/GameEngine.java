package gameEngine.ramses.engine;

import gameEngine.ramses.assetsManagement.Assets;
import gameEngine.ramses.audioManagment.WavAudio;
import gameEngine.ramses.controlls.keyboard.KeyboardManager;
import gameEngine.ramses.controlls.mouse.MouseManager;
import gameEngine.ramses.events.CoreListener;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;


public class GameEngine implements Runnable {

	private static volatile int currentFrameRate = 10;
	private static int currentWidth = 1200;
	private static int currentHeight = 800;
	
	private boolean _running = false;
	
	private int _setFrameRate;
	
	private Canvas _canvas;
	private BufferedImage _image;
	private int[] _pixels;
	
	private KeyboardManager _keyboardManager;
	private MouseManager _mouseManager;
	
	private GameScreen _currentScreen;
	private static GameScreen _currentGameScreen;
	
	private static CoreListener _coreListener = new CoreListener();
	
	private static float _deltaTime = 0;
	private static Graphics2D _graphics;
	
	private static Assets _assets;
	private static WavAudio _audio;
	
	public GameEngine(String nameWindow, int widthFrame, int heightFrame, int frameRate,Assets assets, WavAudio audio){
		
		setUpFrame(nameWindow,widthFrame,heightFrame);
		
		_assets = assets;
		_audio = audio;
		
		loadAssets();
		loadAudio();
		
		currentFrameRate = frameRate;
		
		_image = new BufferedImage(_canvas.getWidth(),_canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
		_pixels = ((DataBufferInt) _image.getRaster().getDataBuffer()).getData();
		
		_setFrameRate = frameRate;
	}
	
	private void setUpFrame(String nameWindow, int widthFrame, int heightFrame){
		
		JFrame window = new JFrame(nameWindow);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		_canvas = new Canvas();
		_canvas.setPreferredSize(new Dimension(widthFrame,heightFrame));
		_canvas.setBackground(Color.PINK);
		
		_keyboardManager = new KeyboardManager();
		_mouseManager = new MouseManager();
				
		_canvas.addKeyListener(_keyboardManager);
		_canvas.addMouseListener(_mouseManager);
		_canvas.addMouseMotionListener(_mouseManager);
		
		window.add(_canvas);
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	
	private void loadAudio(){
		try {
			_audio.load();
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
		try {
			_assets.load();
		} catch (IOException | UnsupportedAudioFileException
				| LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start(){
		_running = true;
		Thread t =  new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		this.render();
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
				_deltaTime =  ((float)getCurrentFrameRate()) / 1000;
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

			//this if statement is for debugging (Will run every second).
			
			if(System.currentTimeMillis() - lastTimer >= 1000){
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
		
		_graphics = g;
		
		g.setColor(Color.PINK);
		g.fillRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
		
		g.drawImage(_image, 0, 0, _canvas.getWidth(),_canvas.getHeight(),null);
		
		if(_currentScreen != null){
			_currentScreen.renderScreen(g);
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
	public static CoreListener getCoreListener(){
		return _coreListener;
	}
	public static float getDeltaTime(){
		return _deltaTime;
	}
	public static Assets getAssets(){
		return _assets;
	}
	public static WavAudio getAudio(){
		return _audio;
	}
	public static Graphics2D getGraphics2D(){
		return _graphics;
	}
}
