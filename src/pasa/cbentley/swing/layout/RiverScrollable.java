package pasa.cbentley.swing.layout;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.dekholm.riverlayout.RiverLayout;

public class RiverScrollable extends JScrollPane {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private SwingCtx sc;

   private RiverPanel   panel;

   public RiverScrollable(SwingCtx sc) {
      this.sc = sc;
      panel = new RiverPanel(sc);
      
      setViewportView(panel);
   }
   public void raddBrHfill(Component c) {
      panel.raddBrHfill(c);
   }
   
   public void raddP(Component c) {
      panel.raddP(c);
   }
   public void raddBr(Component c) {
      panel.raddBr(c);
   }

   public void radd(Component c) {
      panel.radd(c);
   }
   
   public void raddVfill(Component c) {
      panel.raddVfill(c);
   }
   
   public void raddHfill(Component c) {
      panel.raddHfill(c);
   }
   
   public void raddTab(Component c) {
      panel.raddTab(c);
   }
}
