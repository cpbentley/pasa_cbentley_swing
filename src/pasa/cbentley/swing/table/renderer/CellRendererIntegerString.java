package pasa.cbentley.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.ColorUtils;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.swing.color.IntToColor;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererIntegerString extends DefaultTableCellRenderer implements TableCellRenderer {

   private static final long serialVersionUID = 2629606866970459604L;

   protected final SwingCtx  sc;

   public CellRendererIntegerString(SwingCtx sc) {
      this.sc = sc;
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object integer, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, integer, isSelected, hasFocus, row, column);
      if (integer == null)
         return this;
      Integer value = (Integer) integer;
      Color color = getColor(value);
      renderer.setBackground(color);
      return this;
   }

   public Color getColor(int account) {

      //pad with zeros for at least 6 chars
      StringUtils su = sc.getUC().getStrU();
      String str = su.prettyInt0Padd(account, 8);
      
      //#debug
      sc.toDLog().pFlow("str="+str, null, CellRendererIntegerString.class, "getColor", IStringable.LVL_05_FINE, true);
      
      //
      int len = str.length();
      int subStart = len - 6;
      int subEnd = len;
      String sub = str.substring(subStart, subEnd);
      //assume one 1 million
      String colorChars = sub.substring(0, 2);
      String colorSelect = sub.substring(2, 4);
      String colorMod = sub.substring(4, 6);
      char c = colorChars.charAt(0);
      int v0 = Integer.valueOf(colorChars.substring(1, 2));
      int v1 = Integer.valueOf(colorSelect);
      int v2 = Integer.valueOf(colorMod);

      int color = 0xFFFFFF;
      switch (c) {
         case '0':
            //white to bleu
            color = getWhiteToBlue(v0, v1, v2);
            break;
         case '1':
            //blue base to gree
            color = getBlueToGreen(v0, v1, v2);
            break;
         case '2':
            //green to red
            color = getGreenToRed(v0, v1, v2);
            break;
         case '3':
            color = getRedToGrey(v0, v1, v2);
            break;
         case '4':
            color = getGreyToYellow(v0, v1, v2);
            break;
         case '5':
            break;
         case '6':
            break;
         case '7':
            break;
         case '8':
            break;
         case '9':
            break;

         default:
            break;
      }
      int red = ColorUtils.getRed(color);
      int green = ColorUtils.getGreen(color);
      int bleu = ColorUtils.getBlue(color);

      //#debug
      sc.toDLog().pTest("account -> red=" + red + " green=" + green + " blue" + bleu, null, IntToColor.class, "getColorDarkBgAccount", ITechLvl.LVL_05_FINE, true);

      return new Color(red, green, bleu);
   }
   
   /**
    * 
    * @param v0 0 to 9 modifies the blue factor 
    * @param v1 00 to 99
    * @param v2
    * @return
    */
   public int getWhiteToBlue(int v0, int v1, int v2) {
      int red = 255;
      int green = 255;
      int blue = 255;
      blue -= v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      red -= v12;
      green -= v12;
      int v22 = v2 / 2;
      red -= v22;
      green -= v22;
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getBlueToGreen(int v0, int v1, int v2) {
      int red = 50;
      int green = 50;
      int blue = 255;
      red += v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      green += v12;
      blue -= v12;
      int v22 = v2 / 2;
      green += v22;
      blue -= v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getGreenToRed(int v0, int v1, int v2) {
      int red = 50;
      int green = 255;
      int blue = 50;
      green -= v0 * 4;
      //limit is 128 ?
      int v12 = v1 / 2;
      red += v12;
      blue += v12;
      int v22 = v2 / 2;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getRedToGrey(int v0, int v1, int v2) {
      int red = 255;
      int green = 50;
      int blue = 50;
      int v12 = v1 / 2;
      int v22 = v2 / 2;
      red -= v12 + v22;

      red += v12;
      blue += v12;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

   public int getGreyToYellow(int v0, int v1, int v2) {
      int red = 128;
      int green = 128;
      int blue = 128;
      int v12 = v1 / 2;
      int v22 = v2 / 2;
      green += v0 * 4;

      red += v12;
      blue += v12;
      red += v22;
      blue += v22;
      //go to
      return ColorUtils.getRGBInt(red, green, blue);
   }

}