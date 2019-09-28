package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.image.CursorSwing;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * <li> Shows a different icon when mouse pressed
 * <li> User friendly cursor support
 * <li> Support for 
 * @author Charles Bentley
 *
 */
public class BButton extends JButton implements MouseListener, IMyGui {

   protected Icon        selected;

   protected Icon        normal;

   protected CursorSwing cs;

   protected SwingCtx    sc;

   private String        keyNormal;

   public BButton(SwingCtx sc) {
      this.sc = sc;
   }

   public BButton(SwingCtx sc, ActionListener al) {
      this.sc = sc;
      this.addActionListener(al);
      this.addMouseListener(this);
   }

   public BButton(SwingCtx sc, Icon icon, Icon selected, String text) {
      super(text, icon);
      this.sc = sc;
      this.normal = icon;
      this.selected = selected;
      this.addMouseListener(this);
   }

   public BButton(SwingCtx sc, ActionListener al, String key) {
      this(sc, al);
      setTextKey(key);
   }

   public BButton(SwingCtx sc, ActionListener al, String key, Icon icon, Icon selected) {
      this(sc, al);
      this.normal = icon;
      this.selected = selected;
      setTextKey(key);
   }

   public BButton(SwingCtx sc, ActionListener al, Icon icon, Icon selected) {
      this(sc, al, null, icon, selected);
   }

   public BButton(SwingCtx sc, Icon icon, Icon selected) {
      this(sc, icon, selected, null);
   }

   public void setTextKey(String key) {
      this.keyNormal = key;
   }

   public void setIconSelected(Icon icon) {
      selected = icon;
   }

   public void setIconNormal(Icon icon) {
      normal = icon;
      this.setIcon(normal);
   }

   public void setIcon(String iconID, String iconCategory, int iconSize) {
      normal = sc.getResIcon(iconID, iconCategory, iconSize, IconFamily.ICON_MODE_0_DEFAULT);
      selected = sc.getResIcon(iconID, iconCategory, iconSize, IconFamily.ICON_MODE_1_SELECTED);
   }

   public void updateGuiState() {
      if (keyNormal != null) {
         this.setText(sc.getResString(keyNormal));
      }
      if (isSelected()) {
         this.setIcon(selected);
      } else {
         this.setIcon(normal);
      }
   }

   /**
    * 
    * @param path
    */
   public void setCursorImage(String path) {
      if (cs == null) {
         cs = new CursorSwing(sc, this);
         cs.updateCursor(path);
      }
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      this.setIcon(selected);
   }

   public void mouseReleased(MouseEvent e) {
      this.setIcon(normal);
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
      this.setIcon(normal);
   }

   public void guiUpdate() {
      updateGuiState();
      sc.guiUpdateTooltip(this, keyNormal);
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BButton");
      dc.appendVarWithSpace("keyNormal", keyNormal);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BButton");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
