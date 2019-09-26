package pasa.cbentley.swing.data;

import java.awt.Color;
import java.awt.Font;

import pasa.cbentley.swing.ctx.SwingCtx;

public class UIDataElement implements Comparable<UIDataElement> {

   protected final SwingCtx sc;

   private String           key;

   private Color            color;

   private Font             font;

   private String           valueStr;

   public UIDataElement(SwingCtx sc) {
      this.sc = sc;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String string) {
      this.key = string;
   }

   public boolean isColor() {
      return color != null;
   }
   
   public Color getColor() {
      return color;
   }
   
   public void setValueStr(String string) {
      this.valueStr = string;
   }

   public void setColor(Color value) {
      this.color = value;
   }

   public int compareTo(UIDataElement o) {
      return this.key.compareTo(o.key);
   }

   public void setFont(Font font) {
      this.font = font;
   }

}
