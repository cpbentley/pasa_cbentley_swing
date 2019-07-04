package pasa.cbentley.swing.interfaces;

import pasa.cbentley.core.src4.interfaces.ICallBack;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * A call back for a worker in the same thread
 * Similar to {@link ICallBack} with with additional 
 * @author Charles Bentley
 *
 */
public interface ICallBackSwing extends IStringable {

   /**
    * If object is instance of {@link Throwable}, can assume, it failed
    * <br>
    * This method MUST be called in the AWT main thread.
    * @param o Object is often the source on which the callback was provided
    */
   public void callBackInSwingThread(Object o);
}
