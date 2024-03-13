package pasa.cbentley.swing.ctx;

import java.util.ArrayList;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.actions.BActionClear;
import pasa.cbentley.swing.actions.IClearable;

public class SwingCmds implements IStringable {

   private SwingCtx sc;

   private ArrayList<IClearable> clearables = new ArrayList<IClearable>();

   private BActionClear clearAction;
   
   public SwingCmds(SwingCtx sc) {
      this.sc = sc;
   }
   
   public BActionClear getClearAction() {
      if(clearAction == null) {
         clearAction = new BActionClear(sc);
      }
      return clearAction;
   }
   

   public void addClearable(IClearable clearable) {
      clearables.add(clearable);
   }
   
   public void removeClearable(IClearable clearable) {
      clearables.remove(clearable);
   }
   
   public void cmdClear() {
      for (IClearable clearable : clearables) {
         clearable.cmdClear();
      }
   }
   
   
  
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingCmds");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingCmds");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug
   


}
