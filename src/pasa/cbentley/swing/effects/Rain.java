package pasa.cbentley.swing.effects;

import java.awt.Image;
import java.awt.image.PixelGrabber;

import pasa.cbentley.swing.ctx.SwingCtx;

public class Rain implements IModeler {

   public static final int PERTURBATION = 200;

   int[][]                 currentState;

   int[][]                 oldState;

   RasterOffscreen         raster     = null;

   int                     Shading      = 0;

   int[][]                 swapPointer;

   private int             texture[];

   int                     Xoffset      = 0;

   int                     Yoffset      = 0;

   private SwingCtx sc;

   public Rain(SwingCtx sc, RasterOffscreen raster, Image textureImage) {
      this.sc = sc;
      this.raster = raster;
      int renderedWidth = raster.getWidth();
      int renderedHeight = raster.getHeight();
      oldState = new int[renderedWidth][renderedHeight];
      currentState = new int[renderedWidth][renderedHeight];
      texture = new int[renderedWidth * renderedHeight];

      PixelGrabber pixelgrabber = new PixelGrabber(textureImage, 0, 0, renderedWidth, renderedHeight, texture, 0, renderedWidth);
      try {
         pixelgrabber.grabPixels();
      } catch (InterruptedException interruptedexception) {
         interruptedexception.printStackTrace(System.out);

      }
      RainGenerator gen = new RainGenerator(this, currentState, 640, 480);
      gen.start();
   }

   public void addRain(int[][] pointer, int x, int y) {
      if (x > raster.getWidth() - 1 || x < 1 || y > raster.getHeight() - 1 || y < 1)
         return;

      //		for (int i = -17 ; i < 17 ; i++)
      //			for (int j = -17 ; j < 17 ; j++)
      //				pointer[x+i][y+j] += PERTURBATION;	
      try {
         pointer[x][y] += PERTURBATION;

         pointer[x][y - 1] += PERTURBATION;
         pointer[x][y + 1] += PERTURBATION;

         pointer[x + 1][y] += PERTURBATION;
         pointer[x + 1][y + 1] += PERTURBATION;
         pointer[x + 1][y - 1] += PERTURBATION;

         pointer[x - 1][y + 1] += PERTURBATION;
         pointer[x - 1][y] += PERTURBATION;
         pointer[x - 1][y - 1] += PERTURBATION;

         pointer[x - 2][y + 2] += PERTURBATION;
         pointer[x - 2][y + 1] += PERTURBATION;
         pointer[x - 2][y] += PERTURBATION;
         pointer[x - 2][y - 1] += PERTURBATION;
         pointer[x - 2][y - 2] += PERTURBATION;

         pointer[x + 2][y + 2] += PERTURBATION;
         pointer[x + 2][y + 1] += PERTURBATION;
         pointer[x + 2][y] += PERTURBATION;
         pointer[x + 2][y - 1] += PERTURBATION;
         pointer[x + 2][y - 2] += PERTURBATION;
         /*
         pointer[x-3][y+3] += PERTURBATION;
         pointer[x-3][y+2] += PERTURBATION;
         pointer[x-3][y+1] += PERTURBATION;
         pointer[x-3][y] += PERTURBATION;
         pointer[x-3][y-1] += PERTURBATION;
         pointer[x-3][y-2] += PERTURBATION;
         pointer[x-3][y-3] += PERTURBATION;
         pointer[x][y+3] += PERTURBATION;
         pointer[x][y+2] += PERTURBATION;
         pointer[x][y-2] += PERTURBATION;
         pointer[x][y-3] += PERTURBATION;
         */

         pointer[x + 3][y + 3] += PERTURBATION;
         pointer[x + 3][y + 2] += PERTURBATION;
         pointer[x + 3][y + 1] += PERTURBATION;
         pointer[x + 3][y] += PERTURBATION;
         pointer[x + 3][y - 1] += PERTURBATION;
         pointer[x + 3][y - 2] += PERTURBATION;
         pointer[x + 3][y - 3] += PERTURBATION;

      } catch (Exception e) {

      }

   }

   public void drawOffScreen() {
      int height = raster.getHeight();
      int width = raster.getWidth();
      for (int y = 1; y < height - 1; y++)
         for (int x = 1; x < width - 1; x++) {
            // This piece of code calculate the height of every point in the raster
            currentState[x][y] = (((oldState[x - 1][y] + oldState[x + 1][y] + oldState[x][y + 1] + oldState[x][y - 1]) >> 1)) - currentState[x][y];
            currentState[x][y] -= (currentState[x][y] >> 7);

            //where data=0 then still, where data>0 then wave
            int data = (short) (1024 - currentState[x][y]);

            //offsets
            int a = ((x - width) * data / 1024) + width;
            int b = ((y - height) * data / 1024) + height;

            //bounds check
            if (a >= width)
               a = width - 1;
            if (a < 0)
               a = 0;
            if (b >= height)
               b = height - 1;
            if (b < 0)
               b = 0;

            int textOffset = x + y * width;
            raster.offScreenRaster[textOffset] = texture[a + (b * width)];
         }
      swapPointer = currentState;
      currentState = oldState;
      oldState = swapPointer;

   }

   public void addMoveAt(int x, int y) {
      addRain(currentState, x, y);
   }

}
