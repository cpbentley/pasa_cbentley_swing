package pasa.cbentley.swing.table;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.model.BentleyAbstractModel;
import pasa.cbentley.swing.threads.IWorkerPanel;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

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

   public AbstractTabTable(SwingCtx sc, String internalID) {
      super(sc, internalID);
      this.setLayout(new BorderLayout());
   }

   public void cmdToggleRowHeader() {
      table.cmdToggleRowHeader();
   }

   protected abstract BentleyAbstractModel<T> createTableModel();

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

   public void guiUpdate() {
      super.guiUpdate();
      if (table != null) {
         table.guiUpdate();
      }
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
    * <li>ListSelectionModel.SINGLE_SELECTION
    * <li>SINGLE_INTERVAL_SELECTION   ListSelectionModel.SINGLE_INTERVAL_SELECTION
    * <li> MULTIPLE_INTERVAL_SELECTION ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
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
      getSwingCtx().getTU().sortTableDescending(getBenTable().getTable(), col);
   }

   //#enddebug

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

}
