package pasa.cbentley.swing.run;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.swing.table.renderer.CellRendererInteger;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerHSB;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerString;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSB;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSL;
import pasa.cbentley.swing.window.CBentleyFrame;

public class RunTableColors extends RunSwingAbstract {

   public static void main(String[] args) {
      final RunTableColors runner = new RunTableColors();
      runner.initUIThreadOutside();
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            runner.initUIThreadInside();
         }
      });
   }

   public void cmdExit() {

   }

   protected void addI18n(List<String> list) {
   }

   protected void initOutsideUIForPrefs(IPrefs prefs) {

   }

   protected CBentleyFrame initUIThreadInsideSwing() {

      CBentleyFrame frame = new CBentleyFrame(sc);

      String[] columnNames = new String[] { "Number" };
      int size = 1000001;
      Object[][] data = new Object[size][1];
      for (int i = 0; i < size; i++) {
         data[i][0] = new Integer(i);
      }
      JTable table = new JTable(data, columnNames);
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
      table.setFillsViewportHeight(true);
      
      TableCellRenderer renderer = new CellRendererInteger(sc);
      TableCellRenderer renderer2 = new CellRendererIntegerHSB(sc);
      TableCellRenderer renderer3 = new CellRendererIntegerString(sc);
      TableCellRenderer renderer4 = new CellRendererIntegerStringHSB(sc);
      TableCellRenderer renderer5 = new CellRendererIntegerStringHSL(sc);
      table.getColumnModel().getColumn(0).setCellRenderer(renderer5);
      
      JScrollPane spane = new JScrollPane(table);
      JPanel pane = new JPanel();
      pane.setLayout(new BorderLayout());
      pane.add(spane, BorderLayout.CENTER);
      frame.getContentPane().add(pane);
      return frame;
   }

}
