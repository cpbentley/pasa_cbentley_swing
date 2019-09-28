package pasa.cbentley.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * Global clear action. Acts on currently active {@link IClearable}
 * set on 
 * @author Charles Bentley
 *
 */
public class BActionRefresh extends BActionAbstract implements IMyGui {

   /**
    * 
    */
   private static final long   serialVersionUID = -1955694886937869519L;

   private ICommandableRefresh refresher;

   public BActionRefresh(SwingCtx sc, ICommandableRefresh refresher) {
      super(sc);
      this.refresher = refresher;
      keyName = "action.refresh.name";
   }

   public void actionPerformed(ActionEvent e) {
      //#debug
      sc.toDLog().pFlow("", null, BActionRefresh.class, "actionPerformed", ITechLvl.LVL_05_FINE, true);
      refresher.cmdRefresh(this);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BActionRefresh");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BActionRefresh");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

}
