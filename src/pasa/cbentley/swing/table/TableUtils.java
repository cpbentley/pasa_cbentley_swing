package pasa.cbentley.swing.table;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import pasa.cbentley.swing.ctx.SwingCtx;

public class TableUtils {

   private SwingCtx sc;

   public TableUtils(SwingCtx sc) {
      this.sc = sc;
   }

   public void sortTableDescending(JTable table, int column) {
      sortTable(table, column, SortOrder.DESCENDING);
   }

   public void sortTableAscending(JTable table, int column) {
      sortTable(table, column, SortOrder.ASCENDING);
   }

   public void sortTable(JTable table, int column, SortOrder sortOrder) {
      DefaultRowSorter<?, ?> sorter = ((DefaultRowSorter) table.getRowSorter());
      ArrayList<RowSorter.SortKey> list = new ArrayList<RowSorter.SortKey>();
      list.add(new RowSorter.SortKey(column, sortOrder));
      sorter.setSortKeys(list);
      sorter.sort();
   }

   public void resizeColumnWidth(JTable table) {
      for (int column = 0; column < table.getColumnCount(); column++) {
         resizeColumnWidth(table, column);
      }
   }

   public void resizeColumnWidth(JTable table, int column) {
      final TableColumnModel columnModel = table.getColumnModel();
      int width = getColumnHeaderWidth(table, column) + 4; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
         TableCellRenderer renderer = table.getCellRenderer(row, column);
         Component comp = table.prepareRenderer(renderer, row, column);
         width = Math.max(comp.getPreferredSize().width + 4, width);
      }
      if (width > 700)
         width = 700;
      columnModel.getColumn(column).setPreferredWidth(width);
   }

   public void resizeColumnWidthNoMax(JTable table) {
      for (int column = 0; column < table.getColumnCount(); column++) {
         resizeColumnWidthNoMax(table, column);
      }
   }

   public int[] getColumnsSizes(JTable table) {
      int numColumns = table.getColumnCount();
      TableColumnModel columnModel = table.getColumnModel();
      int[] sizes = new int[numColumns];
      for (int column = 0; column < numColumns; column++) {
         TableColumn tableCol = columnModel.getColumn(column);
         sizes[column] = tableCol.getWidth();
      }
      return sizes;
   }

   public int getColumnHeaderWidth(JTable table, int column) {

      TableColumn tableColumn = table.getColumnModel().getColumn(column);
      Object value = tableColumn.getHeaderValue();
      TableCellRenderer renderer = tableColumn.getHeaderRenderer();
      if (renderer == null) {
         renderer = table.getTableHeader().getDefaultRenderer();
      }
      Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, column);
      return c.getPreferredSize().width;
   }

   public void resizeColumnWidthNoMax(JTable table, int column) {
      final TableColumnModel columnModel = table.getColumnModel();
      int width = getColumnHeaderWidth(table, column) + 4; // Min width
      for (int row = 0; row < table.getRowCount(); row++) {
         TableCellRenderer renderer = table.getCellRenderer(row, column);
         Component comp = table.prepareRenderer(renderer, row, column);
         width = Math.max(comp.getPreferredSize().width + 4, width);
      }
      columnModel.getColumn(column).setPreferredWidth(width);
   }

   public void setColumnsSizes(JTable table, int[] sizes) {
      int numColumns = table.getColumnCount();
      TableColumnModel columnModel = table.getColumnModel();
      for (int column = 0; column < numColumns; column++) {
         TableColumn tableCol = columnModel.getColumn(column);
         tableCol.setPreferredWidth(sizes[column]);
      }
   }
}
