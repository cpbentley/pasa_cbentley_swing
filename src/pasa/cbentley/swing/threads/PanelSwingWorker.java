package pasa.cbentley.swing.threads;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * {@link SwingWorker} with call backs to the UI Panel ( {@link IWorkerPanel} ) displaying the results of the work.
 * <br>
 * <br>
 * Usually there will be a progress bar with {@link IWorkerPanel#panelSwingWorkerProcessed(BentleySwingWorker, int)}
 * <br>
 * Worker for counting working being done.
 * 
 * @author Charles Bentley
 *
 * @param <K>
 * @param <V>
 */
public abstract class PanelSwingWorker<K, V> extends SwingWorker<K, V> implements IStringable, PropertyChangeListener {

   private volatile boolean   isPaused;

   private SwingCtx           sc;

   /**
    * Not null
    */
   protected final WorkerStat workerStat;

   protected IWorkerPanel     wp;

   public PanelSwingWorker(SwingCtx sc, IWorkerPanel wp) {
      this.sc = sc;
      this.wp = wp;
      workerStat = new WorkerStat(sc);
      this.addPropertyChangeListener(this);
   }

   /**
    * Exception handling
    */
   protected void done() {
      boolean isCancelled = isCancelled();
      //#debug
      toDLog().pFlow("(" + ((isCancelled) ? "canceled" : "success") + ") for ", this, PanelSwingWorker.class, "done", LVL_04_FINER, true);

      if (isCancelled) {
         //no need to get execution exceptions
         wp.panelSwingWorkerCancelled(this);
      } else {
         try {
            K k = get();
            processResult(k);
         } catch (ExecutionException e) {
            //none since we catch all exceptions
            e.getCause().printStackTrace();
            String msg = String.format("Unexpected problem: %s", e.getCause().toString());
            JOptionPane.showMessageDialog((Component) wp, msg, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         wp.panelSwingWorkerDone(this);
      }
   }

   /**
    * {@link WorkerStat} of this {@link PanelSwingWorker}
    * 
    * @return
    */
   public WorkerStat getWorkerStat() {
      return workerStat;
   }

   public final boolean isPaused() {
      return isPaused;
   }

   /**
    * Custom cancel behavior. if its fails, it will call the 
    * {@link SwingWorker#cancel(boolean)}
    */
   public void cancelNiceButInterrupt() {
      cancel(true);
   }

   /**
    * Try to pause the worker.
    */
   public final void pause() {
      if (!isPaused() && !isDone()) {
         isPaused = true;
         firePropertyChange("paused", false, true);
      }
   }

   protected void processResult(K k) {

   }

   public abstract String getNameForUser();

   public void propertyChange(PropertyChangeEvent e) {
      //this log shows, this method is called ins the AWT thread
      //#debug
      //toDLog().pFlow(sc.toSD().d1(e), this, PanelSwingWorker.class, "propertyChange", LVL_04_FINER, DEV_0_1LINE_THREAD);

      if (e.getNewValue() == StateValue.STARTED) {
         wp.panelSwingWorkerStarted(this);
      }
   }

   public final void resume() {
      if (isPaused() && !isDone()) {
         isPaused = false;
         firePropertyChange("paused", true, false);
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PanelSwingWorker");
      dc.appendVarWithSpace("isPaused", isPaused);
      dc.nlLvl(workerStat);
      dc.nlLvl(wp, "IWorkerPanel");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelSwingWorker");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
