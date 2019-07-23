package pasa.cbentley.swing.threads;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Call back
 * @author Charles Bentley
 *
 */
public interface IWorkerPanel extends IStringable {

   /**
    * Called just before the worker is started
    * @param worker
    */
   public void panelSwingWorkerStarted(PanelSwingWorker worker);

   /**
    * Called in the UI thread when worker is done
    * @param worker
    */
   public void panelSwingWorkerDone(PanelSwingWorker worker);

   /**
    * Worker calls this method to let the GUI know about its progress.
    * <br>
    * Gui may update table view of its model. WorkerStat 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount);

   /**
    * Called in the ui thread if worker is cancelled
    * @param worker
    */
   public void panelSwingWorkerCancelled(PanelSwingWorker worker);

}
