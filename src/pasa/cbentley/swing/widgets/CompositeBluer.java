package pasa.cbentley.swing.widgets;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class CompositeBluer implements Composite, CompositeContext {

   public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
      return this;
   }

   public void dispose() {
   }

   public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
      for (int y = 0; y < dstOut.getHeight(); y++) {
         for (int x = 0; x < dstOut.getWidth(); x++) {
            //src pixel
            int[] srcPixels = new int[4];
            src.getPixel(x, y, srcPixels);
            
            srcPixels[0] = srcPixels[0] / 2; //red
            srcPixels[1] = srcPixels[1] / 2; //green
            srcPixels[2] = srcPixels[2] / 2 + 68; //blue
            
            dstOut.setPixel(x, y, srcPixels);
         }
      }
   }

}
