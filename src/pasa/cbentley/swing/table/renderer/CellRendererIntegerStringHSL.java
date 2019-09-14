/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.robcamick.layout.HSLColor;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererIntegerStringHSL extends DefaultTableCellRenderer implements TableCellRenderer {

   private static final long      serialVersionUID = 2629606866970459604L;

   protected final SwingCtx       sc;

   protected final StringBBuilder sb;

   private DecimalFormat          df2;

   private DecimalFormat          df1;

   private Font                   font;

   private Font                   fontMono;

   public CellRendererIntegerStringHSL(SwingCtx sc) {
      this.sc = sc;
      sb = new StringBBuilder(500);
      df2 = new DecimalFormat("#.00");
      df1 = new DecimalFormat("#.0");
      font = new java.awt.Font("Lucida Console", 0, 12);
      fontMono = new Font("Monospaced", Font.PLAIN, 12);
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object integer, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, integer, isSelected, hasFocus, row, column);
      if (integer == null)
         return this;
      Integer number = (Integer) integer;
      BGColor colors = getColorBg(number);
      renderer.setBackground(colors.colorBg);
      renderer.setForeground(colors.colorFg);
      this.setText(colors.text);
      this.setFont(font);
      return this;
   }

   public class BGColor {

      private Color  colorBg;

      private Color  colorFg;

      private String text;

   }

   public BGColor getColorBg(int number) {

      sb.reset();

      StringUtils su = sc.getUCtx().getStrU();
      String str = su.prettyInt0Padd(number, 6);
      int len = str.length();
      int subStart = len - 6;
      int subEnd = len;
      String sub = str.substring(subStart, subEnd);
      String colorChars = sub.substring(0, 3);
      String colorSelect = sub.substring(3, 6);
      int hue = Integer.valueOf(colorChars); //999 - 000

      float hueLast = hue % 10;
      int last = Integer.valueOf(colorSelect);
      //00 00 00
      //100 000
      //500 000
      //hue is defined by the highest value
      float h = 40 + ((float) hue / 1000f) * 360f;

      //base 5
      int number5 = number / 5;
      int last5 = last / 5;

      float ration500 = last / 500f;
      if (last > 500) {
         ration500 = (1000 - last) / 500f;
      }

      float ratioLast100 = last5 / 100f;
      if (last5 > 100) {
         ratioLast100 = (200 - last5) / 100f;
      }

      float ratioInRange = ratioLast100;
      float s = 50f;
      //s = s + (45f * ratioInRange);
      //float s = 0.25f + (((float) last5 / 200f) * 0.70f);

      float l = 70f;
      //l = l - (20f * ratioInRange);

      Color color = HSLColor.toRGB(h, s, l);

      //base is 0 saturation
      int r = color.getRed();
      int g = color.getGreen();
      int b = color.getBlue();

      float rFloat = (float) r / 255f;
      float gFloat = (float) g / 255f;
      float bFloat = (float) b / 255f;

      int M = Math.max(Math.max(r, g), b);
      int m = Math.min(Math.max(r, g), b);
      int chroma = M - m;

      int value = M;
      int lightness = (M + m) / 2;
      int intensity = (r + g + b) / 3;

      int luma = (int) getLumaFast(r, g, b);

      sb.appendPrettyFront(number, 7, ' ');

      sb.append(' ');
      sb.append("r=");
      sb.appendPrettyFront(r, 3, ' ');
      sb.append(' ');
      sb.append("g=");
      sb.appendPrettyFront(g, 3, ' ');
      sb.append(' ');
      sb.append("b=");
      sb.appendPrettyFront(b, 3, ' ');

      sb.append(" rgb=[");
      sb.append(df2.format(rFloat));
      sb.append(',');
      sb.append(df2.format(gFloat));
      sb.append(',');
      sb.append(df2.format(bFloat));
      sb.append("]");

      sb.append(' ');
      sb.append("hue=");
      sb.append(df1.format(h));
      sb.append('°');
      sb.append(' ');
      sb.append(" saturation=");
      sb.append(df1.format(s));
      sb.append('%');

      sb.append(' ');
      sb.append(" light=");
      sb.append(df1.format(l));
      sb.append('%');
      sb.append(' ');
      sb.append("luma=");
      sb.appendPrettyFront(luma, 3, ' ');
      sb.append(' ');
      sb.append("intensity=");
      sb.appendPrettyFront(intensity, 3, ' ');
      sb.append(' ');
      sb.append("V=");
      sb.appendPrettyFront(value, 3, ' ');
      sb.append(' ');
      sb.append("lightness=");
      sb.appendPrettyFront(lightness, 3, ' ');
      sb.append(' ');
      sb.append("chroma=");
      sb.appendPrettyFront(chroma, 3, ' ');

      BGColor colors = new BGColor();
      colors.colorBg = color;

      //compute fg
      Color colorFgLight = new Color(240, 240, 240);
      Color colorFgDark = new Color(32, 32, 32);

      int greyPivot = 128;
      float lightFloat = l / 100f;
      int lumaPivot = 140; //when above 140 . we need darker color
      int diff = luma - lumaPivot;
      int lumfactor = (int) (diff * 2f);
      int grey = 188 - lumfactor;
      
      //grey += (50f * bFloat);
      //also factoring in lightness.. when more light.. we need darker
      //int lightfactor = (int) (40 * lightFloat);
      //grey -= lightfactor;
      
      grey = Math.min(255, grey);
      grey = Math.max(0, grey);

      sb.append(' ');
      sb.append("grey=");
      sb.append(grey);

      //when blue. we want to make it lighter
      colors.colorFg = new Color(grey, grey, grey);
      colors.colorFg = getFgColorSimple(r, g, b, colorFgLight, colorFgDark);
      //colors.colorFg = HSLColor.toRGB(h + 180, s, l); //complementary

      colors.text = sb.toString();

      //#debug
      sc.toDLog().pFlow(colors.text + " ratioInRange=" + ratioInRange, null, CellRendererIntegerStringHSL.class, "getColor", IStringable.LVL_05_FINE, true);

      return colors;
   }

   public Color getFgColorSimple(int r, int g, int b, Color lightColor, Color darkColor) {
      int luminance = getLumaFast(r, g, b);
      if (luminance < 140) {
         return lightColor;
      } else {
         return darkColor;
      }
   }

   /**
    * Y
    * Optimized for speed.
    * @param r
    * @param g
    * @param b
    * @return
    */
   public int getLumaFast(int r, int g, int b) {
      //blue being the least important
      return (r+r+r+b+g+g+g+g)>>3;
      //return (r << 1 + r + g << 2 + b) >> 3;
   }

   /**
    * Photometric/digital ITU BT.709: http://www.itu.int/rec/R-REC-BT.709
    * @param r
    * @param g
    * @param b
    * @return
    */
   public float getLuma709Photo(float r, float g, float b) {
      return (float) (0.2126f * r + 0.7152f * g + 0.0722f * b);
   }

   /**
    * Digital ITU BT.601 (gives more weight to the R and B components):
    * @param r
    * @param g
    * @param b
    * @return
    */
   public float getLuma601Digital(float r, float g, float b) {
      return (float) (0.299 * r + 0.587 * g + 0.114f * b);
   }

   public Color pickTextColorBasedOnBgColorAdvanced(int r, int g, int b, Color lightColor, Color darkColor) {
      float f255 = 255f;
      float[] uicolors = new float[] { (float) r / f255, (float) g / f255, (float) b / f255 };
      float[] uicolorsMapped = new float[3];
      for (int i = 0; i < uicolors.length; i++) {
         float c = uicolors[i];
         float c2 = 0;
         if (c <= 0.03928f) {
            c2 = c / 12.92f;
         } else {
            c2 = (float) Math.pow((c + 0.055f) / 1.055f, 2.4f);
         }
         uicolorsMapped[i] = c2;
      }
      float L = (0.2126f * uicolorsMapped[0]) + (0.7152f * uicolorsMapped[1]) + (0.0722f * uicolorsMapped[2]);
      return (L > 0.179f) ? darkColor : lightColor;
   }
}