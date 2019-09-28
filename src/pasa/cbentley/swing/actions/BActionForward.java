package pasa.cbentley.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;

public class BActionForward extends BActionAbstract {

   /**
    * 
    */
   private static final long serialVersionUID = -8492009864881596711L;

   private IBackForwardable  r;

   public BActionForward(SwingCtx sc, IBackForwardable r) {
      super(sc);
      this.r = r;
   }

   public BActionForward(SwingCtx sc, IBackForwardable r, Integer mnemonic) {
      super(sc);
      this.r = r;
      putValue(MNEMONIC_KEY, mnemonic);
   }

   public void actionPerformed(ActionEvent e) {
      r.cmdNavForward();
   }

   public void  guiUpdate() {
      this.putValue(Action.NAME, sc.getResString("action.forward.name")); //update with res bundle
      this.putValue(Action.SMALL_ICON, sc.getResIcon("action.forward.icon"));
   }
   
   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ForwardAction");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ForwardAction");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
