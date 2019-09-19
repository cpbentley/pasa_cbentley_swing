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
import pasa.cbentley.swing.imytab.IMyGui;

public class CBentleyFrame extends JFrame implements IStringable, IMyGui, WindowListener, WindowFocusListener {
   public static final int    EVENT_ID_0_ANY       = 0;

   public static final int    EVENT_ID_1_CLOSE     = 1;

   public static final String PREF_MAIN_FULLSCREEN = "fullscreen";

   public static final String PREF_MAIN_H          = "h";

   public static final String PREF_MAIN_SCREENID   = "screenid";

   public static final String PREF_MAIN_W          = "w";

   public static final String PREF_MAIN_X          = "x";

   public static final String PREF_MAIN_Y          = "y";

   public static final String PREF_PREFIX          = "frame";

   public static final int    PRODUCER_ID_2        = 2;

   /**
    * 
    */
   private static final long  serialVersionUID     = -5719466067073255318L;

   private String             pid                  = "";

   private SwingCtx           sc;

   private FrameScreenManager screenManager;

   public CBentleyFrame(SwingCtx sc) {
      this.sc = sc;
      screenManager = new FrameScreenManager(this);
      sc.addAllFrames(this);
      sc.guiRegister(this);
      this.addWindowFocusListener(this);
      this.addWindowListener(this);
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
      screenManager = new FrameScreenManager(this);
      sc.addAllFrames(this);
      this.addWindowFocusListener(this);
      this.addWindowListener(this);
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

   /**
    * Position the Frame using {@link SwingCtx#getPrefs()}
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

   }

   /**
    * TODO Close this frame. If single last, exits the
    * The main window won't be called here.
    * 
    * Subclass implements specific behavior
    */
   public void cmdClose() {
      
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
      if (this.isFullScreen()) {
         prefs.putBoolean(getKeyFullscreen(), true);
         prefs.putInt(getKeyScreenID(), screenManager.getScreenID());

      } else {
         prefs.putInt(getKeyMainX(), this.getX());
         prefs.putInt(getKeyMainY(), this.getY());
         prefs.putInt(getKeyMainW(), this.getWidth());
         prefs.putInt(getKeyMainH(), this.getHeight());
      }
   }

   /**
    * When no special task required. Equivalent to {@link JFrame#EXIT_ON_CLOSE}
    */
   public void setDefExitProcedure() {
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
   }

   public void setExitable(final IExitable ex) {
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            ex.cmdExit();
         }
      });
   }

   public void setFullScreenTrue() {
      screenManager.setFullScreen(true);
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
      dc.root(this, "CBentleyFrame");
      dc.appendVarWithSpace("pid", pid);
      sc.toSD().toStringFrame(this, dc);
      dc.nlLvl(screenManager);
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

   public void windowClosing(WindowEvent e) {

   }

   public void windowDeactivated(WindowEvent e) {

   }

   public void windowDeiconified(WindowEvent e) {

   }

   public void windowIconified(WindowEvent e) {

   }

   public void windowOpened(WindowEvent e) {

   }

   public void windowGainedFocus(WindowEvent e) {

   }

   public void windowLostFocus(WindowEvent e) {

   }

}
