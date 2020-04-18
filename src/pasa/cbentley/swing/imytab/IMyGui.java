package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.IEventsSwing;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Avoid that every single Gui components registers for updating itself
 * 
 * @author Charles Bentley
 *
 */
public interface IMyGui extends IStringable, IEventsSwing {

   /**
    * Update GUI components as if component was created.
    * <li>Update all user displayed texts/strings. (language has changed)
    * <li>Update icons as they may have changed as well. Icon settings update 
    * <br>
    * <br>
    * Global update with {@link SwingCtx#guiUpdate()}
    * <br>
    * But settings text will request a change?
    * 
    * Call this when a component is created and initialized. 
    * {@link TabbedBentleyPanel} must call it when loading a non initialized tab
    */
   public void guiUpdate();
}
