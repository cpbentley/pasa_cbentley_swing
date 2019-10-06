package pasa.cbentley.swing.ctx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.window.CBentleyFrame;

public class SwingCtxFrames implements IStringable {

   private ArrayList<CBentleyFrame> allFrames = new ArrayList<CBentleyFrame>();

   protected final SwingCtx         sc;

   public SwingCtxFrames(SwingCtx sc) {
      this.sc = sc;
   }

   public void addFrame(CBentleyFrame f) {
      if (!allFrames.contains(f)) {
         allFrames.add(f);
      }
   }

   public List<CBentleyFrame> getAllFrames() {
      return Collections.unmodifiableList(allFrames);
   }

   public CBentleyFrame getFirstActive() {
      for (CBentleyFrame frame : allFrames) {
         if (frame.isActive()) {
            return frame;
         }
      }
      return allFrames.get(0);
   }

   /**
    * Number of frames currently visible
    * @return
    */
   public int getNumVisible() {
      int count = 0;
      for (CBentleyFrame frame : allFrames) {
         if (frame.isVisible()) {
            count++;
         }
      }
      return count;
   }

   public CBentleyFrame getFrameMainFirst() {
      for (CBentleyFrame frame : allFrames) {
         if (frame.isMainFrame()) {
            return frame;
         }
      }
      return null;
   }

   /**
    * Call {@link IMyGui#guiUpdate()} on all registered components and on active {@link CBentleyFrame}
    * <br>
    * <br>
    * 
    * <br>
    * Refreshes the state
    */
   public void guiUpdate() {
      for (CBentleyFrame frame : allFrames) {
         if(frame.getJMenuBar() != null) {
            sc.guiUpdateOnChildrenMenuBar(frame.getJMenuBar());
         }
         frame.guiUpdate();
      }
   }

   public boolean removeFrame(JFrame f) {
      return allFrames.remove(f);
   }

   public void revalidateSwingTree() {
      for (CBentleyFrame frame : allFrames) {
         frame.invalidate();
         frame.repaint();
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingCtxFrames");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingCtxFrames");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   /**
    * String of gui update registered object
    * @param dc
    */
   public void toStringGuiUpdate(Dctx dc) {
      dc.append("Frames #" + allFrames.size());
      for (JFrame frame : allFrames) {
         if (frame instanceof CBentleyFrame) {
            dc.nlLvl((CBentleyFrame) frame);
         } else {
            //print with utility method
            sc.toSD().toStringFrame(frame, dc.nLevel());
         }
      }
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("numVisible", getNumVisible());
   }

   //#enddebug

}
