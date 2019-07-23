package pasa.cbentley.swing.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JTable;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.model.ModelTableBAbstract;

/**
 * Used on the header of {@link TableBentley}.
 * <br>
 * <br>
 * @author Charles Bentley
 *
 */
public class MouseEventsModelListener implements IStringable, MouseListener, MouseMotionListener, MouseWheelListener {

   private int                 currentCol = -1;

   private ModelTableBAbstract model;

   private SwingCtx            sc;

   private JTable              table;

   private String              tipGlobal  = "";

   public MouseEventsModelListener(SwingCtx sc, JTable table, ModelTableBAbstract model) {
      this.sc = sc;
      this.table = table;
      this.model = model;

   }

   protected String buildToolTip(String input) {
      String toolTip = "<html><p width=\"500\">" + input + "<br>" + tipGlobal + "</p></html>";
      return toolTip;
   }

   public void mouseClicked(MouseEvent e) {
      int col = table.columnAtPoint(e.getPoint());
      if (e.getButton() == MouseEvent.BUTTON3) {
         sc.getTU().resizeColumnWidth(table, col);
         table.revalidate();
         table.repaint();
         String name = table.getColumnName(col);
         //#debug
         //toDLog().pEvent(name + " column resized", null, PascalSwingCtx.class, "mouseClicked", IDLog.LVL_05_FINE, true);
         //psc.getLog().consoleLog(name + " column resized");
      }
   }

   public void mouseDragged(MouseEvent e) {
      int col = table.columnAtPoint(e.getPoint());
      //#debug
      //toDLog().pEvent(col + " column", null, MouseEventsModelListener.class, "mouseDragged", IDLog.LVL_04_FINER, true);

   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseMoved(MouseEvent e) {
      int col = table.columnAtPoint(e.getPoint()); //could be -1 if result is not in range
      //#debug
      //toDLog().pEvent(col + " column", null, MouseEventsModelListener.class, "mouseMoved", IDLog.LVL_04_FINER, true);

      if (currentCol != col && col >= 0) {
         currentCol = col;
         String tip = model.getToolTips(col);
         String finalTip = buildToolTip(tip);
         table.getTableHeader().setToolTipText(finalTip);
      }
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseWheelMoved(MouseWheelEvent e) {
      int col = table.columnAtPoint(e.getPoint());
      //#debug
      //toDLog().pEvent(col + " column", null, MouseEventsModelListener.class, "mouseWheelMoved", IDLog.LVL_04_FINER, true);

   }

   public void setGlobalTip(String tipGlobal) {
      this.tipGlobal = tipGlobal;
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MouseEventsModelListener");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MouseEventsModelListener");
   }

   //#enddebug

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

}
