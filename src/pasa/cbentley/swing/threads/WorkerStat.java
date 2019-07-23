package pasa.cbentley.swing.threads;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Tells use about the worker.
 * <br>
 * Stats are read by GUI and written by worker thread.
 *
 * Each {@link PanelSwingWorker} may sub class {@link WorkerStat} to include more stats.
 * 
 * Otherwise the default is used
 * @author Charles Bentley
 *
 */
public class WorkerStat implements IStringable {

   protected volatile int entriesDone;

   protected volatile int entriesTotal;

   private SwingCtx sc;

   public WorkerStat(SwingCtx sc) {
      this.sc = sc;
   }

   public int getEntriesDone() {
      return entriesDone;
   }

   /**
    * -1 if unknown
    * @return
    */
   public int getEntriesTotal() {
      return entriesTotal;
   }

   public void setEntriesCount(int count) {
      this.entriesDone = count;
   }

   public void setEntriesTotal(int total) {
      this.entriesTotal = total;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WorkerStat");
      dc.appendVarWithSpace("entriesDone", entriesDone);
      dc.appendVarWithSpace("entriesTotal", entriesTotal);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WorkerStat");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
