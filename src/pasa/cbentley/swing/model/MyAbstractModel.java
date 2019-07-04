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
 * @author Charles Bentley
 *
 * @param <T>
 */
public abstract class MyAbstractModel<T> extends BentleyAbstractModel<T> implements IStringable {

   protected Class[]             classes;

   protected String[]            colNames;

   protected String[]            colToolTips;

   private MyAbstractColumnModel columnModel;

   public MyAbstractModel(SwingCtx sc) {
      super(sc);
   }

   public MyAbstractColumnModel getColumnModel() {
      return columnModel;
   }

   public boolean isCellEditable(int row, int col) {
      return false;
   }

   public void setValueAt(Object value, int row, int col) {
   }

   /**
    * Returns the tooltip text for the column.
    * @param col
    * @return
    */
   public String getToolTips(int col) {
      if (colToolTips == null) {
         //default
         return "No Tooltip";
      }
      return colToolTips[col];
   }

   public Class getColumnClass(int col) {
      return classes[col];
   }

   public int getColumnCount() {
      return colNames.length;
   }


   public String getColumnName(int col) {
      return colNames[col];
   }


   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MyAbstractModel");

   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MyAbstractModel");
   }

   //#enddebug

}
