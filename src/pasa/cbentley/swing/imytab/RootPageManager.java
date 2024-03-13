package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Class used by PascalSwingCtx Components to request focus on a given page
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public class RootPageManager implements ITabPageSettable {

   private SwingCtx           sc;

   private TabbedBentleyPanel root;

   public RootPageManager(SwingCtx sc) {
      this.sc = sc;

   }

   public void setActivePage(TabPage tabPage) {
      showTabPage(tabPage);
   }

   
   public TabPage getPageStringFirst(String id) {
      return root.getPageFirst(id);
   }
   /**
    * The page strings tells us where it is
    * @param ps
    */
   public void showTabPageString(PageStrings ps) {
      String s = ps.getNextString(); //iterator
      //#debug
      //sc.toDLog().pFlow("nextString=" + s, ps, RootPageManager.class, "showTabPageString", IDLog.LVL_05_FINE, true);
      
      //identify the tab with the Strings. we have to find 
      String id = root.getTabInternalID();
      if (s.equals(id)) {
         root.showTabPageStrings(ps);
      } else {
         //the page does not fit this tree
         sc.getLog().consoleLogError("Root does not have");
      }
   }

   public void showTabPage(TabPage tp) {
      PageStrings ps = tp.getPageStrings();
      ps.reset();
      showTabPageString(ps);
   }

   public TabbedBentleyPanel getRoot() {
      return root;
   }

   public void setRoot(TabbedBentleyPanel root) {
      this.root = root;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "RootPageManager");
      dc.nlLvl("Root Panel", root);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "RootPageManager");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

 

}
