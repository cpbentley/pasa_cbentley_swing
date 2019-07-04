package pasa.cbentley.swing.effects;

import pasa.cbentley.swing.ctx.SwingCtx;

public class RasterOffscreen {
   public int[] offScreenRaster;
   private SwingCtx sc;
   private int width;
   private int height;

   public RasterOffscreen(SwingCtx sc, int width, int height) {
      this.sc = sc;
      this.width = width;
      this.height = height;
      offScreenRaster = new int[width * height];
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }
}
