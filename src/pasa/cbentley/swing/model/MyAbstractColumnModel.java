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
public class MyAbstractColumnModel implements IStringable {

   protected Class[]  classes;

   protected String[] colKey;

   private SwingCtx   sc;

   private String     keyPrefixName = "";

   private String     keyPrefixTip  = "";

   public MyAbstractColumnModel(SwingCtx sc) {
      this.sc = sc;

   }

   public void initFor(int numColumns) {
      this.classes = new Class[numColumns];
      this.colKey = new String[numColumns];
   }
   public void setDate(int colIndex, String key) {
      classes[colIndex] = Date.class;
      colKey[colIndex] = key;
   }
   public void setString(int colIndex, String key) {
      classes[colIndex] = String.class;
      colKey[colIndex] = key;
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

   public void setInteger(int colIndex, String key) {
      classes[colIndex] = Integer.class;
      colKey[colIndex] = key;
   }

   public void setBoolean(int colIndex, String key) {
      classes[colIndex] = Boolean.class;
      colKey[colIndex] = key;
   }
   public void setClass(int colIndex, String key, Class c) {
      classes[colIndex] = c;
      colKey[colIndex] = key;
   }
   public void setDouble(int colIndex, String key) {
      classes[colIndex] = Double.class;
      colKey[colIndex] = key;
   }

   /**
    * Returns the tooltip text for the column.
    * @param col
    * @return
    */
   public String getColumnToolTip(int col) {
      return sc.getResString(keyPrefixTip + colKey[col]);
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

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MyAbstractColumnModel");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   public String getKeyPrefixName() {
      return keyPrefixName;
   }

   public void setKeyPrefixName(String keyPrefixName) {
      this.keyPrefixName = keyPrefixName;
   }

   public String getKeyPrefixTip() {
      return keyPrefixTip;
   }

   public void setKeyPrefixTip(String keyPrefixTip) {
      this.keyPrefixTip = keyPrefixTip;
   }

   //#enddebug

}
