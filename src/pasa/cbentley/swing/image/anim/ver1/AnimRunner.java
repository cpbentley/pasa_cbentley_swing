//package pasa.cbentley.swing.image.anim.ver1;
//
//import pasa.cbentley.core.src4.interfaces.ICallBack;
//import pasa.cbentley.core.src4.logging.Dctx;
//import pasa.cbentley.core.src4.logging.IDLog;
//import pasa.cbentley.core.src4.thread.AbstractBRunnable;
//import pasa.cbentley.core.src4.thread.IBProgessable;
//import pasa.cbentley.swing.ctx.SwingCtx;
//
///**
// * Image Animation that can be paused.
// * <br>
// * Uses an {@link IBProgessable} to notify progress of the animation.
// * <br>
// * Runs according to the {@link ImageAnim#nextStep()} method
// * @author Charles Bentley
// *
// */
//public class AnimRunner extends AbstractBRunnable {
//
//   private ICallBack cb;
//
//   private int       currentGIFDelay;
//
//   private ImageAnim ia;
//
//   private SwingCtx  sc;
//
//   private float     speedModifier = 1.0f;
//
//   public AnimRunner(SwingCtx sc) {
//      super(sc.getUCtx());
//      this.sc = sc;
//   }
//
//   public float getSpeedModifier() {
//      return speedModifier;
//   }
//
//   public void runAbstract() {
//      if (ia == null) {
//         return;
//      }
//      getThread().setName("AnimRunner");
//      while (isContinue()) {
//         try {
//
//            //start with the delay of the first frame
//            currentGIFDelay = (int) (ia.getCurrentDelay() * speedModifier);
//
//            long waitTime = currentGIFDelay * 10;
//            if (waitTime == 0) {
//               waitTime = 2; //minimum waiting time
//            }
//            Thread.sleep(waitTime);
//         } catch (InterruptedException e) {
//            //what should we do here? it depends.
//            //user could interrupt to make it run faster
//            //image a long running 
//
//            //#debug
//            sc.toDLog().pFlow("Sleep Interrupted", this, AnimRunner.class, "runAbstract", IDLog.LVL_05_FINE, true);
//         }
//
//         int frameNum = 0;
//         //sync on anim to modify its state
//         synchronized (ia) {
//            ia.nextStep(); //move index to the next frame
//            frameNum = ia.getStep();
//         }
//
//         //use a new event to notify it
//         if (cb != null) {
//            cb.callBack(frameNum);
//         }
//         //ask for a repaint of the component displaying
//         //sc.execute(r);
//      }
//   }
//
//   public void setCallback(ICallBack cb) {
//      this.cb = cb;
//   }
//
//   /**
//    * Once set, any thread that wants to modify its state must
//    * synchronize on {@link ImageAnim} instance.
//    * @param ia
//    */
//   public void setImage(ImageAnim ia) {
//      this.ia = ia;
//   }
//
//   public void setSpeedModifier(float f) {
//      this.speedModifier = f;
//   }
//
//   //#mdebug
//   public void toString(Dctx dc) {
//      dc.root(this, "Anim");
//      super.toString(dc.sup());
//   }
//
//   public void toString1Line(Dctx dc) {
//      dc.root1Line(this, "Anim");
//      super.toString1Line(dc.sup1Line());
//   }
//   //#enddebug
//
//}
