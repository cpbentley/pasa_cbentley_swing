package pasa.cbentley.swing.threads;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Call back
 * @author Charles Bentley
 *
 */
public interface IWorkerPanel extends IStringable {

   public void panelSwingWorkerStarted(PanelSwingWorker worker);

   public void panelSwingWorkerDone(PanelSwingWorker worker);

   /**
    * Worker calls this method to let the GUI know about its progress.
    * <br>
    * Gui may update table view of its model. WorkerStat 
    * @param worker
    * @param entryCount
    */
   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount);

   public void panelSwingWorkerCancelled(PanelSwingWorker worker);

   
}
