package pasa.cbentley.swing.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Label that does not requires a revalidate when next text is set.
 * <br>
 * Text is supposed to be of similar size. usually a timer.
 * 
 * TODO
 * 12:11:00 displays this with possibly animations of moving numbers
 * @author Charles Bentley
 *
 */
public class TimerLabel extends JComponent {

   private String text;

   private Color  textColor;

   private Font   font;

   protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

      g2d.setColor(getForeground());
      g2d.setFont(getFont());

      g2d.drawString(text, 0, 0);
   }

}
