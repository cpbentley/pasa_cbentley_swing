package pasa.cbentley.swing.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pasa.cbentley.core.src4.interfaces.ITechTransform;
import pasa.cbentley.swing.ctx.SwingCtx;

public class DrawUtils implements ITechTransform {

   private SwingCtx sc;

   public DrawUtils(SwingCtx sc) {
      this.sc = sc;
   }

   public void drawRegion(Graphics2D graphics, BufferedImage src, int x_src, int y_src, int width, int height, int transform, int x_dst, int y_dst) {
      // may throw NullPointerException, this is ok
      if (x_src + width > src.getWidth() || y_src + height > src.getHeight() || width < 0 || height < 0 || x_src < 0 || y_src < 0) {
         throw new IllegalArgumentException("Area out of Image");
      }

      java.awt.Image img = src;

      java.awt.geom.AffineTransform t = new java.awt.geom.AffineTransform();

      int dW = width, dH = height;
      switch (transform) {
         case TRANSFORM_0_NONE: {
            break;
         }
         case TRANSFORM_5_ROT_90: {
            t.translate((double) height, 0);
            t.rotate(Math.PI / 2);
            dW = height;
            dH = width;
            break;
         }
         case TRANSFORM_3_ROT_180: {
            t.translate(width, height);
            t.rotate(Math.PI);
            break;
         }
         case TRANSFORM_6_ROT_270: {
            t.translate(0, width);
            t.rotate(Math.PI * 3 / 2);
            dW = height;
            dH = width;
            break;
         }
         case TRANSFORM_2_FLIP_V_MIRROR: {
            t.translate(width, 0);
            t.scale(-1, 1);
            break;
         }
         case TRANSFORM_7_MIRROR_ROT90: {
            t.translate((double) height, 0);
            t.rotate(Math.PI / 2);
            t.translate((double) width, 0);
            t.scale(-1, 1);
            dW = height;
            dH = width;
            break;
         }
         case TRANSFORM_1_FLIP_H_MIRROR_ROT180: {
            t.translate(width, 0);
            t.scale(-1, 1);
            t.translate(width, height);
            t.rotate(Math.PI);
            break;
         }
         case TRANSFORM_4_MIRROR_ROT270: {
            t.rotate(Math.PI * 3 / 2);
            t.scale(-1, 1);
            dW = height;
            dH = width;
            break;
         }
         default:
            throw new IllegalArgumentException("Bad transform");
      }

      java.awt.geom.AffineTransform savedT = graphics.getTransform();

      graphics.translate(x_dst, y_dst);
      graphics.transform(t);

      graphics.drawImage(img, 0, 0, width, height, x_src, y_src, x_src + width, y_src + height, null);

      // return to saved
      graphics.setTransform(savedT);
   }
}
