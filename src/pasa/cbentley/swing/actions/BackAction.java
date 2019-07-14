package pasa.cbentley.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

public class BackAction extends BAbstractAction implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -1955694886937869519L;

   private IBackForwardable  r;

   public BackAction(SwingCtx sc, IBackForwardable r) {
      super(sc);
      this.r = r;
   }

   public BackAction(SwingCtx sc, IBackForwardable r, Integer mnemonic) {
      super(sc);
      this.r = r;
      putValue(MNEMONIC_KEY, mnemonic);
   }

   public void actionPerformed(ActionEvent e) {
      //#debug
      sc.toDLog().pFlow("", null, BackAction.class, "actionPerformed", ITechLvl.LVL_05_FINE, true);

      r.cmdNavBack();
   }

   public void guiUpdate() {
      this.putValue(Action.NAME, sc.getResString("action.back.name")); //update with res bundle
      this.putValue(Action.SMALL_ICON, sc.getResIcon("action.back.icon"));
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BackAction");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BackAction");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
