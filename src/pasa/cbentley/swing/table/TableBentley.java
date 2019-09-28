package pasa.cbentley.swing.table;

import java.awt.event.MouseListener;
import java.util.Comparator;

import javax.swing.DefaultRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.widgets.b.BPopupMenu;

public class TableBentley<T> implements IStringable, IMyGui {

   private ModelTableBAbstract<T>  model;

   private SwingCtx                 sc;

   private JScrollPane              scrollPane;

   private JTable                   table;

   private MouseEventsModelListener lis;

   private BPopupMenu               popupMenu;

   private TableRowNumber           rowTable;

   public TableBentley(SwingCtx sc, ModelTableBAbstract<T> model) {
      this.sc = sc;
      table = new JTable(model);
      this.model = model;
   }

   /**
    * Each columns may be set a {@link MouseListener}
    */
   public void addMouseSupportToTableCells() {

   }

   /**
    * TODO how to prevent sorting.. fix sorting
    */
   public void cmdToggleRowHeader() {
      if (rowTable == null) {
         rowTable = new TableRowNumber(table);
         scrollPane.setRowHeaderView(rowTable);
         scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
      } else {
         rowTable = null;
         scrollPane.setRowHeaderView(null);
      }
      //force a GUI update
      sc.guiUpdate();
   }

   /**
    * Adds Mouse support to the table header
    */
   public void addMouseSupportToTableHeader() {
      lis = new MouseEventsModelListener(sc, table, model);

      table.getTableHeader().addMouseMotionListener(lis);
      table.getTableHeader().addMouseWheelListener(lis);
      table.getTableHeader().addMouseListener(lis);
   }

   /**
    * Resets the data to null
    */
   public void clearTableModel() {
      model.clear();
   }

   public ModelTableBAbstract<T> getModel() {
      return model;
   }

   public void guiUpdate() {
      lis.setGlobalTip(sc.getResString("table.globaltip"));
      model.fireTableStructureChanged(); //force update of columns, cell renderers
      if (popupMenu != null) {
         popupMenu.guiUpdate();
      }
   }

   public JScrollPane getScrollPane() {
      if (scrollPane == null) {
         scrollPane = new JScrollPane(getTable());
      }
      return scrollPane;
   }

   public JTable getTable() {
      return table;
   }

   public void setBPopupMenu(BPopupMenu popupMenu) {
      this.popupMenu = popupMenu;
      table.setComponentPopupMenu(popupMenu);
   }

   public void setColumnRenderer(int col, TableCellRenderer renderer) {
      table.getColumnModel().getColumn(col).setCellRenderer(renderer);
   }

   /**
    * 
    * @param col
    */
   public void setDefSort(int col) {
      table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      //table.setAutoCreateRowSorter(true);
      TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
      table.setRowSorter(sorter);
      table.getRowSorter().toggleSortOrder(col);

   }

   public void setComparator(int col, Comparator comparator) {
      RowSorter<? extends TableModel> rowSorter = table.getRowSorter();
      if(rowSorter != null && rowSorter instanceof DefaultRowSorter) {
         DefaultRowSorter sorter = (DefaultRowSorter) rowSorter;
         sorter.setComparator(col, comparator);
      }
   }
   
   public void setModel(ModelTableBAbstract<T> model) {
      this.model = model;
      this.table.setModel(model);
      TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
      table.setRowSorter(sorter);
   }

   public void setTable(JTable table) {
      this.table = table;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TableBentley");
   }


   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TableBentley");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
