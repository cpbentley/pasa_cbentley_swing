package pasa.cbentley.swing.layout;

import java.awt.Component;

import javax.swing.JPanel;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.dekholm.riverlayout.RiverLayout;

public class RiverPanel extends JPanel {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   protected SwingCtx sc;

   public RiverPanel(SwingCtx sc) {
      this.sc = sc;
      this.setLayout(new RiverLayout());
   }

   public void raddP(Component c) {
      this.add("p", c);
   }
   public void raddBr(Component c) {
      this.add("br", c);
   }
   public void raddBrHfill(Component c) {
      this.add("br hfill", c);
   }
   public void radd(Component c) {
      this.add("", c);
   }
   
   public void raddVfill(Component c) {
      this.add("vfill", c);
   }
   
   public void raddHfill(Component c) {
      this.add("hfill", c);
   }
   
   public void raddTab(Component c) {
      this.add("tab", c);
   }
}
