package pasa.cbentley.swing.menu;

import java.awt.event.InputEvent;

import pasa.cbentley.core.src4.interfaces.ITech;

/**
 * Shortcuts for {@link InputEvent} masks
 * 
 * Just mark classes who need them.
 * 
 * @author Charles Bentley
 *
 */
public interface ITechMenu extends ITech {

   public static final int modAltShit      = InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK;

   public static final int modAlt          = InputEvent.ALT_DOWN_MASK;

   public static final int modCtrl         = InputEvent.CTRL_DOWN_MASK;

   public static final int modCtrlAlt      = InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK;

   public static final int modCtrlAltShift = InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK;

   public static final int modCtrlShift    = InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK;

   public static final int modShift        = InputEvent.SHIFT_DOWN_MASK;
}
