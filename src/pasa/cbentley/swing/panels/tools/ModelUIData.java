package pasa.cbentley.swing.panels.tools;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.data.UIDataElement;
import pasa.cbentley.swing.model.ModelColumnBAbstract;
import pasa.cbentley.swing.model.ModelTableBAbstractWithColModel;

public class ModelUIData extends ModelTableBAbstractWithColModel<UIDataElement> {

   public ModelUIData(SwingCtx sc) {
      super(sc, 2);
      ModelColumnBAbstract columnModel = getColumnModel();
      columnModel.setKeyPrefixName("table.uidata.coltitle.");
      columnModel.setKeyPrefixTip("table.uidata.coltip.");

      columnModel.setInteger(0, "name");
      columnModel.setString(1, "value");

   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      UIDataElement object = getRow(rowIndex);
      if (object == null) {
         return null;
      }
      if (columnIndex == 0) {
         return object.getKey();
      }
      return object;
   }

   protected void computeStats(UIDataElement a, int row) {

   }

}
