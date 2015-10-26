package gameEngine.ramses.controlls.keyboard;

import gameEngine.ramses.engine.FrameworkConsts;
import gameEngine.ramses.events.EventDispatcher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardManager extends EventDispatcher implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		this.dispatchEvent(new EventKeyboard(FrameworkConsts.KEY_PRESSED,e));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.dispatchEvent(new EventKeyboard(FrameworkConsts.KEY_RELEASED,e));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.dispatchEvent(new EventKeyboard(FrameworkConsts.KEY_TYPED,e));
	}

}
