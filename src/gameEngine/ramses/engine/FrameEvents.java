package gameEngine.ramses.engine;

public class FrameEvents {
	
	//Frame events
	public static final String ENTER_FRAME = "EnterFrameEvent";
	public static final String ENTER_SECOND = "EnterSecondEvent";
	
	//Display events
	public static final String ADDED_TO_STAGE = "AddedToStageEvent";
	public static final String REMOVED_FROM_STAGE = "RemovedFromStageEvent";
	
	//Mouse Events
	public static final String MOUSE_DRAGGED = "MouseOnDragEvent";
	public static final String MOUSE_MOVED = "MouseOnMoveEvent";
	public static final String MOUSE_CLICKED = "MouseOnClickEvent";
	public static final String MOUSE_PRESSED = "MouseOnPressedEvent";
	public static final String MOUSE_RELEASED = "MouseOnReleaseEvent";
	public static final String MOUSE_ENTERED = "MouseOnEnterEvent";
	public static final String MOUSE_EXITED = "MouseOnExitEvent";
	
	//Keyboard Events
	public static final String KEY_PRESSED_START = "KeyPressedStartEvent";
	public static final String KEY_PRESSED = "KeyPressedEvent";
	public static final String KEY_RELEASED = "KeyReleasedEvent";
	public static final String KEY_TYPED = "KeyTypedEvent";
	
	//Screen Events 
	public static final String SWITCH_SCREEN = "SwitchScreenEvent";
}
