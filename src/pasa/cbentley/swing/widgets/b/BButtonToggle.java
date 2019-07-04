package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
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
public class BButtonToggle extends JButton implements MouseListener, IMyGui, IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = -1357554885136883960L;

   protected Icon            iconSelected;

   protected Icon            iconNormal;

   protected CursorSwing     cs;

   protected SwingCtx        sc;

   private String            keyNormal;

   private String            keySelected;

   public BButtonToggle(SwingCtx sc) {
      super();
      this.sc = sc;
      this.addMouseListener(this);
   }

   public BButtonToggle(SwingCtx sc, ActionListener al) {
      super();
      this.addActionListener(al);
      this.sc = sc;
      this.addMouseListener(this);
   }

   public void setIcons(Icon icon, Icon selected) {
      this.iconNormal = icon;
      this.iconSelected = selected;
   }

   public void setTextKeys(String key, String keySelected) {
      this.keyNormal = key;
      this.keySelected = keySelected;
   }

   /**
    * 
    * @param iconID
    * @param iconCategory
    * @param iconSize
    */
   public void setIcon(String iconID, String iconCategory, int iconSize) {
      iconNormal = sc.getResIcon(iconID, iconCategory, iconSize, IconFamily.ICON_MODE_0_DEFAULT);
      iconSelected = sc.getResIcon(iconID, iconCategory, iconSize, IconFamily.ICON_MODE_1_SELECTED);
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
   }

   public void mouseReleased(MouseEvent e) {
      updateGuiState();
   }

   public void updateGuiState() {
      if (isSelected()) {
         this.setIcon(iconSelected);
         this.setText(sc.getResString(keySelected));
         this.setToolTipText(sc.getResString(keySelected + ".tip"));
      } else {
         this.setIcon(iconNormal);
         this.setText(sc.getResString(keyNormal));
         this.setToolTipText(sc.getResString(keyNormal + ".tip"));
      }
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void unSelectButton() {

   }

   public void setSelected(boolean b) {
      super.setSelected(b);
      updateGuiState();
   }

   public void guiUpdate() {
      updateGuiState();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BButtonToggle");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BButtonToggle");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
