package pasa.cbentley.swing.widgets.slice9;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * 
 * @author Charles Bentley
 *
 */
public class NineSliceScalingIcon implements Icon {
   
   
   private final BufferedImage image;

   private final int           leftw;

   private final int           rightw;

   private final int           toph;

   private final int           bottomh;

   private int                 width;

   private int                 height;

   protected NineSliceScalingIcon(BufferedImage image, int leftw, int rightw, int toph, int bottomh) {
      this.image = image;
      this.leftw = leftw;
      this.rightw = rightw;
      this.toph = toph;
      this.bottomh = bottomh;
   }

   public int getIconWidth() {
      return width; // Math.max(image.getWidth(null), width);
   }

   public int getIconHeight() {
      return Math.max(image.getHeight(null), height);
   }

   public void paintIcon(Component cmp, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      Insets i;
      if (cmp instanceof JComponent) {
         i = ((JComponent) cmp).getBorder().getBorderInsets(cmp);
      } else {
         i = new Insets(0, 0, 0, 0);
      }

      // g2.translate(x, y); // 1.8.0: work fine?

      int iw = image.getWidth(cmp);
      int ih = image.getHeight(cmp);
      width = cmp.getWidth() - i.left - i.right;
      height = cmp.getHeight() - i.top - i.bottom;

      g2.drawImage(image.getSubimage(leftw, toph, iw - leftw - rightw, ih - toph - bottomh), leftw, toph, width - leftw - rightw, height - toph - bottomh, cmp);

      if (leftw > 0 && rightw > 0 && toph > 0 && bottomh > 0) {
         g2.drawImage(image.getSubimage(leftw, 0, iw - leftw - rightw, toph), leftw, 0, width - leftw - rightw, toph, cmp);
         g2.drawImage(image.getSubimage(leftw, ih - bottomh, iw - leftw - rightw, bottomh), leftw, height - bottomh, width - leftw - rightw, bottomh, cmp);
         g2.drawImage(image.getSubimage(0, toph, leftw, ih - toph - bottomh), 0, toph, leftw, height - toph - bottomh, cmp);
         g2.drawImage(image.getSubimage(iw - rightw, toph, rightw, ih - toph - bottomh), width - rightw, toph, rightw, height - toph - bottomh, cmp);

         g2.drawImage(image.getSubimage(0, 0, leftw, toph), 0, 0, cmp);
         g2.drawImage(image.getSubimage(iw - rightw, 0, rightw, toph), width - rightw, 0, cmp);
         g2.drawImage(image.getSubimage(0, ih - bottomh, leftw, bottomh), 0, height - bottomh, cmp);
         g2.drawImage(image.getSubimage(iw - rightw, ih - bottomh, rightw, bottomh), width - rightw, height - bottomh, cmp);
      }

      g2.dispose();
   }
}