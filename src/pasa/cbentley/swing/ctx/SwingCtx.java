package pasa.cbentley.swing.ctx;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.EventBusArray;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.helpers.StringParametrized;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.SwingPrefs;
import pasa.cbentley.swing.SwingUtilsBentley;
import pasa.cbentley.swing.actions.IBackForwardable;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.cache.IIconCache;
import pasa.cbentley.swing.color.IntToColor;
import pasa.cbentley.swing.data.CombinedResourceBundle;
import pasa.cbentley.swing.data.UIData;
import pasa.cbentley.swing.data.UTF8Control;
import pasa.cbentley.swing.image.DrawUtils;
import pasa.cbentley.swing.imytab.BackForwardTabPage;
import pasa.cbentley.swing.imytab.FrameIMyTab;
import pasa.cbentley.swing.imytab.FrameReference;
import pasa.cbentley.swing.imytab.IMyGui;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.ITabMenuBarFactory;
import pasa.cbentley.swing.imytab.TabIconSettings;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.interfaces.ICallBackSwing;
import pasa.cbentley.swing.logging.SwingDebug;
import pasa.cbentley.swing.table.TableUtils;
import pasa.cbentley.swing.task.TaskExitSmoothIfNoFrames;
import pasa.cbentley.swing.task.TaskGuiUpdate;
import pasa.cbentley.swing.task.TaskUserLog;
import pasa.cbentley.swing.threads.PanelSwingWorker;
import pasa.cbentley.swing.utils.BufferedImageUtils;
import pasa.cbentley.swing.utils.ColorUtilsSwing;
import pasa.cbentley.swing.utils.SwingColorStore;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Plug
 * @author Charles Bentley
 *
 */
public class SwingCtx extends ACtx implements IStringable, ICtx, IEventsSwing, IEventConsumer {
   public static final char       DEF_CHECK             = '%';

   public static final String     PREF_LOCALE_COUNTRY   = "localecountry";

   public static final String     PREF_LOCALE_LANG      = "localelang";

   private IBackForwardable       backforwardable;

   private ExecutorService        backgroundExec;

   private BufferedImageUtils     bufferedImageUtils;

   private List<String>           bundleNames;

   private C5Ctx                  c5;

   private final int              defaultDismissTimeout = ToolTipManager.sharedInstance().getDismissDelay();

   private DrawUtils              du;

   private IEventBus              eventBusSwing;

   private SwingExecutor          executor;

   private IExitable              exitTask;

   public IExitable getExitTask() {
      return exitTask;
   }

   private TaskExitSmoothIfNoFrames taskExitSmooth;

   public TaskExitSmoothIfNoFrames getTaskExitSmoothIfNoFrames() {
      if (taskExitSmooth == null) {
         taskExitSmooth = new TaskExitSmoothIfNoFrames(this);
      }
      return taskExitSmooth;
   }

   private IMyTab                 focusedTab;

   /**
    * Never null. Dummy implementation does not any caching. Just creation
    */
   private IIconCache             iconCache;

   private IntToColor             intToColor;

   private boolean                isGlobalLabelTip      = true;

   private boolean                isResMissingLog       = true;

   private List<IMyGui>           listGuis              = new ArrayList<IMyGui>(3);

   private Locale                 locale;

   /**
    * Locale with all the Strings
    */
   private Locale                 localeFull;

   private Icon                   placeHolder;

   private IPrefs                 prefs;

   /**
    * This resource bundle allows for a root data of US locale data.
    * 
    * <br>
    * Each bundle afterwards overwrite keys with their values.
    * 
    * This means you can have 
    */
   private CombinedResourceBundle resBund;

   //#debug
   private IStringable            root;

   private StringBBuilder         sb;

   private SwingDebug             sd;

   private SwingCmds              swingCmds;

   private SwingColorStore        swingColorStore;

   private TabIconSettings        tabIcons;

   private ITabMenuBarFactory     tabMenuBarFactory;

   private int                    themeIDSound;

   private TableUtils             tu;

   private UIData                 uiData;

   private SwingUtilsBentley      utils;

   private SwingCtxFrames         frames;

   private ColorUtilsSwing        colorUtilsSwing;

   /**
    * Cannot create GUI elements. 
    * @param c5 {@link SwingCtx} is a src 5 compatible library
    */
   public SwingCtx(C5Ctx c5) {
      super(c5.getUCtx());
      this.c5 = c5;
      this.swingCmds = new SwingCmds(this);
      this.locale = Locale.getDefault();
      this.tu = new TableUtils(this);
      this.du = new DrawUtils(this);
      utils = new SwingUtilsBentley(this);

      frames = new SwingCtxFrames(this);

      int[] events = new int[BASE_EVENTS];
      events[PID_01_SWING] = EID_01_SWING_ZZ_NUM;
      events[PID_02_UI] = EID_02_UI_ZZ_NUM;
      events[PID_03_CMD] = EID_03_CMD_ZZ_NUM;
      eventBusSwing = new EventBusArray(uc, this, events);

      executor = new SwingExecutor(this);

      eventBusSwing.setExecutor(executor);
      uc.getEventBusRoot().setExecutor(executor);

      sb = new StringBBuilder(uc);

      swingColorStore = new SwingColorStore(this);

      //we want memory events
      uc.getEventBusRoot().addConsumer(this, PID_3_MEMORY, EID_MEMORY_0_ANY);

      //#debug
      sd = new SwingDebug(this);
   }

   public SwingCtxFrames getFrames() {
      return frames;
   }

   /**
    * Add JFrame to currently visible frames managed by this {@link SwingCtx}
    * @param f
    */
   public void addAllFrames(CBentleyFrame f) {
      frames.addFrame(f);
   }

   public void addI18NKey(ArrayList<String> list) {
      list.add("i18nSwing");
   }

   /**
    * Adds a mouse liste
    * @param c
    */
   public void addToolTipAccelerator(Component c, final int millis) {
      c.addMouseListener(new MouseAdapter() {

         public void mouseEntered(MouseEvent me) {
            ToolTipManager.sharedInstance().setDismissDelay(millis);
         }

         public void mouseExited(MouseEvent me) {
            ToolTipManager.sharedInstance().setDismissDelay(defaultDismissTimeout);
         }
      });
   }

   public String buildStringUISerial(char c1, String s2) {
      sb.reset();
      sb.append(c1);
      sb.append(s2);
      return sb.toString();
   }

   public String buildStringUISerial(String s1, String s2) {
      sb.reset();
      sb.append(s1);
      sb.append(s2);
      return sb.toString();
   }

   /**
    * Uses the {@link SwingCtx} {@link StringBBuilder} to build a string
    * 
    * Only called from the GUI thread.
    * @param s1
    * @param s2
    * @param s3
    * @return
    */
   public String buildStringUISerial(String s1, String s2, String s3) {
      sb.reset();
      sb.append(s1);
      sb.append(s2);
      sb.append(s3);
      return sb.toString();
   }

   public String buildStringUISerial(String s1, String s2, String s3, String s4) {
      sb.reset();
      sb.append(s1);
      sb.append(s2);
      sb.append(s3);
      sb.append(s4);
      return sb.toString();
   }

   public void callBackInMainThread(final ICallBackSwing callBack, final Object o) {
      execute(new Runnable() {

         public void run() {
            callBack.callBackInSwingThread(o);
         }
      });
   }

   /**
    * Exit clean up
    */
   public void cmdExit() {
      frames.savePrefs();
   }

   public void consumeEvent(BusEvent e) {
      if (e.getProducerID() == PID_3_MEMORY) {
         //#debug
         toDLog().pMemory("Memory Event", e, SwingCtx.class, "consumeEvent", LVL_05_FINE, true);
         if (e.getEventID() == EID_MEMORY_2_USER_REQUESTED_GC) {
            swingColorStore.clear();
         }
      }
   }

   public void copyStringToClipboard(String str) {
      //#debug
      toDLog().pFlow(str, null, SwingCtx.class, "copyStringToClipboard", ITechLvl.LVL_05_FINE, true);

      StringSelection selection = new StringSelection(str);
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      clipboard.setContents(selection, selection);
   }

   public Image createImage(String path, String description) {
      ImageIcon ii = this.createImageIcon(path, description);
      return getUtils().iconToImage(ii);
   }

   public Image createImageFromStream(String name) throws IOException {
      InputStream is = getResourceAsStream(name);
      if (is != null) {
         return ImageIO.read(is);
      } else {
         //#debug
         toDLog().pUI("Couldn't find file: " + name, null, SwingCtx.class, "createImageIcon", ITechLvl.LVL_09_WARNING, true);
         return null;
      }
   }

   /**
    * 
    * @param path
    * @param description
    * @return null if could not find an icon
    */
   public ImageIcon createImageIcon(String path, String description) {
      java.net.URL imgURL = getClass().getResource(path);
      if (imgURL != null) {
         return new ImageIcon(imgURL, description);
      } else {
         //#debug
         toDLog().pUI("Couldn't find file: " + path, null, SwingCtx.class, "createImageIcon", ITechLvl.LVL_09_WARNING, true);
         return null;
      }
   }

   /**
    * Look up in the cache
    * @param path
    * @param description
    * @return
    */
   public Icon createImageIconCache(String path, String description) {
      if (iconCache != null) {
         //TODO
         return iconCache.getIcon(path);
      } else {
         return createImageIcon(path, description);
      }
   }

   /**
    * Event generated when the user manually closes a {@link CBentleyFrame}.
    * @param frame
    */
   public void eventCloseThis(CBentleyFrame frame) {
      BusEvent be = this.getEventBusSwing().createEvent(CBentleyFrame.PRODUCER_ID_2, CBentleyFrame.EVENT_ID_1_CLOSE, frame);
      be.setUserEvent();
      be.putOnBus();
   }

   /**
    * Executes the {@link Runnable} in the AWT Event thread.
    * @param r
    */
   public void execute(Runnable r) {
      if (SwingUtilities.isEventDispatchThread()) {
         r.run();
      } else {
         SwingUtilities.invokeLater(r);
      }
   }

   public void executeLaterInUIThread(Runnable r) {
      SwingUtilities.invokeLater(r);
   }

   public void executeLaterInUIThread(final Runnable r, int millis) {
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {

         public void run() {
            SwingUtilities.invokeLater(r);

         }
      }, millis);
   }

   /**
    * Only one by {@link SwingCtx}.
    * 
    * When implementing 2 components with 2 navigation,
    * 
    * each component will have a different SwingCtx. Create a subclass of SwingCtx
    * that override this method.
    * 
    * TODO backstack framework like android.. tricky.
    * Several hierarchies of back/forward
    * @return
    */
   public BackForwardTabPage getBackForward() {
      return (BackForwardTabPage) backforwardable;
   }

   public IBackForwardable getBackforwardable() {
      return backforwardable;
   }

   public BufferedImageUtils getBufImgUtils() {
      if (bufferedImageUtils == null) {
         bufferedImageUtils = new BufferedImageUtils(this);
      }
      return bufferedImageUtils;
   }

   public C5Ctx getC5() {
      return c5;
   }

   public ColorUtilsSwing getColorUtilsSwing() {
      if (colorUtilsSwing == null) {
         colorUtilsSwing = new ColorUtilsSwing(this);
      }
      return colorUtilsSwing;
   }

   public String getClipboardString() {
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      String str = null;
      try {
         Object data = clipboard.getData(DataFlavor.stringFlavor);
         str = data.toString();
      } catch (UnsupportedFlavorException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return str;
   }

   public SwingCmds getCmds() {
      return swingCmds;
   }

   public DrawUtils getDU() {
      return du;
   }

   /**
    * Will manage events defined at the level of {@link UCtx}.
    * @return
    */
   public IEventBus getEventBusRoot() {
      return uc.getEventBusRoot();
   }

   /**
    * Manages events defined at the level of {@link SwingCtx}.
    * @return
    */
   public IEventBus getEventBusSwing() {
      return eventBusSwing;
   }

   /**
    * Returns the Utility executor for non GUI tasks.
    * @return
    */
   public ExecutorService getExecutorService() {
      if (backgroundExec == null) {
         backgroundExec = Executors.newCachedThreadPool();
      }
      return backgroundExec;
   }

   /**
    * Returns the currently User Focused {@link IMyTab}.
    * <br>
    * <br>
    * The tab that has user keyboard focus. 
    * Set
    * TODO make it contextual to a frame ?
    * @return null if none
    */
   public IMyTab getFocusedTab() {
      return focusedTab;
   }

   /**
    * 
    * @param key
    * @return
    */
   public File getFolder(String key) {
      String file = getPrefs().get("folder_" + key, System.getProperty("user.home"));
      File f = new File(file);
      if (f.exists() && f.isDirectory()) {
         return f;
      }
      return new File(System.getProperty("user.home"));
   }

   /**
    * Top level {@link JFrame} that owns this {@link Component}.
    * @param c
    * @return
    */
   public JFrame getFrame(Component c) {
      Component parent = c.getParent();
      while (parent != null && !(parent instanceof JFrame)) {
         parent = parent.getParent();
      }
      if (parent != null) {
         return (JFrame) parent;
      } else {
         return null;
      }
   }

   private Icon getIconPlaceHolder() {
      if (placeHolder == null) {
         placeHolder = createImageIcon("/icons/placeholder.png", null);
         //#mdebug
         if (placeHolder == null) {
            throw new NullPointerException("Place Holder must be defined");
         }
         //#enddebug
      }
      return placeHolder;
   }

   public int getIconSize(int iconSizeFrame) {
      if (iconSizeFrame == IconFamily.ICON_SIZE_FRAME) {
         return IconFamily.ICON_SIZE_0_SMALLEST;
      }
      return IconFamily.ICON_SIZE_0_SMALLEST;
   }

   public int getIconSizeActionDefault() {
      return IconFamily.ICON_SIZE_1_SMALL;
   }

   public IntToColor getIntToColor() {
      if (intToColor == null) {
         intToColor = new IntToColor(this);
      }
      return intToColor;
   }

   public Locale getLocale() {
      return locale;
   }

   /**
    * Never null
    * @return
    */
   public IUserLog getLog() {
      return uc.getUserLog();
   }

   /**
    * Ask Factories to create a MenuBar for this tab
    * 
    * Set it with {@link SwingCtx#setTabMenuBarFactory(ITabMenuBarFactory)}
    * 
    * @param tab
    * @return
    */
   public JMenuBar getMenuBar(IMyTab tab, CBentleyFrame frame) {
      if (tabMenuBarFactory != null) {
         return tabMenuBarFactory.getMenuBar(tab, frame);
      }
      return null;
   }

   /**
    * Returns the preference object for all the Swing related stuff.
    * <br>
    * 
    * <li>It will be used for Frame positions and dimensions
    * <li>Graphical settings
    * The pref must be initialized with {@link SwingCtx#setPrefs(IPrefs)}
    * <br>
    * 
    * @return
    */
   public IPrefs getPrefs() {
      if (prefs == null) {
         prefs = new SwingPrefs(this, Preferences.userNodeForPackage(SwingCtx.class));
      }
      return prefs;
   }

   /**
    * Never null
    * @return
    */
   public ResourceBundle getResBund() {
      return resBund;
   }

   /**
    * Icon whose path is located in the resource bundle.
    * Ignore theme.
    * @param key
    * @return
    */
   public Icon getResIcon(String key) {
      String iconPath = resBund.getString(key);
      if (iconPath != null) {
         return createImageIcon(iconPath, null);
      }
      return null;
   }

   /**
    * Getting the right icon
    * <code>  /icons/themeID/category_id_size.png </code>
    * <br>
    * Looks up the icon in the IconCache if cache is enabled
      @param id
    * @param category "tab" tab icons for instance
    * @return
    */
   public Icon getResIcon(String id, String category, int size) {
      return getResIcon(id, category, size, IconFamily.ICON_MODE_0_DEFAULT);
   }

   /**
    * TODO categories choose the IconSettings class, there is a class for
    * tabs,
    * menu action icons etc
    * @param id
    * @param category
    * @param size
    * @param mode
    * @return
    */
   public Icon getResIcon(String id, String category, int size, int mode) {
      //TODO find a way to disable logging when icons are not supposed to exist
      //#debug
      //toDLog().pUI("id=" + id + " category=" + category + " size=" + size + " mode=" + mode, null, SwingCtx.class, "getResIcon", LVL_04_FINER, true);

      //selector on the category.. then if null
      if (tabIcons == null) {
         //TODO what if this is intentional? We don't want logging
         //#debug
         //toDLog().pUI("TabIconSettings is null. Cannot fetch icons", this, SwingCtx.class, "getResIcon", LVL_09_WARNING, true);
         return null;
      } else {
         //first check if ID has a res for icon
         String str = buildStringUISerial(category, ".", id, ".icon");
         String resIcon = getResStringNull(str);
         if (resIcon != null) {
            //we have one. use it as alis
            id = resIcon;
         }
         String path = tabIcons.getPathIcon(id, category, size, mode);
         Icon ico = createImageIconCache(path, "");
         if (ico == null && mode != IconFamily.ICON_MODE_0_DEFAULT) {
            //look up def mode
            path = tabIcons.getPathIcon(id, category, size, IconFamily.ICON_MODE_0_DEFAULT);

            return createImageIconCache(path, "");
         }
         return ico;
      }
   }

   /**
    * Never null.
    * If place holder is not found, throw an exception
    * Icon whose path is located in the resource bundle.
    * Ignore theme.
    * @param key
    * @return
    */
   public Icon getResIconOrPlaceHolder(String key) {
      String iconPath = resBund.getString(key);
      Icon icon = null;
      if (iconPath != null) {
         icon = createImageIcon(iconPath, null);
      }
      if (icon == null) {
         icon = getIconPlaceHolder();
      }
      return icon;
   }

   public InputStream getResourceAsStream(String name) {
      return SwingCtx.class.getResourceAsStream(name);
   }

   public String getResSound(String key) {
      if (themeIDSound == 0) {
         return null;
      }
      try {
         return resBund.getString(key);
      } catch (MissingResourceException e) {
         //#debug
         toDLog().pSound1("key" + key + "themeID=" + themeIDSound + " " + e.getMessage(), this, SwingCtx.class, "getResSound");
         return null;
      }
   }

   public String getResSound(String interID, int categoryID, int selectorID) {

      return null;
   }

   /**
    * Null if not found
    * @param interID
    * @param category 
    * @param selector
    * @return null if none is defined
    */
   public String getResSound(String interID, String category, String selector) {
      if (themeIDSound == 0) {
         return null;
      }
      try {
         //String path = "/sounds/" + themeTitle + "/" + category + "_" + selector + "_" + ".ogg";

         //we could also use an indirection through the resource bundle
         //give sound internal id and it will return allo.wav
         return resBund.getString("tab." + interID + ".sound." + selector);
      } catch (MissingResourceException e) {
         return null;
      }
   }

   public String getResString(String key) {
      try {
         //#mdebug
         if (key == null) {
            throw new NullPointerException();
         }
         //#enddebug
         return resBund.getString(key);
      } catch (MissingResourceException e) {
         //check if we need to log this
         if (isResMissingLog) {
            getLog().consoleLogError("Cannot find String resource with ID:" + key);
         }
         return key;
      }
   }

   public String getResString(String key, char check, String param1) {
      String rootTitle = getResString(key);
      if (rootTitle != null && rootTitle.charAt(0) == check) {
         StringParametrized strp = new StringParametrized(uc);
         strp.setString(rootTitle.substring(1, rootTitle.length()));
         strp.setParam(buildStringUISerial(check, "1"), param1);
         return strp.getString();
      } else {
         return rootTitle;
      }
   }

   public String getResString(String key, char check, String param1, String param2) {
      String rootTitle = getResString(key);
      if (rootTitle != null && rootTitle.charAt(0) == check) {
         StringParametrized strp = new StringParametrized(uc);
         strp.setString(rootTitle.substring(1, rootTitle.length()));
         strp.setParam(buildStringUISerial(check, "1"), param1);
         strp.setParam(buildStringUISerial(check, "2"), param2);
         return strp.getString();
      } else {
         return rootTitle;
      }
   }

   public String getResString(String key, int param1) {
      return getResString(key, DEF_CHECK, String.valueOf(param1));
   }

   public String getResString(String key, int param1, int param2) {
      return getResString(key, DEF_CHECK, String.valueOf(param1), String.valueOf(param2));
   }

   /**
    * Null if none. used as a query. No error msg sent.
    * @param key
    * @return
    */
   public String getResStringNull(String key) {
      try {
         return resBund.getString(key);
      } catch (MissingResourceException e) {
         return null;
      }
   }

   /**
    * Only valid in the GUI thread
    * @return
    */
   public StringBBuilder getSBBuilder() {
      sb.reset();
      return sb;
   }

   public SwingColorStore getSwingColorStore() {
      return swingColorStore;
   }

   public SwingExecutor getSwingExecutor() {
      return executor;
   }

   public TabIconSettings getTabIcons() {
      return tabIcons;
   }

   public ITabMenuBarFactory getTabMenuBarFactory() {
      return tabMenuBarFactory;
   }

   public TableUtils getTU() {
      return tu;
   }

   public UCtx getUCtx() {
      return uc;
   }

   public UIData getUIData() {
      if (uiData == null) {
         uiData = new UIData(this);
      }
      return uiData;
   }

   public SwingUtilsBentley getUtils() {
      return utils;
   }

   /**
    * Register GUI.
    * <br>
    * <br>
    * <li>Root panels? If not inside a {@link CBentleyFrame}
    * <li>MenuBars? If not inside a {@link CBentleyFrame}
    * <li> {@link CBentleyFrame}
    * <br>
    * <br>
    * 
    * @param gui
    */
   public void guiRegister(IMyGui gui) {
      listGuis.add(gui);
   }

   public void guiRemove(IMyGui gui) {
      listGuis.remove(gui);
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
      //we log the whole pattern conditionally in its own buffer. collapsed into 1 line
      //
      //#debug
      toDLog().methodStart(SwingCtx.class, "guiUpdate", ITechLvl.LVL_05_FINE);

      //#debug
      //toDLog().pFlow(toStringGuiUpdate(), null, SwingCtx.class, "guiUpdate", ITechLvl.LVL_05_FINE, true);

      for (IMyGui gui : listGuis) {
         gui.guiUpdate();
      }
      
      frames.guiUpdate();

      //#debug
      toDLog().methodEnd(SwingCtx.class, "guiUpdate", ITechLvl.LVL_05_FINE);
      //end of log
   }

   public void guiUpdate(IMyTab tab) {
      if (tab instanceof IMyGui) {
         ((IMyGui) tab).guiUpdate();
      }
   }

   
   private TaskGuiUpdate taskGuiUpdate;

   public TaskGuiUpdate getTaskGuiUpdate() {
      if (taskGuiUpdate == null) {
         taskGuiUpdate = new TaskGuiUpdate(this);
      }
      return taskGuiUpdate;
   }

   /**
    * Creates a runnable for Gui update later
    */
   public void guiUpdateLater() {
      this.executeLaterInUIThread(getTaskGuiUpdate());
   }

   public void guiUpdateOnChildren(Container panel) {
      Component[] components = panel.getComponents();
      //#debug
      toDLog().pFlow(components.length + "\t children for container " + panel.getClass().getSimpleName(), null, SwingCtx.class, "guiUpdateOnChildren", LVL_05_FINE, true);
      for (int i = 0; i < components.length; i++) {
         Component c = components[i];
         if (c instanceof IMyGui) {
            ((IMyGui) c).guiUpdate();
         } else if (c instanceof Container) {
            guiUpdateOnChildren((Container) c);
         }
      }
   }

   public void dockExitFor(CBentleyFrame frame) {
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            //if frame the only visible.. do the exit
            executeLaterInUIThread(new Runnable() {
               public void run() {
                  int numVisible = getFrames().getNumVisible();
                  //#debug
                  toDLog().pFlow("numVisible=" + numVisible, frames, SwingCtx.class, "dockExitFor", LVL_05_FINE, true);
                  if (numVisible == 0) {
                     if (exitTask != null) {
                        exitTask.cmdExit();
                     } else {
                        System.exit(0);
                     }
                  }
               }
            }, 2000);
         }
      });
   }

   public void guiUpdateOnChildrenMenu(JMenu menu) {
      Component[] components = menu.getMenuComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof IMyGui) {
            ((IMyGui) (components[i])).guiUpdate();
         } else if (components[i] instanceof Container) {
            guiUpdateOnChildren((Container) components[i]);
         }
      }
   }

   public void guiUpdateOnChildrenMenuPopup(JPopupMenu menu) {
      MenuElement[] menuElements = menu.getSubElements();
      for (int i = 0; i < menuElements.length; i++) {
         MenuElement menuElement = menuElements[i];
         //check the easiest case first
         if (menuElement instanceof IMyGui) {
            ((IMyGui) (menuElements[i])).guiUpdate();
         } else if (menuElement instanceof JMenuItem) {
            //special case for JPopupMenu where menuElement is a JMenuItem of type JPopupMenu$1 
            JMenuItem jitem = (JMenuItem) menuElement;
            Action action = jitem.getAction();
            if (action != null) {
               if (action instanceof IMyGui) {
                  ((IMyGui) action).guiUpdate();
               }
            }
         }
      }
   }

   //#enddebug

   public void guiUpdateTooltip(JComponent comp, String keyNormal) {
      if (keyNormal != null) {
         String tipKey = buildStringUISerial(keyNormal, ".tip");
         String tipString = getResString(tipKey);
         comp.setToolTipText(tipString);
      }
   }

   /**
    * True by default.
    * @return
    */
   public boolean isGlobalLabelTip() {
      return isGlobalLabelTip;
   }

   public boolean isResMissingLog() {
      return isResMissingLog;
   }

   /**
    * Called from a worker thread to publish a log message in the GUI log
    * @param type
    * @param string
    */
   public void publishUILog(final int type, final String str) {
      execute(new TaskUserLog(this,type,str));
   }

   public void registerFontTrueType(String path) {
      try {
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         InputStream is = getResourceAsStream(path);
         Font f = Font.createFont(Font.TRUETYPE_FONT, is);
         ge.registerFont(f);
      } catch (IOException e) {
         //Handle exception
         e.printStackTrace();
      } catch (FontFormatException e) {
         e.printStackTrace();
      }
   }


   /**
    * Repack frames to avoid glitches of sizes
    * @param c
    */
   public void revalidateFrame(Component c) {
      JFrame f = this.getFrame(c);
      if (f != null) {
         //f.pack(); // never use it. it resizes to preferred size of all sub compos.its wierd
         f.doLayout();
         f.revalidate();
         f.repaint();
      } else {
         //this should not happen. dev warning
         c.revalidate();
         c.repaint();
      }
   }

   public void revalidateSwingTree() {
      frames.revalidateSwingTree();
   }

   /**
    * Sets the list of bundles.
    * @param bundleNames
    */
   public void setBundleList(List<String> bundleNames) {
      this.bundleNames = bundleNames;
   }

   public void setExitableMain(IExitable exitTask) {
      this.exitTask = exitTask;

   }

   public void setFocusedTab(IMyTab tab) {
      focusedTab = tab;
   }

   /**
    * Save folder in preferences
    * @param key
    * @param f
    */
   public void setFolder(String key, File f) {
      if (!f.isDirectory()) {
         f = f.getParentFile();
      }
      getPrefs().put("folder_" + key, f.getAbsolutePath());
   }

   public void setGlobalLabelTip(boolean isGlobalLabelTip) {
      this.isGlobalLabelTip = isGlobalLabelTip;
   }

   public void setIBackForwardable(IBackForwardable backforwardable) {
      this.backforwardable = backforwardable;
   }

   public void setLocale(Locale locale) {
      this.locale = locale;
      prefs.put(SwingCtx.PREF_LOCALE_COUNTRY, locale.getCountry());
      prefs.put(SwingCtx.PREF_LOCALE_LANG, locale.getLanguage());
      if (bundleNames == null) {
         throw new NullPointerException("SwingCtx:BundleNames not initialized");
      }
      resBund = new CombinedResourceBundle(this, bundleNames, locale, new UTF8Control());
      resBund.load();
   }

   public void setPrefs(IPrefs prefs) {
      if (prefs != null) {
         this.prefs = prefs;
      }
   }

   public void setResMissingLog(boolean isResMissingLog) {
      this.isResMissingLog = isResMissingLog;
   }

   /**
    * Null if no tab icons
    * @param tabIcons
    */
   public void setTabIcons(TabIconSettings tabIcons) {
      this.tabIcons = tabIcons;
   }

   public void showFrame(FrameReference frame) {
      frame.showFrame();
   }

   public void showFrameThen(FrameReference frame, FrameReference frameAfter) {
      frame.showFrame();
      frame.getFrame().setFrameOnClose(frameAfter);
      //add close listener to show frame after
   }

   /**
    * TODO change set into Add
    * @param tabMenuBarFactory
    */
   public void setTabMenuBarFactory(ITabMenuBarFactory tabMenuBarFactory) {
      this.tabMenuBarFactory = tabMenuBarFactory;
   }

   public FrameIMyTab showInNewFrame(FrameIMyTab f, float width, float height) {
      f.pack();
      //default dimension? decided based on several parameters based on hints
      // current size
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int w = (int) ((float) screenSize.width * width);
      int h = (int) ((float) screenSize.height * height);
      f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
      f.setSize(w, h);
      f.setVisible(true);
      return f;
   }

   /**
    * Shows the {@link IMyTab} as a root in its own {@link FrameIMyTab}.
    * <br>
    * Which frame is asking this? We will position new frame relative to the owner frame.
    * @param tab
    * @return
    */
   public FrameIMyTab showInNewFrame(IMyTab tab) {
      FrameIMyTab f = new FrameIMyTab(tab);
      f.pack();
      //default dimension? decided based on several parameters based on hints
      // current size
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int w = 940;
      int h = 600;
      f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
      f.setSize(w, h);
      f.setVisible(true);
      return f;
   }

   /**
    * Shows the {@link IMyTab} as a root in its own {@link FrameIMyTab}.
    * <br>
    * The {@link TabbedBentleyPanel} is the implicit listener for close events.
    * 
    * @param tabOwner
    * @param tab
    * @return
    */
   public FrameIMyTab showInNewFrame(IMyTab tab, boolean isFullScreen) {
      FrameIMyTab f = new FrameIMyTab(tab);
      if (isFullScreen) {
         f.pack();
         f.setFullScreenTrue();
         f.setVisible(true);
      } else {
         f.pack();
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         int w = 940;
         int h = 600;
         f.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
         f.setSize(w, h);
         f.setVisible(true);
      }
      return f;
   }

   /**
    * 
    * Shows the {@link IMyTab} in a new {@link FrameIMyTab}.
    * 
    * Position is relative to {@link CBentleyFrame} if not null.
    * 
    * If null, defaul method {@link SwingCtx#showInNewFrame(IMyTab)} is called instead.
    * 
    * TODO create a depedency with owner.
    * This means if owner is closed, child frames are closed as well.
    * <br>
    * @param tab
    * @param owner
    * @return
    */
   public FrameIMyTab showInNewFrame(IMyTab tab, CBentleyFrame owner) {
      if (owner == null) {
         //#debug
         toDLog().pNull("CBentleyFrame owner is null", tab, SwingCtx.class, "showInNewFrame", LVL_09_WARNING, true);

         return showInNewFrame(tab);
      }
      FrameIMyTab f = new FrameIMyTab(tab);
      f.pack();
      //default dimension? decided based on several parameters based on hints
      //frame takes the size of owner
      int ox = owner.getX();
      int oy = owner.getY();
      int ow = owner.getWidth();
      int oh = owner.getHeight();
      //take 80% of thise
      int w = (int) (ow * 0.8f);
      int h = (int) (oh * 0.8f);
      int x = ox + (ow - w) / 2;
      int y = oy + (oh - h) / 2;
      f.setLocation(x, y);
      f.setSize(w, h);
      f.setVisible(true);
      return f;
   }

   public FrameIMyTab showInNewFramePackCenter(FrameIMyTab f) {
      f.pack();
      //default dimension? decided based on several parameters based on hints
      int width = f.getPreferredSize().width;
      int height = f.getPreferredSize().height;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      f.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
      f.setVisible(true);
      return f;
   }

   public void swingWorkerCancel(PanelSwingWorker worker) {
      boolean wasCanceled = worker.cancel(true);
      //#debug
      toDLog().pWork("Worker was canceled=" + wasCanceled, worker, SwingCtx.class, "swingWorkerCancel", LVL_05_FINE, true);
   }

   public void swingWorkerExecute(PanelSwingWorker worker) {
      //#debug
      toDLog().pWork("", worker, SwingCtx.class, "swingWorkerExecute", LVL_05_FINE, true);
      worker.execute();
   }

   //#mdebug
   public IDLog toDLog() {
      return uc.toDLog();
   }

   public SwingDebug toSD() {
      return sd;
   }

   public void toString(Dctx dc) {
      dc.root(this, "SwingCtx");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public boolean toString(Dctx dc, Object o) {
      return sd.toString(o, dc);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "SwingCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   /**
    * String rep of every thing
    * 
    * @param dc
    */
   public void toStringAll(Dctx dc) {
      toString(dc);
   }

   /**
    * 
    * @return
    */
   public String toStringGuiUpdate() {
      Dctx dc = new Dctx(getUCtx());
      toStringGu44iUpdate(dc);
      frames.toStringGuiUpdate(dc);
      return dc.toString();
   }

   /**
    * String of gui update registered object
    * @param dc
    */
   public void toStringGu44iUpdate(Dctx dc) {
      dc.append("MyGuis #" + listGuis.size());
      for (IMyGui gui : listGuis) {
         dc.nlLvl("Gui", gui);
      }
   }

   private void toStringPrivate(Dctx dc) {

   }

   public String toStringRunnerAll() {
      if (root == null) {
         return "Root is null";
      }
      return root.toString();
   }

   public void toStringSetRoot(IStringable root) {
      this.root = root;
   }
   //#enddebug

   public void updateAllVisibleTabs() {
      //#debug
      toDLog().pFlow("", null, SwingCtx.class, "updateAllVisibleTabs", ITechLvl.LVL_04_FINER, true);

   }

   /**
    * 
    * @param lang
    * @param country
    * @return
    */
   public boolean updateLocale(String lang, String country) {
      Locale newLocale = new Locale(lang, country);
      try {
         //locale preference is SwingCtx related
         setLocale(newLocale);
         this.guiUpdate();
         this.getLog().consoleLog("Language set to " + lang + " " + country);
         return true;
      } catch (MissingResourceException e) {
         getLog().consoleLogError("Resource Bundle for " + lang + " and " + country + " not found.");
         return false;
      }
   }

}
