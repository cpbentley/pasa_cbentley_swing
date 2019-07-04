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
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Call back to the Panel displaying the results of the work.
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

   /**
    * Not null
    */
   protected final WorkerStat workerStat;

   private volatile boolean   isPaused;

   protected IWorkerPanel     wp;

   private SwingCtx           sc;

   public PanelSwingWorker(SwingCtx sc, IWorkerPanel wp, WorkerStat ws) {
      this.sc = sc;
      this.wp = wp;
      if (ws == null) {
         ws = new WorkerStat(sc);
      }
      workerStat = ws;
   }

   public WorkerStat getWorkerStat() {
      return workerStat;
   }

   public PanelSwingWorker(SwingCtx sc, IWorkerPanel wp) {
      this.sc = sc;
      this.wp = wp;
      workerStat = new WorkerStat(sc);

      this.addPropertyChangeListener(this);
   }

   public void propertyChange(PropertyChangeEvent e) {
      //this log shows, this method is called ins the AWT thread
      //#debug
      //toDLog().pFlow(sc.toSD().d1(e), this, PanelSwingWorker.class, "propertyChange", LVL_04_FINER, DEV_0_1LINE_THREAD);

      if (e.getNewValue() == StateValue.STARTED) {
         wp.panelSwingWorkerStarted(this);
      }
   }

   /**
    * Exception handling
    */
   protected void done() {
      //#debug
      toDLog().pFlow("for ", this, PanelSwingWorker.class, "done", LVL_04_FINER, true);

      if (isCancelled()) {
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

   protected void processResult(K k) {

   }

   public final boolean isPaused() {
      return isPaused;
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

   public final void resume() {
      if (isPaused() && !isDone()) {
         isPaused = false;
         firePropertyChange("paused", true, false);
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PanelSwingWorker");
      dc.appendVarWithSpace("isPaused", isPaused);
      dc.nlLvl(workerStat);
      dc.nlLvl(wp, "IWorkerPanel");
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PanelSwingWorker");
   }
   //#enddebug

}
