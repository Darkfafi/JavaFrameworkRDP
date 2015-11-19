package gameEngine.ramses.controlls.mouse;

import gameEngine.ramses.engine.Framework;
import gameEngine.ramses.events.EventDispatcher;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager extends EventDispatcher implements MouseListener, MouseMotionListener {
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateMouseLocation(e);
		this.dispatchEvent(new EventMouse(Framework.MOUSE_DRAGGED,e));
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		updateMouseLocation(e);
		this.dispatchEvent(new EventMouse(Framework.MOUSE_MOVED,e));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		this.dispatchEvent(new EventMouse(Framework.MOUSE_CLICKED,e));
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		this.dispatchEvent(new EventMouse(Framework.MOUSE_ENTERED,e));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		this.dispatchEvent(new EventMouse(Framework.MOUSE_EXITED,e));
	}
	@Override
	public void mousePressed(MouseEvent e) {
		this.dispatchEvent(new EventMouse(Framework.MOUSE_PRESSED,e));
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		this.dispatchEvent(new EventMouse(Framework.MOUSE_RELEASED,e));
	}
	private void updateMouseLocation(MouseEvent e){
		Mouse.setX(e.getX(), this);
		Mouse.setY(e.getY(), this);
	}
}
