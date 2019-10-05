package pasa.cbentley.swing.utils;

import java.awt.Color;
import java.util.HashMap;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Create and cache {@link Color}
 * 
 * Single thread. Must be accessed from the UI thread
 * @author Charles Bentley
 *
 */
public class SwingColorStore implements IStringable {

   protected final SwingCtx sc;

   private HashMap<String, Color> colors;
   
   /**
    * since its single thread. we can use one for the class
    */
   private StringBBuilder sb;
   
   private int[] rgb = new int[3];
   
   public SwingColorStore(SwingCtx sc) {
      this.sc = sc;
      sb = new StringBBuilder(sc.getUCtx(),20);
      colors = new HashMap<String, Color>();
   }
   
   public void clear() {
      colors.clear();
   }
   
   public Color getColorGrey(int grey) {
      return getColorRGB(grey, grey, grey);
   }
   
   public Color getColorRGB(int rgb) {
      int r = ((rgb >> 16) & 0xFF);
      int g = ((rgb >> 8) & 0xFF);
      int b = (rgb & 0xFF);
      return getColorRGB(r,g,b);
   }
   
   public Color getColorRGB(int r, int g, int b) {
      sb.reset();
      sb.append('@');
      sb.append(r);
      sb.append(g);
      sb.append(b);
      String key = sb.toString();
      Color c = colors.get(key);
      if(c == null) {
         c = new Color(r, g, b);
         colors.put(key, c);
      }
      return c;
   }
   
   public Color getColorHSL(int h, int s, int l) {
      sb.reset();
      sb.append('_');
      sb.append(h);
      sb.append(s);
      sb.append(l);
      String keyHSL = sb.toString();
      Color c = colors.get(keyHSL);
      if(c == null) {
         ColorUtils.getRGBFromH360S100L100Clip(h, s, l, rgb);
         sb.reset();
         sb.append('@');
         sb.append(rgb[0]);
         sb.append(rgb[1]);
         sb.append(rgb[2]);
         String keyRGB = sb.toString();
         Color cRGB = colors.get(keyRGB);
         if(cRGB == null) {
            cRGB = new Color(rgb[0],rgb[1],rgb[2]);
            colors.put(keyRGB, c);
         }
         c = cRGB;
         colors.put(keyHSL, c);
      }
      return c;
   }
   /**
    * 
    * @param h 0-360 moduloed
    * @param s 0-100 clipped
    * @param l 0-100 clipped
    * @return
    */
  public Color getColorHSLClip(int h, int s, int l) {
     if(s < 0) {
        s = 0;
     }
     if(s > 100) {
        s = 100;
     }
     if(l < 0) {
        l = 0;
     }
     if(l > 100) {
        l = 100;
     }
     if(h < 360 || h > 360) {
        h = h % 360;
     }
     return getColorHSL(h, s, l);
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingColorStore");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingColorStore");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug
   

   
}
