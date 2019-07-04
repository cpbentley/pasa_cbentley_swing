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
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * Custom Swing {@link AbstractTableModel}.
 * 
 * No assumption on the column model.
 * 
 * @author Charles Bentley
 *
 * @param <T>
 */
public abstract class BentleyAbstractModel<T> extends AbstractTableModel implements IStringable {

   private ArrayList<T> data = new ArrayList<T>();

   protected SwingCtx   sc;

   public BentleyAbstractModel(SwingCtx sc) {
      this.sc = sc;
   }

   public void addRow(T value) {
      int rowCount = getRowCount();
      data.add(value);
      fireTableRowsInserted(rowCount, rowCount);
      computeStats(value);
   }

   public void addRows(List<T> rows) {
      int rowCount = getRowCount();
      data.addAll(rows);
      fireTableRowsInserted(rowCount, getRowCount() - 1);
      for (int i = 0; i < rows.size(); i++) {
         computeStats(rows.get(i));
      }
   }

   public void addRows(T... value) {
      addRows(Arrays.asList(value));
   }

   
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
    * @param a
    */
   protected abstract void computeStats(T a);

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

   public T getRow(int index) {
      return data.get(index);
   }

   public int getRowCount() {
      return data.size();
   }

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

   public void setValueAt(Object value, int row, int col) {
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public abstract String getToolTips(int col);

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BentleyAbstractModel");

      //displaying all the data should be optional.. in case of huge data set, it
      //is not desirable to see everything
      int count = 0;
      for (T row : data) {
         if (row instanceof IStringable) {
            dc.nlLvlOneLine((IStringable) row);
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
      dc.root1Line(this, "BentleyAbstractModel");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }


   //#enddebug

}
