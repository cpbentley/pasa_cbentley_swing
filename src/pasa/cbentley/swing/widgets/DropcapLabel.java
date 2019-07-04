package pasa.cbentley.swing.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BLabel;

/**
 * Dropcap computes its size.
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class DropcapLabel extends BLabel {

   /**
    * 
    */
   private static final long serialVersionUID = -7036315793658343257L;

   private int               fontHeight;

   private int               numLines;

   public DropcapLabel(SwingCtx sc, String key) {
      super(sc, key);
      setOpaque(false);
   }

   public Dimension getPreferredSize() {
      Dimension p = super.getPreferredSize();
      Insets i = getInsets();
      int min = fontHeight * 3;
      p.height = Math.max(min, numLines * fontHeight);
      p.height += (i.top + i.bottom);

      //#debug
      toDLog().pDraw("height=" + p.height, this, DropcapLabel.class, "getPreferredSize", ITechLvl.LVL_05_FINE, true);
      return p;
   }

   /**
    * pixel left with space
    * @return
    */
   public int computeFreeSpace() {
      return 0;
   }

   @Override
   protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g.create();

      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      g2.setPaint(getBackground());
      g2.fillRect(0, 0, getWidth(), getHeight());

      Insets i = getInsets();
      float x0 = i.left;
      float y0 = i.top;

      Font font = getFont();
      String txt = getText();

      fontHeight = g.getFontMetrics().getHeight();

      FontRenderContext frc = g2.getFontRenderContext();
      Shape shape = new TextLayout(txt.substring(0, 1), font, frc).getOutline(null);

      AffineTransform at1 = AffineTransform.getScaleInstance(5d, 5d);
      Shape s1 = at1.createTransformedShape(shape);
      Rectangle r = s1.getBounds();
      r.grow(8, 3);
      int rw = r.width;
      int rh = r.height;

      AffineTransform at2 = AffineTransform.getTranslateInstance(x0, y0 + rh);
      Shape s2 = at2.createTransformedShape(s1);
      Color bigLetterColor = getForeground();
      bigLetterColor = new Color(45, 200, 180);
      g2.setPaint(bigLetterColor);
      g2.fill(s2);

      g2.setPaint(getForeground());

      float x = x0 + rw;
      float y = y0;
      int w0 = getWidth() - i.left - i.right;
      int w = w0 - rw;

      AttributedString as = new AttributedString(txt.substring(1));
      as.addAttribute(TextAttribute.FONT, font);
      AttributedCharacterIterator aci = as.getIterator();
      LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
      int numLines = 0;
      while (lbm.getPosition() < aci.getEndIndex()) {
         TextLayout tl = lbm.nextLayout(w);
         tl.draw(g2, x, y + tl.getAscent());
         y += tl.getDescent() + tl.getLeading() + tl.getAscent();
         if (y0 + rh < y) {
            x = x0;
            w = w0;
         }
         numLines += 1;
      }
      this.numLines = numLines;
      g2.dispose();
   }
}
