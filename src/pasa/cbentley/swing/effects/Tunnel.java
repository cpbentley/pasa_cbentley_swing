package pasa.cbentley.swing.effects;

import java.awt.Image;
import java.awt.image.PixelGrabber;

import pasa.cbentley.swing.ctx.SwingCtx;

public class Tunnel implements IModeler {

   private static int       angles[][];

   static double            animation     = 0;

   private static int       distances[][];

   static double            movement      = 0.1;

   static int               shiftX        = 0;

   static int               shiftY        = 0;

   private static int       texture[];

   private static final int TEXTUREHEIGHT = 256;

   private static final int TEXTUREWIDTH  = 256;

   private int              scrHeight;

   private int              scrWidth;

   private RasterOffscreen  raster;

   private SwingCtx         sc;

   public Tunnel(SwingCtx sc, RasterOffscreen raster, Image textureImage) {
      this.sc = sc;
      this.raster = raster;
      texture = new int[TEXTUREHEIGHT * TEXTUREHEIGHT];
      this.scrWidth = raster.getWidth();
      this.scrHeight = raster.getHeight();

      PixelGrabber pixelgrabber = new PixelGrabber(textureImage, 0, 0, TEXTUREWIDTH, TEXTUREHEIGHT, texture, 0, TEXTUREWIDTH);
      try {
         pixelgrabber.grabPixels();
      } catch (InterruptedException interruptedexception) {
         interruptedexception.printStackTrace(System.out);

      }

      distances = new int[scrWidth][scrHeight];
      angles = new int[scrWidth][scrHeight];

      // Pregenerating the mapping pixel/coordinate.
      for (int x = 0; x < scrWidth; x++)
         for (int y = 0; y < scrHeight; y++) {
            distances[x][y] = (int) ((30. * TEXTUREHEIGHT / Math.sqrt((x - scrWidth / 2.0) * (x - scrWidth / 2.0) + (y - scrHeight / 2.0) * (y - scrHeight / 2.0))) % TEXTUREHEIGHT);

            angles[x][y] = (int) (0.5 * TEXTUREWIDTH * Math.atan2(y - scrHeight / 2.0, x - scrWidth / 2.0) / Math.PI);
         }
   }

   public void drawOffScreen() {
      // Java timer sucks ! Don't try to make the animation platform independant (Renderer.tick) :( !
      animation += 3;
      movement += 1;//movement + (int)Renderer.tick *  / 10000.0;

      //calculate the shift values out of the animation value
      shiftX = (int) (TEXTUREWIDTH + animation);
      shiftY = (int) (TEXTUREHEIGHT + movement);// / rotation);        

      for (int y = 0, cursor = 0; y < scrHeight; y++)
         for (int x = 0; x < scrWidth; x++, cursor++) {
            raster.offScreenRaster[cursor] = // texture[23543];
                  texture[(distances[x][y] + shiftX) % TEXTUREWIDTH + (((angles[x][y] + shiftY) % TEXTUREHEIGHT) * TEXTUREWIDTH)];
         }
   }

   public void addMoveAt(int x, int y) {

   }

}
