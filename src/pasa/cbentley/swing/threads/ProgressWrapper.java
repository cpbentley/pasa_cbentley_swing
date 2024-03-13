package pasa.cbentley.swing.threads;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.thread.DummyProgress;
import pasa.cbentley.core.src4.thread.IBProgessable;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Wraps a {@link IBProgessable} so that all calls to this {@link IBProgessable} are made in the GUI thread.
 * <br>
 * Does the synchro work between the worker and progress.
 * <br>
 * <br>
 * Setters all run inside a Runnable dispatched to the AWT Event thread.
 * <br>
 * <br>
 * Getters all run synchronized
 * <br>
 * <br>
 * WorkerThread -> {@link ProgressWrapper} -> SwingGUI
 * 
 * @author Charles Bentley
 *
 */
public class ProgressWrapper implements IBProgessable {

   private SwingCtx      sc;

   private IBProgessable spp;

   public ProgressWrapper(SwingCtx appCtx) {
      this.sc = appCtx;
   }

   public ProgressWrapper(SwingCtx appCtx, IBProgessable p) {
      this.sc = appCtx;
      this.spp = p;
   }

   public String getTitle() {
      return null;
   }

   public void setTitle(final String title) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setTitle(title);
         }
      });
   }

   public void error(final String msg) {
      sc.execute(new Runnable() {
         public void run() {
            spp.error(msg);
         }
      });
   }

   public void setMaxValue(final int mv) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setMaxValue(mv);
         }
      });
   }

   public void set(final String title, final String info, final String label, final int maxValue, final int level) {
      sc.execute(new Runnable() {
         public void run() {
            spp.set(title, info, label, maxValue, level);
         }
      });
   }

   public void setLabel(final String s) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setLabel(s);
         }
      });
   }

   public int getLvl() {
      //synchronize?
      return spp.getLvl();
   }

   public void close() {
      this.close("");
   }

   public void setLvl(final int lvl) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setLvl(lvl);
         }
      });
   }

   public void setValue(final int value) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setValue(value);
         }
      });
   }

   public void setInfo(final String info) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setInfo(info);
         }
      });
   }

   public void increment(final int value) {
      sc.execute(new Runnable() {
         public void run() {
            spp.increment(value);
         }
      });
   }

   public IBProgessable getChild() {
      return getChild(null);
   }

   /**
    * Blocking call until the GUI has updated its child
    */
   public IBProgessable getChild(final IBRunnable runnable) {
      final ProgressWrapper pw = new ProgressWrapper(sc);
      //we must create it in event thread. we have to wait
      try {
         sc.execute(new Runnable() {
            public void run() {
               IBProgessable sppNew = (IBProgessable) spp.getChild(runnable);
               pw.spp = sppNew;
            }
         });
      } catch (Exception e) {
         e.printStackTrace();
         return new DummyProgress(sc.getUC());
      }
      return pw;
   }

   public boolean isCanceled() {
      // TODO Auto-generated method stub
      return false;
   }

   public void close(final String msg) {
      sc.execute(new Runnable() {
         public void run() {
            spp.close(msg);
         }
      });
   }

   public void setSwingProgressPanel(IBProgessable spp) {
      this.spp = spp;
   }


   public void setTimeLeft(final long value) {
      sc.execute(new Runnable() {
         public void run() {
            spp.setTimeLeft(value);
         }
      });
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ProgressWrapper");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ProgressWrapper");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
