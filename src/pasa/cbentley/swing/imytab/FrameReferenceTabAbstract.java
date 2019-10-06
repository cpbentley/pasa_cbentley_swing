package pasa.cbentley.swing.imytab;

import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class FrameReferenceTabAbstract extends FrameReference {

   public FrameReferenceTabAbstract(SwingCtx sc) {
      super(sc);
   }

   public void showFrame() {
      if (getFrame() == null) {
         IMyTab about = createTab();
         FrameIMyTab frameTab = new FrameIMyTab(about); //deal with tab dispose auto
         //so the reference to be set to null when closed
         frameTab.setFrameReference(this);
         customizeFrame(frameTab);
         customizeLaunch(frameTab);
         this.setFrame(frameTab);
      } else {
         getFrame().setVisible(true);
      }
   }

   public FrameIMyTab getFrameIMyTab() {
      return (FrameIMyTab)getFrame();
   }
   protected void customizeLaunch(FrameIMyTab f) {
      sc.showInNewFrame(f, 0.6f, 0.9f);
   }

   protected abstract void customizeFrame(FrameIMyTab f);

   protected abstract IMyTab createTab();
}
