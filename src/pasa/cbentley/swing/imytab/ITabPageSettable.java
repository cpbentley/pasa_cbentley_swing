package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.logging.IStringable;

public interface ITabPageSettable extends IStringable {

   
   /**
    * Make the tabPage user visible and give the action/keyboard focus.
    * @param tabPage
    */
   public void setActivePage(TabPage tabPage);
}
