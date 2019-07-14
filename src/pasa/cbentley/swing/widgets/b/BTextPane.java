package pasa.cbentley.swing.widgets.b;

import javax.swing.JTextPane;

import pasa.cbentley.swing.ctx.SwingCtx;

public class BTextPane extends JTextPane {

   protected final SwingCtx sc;

   private boolean          wrapState = true;

   public BTextPane(SwingCtx sc) {
      this.sc = sc;
   }

   public boolean getScrollableTracksViewportWidth() {
      return wrapState;
   }

   public void setLineWrap(boolean wrap) {
      wrapState = wrap;
   }

   public boolean getLineWrap(boolean wrap) {
      return wrapState;
   }
}
