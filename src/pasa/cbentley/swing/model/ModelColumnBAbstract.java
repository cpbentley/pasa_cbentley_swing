package pasa.cbentley.swing.model;

import java.util.Date;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Holds the 
 * <li>name
 * <li>class
 * <li>tooltip
 * @author Charles Bentley
 *
 */
public class ModelColumnBAbstract implements IStringable {

   protected Class[]  classes;

   protected String[] colKey;

   private String     keyPrefixName = "";

   private String     keyPrefixTip  = "";

   private SwingCtx   sc;

   public ModelColumnBAbstract(SwingCtx sc) {
      this.sc = sc;

   }

   public Class getColumnClass(int col) {
      return classes[col];
   }

   public int getColumnCount() {
      return colKey.length;
   }

   public String getColumnName(int col) {
      return sc.getResString(keyPrefixName + colKey[col]);
   }

   /**
    * Returns the tooltip text for the column.
    * @param col
    * @return
    */
   public String getColumnToolTip(int col) {
      return sc.getResString(keyPrefixTip + colKey[col]);
   }

   public String getKeyPrefixName() {
      return keyPrefixName;
   }

   public String getKeyPrefixTip() {
      return keyPrefixTip;
   }

   public void initFor(int numColumns) {
      this.classes = new Class[numColumns];
      this.colKey = new String[numColumns];
   }

   public void sanityCheck() {
      for (int i = 0; i < classes.length; i++) {
         if (classes[i] == null) {
            throw new NullPointerException("Column Index " + i + " not initialized with a class");
         }
         if (colKey[i] == null) {
            throw new NullPointerException("Column Index " + i + " not initialized with a key");
         }
      }
   }

   public void setBoolean(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = Boolean.class;
      colKey[colIndex] = key;
   }

   public void setClass(int colIndex, String key, Class c) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = c;
      colKey[colIndex] = key;
   }

   public void setDate(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = Date.class;
      colKey[colIndex] = key;
   }

   public void setDouble(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = Double.class;
      colKey[colIndex] = key;
   }

   public void setInteger(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = Integer.class;
      colKey[colIndex] = key;
   }

   public void setKeyPrefixName(String keyPrefixName) {
      this.keyPrefixName = keyPrefixName;
   }

   public void setKeyPrefixTip(String keyPrefixTip) {
      this.keyPrefixTip = keyPrefixTip;
   }

   public void setLong(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = Long.class;
      colKey[colIndex] = key;
   }

   public void setString(int colIndex, String key) {
      if(colIndex < 0) {
         return;
      }
      classes[colIndex] = String.class;
      colKey[colIndex] = key;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MyAbstractColumnModel");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MyAbstractColumnModel");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug
}
