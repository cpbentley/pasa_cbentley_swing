package pasa.cbentley.swing.widgets.b;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.interfaces.IMenuSwing;

public class BPopupMenu extends JPopupMenu implements IMyGui, PopupMenuListener, IMenuSwing {

   protected final SwingCtx sc;

   private ActionListener   list;

   private JTable           table;

   public ActionListener getActionListener() {
      return list;
   }

   public BPopupMenu(SwingCtx sc, ActionListener list, PopupMenuListener popupMenuListener) {
      this.sc = sc;
      this.list = list;
      this.addPopupMenuListener(popupMenuListener);
   }

   public BPopupMenu(SwingCtx sc, JTable table) {
      this.sc = sc;
      this.table = table;
      this.addPopupMenuListener(this);
   }

   public void popupMenuCanceled(PopupMenuEvent e) {
   }

   public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
   }

   public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(BPopupMenu.this, new Point(0, 0), table));
            if (rowAtPoint > -1) {
               table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
            }
         }
      });
   }

   public void guiUpdate() {
      sc.guiUpdateOnChildren(this);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BPopupMenu");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BPopupMenu");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}
