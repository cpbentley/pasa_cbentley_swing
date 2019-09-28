package pasa.cbentley.swing.imytab;

import java.util.ArrayList;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;

public class TabbedBentleyPanelComp extends TabbedBentleyPanel {

   /**
    * 
    */
   private static final long        serialVersionUID = -5201186913353128380L;

   private ArrayList<AbstractMyTab> tabs;

   public TabbedBentleyPanelComp(SwingCtx sc, String id) {
      super(sc, id);
      tabs = new ArrayList<AbstractMyTab>();
   }

   public void addTabToArray(AbstractMyTab tab) {
      tabs.add(tab);
   }

   public void disposeTab() {

   }

   public void initTabs() {
      for (AbstractMyTab tab : tabs) {
         this.addMyTab(tab);
      }
   }

   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabbedBentleyPanelComp");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabbedBentleyPanelComp");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
