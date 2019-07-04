//package pasa.cbentley.swing.image.anim.ver1;
//
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.swing.JComponent;
//
//import pasa.cbentley.core.src4.ctx.UCtx;
//import pasa.cbentley.core.src4.interfaces.ICallBack;
//import pasa.cbentley.core.src4.interfaces.ITechTransform;
//import pasa.cbentley.core.src4.logging.Dctx;
//import pasa.cbentley.core.src4.logging.IDLog;
//import pasa.cbentley.core.src4.logging.IStringable;
//import pasa.cbentley.core.src4.thread.AbstractBRunnable;
//import pasa.cbentley.core.src4.thread.IBRunnable;
//import pasa.cbentley.core.src4.thread.IBRunnableListener;
//import pasa.cbentley.core.src4.thread.ITechRunnable;
//import pasa.cbentley.core.src4.thread.old.IMRunnable;
//import pasa.cbentley.swing.ctx.SwingCtx;
//import pasa.cbentley.swing.images.anim.ImageFrame;
//
///**
// * Each {@link ImageComponent} has its own thread?
// * 
// * TODO option to use a single thread that control several
// * 
// * <br>
// * Transition effects
// * @author Charles Bentley
// *
// */
//public class ImageComponent extends JComponent implements MouseListener, ITechRunnable, MouseWheelListener, IStringable, KeyListener, IBRunnableListener, ICallBack {
//
//   /**
//    * 
//    */
//   private static final long serialVersionUID = 2845905827158978898L;
//
//   private AnimRunner        anim;
//
//   private ImageAnim         ia;
//
//   private String            loadingMessage;
//
//   private Dimension         preferredSize;
//
//   private SwingCtx          sc;
//
//   private int               transform;
//
//   private boolean           isShowData;
//
//   public ImageComponent(SwingCtx sc) {
//      this.sc = sc;
//      preferredSize = new Dimension(200, 200);
//      this.addMouseListener(this);
//      this.addMouseWheelListener(this);
//      this.addKeyListener(this);
//   }
//
//   public void callBack(Object o) {
//      sc.execute(new Runnable() {
//         public void run() {
//            ImageComponent.this.repaint();
//         }
//      });
//   }
//
//   public void cmdRandom() {
//      synchronized (ia) {
//         ia.randomStep();
//      }
//   }
//
//   public void cmdIsInverse(boolean b) {
//      synchronized (ia) {
//         ia.setGoingDown(b);
//      }
//   }
//
//   public void cmdIsUpAndDown(boolean b) {
//      synchronized (ia) {
//         ia.setUpAndDown(b);
//      }
//   }
//
//   public void cmdFramePrev() {
//      synchronized (ia) {
//         ia.prevStep();
//         this.repaint();
//      }
//   }
//
//   public void cmdFrameNext() {
//      synchronized (ia) {
//         ia.nextStep();
//         this.repaint();
//      }
//   }
//
//   public void cmdFaster() {
//      if (anim != null) {
//         float f = anim.getSpeedModifier();
//         f -= 0.15f;
//         anim.setSpeedModifier(f);
//         //skip current frame. user wants it faster
//         cmdForward();
//      }
//   }
//
//   /**
//    * Interrupts current anim frame and force the next one
//    */
//   public void cmdForward() {
//      if (anim != null) {
//         if (anim.getThread() != null) {
//            //anim.requestNewState(IBRunnable.STATE_5_INTERRUPTED);
//            anim.getThread().interrupt();
//         }
//      }
//   }
//
//   public void cmdLoop() {
//
//   }
//
//   public void cmdPause() {
//      if (anim != null) {
//         anim.requestNewState(STATE_1_PAUSED);
//      } else {
//
//      }
//   }
//
//   public void cmdPlay() {
//      if (anim != null) {
//         //if anim was stopped
//         if (anim.getState() == ITechRunnable.STATE_1_PAUSED) {
//            anim.requestNewState(ITechRunnable.STATE_0_RUNNING);
//         } else {
//            if (anim.getState() == ITechRunnable.STATE_6_FINISHED) {
//               setImage(ia);
//            }
//            //start 
//         }
//      }
//   }
//
//   public void cmdSlower() {
//      if (anim != null) {
//         float f = anim.getSpeedModifier();
//         f += 0.15f;
//         anim.setSpeedModifier(f);
//      }
//   }
//
//   public void cmdStop() {
//      if (anim != null) {
//         anim.requestNewState(STATE_3_STOPPED);
//         synchronized (ia) {
//            ia.reset();
//         }
//      } else {
//
//      }
//   }
//
//   public AnimRunner createAnimRunner() {
//      AnimRunner anim = new AnimRunner(sc);
//      anim.addListener(this);
//      anim.setCallback(this);
//      return anim;
//   }
//
//   public Dimension getPreferredSize() {
//      return preferredSize;
//   }
//
//   public int getTransform() {
//      return transform;
//   }
//
//   public void keyPressed(KeyEvent e) {
//      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//
//      }
//   }
//
//   public void keyReleased(KeyEvent e) {
//
//   }
//
//   public void keyTyped(KeyEvent e) {
//
//   }
//
//   public void mouseClicked(MouseEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mouseClicked", IDLog.LVL_04_FINER, true);
//   }
//
//   public void mouseEntered(MouseEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mouseEntered", IDLog.LVL_04_FINER, true);
//   }
//
//   public void mouseExited(MouseEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mouseExited", IDLog.LVL_04_FINER, true);
//   }
//
//   public void mousePressed(MouseEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mousePressed", IDLog.LVL_04_FINER, true);
//
//      if (e.getButton() == MouseEvent.BUTTON1) {
//         //pause un pause
//      }
//   }
//
//   public void mouseReleased(MouseEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mouseReleased", IDLog.LVL_04_FINER, true);
//   }
//
//   public void mouseWheelMoved(MouseWheelEvent e) {
//      //#debug
//      toDLog().pFlow(sc.toSD().d1(e), this, ImageComponent.class, "mouseWheelMoved", IDLog.LVL_04_FINER, true);
//
//      if (e.getWheelRotation() > 0) {
//         ia.nextStep();
//      } else {
//         ia.prevStep();
//      }
//      this.repaint();
//   }
//
//   /**
//    * Transform may change preferred size
//    */
//   protected void paintComponent(Graphics g) {
//      super.paintComponent(g);
//      Rectangle clipBounds = g.getClipBounds();
//      if (ia == null) {
//         if (loadingMessage != null) {
//            g.drawString(loadingMessage, 40, 40);
//         } else {
//            g.drawString("No Image", 40, 40);
//         }
//      } else {
//         ImageFrame frame = ia.getCurrentFrame();
//         BufferedImage currentImg = ia.getCurrentImg();
//         if (currentImg != null) {
//            sc.getDU().drawRegion((Graphics2D) g, currentImg, 0, 0, currentImg.getWidth(), currentImg.getHeight(), transform, clipBounds.x, clipBounds.y);
//         } else {
//            g.drawString("Null Image", 40, 40);
//         }
//         int x = 5;
//         int y = 30;
//         g.drawString("#"+ia.getStep(), x, y);
//         y+= g.getFontMetrics().getHeight();
//         g.drawString(""+frame.getDisposal(), x, y);
//         y+= g.getFontMetrics().getHeight();
//         g.drawString(""+frame.getDelay(), x, y);
//      }
//     
//   }
//
//   public void runnerException(IBRunnable runner, Throwable e) {
//      //be careful. we are in the runner thread
//
//   }
//
//   public void runnerNewState(IBRunnable runner, int newState) {
//      //be careful. we are in the runner thread
//
//      //#debug
//      toDLog().pFlow("newState=" + AbstractBRunnable.toStringState(newState), this, ImageComponent.class, "runnerNewState", IDLog.LVL_05_FINE, true);
//
//      sc.execute(new Runnable() {
//         public void run() {
//            ImageComponent.this.repaint();
//         }
//      });
//   }
//
//   /**
//    * Sets the ImageAnim and start playing it
//    * @param ia
//    */
//   public void setImage(ImageAnim ia) {
//      this.ia = ia;
//      if (ia != null) {
//         preferredSize = new Dimension(ia.getCurrentImg().getWidth(), ia.getCurrentImg().getHeight());
//
//         //#debug
//         toDLog().pFlow("", this, ImageComponent.class, "setImage", IDLog.LVL_05_FINE, false);
//
//         AnimRunner anim = createAnimRunner();
//         anim.setImage(ia);
//         anim.setCallback(this);
//         this.anim = anim;
//         sc.getExecutorService().execute(anim);
//      }
//   }
//
//   public void setLoadingMessage(String message) {
//      ia = null;
//      this.loadingMessage = message;
//   }
//   
//   /**
//    * Each frame may have a transition effect applied
//    */
//   public void setTransitionEffects() {
//      
//   }
//
//   public void cmdShowDataToggle() {
//      isShowData = !isShowData;
//   }
//
//   /**
//    * Update preferred size
//    * @param transform
//    */
//   public void setTransform(int transform) {
//      if (ia != null) {
//         switch (transform) {
//            case ITechTransform.TRANSFORM_4_MIRROR_ROT270:
//            case ITechTransform.TRANSFORM_5_ROT_90:
//            case ITechTransform.TRANSFORM_6_ROT_270:
//            case ITechTransform.TRANSFORM_7_MIRROR_ROT90:
//               preferredSize = new Dimension(ia.getCurrentImg().getHeight(), ia.getCurrentImg().getWidth());
//               break;
//            default:
//               preferredSize = new Dimension(ia.getCurrentImg().getWidth(), ia.getCurrentImg().getHeight());
//               break;
//         }
//      }
//      this.transform = transform;
//   }
//
//   //#mdebug
//   public IDLog toDLog() {
//      return sc.toDLog();
//   }
//
//   public String toString() {
//      return Dctx.toString(this);
//   }
//
//   public void toString(Dctx dc) {
//      dc.root(this, "ImageComponent");
//      dc.appendVarWithSpace("loadingMessage", loadingMessage);
//      dc.appendVarWithSpace("transform", transform);
//      dc.appendVarWithSpace("pw", preferredSize.width);
//      dc.appendVarWithSpace("ph", preferredSize.height);
//      dc.nlLvlTitleIfNull(ia, "ImageAnim");
//      dc.nlLvlTitleIfNull(anim, "AnimRunnable");
//
//   }
//
//   public String toString1Line() {
//      return Dctx.toString1Line(this);
//   }
//
//   public void toString1Line(Dctx dc) {
//      dc.root1Line(this, "ImageComponent");
//      sc.toSD().d1((JComponent) this, dc);
//   }
//
//   public UCtx toStringGetUCtx() {
//      return sc.getUCtx();
//   }
//   //#enddebug
//
//}
