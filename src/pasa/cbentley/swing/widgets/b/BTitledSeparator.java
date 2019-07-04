package pasa.cbentley.swing.widgets.b;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

public class BTitledSeparator extends JLabel {
   
   private class TitledSeparatorIcon implements Icon {
      
      
      private Paint painter1;

      private Paint painter2;

      private int   width = -1;

      
      public int getIconHeight() {
         return height;
      }

      
      public int getIconWidth() {
         return 200; // dummy width
      }

      
      public void paintIcon(Component c, Graphics g, int x, int y) {
         int w = c.getWidth();
         if (w != width || Objects.isNull(painter1) || Objects.isNull(painter2)) {
            width = w;
            Point2D start = new Point2D.Float();
            Point2D end = new Point2D.Float(width, 0);
            float[] dist = { 0f, 1f };
            Color ec = Optional.ofNullable(getBackground()).orElse(UIManager.getColor("Panel.background"));
            Color sc = Optional.ofNullable(target).orElse(ec);
            painter1 = new LinearGradientPaint(start, end, dist, new Color[] { sc.darker(), ec });
            painter2 = new LinearGradientPaint(start, end, dist, new Color[] { sc.brighter(), ec });
         }
         int h = getIconHeight() / 2;
         Graphics2D g2 = (Graphics2D) g.create();
         // XXX: g2.translate(x, y);
         g2.setPaint(painter1);
         g2.fillRect(x, y, width, getIconHeight());
         g2.setPaint(painter2);
         g2.fillRect(x, y + h, width, getIconHeight() - h);
         g2.dispose();
      }
   }

   protected final int    height;

   protected final Color  target;

   protected final String title;

   protected final int    titlePosition;

   protected BTitledSeparator(String title, Color target, int height, int titlePosition) {
      super();
      this.title = title;
      this.target = target;
      this.height = height;
      this.titlePosition = titlePosition;
      updateBorder();
   }

   protected BTitledSeparator(String title, int height, int titlePosition) {
      this(title, null, height, titlePosition);
   }

   
   public Dimension getMaximumSize() {
      return new Dimension(Short.MAX_VALUE, super.getPreferredSize().height);
   }

   private void updateBorder() {
      Icon icon = new TitledSeparatorIcon();
      MatteBorder matteBorder = BorderFactory.createMatteBorder(height, 0, 0, 0, icon);
      setBorder(BorderFactory.createTitledBorder(matteBorder, title, TitledBorder.DEFAULT_JUSTIFICATION, titlePosition));
   }

   public void updateUI() {
      super.updateUI();
      updateBorder();
   }
}