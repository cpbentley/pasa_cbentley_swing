package pasa.cbentley.swing.actions;

import pasa.cbentley.core.src4.logging.IStringable;

public interface IExitable extends IStringable {

   /**
    * Chance to start threads that will run during the exit prodecures.
    * 
    * A Gui frame showing a cancel exit button for example
    */
   public void prepareExit();
   
   public void cmdExit();
}
