package pasa.cbentley.swing.style;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import pasa.cbentley.swing.ctx.SwingCtx;

public class Style {

   private SwingCtx sc;

   private Color    bgColor;

   private Color    fgColor;

   private Color    borderColor;

   private Font     font;

   /**
    * Relative increment from 
    */
   private Integer  fontRelSize;

   public Color getBgColor() {
      return bgColor;
   }

   /**
    * Will only be seen if {@link Component} is opaque.
    * @param bgColor
    */
   public void setBgColor(Color bgColor) {
      this.bgColor = bgColor;
   }

   public Color getFgColor() {
      return fgColor;
   }

   public void setFgColor(Color fgColor) {
      this.fgColor = fgColor;
   }

   public Color getBorderColor() {
      return borderColor;
   }

   public void setBorderColor(Color borderColor) {
      this.borderColor = borderColor;
   }

   public Font getFont() {
      return font;
   }

   public void setFont(Font font) {
      this.font = font;
   }

   public Integer getBorderSize() {
      return borderSize;
   }

   public void setBorderSize(Integer borderSize) {
      this.borderSize = borderSize;
   }

   public Border getBorder() {
      return border;
   }

   public void setBorder(Border border) {
      this.border = border;
   }

   private Integer borderSize;

   private Border  border;

   private Integer fontStyle;

   public Style(SwingCtx sc) {
      this.sc = sc;

   }

   public Integer getFontStyle() {
      return fontStyle;
   }

   public void applyStyleTo(JComponent c) {
      if (bgColor != null) {
         c.setBackground(bgColor);
      }
      if (bgColor != null) {
         c.setForeground(fgColor);
      }
      if (border != null) {
         c.setBorder(border);
      } else {
         //create one
         if (borderSize != null) {
            Border border = BorderFactory.createLineBorder(borderColor, borderSize);
            c.setBorder(border);
         }
      }
      if (font != null) {
         c.setFont(font);
      }
   }

   public Integer getFontRelSize() {
      return fontRelSize;
   }

   public void setFontRelSize(Integer fontRelSize) {
      this.fontRelSize = fontRelSize;
   }

   public void setFontStyle(Integer fontStyle) {
      this.fontStyle = fontStyle;
   }
}
