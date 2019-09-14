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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.helpers.ColorData;
import pasa.cbentley.core.src4.helpers.IntegerString;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererIntegerStringPascal extends DefaultTableCellRenderer implements TableCellRenderer {

   public class BGColor {

      private Color  colorBg;

      private Color  colorFg;

      private String text;

   }

   private static final long      serialVersionUID = 2629606866970459604L;

   private Border                 border;

   private ColorData              colorData;

   BGColor                        colors           = new BGColor();

   private Font                   font;

   private Font                   fontMono;

   private IntegerString          istr;

   protected final StringBBuilder sb;

   protected final SwingCtx       sc;

   private Color                  fgLuminance50;

   public CellRendererIntegerStringPascal(SwingCtx sc) {
      this.sc = sc;
      istr = new IntegerString(sc.getUCtx());
      sb = new StringBBuilder(500);
      font = new java.awt.Font("Lucida Console", 0, 12);
      fontMono = new Font("Monospaced", Font.PLAIN, 12);
      colorData = new ColorData(sc.getUCtx());
      fgLuminance50 = sc.getSwingColorStore().getColorRGB(110, 110, 110);
      border = BorderFactory.createEmptyBorder();
   }

   public BGColor getColorBg(int number) {

      sb.reset();
      istr.setNumber(number);

      float hue = 0f;
      float l = 0f;
      float s = 0f;
      int numChars = istr.getSize();
      boolean isGreyMode = false;
      switch (numChars) {
         case 1:
            s = 100f;
            l = 50f; //1-9
            if (istr.getDigitFromLeft1() == 5) {
               s = 0;
            }
            break;
         case 2:
            s = 95f;
            l = 65f; //10-99
            if (istr.getDigitFromLeft1() == 5) {
               s = 0;
            }
            break;
         case 3:
            s = 100f;
            l = 97f; //100-999
            if (istr.getDigitFromLeft1() == 5) {
               s = 15;
            }
            break;
         case 4:
            s = 94f;
            l = 94f; //1000-9999
            if (istr.getDigitFromLeft1() == 5) {
               s = 15;
            }
            break;
         case 5:
            s = 85f;
            l = 85f; //10 000-99 999
            if (istr.getDigitFromLeft1() == 5) {
               s = 15;
            }
            break;
         case 6:
            s = 65f;
            l = 70f; //100 000-999 999
            //case of 550000
            if (istr.getDigitFromLeft1() == 5) {
               if (istr.getDigitFromLeft2() == 5) {
                  s = 0;
               } else {
                  s = 15f;
               }
            }
            break;
         case 7:
            //depending on the first digit
            switch (istr.getDigitFromLeft1()) {
               case 1:
                  s = 55f;
                  l = 50f; //1 000 000-9 999 999
                  break;
               case 2:
                  s = 45f;
                  l = 40f; //1 000 000-9 999 999
                  break;
               case 3:
                  s = 85f;
                  l = 25f; //1 000 000-9 999 999
                  break;
               case 4:
                  s = 60f;
                  l = 20f; //1 000 000-9 999 999
                  break;
               case 5:
                  s = 15f;
                  l = 30f; //1 000 000-9 999 999
                  break;
               case 6:
                  s = 70f;
                  l = 20f; //1 000 000-9 999 999
                  break;
               case 7:
                  s = 80f;
                  l = 20f; //1 000 000-9 999 999
                  break;
               case 8:
                  s = 90f;
                  l = 15f; //1 000 000-9 999 999
                  break;
               case 9:
                  s = 100f;
                  l = 10f; //1 000 000-9 999 999
                  break;
               default:
                  break;
            }

            if (istr.getDigitFromLeft2() == 5) {
               isGreyMode = true;
               s = 15f;
               if (istr.getDigitFromLeft3() == 5) {
                  s = 0f;
               }
            }
            //compute hue. extra hue depends on first digit
            hue = getHueFirstYellow(istr.getDigitFromLeft2(), hue, istr.getDigitFromLeft1());
            break;

      }

      if (numChars < 7) {
         hue = getHueFirstYellow(istr.getDigitFromLeft1(), hue, 1);

         //special case for greys
         if (istr.getDigitFromLeft1() == 5) {
            isGreyMode = true;
            //take the coloring of the next most significant digit
            hue = getHueFirstYellow(istr.getDigitFromLeft2(), hue, istr.getDigitFromLeft2());
         }
      }

      //override with special historical accounts
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

      float diffSaturation = 100f - s;
      float diffLuminance = 100f - l;
      //don't make 2 letters number any special

      if (numChars >= 3) {
         int num = istr.getNumIdenticalDigitsFromRight();
         int numZeros = 0;
         int numNonZerosDigits = 0;
         if (istr.getDigitFromRight1() == 0) {
            numZeros = num;
         } else {
            numNonZerosDigits = num;
         }
         if (numZeros != 0) {
            float ratioDiffNumZeros = (float) numZeros / 6f;
            float incrementLuminance = diffLuminance * ratioDiffNumZeros;
            switch (numZeros) {
               case 1:
                  //max 2% luminance
                  incrementLuminance = Math.min(incrementLuminance, 2f);
                  break;
               case 2:
                  incrementLuminance = Math.min(incrementLuminance, 4f);
                  break;
               case 3:
                  incrementLuminance = Math.min(incrementLuminance, 8f);
                  break;
               case 4:
                  incrementLuminance = Math.min(incrementLuminance, 16f);
                  break;
               case 5:
                  incrementLuminance = Math.min(incrementLuminance, 32f);
                  break;
               case 6:
                  incrementLuminance = Math.min(incrementLuminance, 64f);
                  break;
               default:
                  break;
            }
            l = l + incrementLuminance;
         }
         if (numNonZerosDigits >= 3) {
            float ratioDiffNum = (float) numNonZerosDigits / 6f;
            float incrementSaturation = diffSaturation * ratioDiffNum;
            switch (numZeros) {
               case 3:
                  //max 2% luminance
                  incrementSaturation = Math.min(incrementSaturation, 8f);
                  break;
               case 4:
                  incrementSaturation = Math.min(incrementSaturation, 16f);
                  break;
               case 5:
                  incrementSaturation = Math.min(incrementSaturation, 32f);
                  break;
               case 6:
                  incrementSaturation = Math.min(incrementSaturation, 64f);
                  break;
               case 7:
                  incrementSaturation = Math.min(incrementSaturation, 64f);
                  break;
               default:
                  break;
            }
            if (isGreyMode) {
               float ratioDarkening = 0.2f;
               //grey.. we darkened it
               l = l - (l * ratioDarkening);
            } else {
               //increase saturation
               s = s + incrementSaturation;
            }
         }
      }

      //only interesting for bigger numbers
      if (numChars >= 5 && istr.isPalindrome()) {
         //override some previous parameters for some effect
         s += 10;
      }

      colorData.setH360S100L100(hue, s, l);
      sb.appendPrettyFront(istr.getString(), 9, '-');
      colorData.createString(sb);
      colors.colorBg = sc.getSwingColorStore().getColorRGB(colorData.getRed255(), colorData.getGreen255(), colorData.getBlue255());
      colors.colorFg = getFgColor(colorData);
      colors.text = sb.toString();
      return colors;
   }

   public Color getFgColor(ColorData colorData) {
      Color colorFg = sc.getSwingColorStore().getColorRGB(255, 255, 255);

      int luma = colorData.getLuma255();
      float saturation100 = colorData.getSaturation100();

      //first deal with the greys... saturations around 0 and 15%
      if (saturation100 <= 16) {
         if (luma > 160) {
            colorFg = sc.getSwingColorStore().getColorGrey(150);
         } else if (luma > 90) {
            colorFg = sc.getSwingColorStore().getColorGrey(120);
         } else {
            colorFg = sc.getSwingColorStore().getColorGrey(110);
         }
      } else if (luma > 160) {
         if (saturation100 > 40) {
            colorFg = sc.getSwingColorStore().getColorRGB(110, 110, 110);
         } else {
            colorFg = sc.getSwingColorStore().getColorRGB(48, 48, 48);
         }
      } else if (luma > 140) {
         colorFg = sc.getSwingColorStore().getColorRGB(16, 16, 16);
      } else {
         if (luma < 50) {
            //orange
            colorFg = sc.getSwingColorStore().getColorRGB(110, 110, 110);
         } else if (luma < 75) {
            colorFg = sc.getSwingColorStore().getColorRGB(128, 128, 128);
         } else if (luma < 85) {
            colorFg = sc.getSwingColorStore().getColorRGB(138, 138, 138);
         } else {
            if (saturation100 > 80f) {
               //with saturated colors
               if (colorData.getRedishFloat() >= 0.4) {
                  //with s
                  colorFg = new Color(230, 230, 230);
                  colorFg = new Color(228, 179, 129);
               } else if (colorData.getBlueishFloat() >= 0.4) {
                  colorFg = new Color(210, 210, 210);
                  colorFg = new Color(228, 179, 129);
               } else {
                  float light100 = colorData.getLight100();
                  if (light100 < 40) {
                     //lighter grey
                     int grey = (int) (240 - light100);
                     colorFg = new Color(grey, grey, grey);
                  } else {
                     colorFg = new Color(128, 128, 128);
                  }
               }
            } else {
               colorFg = sc.getSwingColorStore().getColorRGB(228, 179, 129);
            }
         }
      }
      return colorFg;
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
                  h = 150f; //green
                  break;
               case 5:
                  h = 210f; //light blue
                  break;
               case 6:
                  h = 15f; //saumon
                  break;
               case 7:
                  h = 255f; //blue purple
                  break;
               case 8:
                  h = 75f; //yellow-green
                  break;
               case 9:
                  h = 165f; //green
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

   @Override
   public Component getTableCellRendererComponent(JTable table, Object integer, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, integer, isSelected, hasFocus, row, column);
      if (integer == null)
         return this;
      Integer number = (Integer) integer;
      BGColor colors = getColorBg(number);
      renderer.setBackground(colors.colorBg);
      renderer.setForeground(colors.colorFg);
      this.setBorder(border);
      this.setText(colors.text);
      this.setFont(font);
      return this;
   }

}