package pasa.cbentley.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.utils.StringUtils;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererIntegerStringHSB extends DefaultTableCellRenderer implements TableCellRenderer {

   private static final long serialVersionUID = 2629606866970459604L;
   protected final SwingCtx sc;

   public CellRendererIntegerStringHSB(SwingCtx sc) {
      this.sc = sc;
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object integer, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, integer, isSelected, hasFocus, row, column);
      if (integer == null)
         return this;
      Integer number = (Integer) integer;
      renderer.setBackground(getColor(number));
      return this;
   }
   
   public Color getColor(int number) {
      StringUtils su = sc.getUC().getStrU();
      String str = su.prettyInt0Padd(number, 6);
      int len = str.length();
      int subStart = len - 6;
      int subEnd = len;
      String sub = str.substring(subStart, subEnd);
      String colorChars = sub.substring(0, 3);
      String colorSelect = sub.substring(3, 6);
      int hue = Integer.valueOf(colorChars);
      float hueLast = hue % 10;
      int last = Integer.valueOf(colorSelect);
      //00 00 00
      //100 000
      //500 000
      //hue is defined by the highest value
      float h = ((float)hue / 1000f);
      
      //#debug
      sc.toDLog().pFlow("#"+ number + " h="+h, null, CellRendererIntegerStringHSB.class, "getColor", IStringable.LVL_05_FINE, true);
      
      int last5 = last / 5;
      //float s = 0.99f;
      float s = 0.25f + (((float)last5 /200f) * 0.70f);
      
      //brightness is the 5 in 1000, 200 different colors
      float b = 0.85f;
      //float b = 0.70f + (0.2f * (hueLast / 10f));
      Color color = Color.getHSBColor(h, s, b);
      return color;
   }

}