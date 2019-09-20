package pasa.cbentley.swing.actions;

import javax.swing.AbstractAction;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * Abstract stub class for all our Swing Actions
 * @author Charles Bentley
 *
 */
public abstract class BAbstractAction extends AbstractAction implements IMyGui, IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = 4061766705523249393L;
   
   
   protected final SwingCtx sc;

   public BAbstractAction(SwingCtx sc) {
      this.sc = sc;
   }
   
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BAbstractAction");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BAbstractAction");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
   
}
