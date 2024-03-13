package pasa.cbentley.swing.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 *
 * @author leonardo
 */
public class FireEffectView extends JFrame implements IStringable {

   private int[]            palette = new int[256];

   private BufferedImage    offscreen;

   private int[]            data;

   private int[]            pixels;

   private int              w;

   private int              h;

   Random                   r       = new Random();

   protected final SwingCtx sc;

   public FireEffectView(SwingCtx sc) {
      this.sc = sc;
      setSize(800, 600);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      w = 400;
      h = 300;
      offscreen = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
      data = ((DataBufferInt) offscreen.getRaster().getDataBuffer()).getData();
      pixels = new int[w * h];

      for (int x = 0; x < 256; x++) {
         float saturation = 1f - x / 512f;
         palette[x] = Color.HSBtoRGB(x / 76f, saturation, Math.min(1f, x / 48f));
      }

      int[] rgb = new int[3];

      //generate the palette
      for (int x = 0; x < 256; x++) {
         //HSLtoRGB is used to generate colors:
         //Hue goes from 0 to 85: red to yellow
         //Saturation is always the maximum: 255
         //Lightness is 0..255 for x=0..128, and 255 for x=128..255
         float hue360 = (float) x / 3f;
         float saturation255 = 255;
         float light255 = Math.min(255, x * 2);
         ColorUtils.getRGBFromH360S255L255Unsafe(hue360, saturation255, light255, rgb);
         //set the palette to the calculated RGB value
         palette[x] = ColorUtils.getRGBInt(rgb);
      }

      new Thread(new Runnable() {
         public void run() {
            while (true) {
               repaint();
               try {
                  Thread.sleep(5);
               } catch (InterruptedException ex) {
                  Logger.getLogger(FireEffectView.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
         }
      }).start();
   }

   @Override
   public void paint(Graphics g) {
      //super.paint(g);

      int offset = w * (h - 1);
      for (int x = 0; x < w; x++) {
         pixels[x + offset] = r.nextInt(256); //random pallete indexes
      }
      for (int x = 0; x < w; x++) {
         pixels[x + w * (h - 30)] = Math.random() > 0.55 ? 0 : 255;
      }

      //do the fire calculations for every pixel, from top to bottom
      //      offset = 0;
      //      for (int y = 5; y < h - 1; y++) {
      //         for (int x = 5; x < w - 2; x++) {
      //            int offset1 = (y + w) * (x - 1 + w);
      //            //int p1 = pixels[offset1];
      //            
      //            int offset2 = ((y + w) % h) * (x % w);
      //            int p2 = pixels[offset2];
      //            int p1 = p2;
      //            
      //            int offset3 = ((y + w) % h) * ((x + 1) % w);
      //            int p3 = pixels[offset3];
      //            
      //            int offset4 = ((y + (2 * w)) % h) * (x % w);
      //            int p4 = pixels[offset4];
      //            
      //            int total = p1 + p2 + p3 + p4;
      //            int value = total * 32 / 129;
      //            pixels[x + (w * y)] = value; //current value is a combination of what is above
      //         }
      //      }

      for (int y = 5; y < h - 2; y++) {
         for (int x = 5; x < w - 2; x++) {
            int dataNext = pixels[x + w * y];
            int dataBottom = pixels[x + w * (y + 1)];
            int dataBottomRight = pixels[(x - 1) + w * (y + 1)];
            int dataBottomLeft = pixels[(x + 1) + w * (y + 1)];
            int dateBottom2 = pixels[x + w * (y + 2)];
            pixels[x + w * y] = (int) ((int) ((dataNext + dataBottom + dataBottomRight + dataBottomLeft + dateBottom2) / 5.02) * 1.01);
         }
      }

      //#debug
      //toDLog().pAlways("msg", this, FireEffectView.class, "paint", LVL_05_FINE, false);

      //      for (int y = 5; y < 320 - 2; y++) {
      //         for (int x = 5; x < 400 - 2; x++) {
      //            pixels[x + 400 * y] = (int) ((int) ((pixels[x + 400 * y] + pixels[x + 400 * (y + 1)] + pixels[(x - 1) + 400 * (y + 1)] + pixels[(x + 1) + 400 * (y + 1)] + pixels[x + 400 * (y + 2)]) / 5.02) * 1.01);
      //         }
      //      }

      for (int i = 0; i < data.length; i++) {
         int paletteIndex = pixels[i];
         //int paletteIndex = r.nextInt(palette.length);
         data[i] = palette[paletteIndex];
      }
      g.drawImage(offscreen, 0, 0, 800, 600, null);

   }

   public static void main(String[] args) {
      UCtx uc = new UCtx();
      C5Ctx c5 = new C5Ctx(uc);
      final SwingCtx sc = new SwingCtx(c5);
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new FireEffectView(sc).setVisible(true);
         }
      });
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FireEffectView");
      toStringPrivate(dc);

      dc.nlLvl("palette", palette, 10);

      dc.nlLvl("data", pixels, 64);

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("w", w);
      dc.appendVarWithSpace("h", h);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FireEffectView");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   //#enddebug

}