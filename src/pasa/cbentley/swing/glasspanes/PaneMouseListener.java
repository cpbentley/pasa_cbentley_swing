package pasa.cbentley.swing.glasspanes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Provides automatic glasspane appearance on mouse entry.
 * And disappearance on mouse exit.
 * 
 * When other actions such a button presses should hide the glasspane, it must be done
 * manually by action.
 * <br>
 * <br>
 * The exit cmd might be conditional to another event.
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class PaneMouseListener implements MouseListener, MouseMotionListener {

   private SwingCtx          sc;

   private DrawDarkPixelPane dpane;

   private JComponent        c;

   WidgetPeeringHole         holeCompo;

   public PaneMouseListener(SwingCtx sc, DrawDarkPixelPane dpane, JComponent c, WidgetPeeringHole hole) {
      this.sc = sc;
      this.dpane = dpane;
      this.c = c;
      this.holeCompo = hole;
      this.c.addMouseListener(this);
      this.c.addMouseMotionListener(this);
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
      //#debug
      sc.toDLog().pTest("", null, PaneMouseListener.class, "mousePressed", ITechLvl.LVL_05_FINE, true);
   }

   public void mouseReleased(MouseEvent e) {
      //#debug
      sc.toDLog().pTest("", null, PaneMouseListener.class, "mouseReleased", ITechLvl.LVL_05_FINE, true);
   }

   public void mouseEntered(MouseEvent e) {
      Point pd = SwingUtilities.convertPoint(c, 0, 0, dpane);

      Rectangle rectScreen = new Rectangle(pd);
      rectScreen.height = c.getHeight();
      rectScreen.width = c.getWidth();
      //#debug
      sc.toDLog().pTest("Shape is " + sc.toSD().d1(rectScreen), null, PaneMouseListener.class, "mouseEntered", ITechLvl.LVL_05_FINE, true);

      holeCompo.setHoleShape(rectScreen);
      //check if message will be out of frame.. then move it up
      int y = c.getY() + c.getHeight();
      if (y + c.getHeight() > dpane.getHeight()) {
         y = c.getY() - c.getHeight();
      }
      holeCompo.setMessagePoint(new Point(c.getX(), y));
      dpane.addHole(holeCompo);
      dpane.setVisible(true);
      dpane.repaint();
   }

   /**
    * The mouseExited will be called as soon as Mouse is moved once the Glasspane.
    * <br>
    * Therefore, we have to compute exit in the {@link PaneMouseEventDispatcher}
    */
   public void mouseExited(MouseEvent e) {
      //#debug
      sc.toDLog().pTest("", null, PaneMouseListener.class, "mouseExited", ITechLvl.LVL_05_FINE, true);

//      if (!dpane.isOverHole(holeCompo)) {
//         //we want an exit on the glasspane
//         dpane.hideGlassPane();
//      }
   }

   public void mouseDragged(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   public void mouseMoved(MouseEvent e) {
      //#debug
      sc.toDLog().pTest("" + sc.toSD().d1(e), null, PaneMouseListener.class, "mouseMoved", ITechLvl.LVL_05_FINE, true);
      Rectangle r = c.getBounds();
   }

}
