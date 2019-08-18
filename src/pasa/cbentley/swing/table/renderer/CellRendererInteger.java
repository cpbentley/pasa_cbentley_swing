package pasa.cbentley.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 */
public class CellRendererInteger extends DefaultTableCellRenderer implements TableCellRenderer {

   private static final long serialVersionUID = 2629606866970459604L;

   public CellRendererInteger(SwingCtx sc) {
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object integer, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, integer, isSelected, hasFocus, row, column);
      if (integer == null)
         return this;
      Integer value = (Integer) integer;
      float f100 = value / 100;
      int rest = value % 100;
      int rgb = 0xEEEEEE-(value>>2);
      Color color = new Color(rgb);
      renderer.setBackground(color);
      return this;
   }

}