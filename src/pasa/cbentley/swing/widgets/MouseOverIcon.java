package pasa.cbentley.swing.widgets;

import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

import pasa.cbentley.swing.ctx.SwingCtx;

public class MouseOverIcon implements Icon {

   private SwingCtx sc;

   private Icon     icon;

   private Composite compo;

   public MouseOverIcon(SwingCtx sc, Icon icon, Composite compo) {
      this.sc = sc;
      this.icon = icon;
      this.compo = compo;

   }

   public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2d = (Graphics2D) g;
      Composite cold = g2d.getComposite();
      g2d.setComposite(compo);
      icon.paintIcon(c, g2d, x, y);
      g2d.setComposite(cold);
   }

   public int getIconWidth() {
      return icon.getIconWidth();
   }

   public int getIconHeight() {
      return icon.getIconHeight();
   }

}
