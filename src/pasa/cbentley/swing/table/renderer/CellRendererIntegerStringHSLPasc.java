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
public class CellRendererIntegerStringHSLPasc extends DefaultTableCellRenderer implements TableCellRenderer {

   public class BGColor {

      private Color  colorBg;

      private Color  colorFg;

      private String text;

   }

   private static final long      serialVersionUID = 2629606866970459604L;

   private DecimalFormat          df1;

   private DecimalFormat          df2;

   private Font                   font;

   private Font                   fontMono;

   private float[]                hues             = new float[] { 60f, 70f };

   protected final StringBBuilder sb;

   protected final SwingCtx       sc;

   public CellRendererIntegerStringHSLPasc(SwingCtx sc) {
      this.sc = sc;
      sb = new StringBBuilder(sc.getUCtx(),500);
      df2 = new DecimalFormat("0.00");
      df1 = new DecimalFormat("0.0");
      font = new java.awt.Font("Lucida Console", 0, 12);
      fontMono = new Font("Monospaced", Font.PLAIN, 12);
   }

   public BGColor getColorBg(int number) {

      sb.reset();

      //number is normalized 8 characters
      
      int intCharValueLeft1 = -1;
      int intCharValue2 = -1;
      int intCharValue3 = -1;
      int intCharValue4 = -1;
      int intCharValue5 = -1;
      int intCharValue6 = -1;
      
      String strNumber = String.valueOf(number);
      char firstChar = strNumber.charAt(0);
      //1 base count of 
      intCharValueLeft1 = Character.getNumericValue(firstChar);
      
      char charSecond = 0;
      if (strNumber.length() > 1) {
         charSecond = strNumber.charAt(1);
      }
      int secondCharValue = Character.getNumericValue(charSecond);

      StringUtils su = sc.getUCtx().getStrU();
      String strNumberNormalized = su.prettyIntPaddStr(number, 9, "-"); //assume 999 999 999 1 billion
      int numChars = strNumber.length();
      float hue = 0;
      float s = 100f;
      float l = 100f;

      //
      char charRight1 = strNumberNormalized.charAt(strNumberNormalized.length() - 1);
      char charRight2 = strNumberNormalized.charAt(strNumberNormalized.length() - 2);
      char charRight3 = strNumberNormalized.charAt(strNumberNormalized.length() - 3);
      char charRight4 = strNumberNormalized.charAt(strNumberNormalized.length() - 4);
      char charRight5 = strNumberNormalized.charAt(strNumberNormalized.length() - 5);
      char charRight6 = strNumberNormalized.charAt(strNumberNormalized.length() - 6);
      char charRight7 = strNumberNormalized.charAt(strNumberNormalized.length() - 7);

      int char5Integer = Character.getNumericValue(charRight6);
      //special colors for the first 1000.
      int char4Integer = Character.getNumericValue(charRight5);

      switch (numChars) {
         case 1:
            l = 50f; //1-9
            s = 100f;
            if (intCharValueLeft1 == 5) {
               s = 0;
            }
            break;
         case 2:
            l = 65f; //10-99
            s = 95f;
            if (intCharValueLeft1 == 5) {
               s = 0;
            }
            break;
         case 3:
            l = 97f; //100-999
            s = 100f;
            if (intCharValueLeft1 == 5) {
               s = 15;
            }
            break;
         case 4:
            l = 94f; //1000-9999
            s = 94f;
            if (intCharValueLeft1 == 5) {
               s = 15;
            }
            break;
         case 5:
            l = 85f; //10 000-99 999
            s = 85f;
            if (intCharValueLeft1 == 5) {
               s = 15;
            }
            break;
         case 6:
            l = 70f; //100 000-999 999
            s = 65f;
            //case of 550000
            if (intCharValueLeft1 == 5) {
               if (secondCharValue == 5) {
                  s = 0;
               } else {
                  s = 15f;
               }
            }
            break;
         case 7:

            switch (intCharValueLeft1) {
               case 1:
                  l = 50f; //1 000 000-9 999 999
                  s = 55f;
                  break;
               case 2:
                  l = 40f; //1 000 000-9 999 999
                  s = 45f;
                  break;
               case 3:
                  l = 30f; //1 000 000-9 999 999
                  s = 90f;
                  break;
               case 4:
                  l = 20f; //1 000 000-9 999 999
                  s = 60f;
                  break;
               case 5:
                  l = 30f; //1 000 000-9 999 999
                  s = 15f;
                  break;
               case 6:
                  l = 20f; //1 000 000-9 999 999
                  s = 60;
                  break;
               case 7:
                  l = 20f; //1 000 000-9 999 999
                  s = 100f;
                  break;
               case 8:
                  l = 60f; //1 000 000-9 999 999
                  s = 30f;
                  break;
               case 9:
                  l = 15f; //1 000 000-9 999 999
                  s = 90f;
                  break;
               default:
                  break;
            }

            if (secondCharValue == 5) {
               s = 15f;
            }
            //compute hue based on 3rd charValue
            hue = getHueFirstYellow(char5Integer, hue, intCharValueLeft1);
            intCharValueLeft1 = char5Integer;
            secondCharValue = char4Integer;
            break;

      }
      //hue = getHueWithLastPink(firstCharValue, hue);
      //hue = getHueWithLastGreen(firstCharValue, hue);

      //hue = getHueFirstYellowShift(firstCharValue, shift);
      if (numChars == 7) {

      } else {
         hue = getHueFirstYellow(intCharValueLeft1, hue, 1);
      }

      //special historical account
      switch (number) {
         case 0:
            hue = 0f;
            s = 100f;
            l = 100f;
            break;
         case 86646:
            hue = 30f;
            s = 100f;
            l = 60f;
         default:
            break;

      }

      //special case for greys
      if (intCharValueLeft1 == 5) {
         //greys 0 saturation
         //take the coloring of the next most significant digit
         hue = getHueFirstYellow(secondCharValue, hue, secondCharValue);
      }

      float diffLuminance = 100f - l;
      float ratioDiff = 0f;
      //don't make 2 letters number any special
      if (numChars != 2) {
         int numZeros = 0;
         if (charRight1 == '0') {
            numZeros++;
            if (charRight2 == '0') {
               numZeros++;
               if (charRight3 == '0') {
                  numZeros++;
                  if (charRight4 == '0') {
                     numZeros++;
                     if (charRight5 == '0') {
                        numZeros++;
                        if (charRight6 == '0') {
                           numZeros++;
                        }
                     }
                  }
               }
            }
         }
         ratioDiff = (float)numZeros / 6f;
         float incrementLuminance = diffLuminance * ratioDiff;
         switch (numZeros) {
            case 1:
               //max 2% luminance
               incrementLuminance = Math.min(incrementLuminance, 2f);
               break;
            case 2:
               incrementLuminance = Math.min(incrementLuminance, 4f);
               break;
            case 3:
               incrementLuminance=  Math.min(incrementLuminance, 8f);
               break;
            case 4:
               incrementLuminance=  Math.min(incrementLuminance, 16f);
               break;
            case 5:
               incrementLuminance=  Math.min(incrementLuminance, 32f);
               break;
            case 6:
               incrementLuminance=  Math.min(incrementLuminance, 64f);
               break;
            default:
               break;
         }
         l = l + incrementLuminance;
      }
      if (charRight1 == charRight2 && charRight1 == charRight3) {

         if (charRight1 != '0') {
            if (intCharValueLeft1 == 5) {
               float ratioDarkening = 0.2f;
               //grey.. we darkened it
               l = l - (l * ratioDarkening);
            } else {
               s = 100f;
            }
         }
      }

      Color color = HSLColor.toRGB(hue, s, l);

      //base is 0 saturation
      int r = color.getRed();

      int g = color.getGreen();

      int b = color.getBlue();

      float rFloat = (float) r / 255f;

      float gFloat = (float) g / 255f;

      float bFloat = (float) b / 255f;

      int M = Math.max(Math.max(r, g), b);

      int m = Math.min(Math.min(r, g), b);

      int sumRGB = (r + g + b);

      float greenish = (float) g / (float) sumRGB;
      float redish = (float) r / (float) sumRGB;
      float blueish = (float) b / (float) sumRGB;

      int chroma = M - m;

      int value = M;

      int lightness = (M + m) / 2;

      int intensity = sumRGB / 3;

      int luma = (int) getLumaFast(r, g, b);

      sb.append(strNumberNormalized);
      //sb.appendPrettyFront(number, 7, ' ');

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
      sb.appendPrettyFront(df1.format(hue), 5, ' ');
      sb.append('°');
      sb.append(' ');
      sb.append(" saturation=");
      sb.appendPrettyFront(df1.format(s), 5, ' ');
      sb.append('%');

      sb.append(' ');
      sb.append(" light=");
      sb.appendPrettyFront(df1.format(l), 5, ' ');
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

      sb.append(' ');
      sb.append("ish ");
      sb.append("r=");
      sb.append(df2.format(redish));
      sb.append(' ');
      sb.append("g=");
      sb.append(df2.format(greenish));
      sb.append(' ');
      sb.append("b=");
      sb.append(df2.format(blueish));

      BGColor colors = new BGColor();
      colors.colorBg = color;

      //compute fg
      Color colorFgLight = new Color(160, 160, 160);

      Color colorFgDark = new Color(96, 96, 96);
      //the bigger the chroma.. the softer the grey want
      colorFgLight = new Color(255, 255, 255);
      colorFgDark = new Color(0, 0, 0);
      int luminance = getLumaFast(r, g, b);
      if (luminance > 140) {
         colors.colorFg = new Color(16, 16, 16);
      } else {
         if (luminance < 75) {
            //orange
            colors.colorFg = new Color(228, 179, 129);
         } else {
            if (s > 80f) {
               //with saturated colors
               if (redish >= 0.4) {
                  //with s
                  colors.colorFg = new Color(230, 230, 230);
                  colors.colorFg = new Color(228, 179, 129);
               } else if (blueish >= 0.4) {
                  colors.colorFg = new Color(210, 210, 210);
                  colors.colorFg = new Color(228, 179, 129);
               } else {
                  if (l < 40) {
                     //lighter grey
                     int grey = (int) (240 - l);
                     colors.colorFg = new Color(grey, grey, grey);
                  } else {
                     colors.colorFg = new Color(128, 128, 128);
                  }

               }
            } else {
               colors.colorFg = new Color(240, 240, 240);
               colors.colorFg = new Color(228, 179, 129);
            }
         }
      }

      //      float fh = (hue + 180f) % 360;
      //      float fs = (s+50f) % 100;
      //      float fl = (l+50f) % 100;
      //      colors.colorFg = HSLColor.toRGB(fh, fs, fl);

      //colors.colorFg = getFgColorSimple(r, g, b, colorFgLight, colorFgDark);
      colors.text = sb.toString();

      //#debug
      sc.toDLog().pFlow(colors.text, null, CellRendererIntegerStringHSLPasc.class, "getColor", IStringable.LVL_05_FINE, true);

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

   private float getHueFirstYellow(int firstCharValue, float h, int secondary) {
      switch (firstCharValue) {
         case 0:
            switch (secondary) {
               case 1:
                  h = 210f; //light blue
                  break;
               case 2:
                  h = 120f; //green
                  break;
               case 3:
                  h = 330f; //fuschia
                  break;
               case 4:
                  h = 150; //green
                  break;
               default:
                  h = 245; //green
                  break;
            }
            break;
         case 1:
            h = 60f; //yellow
            break;
         case 2:
            h = 240f; //blue light
            break;
         case 3:
            h = 0f; //red  +180
            break;
         case 4:
            h = 90f; //green +120
            break;
         case 5:
            //grey
            break;
         case 6:
            h = 300f; //pink
            break;
         case 7:
            h = 180f; //cyan
            break;
         case 8:
            h = 30f; //orange  //will give browns at l=60
            break;
         case 9:
            h = 270f; //purple 
            break;
         default:
            break;
      }
      return h;
   }

   private float getHueFirstYellowShift(int firstCharValue, float positiveShift) {
      float h = 0f;
      switch (firstCharValue) {
         case 1:
            h = 60f; //yellow
            break;
         case 2:
            h = 180; //cyan
            break;
         case 3:
            h = 300f; //pink
            break;
         case 4:
            h = 90f; //green light
            break;
         case 5:
            h = 210f; //purple +180
            break;
         case 6:
            h = 330f; //red
            break;
         case 7:
            h = 120f; //pink
            break;
         case 8:
            h = 240f; //cyan
            break;
         case 9:
            h = 0f; //red
            break;
         default:
            break;
      }
      return h + positiveShift;
   }

   private float getHueWithLastGreen(int firstCharValue, float h) {
      switch (firstCharValue) {
         case 1:
            h = 0f; //red
            break;
         case 2:
            h = 30f; //orange
            break;
         case 3:
            h = 60f; //yellow
            break;
         case 4:
            h = 120f; //green
            break;
         case 5:
            h = 180f; //cyan
            break;
         case 6:
            h = 240f; //blue
            break;
         case 7:
            h = 270f; //purple
            break;
         case 8:
            h = 300f; //pink
            break;
         case 9:
            h = 90; //pink
            break;
         default:
            break;
      }
      return h;
   }

   private float getHueWithLastPink(int firstCharValue, float h) {
      switch (firstCharValue) {
         case 1:
            h = 0f; //red
            break;
         case 2:
            h = 30f; //orange
            break;
         case 3:
            h = 60f; //yellow
            break;
         case 4:
            h = 120f; //green
            break;
         case 5:
            h = 160f; //blue-green
            break;
         case 6:
            h = 180f; //cyan
            break;
         case 7:
            h = 240f; //blue
            break;
         case 8:
            h = 280f; //purple
            break;
         case 9:
            h = 300f; //pink
            break;
         default:
            break;
      }
      return h;
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
    * Y
    * Optimized for speed.
    * @param r
    * @param g
    * @param b
    * @return
    */
   public int getLumaFast(int r, int g, int b) {
      //blue being the least important
      return (r + r + r + b + g + g + g + g) >> 3;
      //return (r << 1 + r + g << 2 + b) >> 3;
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