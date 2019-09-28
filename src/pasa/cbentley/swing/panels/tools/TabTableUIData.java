package pasa.cbentley.swing.panels.tools;

import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.UIManager;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.data.UIData;
import pasa.cbentley.swing.data.UIDataElement;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.model.ModelTableBAbstract;
import pasa.cbentley.swing.table.AbstractTabTable;
import pasa.cbentley.swing.threads.PanelSwingWorker;

/**
 * 
 * TODO
 * 
 * Displays keys and values from {@link UIData} in a table
 * 
 * @author Charles Bentley
 *
 */
public class TabTableUIData extends AbstractTabTable<UIDataElement> {

   private int filterType;

   public TabTableUIData(SwingCtx sc) {
      super(sc, "uidata");
   }

   public TabTableUIData(SwingCtx sc, String id) {
      super(sc, id);
   }

   public void panelSwingWorkerDone(PanelSwingWorker worker) {

   }

   public void setFilterType(int filterType) {
      this.filterType = filterType;
   }

   public void panelSwingWorkerProcessed(PanelSwingWorker worker, int entryCount) {

   }

   public void panelSwingWorkerCancelled(PanelSwingWorker worker) {

   }

   protected void initTab() {
      //#debug
      toDLog().pFlow("", this, TabTableUIData.class, "initTab", LVL_05_FINE, true);
      super.initTab();
      
      getBenTable().setDefSort(0);
      getBenTable().setColumnRenderer(1, new CellRendererUIData(sc));
   }

   protected ModelTableBAbstract<UIDataElement> createTableModel() {
      ModelUIData model = new ModelUIData(sc);
      List<UIDataElement> elements = null;
      if(filterType == 1) {
         elements = sc.getUIData().getElementsColors();
      } else if(filterType == 2) {
            elements = sc.getUIData().getElementsFonts();
      } else {
         elements = sc.getUIData().getElementsAll();
      }
      for (UIDataElement ui : elements) {
         model.addRow(ui);
      }
      return model;

   }

   public void tabLostFocus() {
      //#debug
      toDLog().pFlow("", this, TabTableUIData.class, "tabLostFocus", LVL_05_FINE, true);
   }

   public void tabGainFocus() {
      //#debug
      toDLog().pFlow("", this, TabTableUIData.class, "tabGainFocus", LVL_05_FINE, true);
   }

}
