package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Encapsulates a possibly null reference to a {@link CBentleyFrame}
 * 
 * When null, frame is deemed inactive. 
 * 
 * {@link FrameReference#isInactive()}
 * 
 * @author Charles Bentley
 *
 */
public class FrameReference implements IStringable {

   protected final SwingCtx sc;

   private CBentleyFrame    frame;

   public FrameReference(SwingCtx sc) {
      this.sc = sc;
   }

   public CBentleyFrame getFrame() {
      return frame;
   }

   public boolean isInactive() {
      return frame == null;
   }

   public boolean isActive() {
      return frame != null;
   }

   public void showFrame() {
      if (frame != null) {
         frame.setVisible(true);
      }
   }

   public void closeFrame() {
      if (frame != null) {
         frame.setVisible(false);
      }
   }

   public void setFrame(CBentleyFrame frame) {
      this.frame = frame;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FrameReference");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FrameReference");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   //#enddebug

}
