package pasa.cbentley.swing.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Custom Swing {@link AbstractTableModel}.
 * 
 * No assumption on the column model.
 * 
 * {@link ModelTableBAbstract#computeStats(Object, int)}
 * 
 * @author Charles Bentley
 *
 * @param <T>
 */
public abstract class ModelTableBAbstract<T> extends AbstractTableModel implements IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = 5289855560272199017L;

   private ArrayList<T>      data             = new ArrayList<T>();

   protected final SwingCtx  sc;

   /**
    * Simple constructor. Assign the context.
    * @param sc
    */
   public ModelTableBAbstract(SwingCtx sc) {
      this.sc = sc;
   }

   /**
    * Add the value as a row data structure, compute stat and call 
    * {@link AbstractTableModel#fireTableRowsInserted(int, int)}
    * @param value
    */
   public void addRow(T value) {
      int rowCount = getRowCount();
      data.add(value);
      computeStats(value, rowCount);
      fireTableRowsInserted(rowCount, rowCount);
   }

   /**
    * for each of the values add a row data structure and compute stat.
    * 
    *  Then call {@link AbstractTableModel#fireTableRowsInserted(int, int)} for those rows.
    * @param rows
    */
   public void addRows(List<T> rows) {
      int rowCount = getRowCount();
      int numNewRows = rows.size();
      int lastNewRow = rowCount + numNewRows - 1;
      data.addAll(rows);
      for (int i = 0; i < numNewRows; i++) {
         computeStats(rows.get(i), rowCount + i);
      }
      try {
         fireTableRowsInserted(rowCount, lastNewRow);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Convenience method.
    * @param value
    */
   public void addRows(T... value) {
      addRows(Arrays.asList(value));
   }

   /**
    * Clear all the rows.
    * 
    * {@link AbstractTableModel#fireTableRowsDeleted(int, int)}
    */
   public void clear() {
      int rowCount = getRowCount();
      if (rowCount != 0) {
         int lastRow = rowCount - 1;
         data.clear();
         fireTableRowsDeleted(0, lastRow);
      }
   }

   /**
    * Possibility to compute values from added elements.
    * 
    * This method is called for newly added elements. So only statistics about
    * itself or past added elements can be computed.
    * 
    * For computing stats knowing all elements have been added
    * 
    * @param a
    * @param row the row of the object being inserted.
    */
   protected abstract void computeStats(T a, int row);

   /**
    * Called once data feeder knows all the elements have been added to the model.
    * 
    * The default implementation does nothing.
    * 
    * A subclass has to override and implement it.
    * 
    * @param a
    * @param row
    */
   public void computeStatsGlobal() {

   }

   /**
    * Returns a read only copy of the model data.
    * @return
    */
   public List<T> getAllDataCopy() {
      return Collections.unmodifiableList(data);
   }

   /**
    * Dangerous!! Only use if you know if reference won't leak outside your classe usage.
    * <br>
    * Modifications won't be fire back to the model either!!
    * @return
    */
   public List<T> getAllDataReference() {
      return data;
   }

   /**
    * 
    * @param index
    * @return
    */
   public T getRow(int index) {
      return data.get(index);
   }

   /**
    * Equivalent of getSize()
    */
   public int getRowCount() {
      return data.size();
   }

   /**
    * Returns the string for the column title tool tip.
    * @param col
    * @return
    */
   public abstract String getToolTips(int col);

   /**
    * 
    */
   public boolean isCellEditable(int row, int col) {
      return false;
   }

   public boolean removeRow(T value) {
      int index = data.indexOf(value);
      if (index != -1) {
         data.remove(index);
         fireTableRowsDeleted(index, index);
         return true;
      }
      return false;
   }

   /**
    * Does nothing. not editable
    */
   public void setValueAt(Object value, int row, int col) {
   }

   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ModelTableBAbstract");

      //displaying all the data should be optional.. in case of huge data set, it
      //is not desirable to see everything
      int count = 0;
      for (T row : data) {
         if (row instanceof IStringable) {
            dc.nlLvl1Line((IStringable) row);
         } else {
            dc.append(row.toString());
         }
         count++;
         if (count > 1000) {
            dc.append("too many records to list....");
            break;
         }
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ModelTableBAbstract");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}
