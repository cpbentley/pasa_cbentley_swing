package pasa.cbentley.swing.task;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechDev;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Reads the number of visible frames from {@link SwingCtx}
 * 
 * When 0, run the {@link IExitable} or if null hard exit.
 * 
 * @author Charles Bentley
 *
 */
public class TaskExitSmoothIfNoFrames implements Runnable, IStringable {

   protected final SwingCtx sc;

   public TaskExitSmoothIfNoFrames(SwingCtx sc) {
      this.sc = sc;

   }

   public void run() {
      int numVisible = sc.getFrames().getNumVisible();

      //#debug
      sc.toDLog().pFlow("numVisible=" + numVisible, sc.getFrames(), TaskExitSmoothIfNoFrames.class, "run", LVL_05_FINE, true);
      if (numVisible == 0) {
         IExitable exitable = sc.getExitTask();
         if (exitable != null) {
            exitable.cmdExit();
         } else {
            //#debug
            sc.toDLog().pFlow("Calling System.exit(0)", null, TaskExitSmoothIfNoFrames.class, "run", LVL_05_FINE, ITechDev.DEV_4_THREAD);
            System.exit(0);
         }
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TaskExitSmooth");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TaskExitSmooth");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}
