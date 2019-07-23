package pasa.cbentley.swing.table;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

/**
 *	Use a JTable as a renderer for row numbers of a given main table.
 *  This table must be added to the row header of the scrollpane that
 *  contains the main table.
 */
public class TableRowNumber extends JTable implements ChangeListener, PropertyChangeListener, TableModelListener {
   /**
    * 
    */
   private static final long serialVersionUID = 9112973383448504330L;

   private JTable            main;

   public TableRowNumber(JTable table) {
      main = table;
      main.addPropertyChangeListener(this);
      main.getModel().addTableModelListener(this);

      setFocusable(false);
      setAutoCreateColumnsFromModel(false);
      setSelectionModel(main.getSelectionModel());

      TableColumn column = new TableColumn();
      column.setHeaderValue(" ");
      addColumn(column);
      column.setCellRenderer(new CellRendererRowNumber());

      getColumnModel().getColumn(0).setPreferredWidth(50);
      setPreferredScrollableViewportSize(getPreferredSize());
   }

   /**
    * 
    */
   public void addNotify() {
      super.addNotify();
      Component c = getParent();
      //  Keep scrolling of the row table in sync with the main table.
      if (c instanceof JViewport) {
         JViewport viewport = (JViewport) c;
         viewport.addChangeListener(this);
      }
   }

   public int getRowCount() {
      return main.getRowCount();
   }

   public int getRowHeight(int row) {
      int rowHeight = main.getRowHeight(row);

      if (rowHeight != super.getRowHeight(row)) {
         super.setRowHeight(row, rowHeight);
      }

      return rowHeight;
   }

   /**
    *  No model is being used for this table so just use the row number
    *  as the value of the cell.
    */
   public Object getValueAt(int row, int column) {
      return Integer.toString(row + 1);
   }

   /**
    *  Don't edit data in the main TableModel by mistake
    */
   public boolean isCellEditable(int row, int column) {
      return false;
   }

   /**
    *  Do nothing since the table ignores the model
    */
   public void setValueAt(Object value, int row, int column) {
   }

   /**
    * Implement the {@link ChangeListener#stateChanged(ChangeEvent)}
    */
   public void stateChanged(ChangeEvent e) {
      //  Keep the scrolling of the row table in sync with main table

      JViewport viewport = (JViewport) e.getSource();
      JScrollPane scrollPane = (JScrollPane) viewport.getParent();
      scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
   }

   /**
    * Implement the {@link PropertyChangeListener#propertyChange(PropertyChangeEvent)}
    */
   public void propertyChange(PropertyChangeEvent e) {
      //  Keep the row table in sync with the main table

      if ("selectionModel".equals(e.getPropertyName())) {
         setSelectionModel(main.getSelectionModel());
      }

      if ("rowHeight".equals(e.getPropertyName())) {
         repaint();
      }

      if ("model".equals(e.getPropertyName())) {
         main.getModel().addTableModelListener(this);
         revalidate();
      }
   }

   /**
    * Implement the {@link TableModelListener#tableChanged(TableModelEvent)}
    */
   public void tableChanged(TableModelEvent e) {
      revalidate();
   }

}