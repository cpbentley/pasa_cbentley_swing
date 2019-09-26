package pasa.cbentley.swing.run;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.swing.imytab.TabbedBentleyPanelComp;
import pasa.cbentley.swing.panels.tools.TabTableUIData;
import pasa.cbentley.swing.window.CBentleyFrame;

public class RunUIDataTable extends RunSwingAbstract {

   public static void main(String[] args) {
      final RunUIDataTable runner = new RunUIDataTable();
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

      TabbedBentleyPanelComp tabbedPanel = new TabbedBentleyPanelComp(sc, "root_uidata");
      
      TabTableUIData table = new TabTableUIData(sc);
      table.setFilterType(0);
      tabbedPanel.addTabToArray(table);
      
      TabTableUIData table2 = new TabTableUIData(sc);
      table2.setFilterType(1);
      tabbedPanel.addTabToArray(table2);
      
      TabTableUIData table3 = new TabTableUIData(sc);
      table3.setFilterType(2);
      tabbedPanel.addTabToArray(table3);
     
      
      JPanel pane = new JPanel();
      pane.setLayout(new BorderLayout());
      pane.add(tabbedPanel, BorderLayout.CENTER);

      tabbedPanel.initCheck();

      frame.getContentPane().add(pane);

      return frame;
   }

}
