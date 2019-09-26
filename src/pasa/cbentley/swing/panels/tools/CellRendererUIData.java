package pasa.cbentley.swing.panels.tools;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.data.UIDataElement;

public class CellRendererUIData extends DefaultTableCellRenderer {
   
   protected final SwingCtx psc;

   public CellRendererUIData(SwingCtx sc) {
      this.psc = sc;
   }

   @Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      
      if (value instanceof UIDataElement) {
         UIDataElement ui = (UIDataElement) value;
         if(ui.isColor()) {
            renderer.setBackground(ui.getColor());
         }
      } else {
         
      }
      return this;

   }
}
