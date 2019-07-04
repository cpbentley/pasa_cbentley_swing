package pasa.cbentley.swing.imytab;

import javax.swing.JPopupMenu;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.menu.NavigateMenu;

/**
 * Provides default actions for moving the tab.
 * This is the menu that will be shown when activated on the tab title
 * @author Charles Bentley
 *
 */
public class TabPopupMenu extends JPopupMenu implements IMyGui {

   private IMyTab tab;
   private SwingCtx sc;
   private NavigateMenu navigateMenu;

   public TabPopupMenu(SwingCtx sc) {
      this.sc = sc;
      navigateMenu = new NavigateMenu(sc);
      this.add(navigateMenu);
   }

   public IMyTab getTab() {
      return tab;
   }

   public void setTab(IMyTab tab) {
      this.tab = tab;
   }

   public void guiUpdate() {
      navigateMenu.guiUpdate();
   }
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TabPopupMenu");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabPopupMenu");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
   

}
