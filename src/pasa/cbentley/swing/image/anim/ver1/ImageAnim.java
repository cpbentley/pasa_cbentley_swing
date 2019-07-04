//package pasa.cbentley.swing.image.anim.ver1;
//
//import java.awt.image.BufferedImage;
//import java.util.Random;
//
//import pasa.cbentley.core.src4.ctx.UCtx;
//import pasa.cbentley.core.src4.logging.Dctx;
//import pasa.cbentley.core.src4.logging.IDLog;
//import pasa.cbentley.core.src4.logging.IStringable;
//import pasa.cbentley.swing.ctx.SwingCtx;
//import pasa.cbentley.swing.images.anim.ImageFrame;
//
///**
// * 
// * @author Charles Bentley
// *
// */
//public class ImageAnim implements IStringable {
//
//   private ImageFrame[] frames;
//
//   private boolean      isInverse;
//
//   private boolean      isUpAndDown;
//
//   /**
//    * Starts at zero
//    */
//   private int          step;
//
//   private SwingCtx     sc;
//
//   private String       source;
//
//   public String getSource() {
//      return source;
//   }
//
//   public void setSource(String source) {
//      this.source = source;
//   }
//
//   public ImageAnim(SwingCtx sc, ImageFrame[] frames) {
//      this.sc = sc;
//      this.frames = frames;
//   }
//
//   public void reset() {
//      step = 0;
//   }
//
//   /**
//    * Delay in deci seconds
//    * @return
//    */
//   public int getCurrentDelay() {
//      int stepVal = step;
//      if (frames != null && stepVal >= 0 && stepVal < frames.length) {
//         return frames[stepVal].getDelay();
//      }
//      return 0;
//   }
//
//   public ImageFrame getCurrentFrame() {
//      if (frames != null && step >= 0 && step < frames.length) {
//         return frames[step];
//      }
//      return null;
//   }
//
//   public BufferedImage getCurrentImg() {
//      if (frames != null && step >= 0 && step < frames.length) {
//         return frames[step].getImage();
//      }
//      return null;
//   }
//
//   public BufferedImage getImg(int step) {
//      if (frames != null && step >= 0 && step < frames.length) {
//         return frames[step].getImage();
//      }
//      return null;
//   }
//
//   public int getStep() {
//      return step;
//   }
//
//   public boolean isGoingDown() {
//      return isInverse;
//   }
//
//   public boolean isUpAndDown() {
//      return isUpAndDown;
//   }
//
//   /**
//    * Read animation for next frame.
//    */
//   public void nextStep() {
//      if (isInverse) {
//         step--;
//      } else {
//         step++;
//      }
//      if (step == frames.length) {
//         if (isUpAndDown) {
//            isInverse = true;
//            step = frames.length - 1;
//         } else {
//            step = 0;
//         }
//      } else if (step <= -1) {
//         if (isUpAndDown) {
//            isInverse = false;
//            step = 0;
//         } else {
//            //reverse case
//            step = frames.length - 1;
//         }
//      }
//
//   }
//
//   public void prevStep() {
//      if (isInverse) {
//         step++;
//      } else {
//         step--;
//      }
//      if (step == frames.length) {
//         if (isUpAndDown) {
//            isInverse = true;
//            step = frames.length - 1;
//         } else {
//            step = 0;
//         }
//      } else if (step <= -1) {
//         if (isUpAndDown) {
//            isInverse = false;
//            step = 0;
//         } else {
//            //reverse case
//            step = frames.length - 1;
//         }
//      }
//   }
//
//   public void randomStep() {
//      step = new Random().nextInt(frames.length);
//   }
//
//   /**
//    * Starts at the end of the frames
//    * @param isGoingDown
//    */
//   public void setGoingDown(boolean isGoingDown) {
//      this.isInverse = isGoingDown;
//   }
//
//   public void setUpAndDown(boolean isUpAndDown) {
//      this.isUpAndDown = isUpAndDown;
//   }
//
//   //#mdebug
//   public String toString() {
//      return Dctx.toString(this);
//   }
//
//   public void toString(Dctx dc) {
//      dc.root(this, "ImageAnim");
//   }
//
//   public String toString1Line() {
//      return Dctx.toString1Line(this);
//   }
//
//   public IDLog toDLog() {
//      return sc.toDLog();
//   }
//
//   public void toString1Line(Dctx dc) {
//      dc.root1Line(this, "ImageAnim");
//      dc.appendVarWithSpace("#frames", frames.length);
//      dc.appendVarWithSpace("isInverse", isInverse);
//      dc.appendVarWithSpace("isUpAndDown", isUpAndDown);
//      dc.appendVarWithSpace("step", step);
//      dc.appendVarWithSpace("source", source);
//      //
//      dc.append("Frames");
//      dc.tab();
//      for (int i = 0; i < frames.length; i++) {
//         dc.nlLvlOneLine(i + ":", frames[i]);
//      }
//      dc.tabRemove();
//   }
//
//   public UCtx toStringGetUCtx() {
//      return sc.getUCtx();
//   }
//   //#enddebug
//
//}
