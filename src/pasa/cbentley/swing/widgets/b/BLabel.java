package pasa.cbentley.swing.widgets.b;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.style.Style;
import pasa.cbentley.swing.widgets.KeyedSentence;

/**
 * Support multiple keys.
 * <br>
 * By default no keys
 * @author Charles Bentley
 *
 */
public class BLabel extends JLabel implements IMyGui, MouseListener, MouseWheelListener, IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = 3046161597392552465L;

   private String            key;

   private String            keyTip;

   public String getKeyTip() {
      return keyTip;
   }

   public void setKeyTip(String keyTip) {
      this.keyTip = keyTip;
   }

   private SwingCtx          sc;

   private boolean           isAntiAlias;

   private KeyedSentence     sentence;

   private Style             style;

   public BLabel(SwingCtx sc) {
      this.sc = sc;
      this.addMouseListener(this);
      this.addMouseWheelListener(this);
   }

   public Style getStyle() {
      return style;
   }

   
   /**
    * Define the style
    * @param style
    */
   public void setStyle(Style style) {
      this.style = style;
   }

   public BLabel(SwingCtx sc, String key) {
      this.sc = sc;
      this.setKey(key);
      this.addMouseListener(this);
      this.addMouseWheelListener(this);
   }

   public String getKey() {
      return key;
   }

   public KeyedSentence newSentence() {
      sentence = new KeyedSentence(sc);
      return sentence;
   }

   public void guiUpdate() {
      if (sentence != null) {
         setText(sentence.getSentence());
         if (sentence.hasToolTip()) {
            setToolTipText(sentence.getKeyToolTip());
         }
      } else if (key != null) {
         setText(sc.getResString(key));
         //setToolTipText(sc.getResString(key+".tip"));
      }
      if (style != null) {
         Font fontLabel = sc.getUIData().getFontLabel();
         int incr = 0;
         if (style.getFontRelSize() != null) {
            incr = style.getFontRelSize().intValue();
         }
         int fontStyle = Font.PLAIN;
         if (style.getFontStyle() != null) {
            fontStyle = style.getFontStyle().intValue();
         }
         Font df = fontLabel.deriveFont(fontStyle, fontLabel.getSize2D() + incr);
         this.setFont(df);
      }
   }

   public void setSentence(KeyedSentence sentence) {
      this.sentence = sentence;
   }

   public void setKey(String key) {
      this.key = key;
      this.setText(key);
   }

   protected void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      if (isAntiAlias) {
         g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      super.paintComponent(g2d);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BLabel");
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BLabel");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

   public void mouseClicked(MouseEvent e) {

   }

   public void mousePressed(MouseEvent e) {

   }

   public void mouseReleased(MouseEvent e) {

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {

   }

   public void mouseWheelMoved(MouseWheelEvent e) {
      if (e.getWheelRotation() > 0) {
         isAntiAlias = false;
      } else {
         isAntiAlias = true;
      }
      this.repaint();
      e.consume(); //prevent back//forward
   }

   public void setStyleBold(int sizeDiff) {
      Font f = sc.getUIData().getFontLabel();
      Font df = f.deriveFont(Font.BOLD, f.getSize2D() + sizeDiff);
      this.setFont(df);
   }

   /**
    * update the text
    * @param key
    * @param value
    */
   public void setKeyParam(String keyParam, String value) {
      //
      String str = sc.getResString(key);
      String s = str.replace(keyParam, value);
      setText(s);
   }

}