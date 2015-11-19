package gameEngine.ramses.controlls.keyboard;

import gameEngine.ramses.engine.Framework;
import gameEngine.ramses.events.EventDispatcher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.KeyStroke;

public class KeyboardManager extends EventDispatcher implements KeyListener{
	
	// keydown , key en keyUp (idea)
	
	//char c=(char)keycode
	private static ArrayList<Integer> _keys = new ArrayList<Integer>();
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!_keys.contains(e.getKeyCode())){
			_keys.add((Integer)e.getKeyCode());
			this.dispatchEvent(new EventKeyboard(Framework.KEY_PRESSED_START,e.getKeyCode()));
		}
		for(int i = _keys.size() - 1; i >= 0; i--){
			this.dispatchEvent(new EventKeyboard(Framework.KEY_PRESSED,_keys.get(i)));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(_keys.contains(e.getKeyCode())){
			_keys.remove((Integer)e.getKeyCode());
		}
		
		this.dispatchEvent(new EventKeyboard(Framework.KEY_RELEASED,e.getKeyCode()));
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		this.dispatchEvent(new EventKeyboard(Framework.KEY_TYPED,e.getKeyCode()));
	}
	
	
	public static boolean pressedKeyCode(int keyCode){
		return _keys.contains(keyCode);
	}
	
	public static boolean pressedKeyChar(char keyChar){
		KeyStroke ks = KeyStroke.getKeyStroke(keyChar, 0);
		return _keys.contains(ks.getKeyCode());
	}
}
