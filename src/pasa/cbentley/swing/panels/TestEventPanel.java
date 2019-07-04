package pasa.cbentley.swing.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.window.CBentleyFrame;

public class TestEventPanel extends JPanel implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private SwingCtx sc;

   private JButton  butNorth;

   private JButton  butCenter;

   private JButton  butSouth;

   private JButton  butLeft;

   private JButton  butRight;

   public TestEventPanel(SwingCtx sc) {
      this.sc = sc;

      this.setLayout(new BorderLayout());

      butNorth = new JButton("North");
      butCenter = new JButton("Center");
      butSouth = new JButton("South");
      butLeft = new JButton("Left");
      butRight = new JButton("Right");

      this.add(butNorth, BorderLayout.NORTH);
      this.add(butCenter, BorderLayout.CENTER);
      this.add(butSouth, BorderLayout.SOUTH);
      this.add(butLeft, BorderLayout.WEST);
      this.add(butRight, BorderLayout.EAST);

      butCenter.addActionListener(this);
      butNorth.addActionListener(this);
      butLeft.addActionListener(this);
      butRight.addActionListener(this);
      butSouth.addActionListener(this);

      this.addAncestorListener(new AncestorListener() {
         public void ancestorRemoved(AncestorEvent event) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "ancestorRemoved", ITechLvl.LVL_04_FINER, true);
         }

         public void ancestorMoved(AncestorEvent event) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "ancestorMoved", ITechLvl.LVL_04_FINER, true);
         }

         public void ancestorAdded(AncestorEvent event) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "ancestorAdded", ITechLvl.LVL_04_FINER, true);
         }
      });

      this.addComponentListener(new ComponentListener() {

         public void componentShown(ComponentEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentShown", ITechLvl.LVL_04_FINER, true);
         }

         public void componentResized(ComponentEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentResized", ITechLvl.LVL_04_FINER, true);
         }

         public void componentMoved(ComponentEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentMoved", ITechLvl.LVL_04_FINER, true);
         }

         public void componentHidden(ComponentEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentHidden", ITechLvl.LVL_04_FINER, true);
         }
      });

      this.addContainerListener(new ContainerListener() {

         public void componentRemoved(ContainerEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentRemoved", ITechLvl.LVL_04_FINER, true);
         }

         public void componentAdded(ContainerEvent e) {
            //#debug
            toDLog().pFlow("", null, TabbedBentleyPanel.class, "componentAdded", ITechLvl.LVL_04_FINER, true);
         }
      });
   }

   public void validate() {
      //#debug
      toDLog().pFlow("", null, TestEventPanel.class, "validate", ITechLvl.LVL_03_FINEST, true);
      super.validate();
   }

   protected void validateTree() {
      //#debug
      toDLog().pFlow("", null, TestEventPanel.class, "validateTree", ITechLvl.LVL_03_FINEST, true);
      super.validateTree();
   }
   
   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }
   //#enddebug
   
   public void actionPerformed(ActionEvent e) {
      //#debug
      toDLog().pFlow("" + e.getSource(), null, TestEventPanel.class, "actionPerformed", ITechLvl.LVL_03_FINEST, true);
   }
   
   public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            UCtx uc = new UCtx();
            //#debug
            uc.toDLog().getDefault().getConfig().setLevelGlobal(ITechLvl.LVL_03_FINEST);
            
            SwingCtx sc = new SwingCtx(uc);
            CBentleyFrame rootFrame = new CBentleyFrame(sc);
            rootFrame.setDefExitProcedure();
            TestEventPanel panel = new TestEventPanel(sc);
            rootFrame.getContentPane().add(panel);
            rootFrame.pack();
            rootFrame.positionFrame();

         }
      });

   }
}
