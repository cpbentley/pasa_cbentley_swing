package pasa.cbentley.swing.task;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechDev;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.ObjectSC;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Reads the number of visible frames from {@link SwingCtx}
 * 
 * When 0, run the {@link IExitable} or if null hard exit.
 * 
 * @author Charles Bentley
 *
 */
public class TaskExitSmoothIfNoFrames extends ObjectSC implements Runnable, IStringable {

   public TaskExitSmoothIfNoFrames(SwingCtx sc) {
      super(sc);
   }

   public void run() {
      int numVisible = sc.getFrames().getNumVisible();

      //#debug
      toDLog().pFlow("numVisible=" + numVisible, sc.getFrames(), TaskExitSmoothIfNoFrames.class, "run@28", LVL_05_FINE, true);
      if (numVisible == 0) {
         IExitable exitable = sc.getExitTask();
         if (exitable != null) {
            exitable.cmdExit();
         } else {
            //#debug
            toDLog().pFlow("Calling System.exit(0)", null, TaskExitSmoothIfNoFrames.class, "run@35", LVL_05_FINE, ITechDev.DEV_4_THREAD);
            System.exit(0);
         }
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, TaskExitSmoothIfNoFrames.class, 50);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, TaskExitSmoothIfNoFrames.class, 50);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
