package pasa.cbentley.swing.glasspanes;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.panels.MemoryPanelProgressBar;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Fill alpha color all over the screen, except on the area defined by the Shape.
 * 
 * <li>implicit hide when mouse is outside a hole
 * <li>explicit out.. interface component or F1 toggle for going out
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class DrawDarkPixelPane extends JComponent implements IStringable, ComponentListener {

   /**
    * 
    */
   private static final long serialVersionUID = 247131934824785979L;

   public static void main(String[] args) {
      UCtx uc = new UCtx();
      C5Ctx c5 = new C5Ctx(uc);
      final SwingCtx sc = new SwingCtx(c5);
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            final CBentleyFrame frame = new CBentleyFrame(sc);
            frame.setTitle("DarkPixel Pane Demo");

            //use our own exit procedure
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            //default is borderlayout
            JPanel panelNorth = new JPanel();

            final MemoryPanelProgressBar memPanel = new MemoryPanelProgressBar(sc);
            memPanel.setName("Memory Panel");
            panelNorth.add(memPanel);

            final DrawDarkPixelPane dpane = new DrawDarkPixelPane(sc);
            PaneMouseEventDispatcher ed = new PaneMouseEventDispatcher(frame, dpane);

            frame.setGlassPane(dpane);

            WidgetPeeringHole holeMemPanel = new WidgetPeeringHole();
            holeMemPanel.setMessage("Your mouse is over the memory panel");
            holeMemPanel.setComponent(memPanel);
            PaneMouseListener pml = new PaneMouseListener(sc, dpane, memPanel, holeMemPanel);

            JPanel panelSouth = new JPanel();
            JButton butReload = new JButton("Reload");
            butReload.setToolTipText("Reload ToolTip");
            butReload.addActionListener(new ActionListener() {

               public void actionPerformed(ActionEvent e) {
                  int res = JOptionPane.showConfirmDialog(frame, "Do you want to hide the glass pane?");
                  if (res == JOptionPane.OK_OPTION) {
                     dpane.hideGlassPane();
                  } else {

                  }
               }
            });
            butReload.setName("Reload Button");
            WidgetPeeringHole holeButReload = new WidgetPeeringHole();
            holeButReload.setMessage("Your mouse is over the reload button");
            holeButReload.setComponent(butReload);
            PaneMouseListener butReloadPaneMouseListener = new PaneMouseListener(sc, dpane, butReload, holeButReload);

            panelSouth.add(butReload);

            JPanel panelCenter = new JPanel();
            panelCenter.setPreferredSize(new Dimension(600, 600));

            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.pack();
            frame.positionFrame();

            frame.setVisible(true);
         }
      });
   }

   private List<WidgetPeeringHole> listOfHoles = new ArrayList<WidgetPeeringHole>(3);

   private SwingCtx                sc;

   private int                     w;

   private int                     h;

   private BufferedImage           bi;

   private Graphics2D              g2d;

   public void clearHoles() {
      listOfHoles.clear();
   }

   public void addHole(WidgetPeeringHole hole) {
      listOfHoles.add(hole);
   }

   public boolean isHoled(Component c) {
      for (WidgetPeeringHole hole : listOfHoles) {
         if (hole.getComponent().equals(c)) {
            return true;
         }
      }
      return false;
   }

   public DrawDarkPixelPane(SwingCtx sc) {
      this.sc = sc;
      this.addComponentListener(this);
   }

   public void initSize() {
      int nw = getSize().width;
      int nh = getSize().height;
      if (w != nw || h != nh) {
         w = nw;
         h = nh;
         //this create an image for every repaint
         bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
         g2d = bi.createGraphics();
      }

   }

   public void hideGlassPane() {
      this.setVisible(false);
      this.clearHoles();
      this.repaint();
   }

   AlphaComposite compositeClear   = AlphaComposite.getInstance(AlphaComposite.CLEAR, 1.0f);

   //animating from 0.0 to 0.4 over 1000 millis?
   AlphaComposite compositeSrcOver = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);

   AlphaComposite compositeSrcOpaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

   
   
   protected void paintComponent(Graphics g) {
      Graphics2D component2DGraphics = (Graphics2D) g;

      //first clear everything
      g2d.setComposite(compositeClear);
      g2d.fillRect(0, 0, getWidth(), getHeight());

      g2d.setComposite(compositeSrcOver);
      g2d.setColor(Color.black);
      g2d.fillRect(0, 0, getWidth(), getHeight());

      for (WidgetPeeringHole hole : listOfHoles) {
         Shape clearShape = hole.getHoleShape();
         //#debug
         //toDLog().pDraw("Drawing Shape="+sc.toSD().d1(clearShape), this, DrawDarkPixelPane.class, "paintComponent", IDLog.LVL_05_FINE, true);
         if (clearShape != null) {
            g2d.setColor(Color.red);
            g2d.setComposite(compositeClear);
            g2d.fill(clearShape);
         }
      }

      for (WidgetPeeringHole hole : listOfHoles) {
         String message = hole.getMessage();
         Point messagePoint = hole.getMessagePoint();
         //then we 
         if (message != null) {
            g2d.setComposite(compositeSrcOpaque);
            g2d.setColor(Color.WHITE);
            int x = messagePoint.x;
            int y = messagePoint.y;
            Rectangle2D rect = g2d.getFont().getStringBounds(message, g2d.getFontRenderContext());
            g2d.fillRect(x, y, rect.getBounds().width, rect.getBounds().height);
            g2d.setColor(Color.BLACK);
            g2d.drawString(message, x, y + g2d.getFontMetrics().getAscent());
         }
      }

      component2DGraphics.drawImage(bi, null, 0, 0);

   }

   public boolean isOverHole(WidgetPeeringHole holeCompo) {
      return true;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "DrawDarkPixelPane");
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DrawDarkPixelPane");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

   public void componentResized(ComponentEvent e) {
      initSize();
   }

   public void componentMoved(ComponentEvent e) {
   }

   public void componentShown(ComponentEvent e) {
   }

   public void componentHidden(ComponentEvent e) {
   }

}
