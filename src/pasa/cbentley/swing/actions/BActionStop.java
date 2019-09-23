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
public class BActionStop extends BActionAbstract implements IMyGui {

   /**
    * 
    */
   private static final long serialVersionUID = -1955694886937869519L;

   public BActionStop(SwingCtx sc) {
      super(sc);
      guiUpdate();
   }

   public BActionStop(SwingCtx sc, Integer mnemonic) {
      super(sc);
      putValue(MNEMONIC_KEY, mnemonic);
      guiUpdate();
   }

   public void actionPerformed(ActionEvent e) {
      //#debug
      sc.toDLog().pFlow("", null, BActionStop.class, "actionPerformed", ITechLvl.LVL_05_FINE, true);
      sc.getCmds().cmdClear();
   }

   public void guiUpdate() {
      this.putValue(Action.NAME, sc.getResString("action.stop.name")); //update with res bundle
      this.putValue(Action.SMALL_ICON, sc.getResIcon("action.stop.icon"));
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "ActionStop");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ActionStop");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
   

}
