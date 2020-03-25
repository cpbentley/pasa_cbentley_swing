package pasa.cbentley.swing.imytab;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * A {@link FrameReference} that uses {@link FrameIMyTab}
 * 
 * @author Charles Bentley
 *
 */
public abstract class FrameReferenceTabAbstract extends FrameReference {

   public FrameReferenceTabAbstract(SwingCtx sc) {
      super(sc);
   }

   /**
    * Custom frame and launch, then shows the frame on screen
    */
   public void showFrame() {
      if (getFrame() == null) {
         IMyTab tab = createTab();
         FrameIMyTab frameTab = new FrameIMyTab(tab); //deal with tab dispose auto
         //so the reference to be set to null when closed
         frameTab.setFrameReference(this);
         customizeFrame(frameTab);
         customizeLaunch(frameTab);
         this.setFrame(frameTab);
      } else {
         getFrame().setVisible(true);
      }
   }

   /**
    * Current {@link FrameIMyTab}
    * @return
    */
   public FrameIMyTab getFrameIMyTab() {
      return (FrameIMyTab) getFrame();
   }

   /**
    * Allows the customization of the position and the size
    * @param f
    */
   protected void customizeLaunch(FrameIMyTab f) {
      sc.showInNewFrame(f, 0.6f, 0.9f);
   }

   /**
    * Gives the opportunity of the implementation to set its own icon
    * @param f
    */
   protected abstract void customizeFrame(FrameIMyTab f);

   /**
    * Creates the {@link IMyTab} that will live inside the {@link FrameIMyTab}
    * @return
    */
   protected abstract IMyTab createTab();
}
