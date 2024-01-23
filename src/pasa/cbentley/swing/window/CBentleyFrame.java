package pasa.cbentley.swing.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.FrameReference;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.task.TaskExitHard;

/**
 * {@link JFrame} with support for
 * <li> X,Y and W,H values
 * 
 * @author Charles Bentley
 *
 */
public class CBentleyFrame extends JFrame implements IStringable, IMyGui, WindowListener, WindowFocusListener {
   public static final int          EVENT_ID_0_ANY       = 0;

   public static final int          EVENT_ID_1_CLOSE     = 1;

   public static final String       PREF_MAIN_FULLSCREEN = "fullscreen";

   public static final String       PREF_MAIN_H          = "h";

   public static final String       PREF_MAIN_SCREENID   = "screenid";

   public static final String       PREF_MAIN_W          = "w";

   public static final String       PREF_MAIN_X          = "x";

   public static final String       PREF_MAIN_Y          = "y";

   public static final String       PREF_PREFIX          = "frame";

   public static final int          PRODUCER_ID_2        = 2;

   /**
    * 
    */
   private static final long        serialVersionUID     = -5719466067073255318L;

   private boolean                  isMainFrame;

   private String                   pid                  = "";

   private SwingCtx                 sc;

   private final FrameScreenManager screenManager;

   private boolean                  isHardExitOnClose;

   public CBentleyFrame(SwingCtx sc) {
      this(sc, "");
   }

   /**
    * Registers the frame in the {@link SwingCtx}.
    * The frameID is used for 
    * <li> key for the title 
    * <li> save frame position and size in preferences
    * 
    * @param sc
    * @param frameID
    */
   public CBentleyFrame(SwingCtx sc, String frameID) {
      this.sc = sc;
      pid = frameID;

      sc.getFrames().addFrame(this);
      sc.guiRegister(this);

      screenManager = new FrameScreenManager(this);
      this.addWindowFocusListener(this);
      this.addWindowListener(this);
   }

   /**
    * Close this frame. If single last, exits the
    * The main window won't be called here.
    * 
    * Subclass implements specific behavior
    */
   public void cmdClose() {
      //effectively close it
      WindowEvent we = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
      this.dispatchEvent(we);
   }

   protected void closeCleanUp() {
      this.savePrefs();
      sc.guiRemove(this);
      sc.getFrames().removeFrame(this);
      sc.eventCloseThis(this);
   }

   private void cmdToggleFullScreen(JButton butTogFullScreen) {
      boolean isFull = screenManager.toggleFullScreen();
      if (isFull) {
         butTogFullScreen.setText("Normal");
      } else {
         butTogFullScreen.setText("Fullscreen");
      }
   }

   private String getKeyFullscreen() {
      return PREF_PREFIX + pid + PREF_MAIN_FULLSCREEN;
   }

   private String getKeyMainH() {
      return PREF_PREFIX + pid + PREF_MAIN_H;
   }

   private String getKeyMainW() {
      return PREF_PREFIX + pid + PREF_MAIN_W;
   }

   private String getKeyMainX() {
      return PREF_PREFIX + pid + PREF_MAIN_X;
   }

   private String getKeyMainY() {
      return PREF_PREFIX + pid + PREF_MAIN_Y;
   }

   private String getKeyScreenID() {
      return PREF_PREFIX + pid + PREF_MAIN_SCREENID;
   }

   public SwingCtx getSc() {
      return sc;
   }

   /**
    * Compute the title of the frame
    * @return
    */
   public String getTitleFrame() {
      return sc.getResString(pid + ".title");
   }

   public void guiUpdate() {
      //if title was set using key
      String title = getTitleFrame();
      this.setTitle(title);
      JMenuBar jm = this.getJMenuBar();
      if (jm instanceof IMyGui) {
         IMyGui gui = ((IMyGui) jm);
         gui.guiUpdate();
      }
      sc.guiUpdateOnChildren(this.getContentPane());
   }

   public boolean isFullScreen() {
      return screenManager.isFullScreen();
   }

   public boolean isMainFrame() {
      return isMainFrame;
   }

   /**
    * Position the Frame using {@link SwingCtx#getPrefs()}
    * 
    * TODO refactor to framePositionAndShow
    */
   public void positionFrame() {
      positionFrame(sc.getPrefs());
   }

   /**
    * This method must be used for plug in the frame into the {@link SwingCtx} framework.
    * <br>
    * <li>position frame
    * <li>make it visible
    * <li> Add to {@link SwingCtx} managed list
    * @param prefs
    */
   public void positionFrame(IPrefs prefs) {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int w = prefs.getInt(getKeyMainW(), 940);
      int h = prefs.getInt(getKeyMainH(), 600);
      int dx = screenSize.width / 2 - w / 2;
      int x = prefs.getInt(getKeyMainX(), dx);
      int dy = screenSize.height / 2 - h / 2;
      int y = prefs.getInt(getKeyMainY(), dy);

      //set location to screen
      boolean isFs = prefs.getBoolean(getKeyFullscreen(), false);
      int screenID = prefs.getInt(getKeyScreenID(), -1); //screenID is used for fullscreen
      if (isFs) {
         //check if screen is still available
         this.setFullScreenTrue(screenID);
         //make it visible auto
      } else {
         //check if x,y saved is matching the current screen configuration
         this.setLocation(x, y);
         this.setSize(w, h);
         screenManager.verifyAndMove();
      }
      this.setVisible(true);

      //#debug
      toDLog().pFlow("Frame shown and positioned", this, CBentleyFrame.class, "positionFrame@line212", LVL_05_FINE, true);
   }

   /**
    * Save frame preferences using {@link SwingCtx#getPrefs()}
    *
    */
   public void savePrefs() {
      savePrefs(sc.getPrefs());
   }

   /**
    * Saves frame position, size and other user properties to the {@link IPrefs}.
    * <br>
    * TODO how to make it easy and automatic
    * @param prefs
    */
   public void savePrefs(IPrefs prefs) {
      //#debug
      toDLog().pFlow("Before", prefs, CBentleyFrame.class, "savePrefs", LVL_04_FINER, false);

      if (this.isFullScreen()) {
         prefs.putBoolean(getKeyFullscreen(), true);
         prefs.putInt(getKeyScreenID(), screenManager.getScreenID());

      } else {
         prefs.putInt(getKeyMainX(), this.getX());
         prefs.putInt(getKeyMainY(), this.getY());
         prefs.putInt(getKeyMainW(), this.getWidth());
         prefs.putInt(getKeyMainH(), this.getHeight());
      }
      
      //#debug
      toDLog().pFlow("After", prefs, CBentleyFrame.class, "savePrefs", LVL_04_FINER, false);
   }

   /**
    * When no special task required. Equivalent to {@link JFrame#EXIT_ON_CLOSE}
    */
   public void setExitProcedureExit0() {
      isHardExitOnClose = true;
   }

   /**
    * Position the frame on the center of the main screen.
    */
   public void setFramePositionCenter() {
      int fw = this.getWidth();
      int fh = this.getHeight();
      Screen screen = sc.getUtils().getScreen(0);
      int nx = screen.getX() + (screen.getWidth() - fw) / 2;
      int ny = screen.getY() + (screen.getHeight() - fh) / 2;
      this.setLocation(nx, ny);
   }

   private FrameReference frameToShowOnClose;

   /**
    * On exit, shows this frames
    * @param frame
    */
   public void setFrameOnClose(final FrameReference frame) {
      frameToShowOnClose = frame;
   }

   /**
    * When no more frames are visible, the application exits.
    */
   private boolean isHeadlessAllowed = false;

   /**
    * Dock this frame to the {@link SwingCtx} exitable framework
    */
   public void setHeadlessAllowed() {
      isHeadlessAllowed = true;
   }

   public void setFullScreenTrue() {
      screenManager.setFullScreen(true);
   }

   public void setFullScreenFalse() {
      screenManager.setFullScreen(false);
   }

   /**
    * Set fullscreen on given screenid, if screen id is not available. do nothing.
    * 
    * @param screenid
    */
   public void setFullScreenTrue(int screenid) {
      screenManager.setScreenID(screenid);
      screenManager.setFullScreen(true);
   }

   /**
    * Flags this frame as main, which means that when it is closed,
    * the {@link IExitable} is executed and all frames are closed.
    * The application is exited.
    * 
    * False by default.
    * 
    * If no main frame is defined, Exitable will be called when all frames are closed
    */
   public void setMainFrame() {
      isMainFrame = true;
   }

   public void setPrefID(String pid) {
      if (pid != null) {
         this.pid = pid;
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, CBentleyFrame.class);
      dc.appendVarWithSpace("pid", pid);
      sc.toSD().toStringFrameNl(this, dc);
      dc.nlLvl(screenManager);
      dc.nlLvl(frameToShowOnClose, "frameToShowOnClose");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CBentleyFrame");
      sc.toSD().toStringFrame1Line(this, dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

   public void windowActivated(WindowEvent e) {

   }

   public void windowClosed(WindowEvent e) {

   }

   public boolean isHeadlessNotAllowed() {
      return !isHeadlessAllowed;
   }

   public void windowClosing(WindowEvent e) {
      //#debug
      toDLog().pFlow("isHardExitOnClose=" + isHardExitOnClose, this, CBentleyFrame.class, "windowClosing", LVL_05_FINE, true);
      closeCleanUp();

      //hard exist on close will override all others checks
      if (isHardExitOnClose) {
         //exits once the current queue of GUI events is finished
         sc.executeLaterInUIThread(new TaskExitHard());

      } else {

         if (frameToShowOnClose != null) {
            frameToShowOnClose.showFrame();
         }

         if (isHeadlessNotAllowed()) {
            //if 0 visible call the exit procecdure
            sc.executeLaterInUIThread(sc.getTaskExitSmoothIfNoFrames());
         }
      }
   }

   public void windowDeactivated(WindowEvent e) {

   }

   public void windowDeiconified(WindowEvent e) {

   }

   public void windowGainedFocus(WindowEvent e) {

   }

   public void windowIconified(WindowEvent e) {

   }

   public void windowLostFocus(WindowEvent e) {

   }

   public void windowOpened(WindowEvent e) {

   }

}
