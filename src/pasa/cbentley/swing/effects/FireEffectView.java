package pasa.cbentley.swing.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import pasa.cbentley.core.src4.utils.ColorUtils;

/**
 *
 * @author leonardo
 */
public class FireEffectView extends JFrame {

   private int[]         palette = new int[256];

   private BufferedImage offscreen;

   private int[]         data;

   private int[]         pixels;

   private int           w;

   private int           h;

   public FireEffectView() {
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
         pixels[x + offset] = (int) ((Math.random() + 32768) % 256); //random pallete indexes
      }

      //do the fire calculations for every pixel, from top to bottom
      offset = 0;
      for (int y = 0; y < h - 1; y++) {
         for (int x = 0; x < w; x++) {
            int offset1 = (y + 1 % h) * ((x - 1 + w) % w);
            int p1 = pixels[offset1];
            int offset2 = (y + 1 % h) * (x % w);
            int p2 = pixels[offset2];
            int offset3 = (y + 1 % h) * ((x + 1) % w);
            int p3 = pixels[offset3];
            int offset4 = (y + 2 % h) * (x % w);
            int p4 = pixels[offset4];
            int total = p1 + p2 + p3 + p4;
            int value = total * 32 / 129;
            pixels[x * y] = value; //current value is a combination of what is above
         }
      }

//      for (int y = 5; y < 320 - 2; y++) {
//         for (int x = 5; x < 400 - 2; x++) {
//            pixels[x + 400 * y] = (int) ((int) ((pixels[x + 400 * y] + pixels[x + 400 * (y + 1)] + pixels[(x - 1) + 400 * (y + 1)] + pixels[(x + 1) + 400 * (y + 1)] + pixels[x + 400 * (y + 2)]) / 5.02) * 1.01);
//         }
//      }

      for (int i = 0; i < data.length; i++) {
         data[i] = palette[pixels[i]];
      }
      g.drawImage(offscreen, 0, 0, 800, 600, null);

   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new FireEffectView().setVisible(true);
         }
      });
   }

}