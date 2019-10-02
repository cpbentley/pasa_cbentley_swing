package pasa.cbentley.swing.table;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

/**
 * Base class for hosting a {@link TableBentley} in a {@link AbstractMyTab}
 * @author Charles Bentley
 *
 * @param <T>
 */
public abstract class AbstractTabTable<T> extends AbstractMyTab implements IWorkerPanel {

   /**
    * 
    */
   private static final long  serialVersionUID = -1129654689399553379L;

   private int                selectionMode;

   /**
    * Contains the MyAbstractModel
    */
   private TableBentley<T>    table;

   /**
    * Worker used to populate the table.
    */
   protected PanelSwingWorker workerTable;

   /**
    * 
    * @param sc
    * @param internalID
    */
   public AbstractTabTable(SwingCtx sc, String internalID) {
      super(sc, internalID);
      this.setLayout(new BorderLayout());
   }

   /**
    * Caller must be aware that this method might be slow and will run in the GUI thread.
    * 
    * Caller should display a message before calling this and hiding it once done.
    */
   public void cmdToggleRowHeader() {
      table.cmdToggleRowHeader();
   }
   
   public void cmdFit() {
      resizeTableColumns();
   }

   /**
    * 
    * @return
    */
   protected abstract ModelTableBAbstract<T> createTableModel();

   /**
    * 
    */
   public void disposeTab() {
   }

   /**
    * The {@link TableBentley}. Register selection listeners here
    * @return
    */
   public TableBentley<T> getBenTable() {
      if (table == null) {
         //tab was init yet
         initCheck();
         if (table == null) {
            throw new NullPointerException("Failed to initialized correctly");
         }
      }
      return table;
   }

   /**
    * 
    * @return
    */
   public JTable getJTable() {
      return getBenTable().getTable();
   }

   /**
    * 
    */
   public void guiUpdate() {
      //remember current size of columns
      int[] sizes =  getSwingCtx().getTU().getColumnsSizes(getBenTable().getTable());
      super.guiUpdate();
      if (table != null) {
         table.guiUpdate();
      }
      getSwingCtx().getTU().setColumnsSizes(getBenTable().getTable(),sizes);
   }

   /**
    * Sets a Borderlayout
    * Put the table in CENTER of borderlayout
    */
   protected void initTab() {
      this.setLayout(new BorderLayout());

      this.table = new TableBentley<T>(getSwingCtx(), createTableModel());
      BPopupMenu createTablePopUpMenu = new BPopupMenu(sc, getJTable());
      subPopulatePopMenu(createTablePopUpMenu);
      table.setBPopupMenu(createTablePopUpMenu);
      this.add(getBenTable().getScrollPane(), BorderLayout.CENTER);
      table.addMouseSupportToTableHeader();
   }

   public void panelSwingWorkerStarted(PanelSwingWorker worker) {

   }

   public void resizeTableColumns() {
      getSwingCtx().getTU().resizeColumnWidthNoMax(getBenTable().getTable());
   }

   /**
    * <li> {@link ListSelectionModel#SINGLE_SELECTION}
    * <li> {@link ListSelectionModel#SINGLE_INTERVAL_SELECTION}
    * <li> {@link ListSelectionModel#MULTIPLE_INTERVAL_SELECTION}
    * @param i
    */
   public void setSelectionMode(int i) {
      selectionMode = i;
      getBenTable().getTable().setSelectionMode(i);
   }

   /**
    * 
    */
   public void setSelectionModeSingle() {
      this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
   }

   public void sortTableColAscending(int col) {
      getSwingCtx().getTU().sortTableAscending(getBenTable().getTable(), col);
   }

   public void sortTableColDescending(int col) {
      getSwingCtx().getTU().sortTableDescending(getBenTable().getTable(), col);
   }

   protected void subPopulatePopMenu(BPopupMenu createTablePopUpMenu) {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "AbstractTabTable");
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractTabTable");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug
}
