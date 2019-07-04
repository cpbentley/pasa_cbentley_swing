package pasa.cbentley.swing.window;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Associated with a JFrame.
 * <br>
 * @author Charles Bentley
 *
 */
public class FrameScreenManager implements IStringable {

   private boolean       isFullScreen = false;

   private int           prevX, prevY, prevWidth, prevHeight;

   private CBentleyFrame f;

   private boolean       isOnlyHeight;

   private int           screenID;

   /**
    * The {@link CBentleyFrame} provides the context.
    * @param frame
    */
   public FrameScreenManager(CBentleyFrame frame) {
      f = frame;
   }

   /**
    * Used only when asked for fullscreen
    * @param id
    */
   public void setScreenID(int id) {
      screenID = id;
   }

   public void setOnlyHeight(boolean v) {
      isOnlyHeight = v;
   }

   public boolean isFullScreen() {
      return isFullScreen;
   }

   /**
    * 
    * @param v
    */
   public void setFullScreen(boolean v) {
      if (v) {
         if (!isFullScreen) {
            setFullScreen();
         }
      } else {
         if (isFullScreen) {
            exitFS();
         }
      }
   }

   public boolean toggleFullScreen() {
      if (isFullScreen == false) {
         setFullScreen();
         return true;
      } else {
         exitFS();
         return false;
      }
   }

   /**
    * 
    */
   protected void exitFS() {
      f.setVisible(true);
      f.setBounds(prevX, prevY, prevWidth, prevHeight);
      f.dispose();
      f.setUndecorated(false);
      f.setVisible(true);
      isFullScreen = false;
   }

   protected void setFullScreen() {
      saveFrameBounds();

      f.dispose(); //Destroys the whole JFrame but keeps organized every Component                               
                   //Needed if you want to use Undecorated JFrame
                   //dispose() is the reason that this trick doesn't work with videos
      f.setUndecorated(true);

      int fsWidth = prevWidth;
      int fsHeight = prevHeight;
      int fsX = 0;
      int fsY = 0;
      //decide on which screen to fullscreen
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      if (screenID >= 0 && screenID < gds.length) {
         Rectangle bounds = gds[screenID].getDefaultConfiguration().getBounds();
         int bx = (int) bounds.getX();
         int by = (int) bounds.getY();
         if (bx <= prevX && prevX <= bx + bounds.getWidth()) {
            if (by <= prevY && prevY <= by + bounds.getHeight()) {
               fsX = bx;
               fsY = by;
               fsWidth = (int) bounds.getWidth();
               fsHeight = (int) bounds.getHeight();
            }
         }
      } else {
         //check relative to x,y position
         for (int i = 0; i < gds.length; i++) {
            GraphicsDevice gd = gds[i];
            Rectangle bounds = gd.getDefaultConfiguration().getBounds();
            int bx = (int) bounds.getX();
            int by = (int) bounds.getY();
            if (bx <= prevX && prevX <= bx + bounds.getWidth()) {
               if (by <= prevY && prevY <= by + bounds.getHeight()) {
                  fsX = bx;
                  fsY = by;
                  fsWidth = (int) bounds.getWidth();
                  fsHeight = (int) bounds.getHeight();
                  break;
               }
            }
         }
      }
      if (isOnlyHeight) {
         fsWidth = prevWidth;
      }
      f.setBounds(fsX, fsY, fsWidth, fsHeight);
      f.setVisible(true);
      isFullScreen = true;
   }

   public void saveFrameBounds() {
      prevX = f.getX();
      prevY = f.getY();
      prevWidth = f.getWidth();
      prevHeight = f.getHeight();
   }

   public int getPrevX() {
      return prevX;
   }

   public int getPrevY() {
      return prevY;
   }

   public int getPrevWidth() {
      return prevWidth;
   }

   public int getPrevHeight() {
      return prevHeight;
   }

   public int getScreenID() {
      return screenID;
   }

   /**
    * Returns the screen on which the Frame is located.
    * <br>
    * -1 if none.
    * @param frame
    * @return
    */
   public int getScreenIDFromXY() {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      for (int i = 0; i < gds.length; i++) {
         GraphicsDevice gd = gds[i];
         Rectangle bounds = gd.getDefaultConfiguration().getBounds();
         int bx = (int) bounds.getX();
         int by = (int) bounds.getY();
         if (bx <= f.getX() && f.getX() <= bx + bounds.getWidth()) {
            if (by <= f.getY() && f.getY() <= by + bounds.getHeight()) {
               return i;
            }
         }
      }
      return -1;
   }

   /**
    * If frame is not visible, move it to 0,0
    * @param frame
    */
   public void verifyAndMove() {
      int screenID = getScreenIDFromXY();
      if (screenID == -1) {
         f.setLocation(0, 0);
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "FrameScreenManager");
      dc.appendVarWithSpace("isFullscreen", isFullScreen);
      dc.appendVarWithSpace("isOnlyHeight", isOnlyHeight);
      dc.appendVarWithSpace("screenID", screenID);
      dc.nl();
      dc.appendVarWithSpace("prevX", prevX);
      dc.appendVarWithSpace("prevY", prevY);
      dc.appendVarWithSpace("prevWidth", prevWidth);
      dc.appendVarWithSpace("prevHeight", prevHeight);
      dc.nlLvl(f);
   }

   public UCtx toStringGetUCtx() {
      return f.getSc().getUCtx();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FrameScreenManager");
      dc.appendVarWithSpace("isFullscreen", isFullScreen);
      dc.appendVarWithSpace("isOnlyHeight", isOnlyHeight);
      dc.appendVarWithSpace("screenID", screenID);
      dc.appendVarWithSpace("prevX", prevX);
      dc.appendVarWithSpace("prevY", prevY);
      dc.appendVarWithSpace("prevWidth", prevWidth);
      dc.appendVarWithSpace("prevHeight", prevHeight);
   }

   //#enddebug

}