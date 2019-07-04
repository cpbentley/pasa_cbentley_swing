package pasa.cbentley.swing.widgets.slice9;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultButtonModel;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BButton;

/**
 * Button with a nine slice scaled background image
 * @author Charles Bentley
 *
 */
public class NineSliceScalingButton extends BButton {
   /**
    * 
    */
   private static final long             serialVersionUID = 1L;

   private final transient BufferedImage image;

   private int                           leftw;

   private int                           rightw;

   private int                           toph;

   private int                           bottomh;

   public NineSliceScalingButton(SwingCtx sc, ActionListener al, String key, BufferedImage image) {
      super(sc, al, key);
      this.image = image;
      setModel(new DefaultButtonModel());
      setContentAreaFilled(false);
   }

   public void setTBLR(int top, int bot, int left, int right) {
      leftw = left;
      rightw = right;
      toph = top;
      bottomh = bot;
   }

   protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      int iw = image.getWidth(this);
      int ih = image.getHeight(this);
      int bw = getWidth();
      int bh = getHeight();

      g2.drawImage(image.getSubimage(leftw, toph, iw - leftw - rightw, ih - toph - bottomh), leftw, toph, bw - leftw - rightw, bh - toph - bottomh, this);

      g2.drawImage(image.getSubimage(leftw, 0, iw - leftw - rightw, toph), leftw, 0, bw - leftw - rightw, toph, this);
      g2.drawImage(image.getSubimage(leftw, ih - bottomh, iw - leftw - rightw, bottomh), leftw, bh - bottomh, bw - leftw - rightw, bottomh, this);
      g2.drawImage(image.getSubimage(0, toph, leftw, ih - toph - bottomh), 0, toph, leftw, bh - toph - bottomh, this);
      g2.drawImage(image.getSubimage(iw - rightw, toph, rightw, ih - toph - bottomh), bw - rightw, toph, rightw, bh - toph - bottomh, this);

      g2.drawImage(image.getSubimage(0, 0, leftw, toph), 0, 0, this);
      g2.drawImage(image.getSubimage(iw - rightw, 0, rightw, toph), bw - rightw, 0, this);
      g2.drawImage(image.getSubimage(0, ih - bottomh, leftw, bottomh), 0, bh - bottomh, this);
      g2.drawImage(image.getSubimage(iw - rightw, ih - bottomh, rightw, bottomh), bw - rightw, bh - bottomh, this);

      g2.dispose();
      super.paintComponent(g);
   }
}