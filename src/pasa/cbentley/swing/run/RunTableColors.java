package pasa.cbentley.swing.run;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.swing.table.renderer.CellRendererInteger;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerHSB;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerString;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSB;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSL;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringHSLPasc;
import pasa.cbentley.swing.table.renderer.CellRendererIntegerStringPascal;
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
      frame.savePrefs();
      System.exit(0);
   }

   protected void addI18n(List<String> list) {
   }

   protected void initOutsideUIForPrefs(IPrefs prefs) {

   }

   protected CBentleyFrame initUIThreadInsideSwing() {

      CBentleyFrame frame = new CBentleyFrame(sc);
      frame.setDefExitProcedure();

      final String[] columnNames = new String[] { "Number" };
      Object[][] data = null;
      boolean all = false;
      if (all) {
         data = populate5000000();
      } else {
         data = populateSmart();
      }
      final Object[][] rowData = data;

      AbstractTableModel model = new AbstractTableModel() {
         public String getColumnName(int column) {
            return columnNames[column].toString();
         }

         public int getRowCount() {
            return rowData.length;
         }

         public int getColumnCount() {
            return columnNames.length;
         }

         public Object getValueAt(int row, int col) {
            return rowData[row][col];
         }

         public boolean isCellEditable(int row, int column) {
            return false;
         }

         public void setValueAt(Object value, int row, int col) {
            rowData[row][col] = value;
            fireTableCellUpdated(row, col);
         }
      };

      final JTable table = new JTable(model);
     
      table.setPreferredScrollableViewportSize(new Dimension(500, 70));
      table.setFillsViewportHeight(true);

      TableCellRenderer renderer = new CellRendererInteger(sc);
      TableCellRenderer renderer2 = new CellRendererIntegerHSB(sc);
      TableCellRenderer renderer3 = new CellRendererIntegerString(sc);
      TableCellRenderer renderer4 = new CellRendererIntegerStringHSB(sc);
      TableCellRenderer renderer5 = new CellRendererIntegerStringHSL(sc);
      TableCellRenderer renderer6 = new CellRendererIntegerStringHSLPasc(sc);
      TableCellRenderer renderer7 = new CellRendererIntegerStringPascal(sc);
      table.getColumnModel().getColumn(0).setCellRenderer(renderer7);

      table.setRowMargin(0);
      table.setShowHorizontalLines(false); //???
      table.setShowVerticalLines(false); //???
      
      JScrollPane spane = new JScrollPane(table);
      JPanel pane = new JPanel();
      pane.setLayout(new BorderLayout());
      pane.add(spane, BorderLayout.CENTER);
      frame.getContentPane().add(pane);

      //Rectangle cellRect = table.getCellRect(500000, 0, true);
      //table.scrollRectToVisible(cellRect);

      table.addKeyListener(new KeyListener() {

         public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

         }

         public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

         }

         public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_1) {
               moveTo(100000);
            } else if (e.getKeyCode() == KeyEvent.VK_2) {
               moveTo(200000);
            } else if (e.getKeyCode() == KeyEvent.VK_3) {
               moveTo(300000);
            } else if (e.getKeyCode() == KeyEvent.VK_4) {
               moveTo(400000);
            } else if (e.getKeyCode() == KeyEvent.VK_5) {
               moveTo(500000);
            } else if (e.getKeyCode() == KeyEvent.VK_6) {
               moveTo(600000);
            } else if (e.getKeyCode() == KeyEvent.VK_7) {
               moveTo(700000);
            } else if (e.getKeyCode() == KeyEvent.VK_8) {
               moveTo(800000);
            } else if (e.getKeyCode() == KeyEvent.VK_9) {
               moveTo(900000);
            } else if (e.getKeyCode() == KeyEvent.VK_0) {
               moveTo(40000);
            } else if (e.getKeyCode() == KeyEvent.VK_HOME) {
               moveTo(0);
            } else if (e.getKeyCode() == KeyEvent.VK_END) {
               moveTo(999999);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
               moveTo(10000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
               moveTo(20000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
               moveTo(30000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
               moveTo(40000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD5) {
               moveTo(50000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
               moveTo(60000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
               moveTo(70000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
               moveTo(80000);
            } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
               moveTo(90000);
            } else if (e.getKeyCode() == KeyEvent.VK_F1) {
               moveTo(1000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F2) {
               moveTo(2000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F3) {
               moveTo(3000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F4) {
               moveTo(4000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F5) {
               moveTo(5000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F6) {
               moveTo(6000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F7) {
               moveTo(7000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F8) {
               moveTo(8000000);
            } else if (e.getKeyCode() == KeyEvent.VK_F9) {
               moveTo(9000000);
            }
         }

         public void moveTo(int value) {
            Rectangle cellRect = table.getCellRect(value, 0, true);
            table.scrollRectToVisible(cellRect);
         }
      });

      return frame;
   }

   private Object[][] populate5000000() {
      int size = 5000001;
      Object[][] data = new Object[size][1];
      for (int i = 0; i < size; i++) {
         data[i][0] = new Integer(i);
      }
      return data;
   }

   private Object[][] populateSmart() {

      ArrayList<Integer> data = new ArrayList<Integer>();

      int count = 0;
      for (int i = 0; i < 10; i++) {
         data.add(new Integer(i));
         count++;
      }
      boolean isFirstSkipped = true;
      while (count < 100) {
         if (isFirstSkipped) {
            count++;
         }

         data.add(new Integer(count++));
         data.add(new Integer(count++));
         count += 8;

         if (isFirstSkipped) {
            count--;
         }
      }

      while (count < 1000) {
         if (isFirstSkipped) {
            count++;
         }
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         count += 97;
         if (isFirstSkipped) {
            count--;
         }
      }

      while (count < 10000) {
         if (isFirstSkipped) {
            count++;
         }
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         if (isFirstSkipped) {
            count--;
         }
         count += 997;
      }

      while (count < 100000) {
         if (isFirstSkipped) {
            count++;
         }
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         if (isFirstSkipped) {
            count--;
         }
         count += 9997;
      }
      data.add(new Integer(86646));

      while (count < 500000) {
         if (isFirstSkipped) {
            count++;
         }
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         if (isFirstSkipped) {
            count--;
         }
         count += 99998;
      }

      data.add(new Integer(500001));
      data.add(new Integer(500002));

      data.add(new Integer(510001));
      data.add(new Integer(510002));
      data.add(new Integer(520001));
      data.add(new Integer(520002));
      data.add(new Integer(530001));
      data.add(new Integer(530002));
      data.add(new Integer(540001));
      data.add(new Integer(540002));

      data.add(new Integer(550001));
      data.add(new Integer(550002));
      data.add(new Integer(560001));
      data.add(new Integer(560002));
      data.add(new Integer(570001));
      data.add(new Integer(570002));
      data.add(new Integer(580001));
      data.add(new Integer(580002));
      data.add(new Integer(590001));
      data.add(new Integer(590002));

      count += 100000;

      //special case for 500 000
      while (count < 1000000) {
         if (isFirstSkipped) {
            count++;
         }
         data.add(new Integer(count++));
         data.add(new Integer(count++));
         if (isFirstSkipped) {
            count--;
         }
         count += 99998;
      }

      while (count < 10000000) {

         int subCount = 100000;
         //inside
         for (int i = 0; i < 9; i++) {
            //special case for 500 000
            while (count < 10000000 + subCount) {
               if (isFirstSkipped) {
                  count++;
               }
               data.add(new Integer(count++));
               data.add(new Integer(count++));
               if (isFirstSkipped) {
                  count--;
               }
               count += 99998;
            }
            subCount += 100000;
         }
      }

      int size = data.size();
      Object[][] datas = new Object[size][1];
      for (int i = 0; i < datas.length; i++) {
         datas[i][0] = data.get(i);
      }
      return datas;
   }

}
