package pasa.cbentley.swing.widgets.b;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.utils.DocRefresher;

public class BTextField extends JTextField implements IMyGui, FocusListener {

   /**
    * 
    */
   private static final long serialVersionUID = 3046161597392552465L;

   private String            key;

   private SwingCtx          sc;

   private String            keyHint;

   private String            keyTip;

   private boolean           isShowingHint;

   public BTextField(SwingCtx sc) {
      this.sc = sc;
   }

   public BTextField(SwingCtx sc, int cols) {
      super(cols);
      this.sc = sc;
   }

   public BTextField(SwingCtx sc, String key) {
      this.sc = sc;
      this.setKey(key);
   }

   public BTextField(SwingCtx sc, int cols, ActionListener al, DocumentListener doc) {
      super(cols);
      this.sc = sc;
      this.addActionListener(al);
      this.getDocument().addDocumentListener(doc);
   }

   /**
    * Correct way to check if this {@link BTextField} is empty.
    * 
    * @return
    */
   public boolean isShowingHint() {
      return isShowingHint;
   }

   public void setKeyHint(String keyHint) {
      this.keyHint = keyHint;
      super.addFocusListener(this);
   }

   public void focusGained(FocusEvent e) {
      if (this.getText().isEmpty()) {
         super.setText("");
         isShowingHint = false;
         setForeground(sc.getUIData().getCTextFieldForeground());
      }
   }

   public void setTextStringNullVoid(String str) {
      String text = "";
      if (str != null) {
         text = str;
      }
      this.setText(text);
   }

   public void setTextIntegerNullVoid(Integer integer) {
      String text = "";
      if (integer != null) {
         text = integer.toString();
      }
      this.setText(text);
   }

   public void focusLost(FocusEvent e) {
      if (this.getText().isEmpty()) {
         super.setText(sc.getResString(keyHint));
         isShowingHint = true;
         setForeground(sc.getUIData().getCTextFieldForeground().brighter().brighter());
      }
   }

   public String getKey() {
      return key;
   }

   public void guiUpdate() {
      if (key != null) {
         setText(sc.getResString(key));
         if (getKeyTip() == null) {
            sc.guiUpdateTooltip(this, key);
         }
      }
      if (getKeyTip() != null) {
         setToolTipText(sc.getResString(getKeyTip()));
      }
   }

   public void setKey(String key) {
      this.key = key;
      this.setText(key);
   }

   public String getText() {
      return isShowingHint ? "" : super.getText();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BLabel");
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

   public String getKeyTip() {
      return keyTip;
   }

   public void setKeyTip(String keyTip) {
      this.keyTip = keyTip;
   }

   //
   //   @Override
   //   protected void paintComponent(Graphics g) {
   //      if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
   //         Graphics2D g2 = (Graphics2D) g.create();
   //         g2.setPaint(getBackground());
   //         g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
   //         g2.dispose();
   //      }
   //      super.paintComponent(g);
   //   }
   //
   //   @Override;
   //   public void updateUI() {
   //      super.updateUI();
   //      setOpaque(false);
   //      setBorder(new RoundedCornerBorder());
   //   }
   //
}
