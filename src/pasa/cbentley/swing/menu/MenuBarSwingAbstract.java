package pasa.cbentley.swing.menu;

import javax.swing.JMenuBar;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.menu.ITechMenu;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * 
 * @author Charles Bentley
 *
 */
public abstract class MenuBarSwingAbstract extends JMenuBar implements IMyGui, ITechMenu {

   /**
    * 
    */
   private static final long serialVersionUID = 1774778387836051346L;

   protected CBentleyFrame   owner;

   protected final SwingCtx  sc;

   /**
    * 
    * @param psc
    * @param owner
    */
   public MenuBarSwingAbstract(SwingCtx psc, CBentleyFrame owner) {
      this.sc = psc;
      this.owner = owner;
   }

   /**
    * 
    */
   public void guiUpdate() {
      sc.guiUpdateOnChildren(this);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MenuBarSwingAbstract");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MenuBarSwingAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
