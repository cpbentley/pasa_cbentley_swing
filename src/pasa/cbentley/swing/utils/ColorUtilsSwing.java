package pasa.cbentley.swing.utils;

import java.awt.Color;

import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Uses {@link ColorUtils}
 * 
 * but with instance variables on Color cache can be enabled
 * @author Charles Bentley
 *
 */
public class ColorUtilsSwing {

   private SwingColorStore  store;

   protected final SwingCtx sc;

   public ColorUtilsSwing(SwingCtx sc) {
      this.sc = sc;
      store = sc.getSwingColorStore();

   }

   public Color getDarker(Color rgb, int percent) {
      return store.getColorRGB(ColorUtils.getDarker(rgb.getRGB(), percent));
   }

   public Color getBrighter(Color rgb, int percent) {
      return store.getColorRGB(ColorUtils.getBrighter(rgb.getRGB(), percent));
   }

   public Color getMedian(Color rgb1, Color rgb2) {
      return store.getColorRGB(ColorUtils.getMedian(rgb1.getRGB(), rgb2.getRGB()));
   }
}
