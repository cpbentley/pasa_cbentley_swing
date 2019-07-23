package pasa.cbentley.swing.table;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 *  Attempt to mimic the table header renderer
 */
public class CellRendererRowNumber extends DefaultTableCellRenderer {

   /**
    * 
    */
   private static final long serialVersionUID = -8370039621368830459L;

   public CellRendererRowNumber() {
      setHorizontalAlignment(JLabel.CENTER);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      if (table != null) {
         JTableHeader header = table.getTableHeader();
         if (header != null) {
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
         }
      }
      if (isSelected) {
         setFont(getFont().deriveFont(Font.BOLD));
      }
      setText((value == null) ? "" : value.toString());
      setBorder(UIManager.getBorder("TableHeader.cellBorder"));
      return this;
   }
}