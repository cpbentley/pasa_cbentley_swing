package pasa.cbentley.swing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.swing.ctx.SwingCtx;

public class SwingPrefs implements IPrefs {

   private Preferences prefs;

   private SwingCtx    sc;

   public SwingPrefs(SwingCtx sc, Preferences prefs) {
      this.prefs = prefs;
      this.sc = sc;
   }

   public void clear() {
      try {
         prefs.clear();
      } catch (BackingStoreException e) {
         e.printStackTrace();

      }
   }

   public void export(OutputStream os) {
      try {
         prefs.exportSubtree(os);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (BackingStoreException e) {
         e.printStackTrace();
      }
   }

   public String get(String key, String def) {
      return prefs.get(key, def);
   }

   public boolean getBoolean(String key, boolean def) {
      return prefs.getBoolean(key, def);
   }

   public double getDouble(String key, double d) {
      return prefs.getDouble(key, d);
   }

   public int getInt(String key, int def) {
      return prefs.getInt(key, def);
   }

   public IntToStrings getKeys() {
      try {
         String[] keys = prefs.keys();
         return new IntToStrings(sc.getUCtx(), keys);
      } catch (BackingStoreException e) {
         e.printStackTrace();
      }
      return null;
   }

   public String[] getStrings(String key, char separator) {
      String root1 = prefs.get(key, "");
      if (root1.equals("")) {
         return new String[0];
      } else {
         String[] data = root1.split(";");
         return data;
      }
   }

   public void put(String key, String value) {
      prefs.put(key, value);
   }

   public void put(String key, String[] strs, char separator) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < strs.length; i++) {
         if (i != 0) {
            sb.append(separator);
         }
         sb.append(strs[i]);

      }
      prefs.put(key, sb.toString());
   }

   public void putBoolean(String key, boolean value) {
      prefs.putBoolean(key, value);
   }

   public void putDouble(String key, double value) {
      prefs.putDouble(key, value);
   }

   public void putInt(String key, int value) {
      prefs.putInt(key, value);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingPrefs");
      try {
         String[] keys = prefs.keys();
         for (String key : keys) {
            dc.nl();
            dc.appendVar(key, prefs.get(key, ""));
         }
      } catch (BackingStoreException e) {
         e.printStackTrace();
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingPrefs");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}
