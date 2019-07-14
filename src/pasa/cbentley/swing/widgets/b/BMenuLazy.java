package pasa.cbentley.swing.widgets.b;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class BMenuLazy extends BMenu implements MenuListener {

   /**
    * 
    */
   private static final long serialVersionUID = -762448178977168373L;

   private boolean           isInitialized;

   public BMenuLazy(SwingCtx sc, String key) {
      super(sc, key);
      this.addMenuListener(this);
   }

   public void initCheck() {
      if (!isInitialized) {
         initMenu();
         //we have to update the gui on the menu
         sc.guiUpdateOnChildrenMenu(this);
         isInitialized = true;
      }
   }

   protected abstract void initMenu();

   public void menuCanceled(MenuEvent e) {

   }

   public void menuDeselected(MenuEvent e) {

   }

   public void menuSelected(MenuEvent e) {
      if (e.getSource() == this) {
         initCheck();
      }
   }
}
