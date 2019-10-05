package pasa.cbentley.swing.layout;

import java.awt.Component;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BPanel;
import pasa.dekholm.riverlayout.RiverLayout;

/**
 * Provides a {@link BPanel} with {@link RiverLayout} and method shortcuts.
 * 
 * @author Charles Bentley
 *
 */
public class RiverPanel extends BPanel implements IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = 1906733624018995283L;

   public RiverPanel(SwingCtx sc) {
      super(sc);
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
   public void raddBrHfillCenter(Component c) {
      this.add("br hfill center", c);
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

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "RiverPanel");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "RiverPanel");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
