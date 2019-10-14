package pasa.cbentley.swing.cmd;

import pasa.cbentley.core.src4.logging.IStringable;

public interface ICommandableRefresh extends IStringable {

   
   /**
    * The source has new content. Refresh
    * @param source
    */
   public void cmdRefresh(Object source);
}
