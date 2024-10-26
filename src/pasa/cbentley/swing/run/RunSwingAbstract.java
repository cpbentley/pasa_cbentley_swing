package pasa.cbentley.swing.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import pasa.cbentley.core.src4.ctx.ConfigUDef;
import pasa.cbentley.core.src4.ctx.IConfig;
import pasa.cbentley.core.src4.ctx.IConfigU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.BasicPrefs;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ILogEntryAppender;
import pasa.cbentley.core.src4.logging.IStringable;
//#debug
import pasa.cbentley.core.src4.logging.LogConfiguratorAllFinest;
import pasa.cbentley.core.src4.logging.PreferencesSpyLogger;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.core.swing.ctx.SwingCoreCtx;
import pasa.cbentley.swing.SwingPrefs;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Preferences will be based on this implementation's class name
 * 
 * The default behavior has a default frame. however it can become null
 * @author Charles Bentley
 *
 */
public abstract class RunSwingAbstract implements IExitable, IStringable {

   protected final C5Ctx    c5;

   /**
    * The first frame.
    */
   protected CBentleyFrame  frame;

   protected final SwingCtx sc;

   protected final UCtx     uc;

   protected boolean        isSpyLoggerEnabled;

   public RunSwingAbstract(IConfigU configU) {
      if (configU == null) {
         configU = new ConfigUDef();
      }
      this.uc = new UCtx(configU);
      this.c5 = new C5Ctx(uc);
      this.sc = new SwingCtx(c5);

      //#debug
      this.sc.toStringSetRoot(this);

      //#debug
      toDLog().pCreate("", this, RunSwingAbstract.class, "Created@65", LVL_04_FINER, true);

   }

   /**
    * Uses all default {@link IConfig} for {@link UCtx} and {@link SwingCtx}
    */
   public RunSwingAbstract() {
      this(null);
   }

   public void prepareExit() {

   }

   /**
    * Subclass add its i18n filename to the list.
    * @param list
    */
   protected abstract void addI18n(List<String> list);

   /**
    * Initialize preferences for contexts below.
    * 
    * Do NOT deal with UI related preferences.
    * @param prefs
    */
   protected abstract void initOutsideUIForPrefs(IPrefs prefs);

   /**
    * Called by?
    */
   protected void initSkinner() {
      //load the look and feel before any Swing component
   }

   /**
    * Template Stuff specific to Swing
    */
   protected final void initUIThreadInside() {
      initSkinner();

      frame = initUIThreadInsideSwing();

      if (frame != null) {
         sc.guiUpdate();
         frame.positionFrame();
      }

      initPostFrameShown();
   }

   protected void initPostFrameShown() {

   }

   /**
    * Call this method once the runner has been instantiated and configured in the static void main thread.
    */
   public void run() {
      this.initUIThreadOutside();
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            initUIThreadInside();
         }
      });
   }

   /**
    * Implementation must create a {@link CBentleyFrame}
    * 
    * The first frame to be shown using the default behavior of {@link RunSwingAbstract}.
    * 
    * Could be null if none
    * 
    * @return CBentleyFrame main frame that will be displayed
    */
   protected abstract CBentleyFrame initUIThreadInsideSwing();

   /**
    * Called first before anything else.
    * 
    * <li> Loads {@link IPrefs}
    */
   public final void initUIThreadOutside() {

      //setup preferences
      String namePreferenceKey = this.getClass().getSimpleName();
      Preferences preferences = Preferences.userRoot().node(namePreferenceKey);
      //windows bug
      //WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) 
      // Solution Create new key HKEY_LOCAL_MACHINE\Software\JavaSoft\Prefs

      IPrefs prefs = null;

      //we never know if implementation
      if (preferences == null) {
         //#debug
         toDLog().pInit("Failure to create for class " + namePreferenceKey, null, RunSwingAbstract.class, "initUIThreadOutside@166", LVL_05_FINE, true);
         prefs = new BasicPrefs(uc);
      } else {
         //#debug
         toDLog().pInit("Preferences created for class " + namePreferenceKey, null, RunSwingAbstract.class, "initUIThreadOutside@166", LVL_05_FINE, true);
         prefs = new SwingPrefs(sc, preferences);
      }

      if (isSpyLoggerEnabled) {
         prefs = new PreferencesSpyLogger(uc, prefs);
      }

      sc.setPrefs(prefs);
      initOutsideUIForPrefs(prefs);

      ArrayList<String> list = new ArrayList<String>();
      sc.addI18NKey(list);
      addI18n(list);

      sc.setBundleList(list);

      String language = "en";
      String country = "US";
      Locale currentLocale = new Locale(language, country);
      sc.setLocale(currentLocale);

      //#debug
      toDLog().pFlow("language=" + language + "country=" + country, this, RunSwingAbstract.class, "initUIThreadOutside@line176", LVL_05_FINE, true);

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, RunSwingAbstract.class, 199);
      toStringPrivate(dc);

      dc.nlLvl(uc);
      dc.nlLvl(c5);
      dc.nlLvl(sc);

      dc.nlLvl(frame, "main");

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RunSwingAbstract.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
