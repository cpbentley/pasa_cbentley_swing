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
public abstract class ModelTableBAbstractWithColModel<T> extends ModelTableBAbstract<T> implements IStringable {

   private ModelColumnBAbstract columnModel;
   
   public ModelTableBAbstractWithColModel(SwingCtx sc, int numColumns) {
      super(sc);
      columnModel = new ModelColumnBAbstract(sc);
      columnModel.initFor(numColumns);
   }
   
   public ModelColumnBAbstract getColumnModel() {
      return columnModel;
   }


   /**
    * Reset the col
    */
   public void guiUpdate() {
      
   }
   
   /**
    * Returns the tooltip text for the column.
    * @param col
    * @return
    */
   public String getToolTips(int col) {
      return columnModel.getColumnToolTip(col);
   }

   public Class getColumnClass(int col) {
      return columnModel.getColumnClass(col);
   }

   public int getColumnCount() {
      return columnModel.getColumnCount();
   }


   public String getColumnName(int col) {
      return columnModel.getColumnName(col);
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "BentleyAbstractModelWithColModel");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      dc.nlLvl(columnModel);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BentleyAbstractModelWithColModel");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
