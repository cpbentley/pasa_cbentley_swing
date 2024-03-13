package pasa.cbentley.swing.glasspanes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Redirect mouse events, only to components currently holed.
 * 
 * @author Charles Bentley
 *
 */
public class PaneMouseEventDispatcher implements MouseListener, IStringable {

   private DrawDarkPixelPane dpane;

   private CBentleyFrame     frame;

   private SwingCtx          sc;

   public PaneMouseEventDispatcher(CBentleyFrame frame, DrawDarkPixelPane dpane) {
      this.sc = frame.getSc();
      this.frame = frame;
      this.dpane = dpane;
      dpane.addMouseListener(this);
   }

   public void mouseClicked(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mouseDragged(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mouseEntered(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mouseExited(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mouseMoved(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mousePressed(MouseEvent e) {
      redispatchMouseEvent(e, false);
   }

   public void mouseReleased(MouseEvent e) {
      redispatchMouseEvent(e, true);
   }

   //A basic implementation of redispatching events.
   private void redispatchMouseEvent(MouseEvent e, boolean repaint) {
      //#debug
      toDLog().pFlow("" + frame.getSc().toSD().d1(e), null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", ITechLvl.LVL_05_FINE, true);

      Point glassPanePoint = e.getPoint();
      Container container = frame.getContentPane();
      Point containerPoint = SwingUtilities.convertPoint(dpane, glassPanePoint, container);
      if (containerPoint.y < 0) { //we're not in the content pane
         JMenuBar menuBar = frame.getJMenuBar();
         if (menuBar != null && containerPoint.y + menuBar.getHeight() >= 0) {
            //The mouse event is over the menu bar.
            //Could handle specially.
         } else {
            //The mouse event is over non-system window 
            //decorations, such as the ones provided by
            //the Java look and feel.
            //Could handle specially.
         }
      } else {
         //The mouse event is probably over the content pane.
         //Find out exactly which component it's over.  
         Component component = SwingUtilities.getDeepestComponentAt(container, containerPoint.x, containerPoint.y);

         if (component != null) {
            if (dpane.isHoled(component)) {
               //#debug
               toDLog().pFlow("Component is     holed " + sc.toSD().d1(component), null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", ITechLvl.LVL_05_FINE, true);
               //Forward events over the check box.
               Point componentPoint = SwingUtilities.convertPoint(dpane, glassPanePoint, component);
               component.dispatchEvent(new MouseEvent(component, e.getID(), e.getWhen(), e.getModifiers(), componentPoint.x, componentPoint.y, e.getClickCount(), e.isPopupTrigger()));
               //#debug
               //toDLog().pFlow("Component Point=" + componentPoint.x + " " + componentPoint.y, null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", IDLog.LVL_05_FINE, true);
               //toDLog().pFlow("GlassPane Point=" + glassPanePoint.x + " " + glassPanePoint.y, null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", IDLog.LVL_05_FINE, true);
               //Point glassPanePointBijective = SwingUtilities.convertPoint(component, componentPoint, dpane);
               //#debug
               //toDLog().pFlow("Component           Point=" + componentPoint.x + " " + componentPoint.y, null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", IDLog.LVL_05_FINE, true);
               //toDLog().pFlow("GlassPane Bijective Point=" + glassPanePointBijective.x + " " + glassPanePointBijective.y, null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", IDLog.LVL_05_FINE, true);

            } else {
               //#debug
               toDLog().pFlow("Component is NOT holed " + sc.toSD().d1(component), null, PaneMouseEventDispatcher.class, "redispatchMouseEvent", ITechLvl.LVL_05_FINE, true);
            }
         }
      }

      //Update the glass pane if requested.
      if (repaint) {
         dpane.repaint();
      }
   }

   //#mdebug
   private IDLog toDLog() {
      return frame.getSc().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "PaneMouseEventDispatcher");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PaneMouseEventDispatcher");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }


   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug
}
