package pasa.cbentley.swing.imytab;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

public class FrameReference {

   protected final SwingCtx sc;

   private CBentleyFrame     frame;

   public FrameReference(SwingCtx sc) {
      this.sc = sc;
   }

   public CBentleyFrame getFrame() {
      return frame;
   }

   public void setFrame(CBentleyFrame frame) {
      this.frame = frame;
   }

}
