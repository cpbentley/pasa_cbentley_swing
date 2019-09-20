package pasa.cbentley.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * Global clear action. Acts on currently active {@link IClearable}
 * set on 
 * @author Charles Bentley
 *
 */
public class ActionPause extends BAbstractAction implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -1955694886937869519L;

   public ActionPause(SwingCtx sc) {
      super(sc);
      guiUpdate();
   }

   public ActionPause(SwingCtx sc, Integer mnemonic) {
      super(sc);
      putValue(MNEMONIC_KEY, mnemonic);
      guiUpdate();
   }

   public void actionPerformed(ActionEvent e) {
      //#debug
      sc.toDLog().pFlow("", null, ActionPause.class, "actionPerformed", ITechLvl.LVL_05_FINE, true);
      sc.getCmds().cmdClear();
   }

   public void guiUpdate() {
      this.putValue(Action.NAME, sc.getResString("action.pause.name")); //update with res bundle
      this.putValue(Action.SMALL_ICON, sc.getResIcon("action.pause.icon"));
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ActionPause");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ActionPause");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
