package pasa.cbentley.swing.imytab;

import javax.swing.JMenuBar;

import pasa.cbentley.swing.window.CBentleyFrame;

public interface ITabMenuBarFactory {

   
   /**
    * 
    * @param owner
    * @param frame
    * @return
    */
   public JMenuBar getMenuBar(Object owner, CBentleyFrame frame);
}
