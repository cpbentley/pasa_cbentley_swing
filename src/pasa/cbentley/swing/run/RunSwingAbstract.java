package pasa.cbentley.swing.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.prefs.Preferences;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IConfig;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.swing.SwingPrefs;
import pasa.cbentley.swing.actions.IExitable;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;


/**
 * Preferences will be based on this implementation's class name
 * 
 * @author Charles Bentley
 *
 */
public abstract class RunSwingAbstract implements IExitable, IStringable {

   protected final C5Ctx          c5;

   protected CBentleyFrame        frame;

   protected final SwingCtx       sc;

   protected final UCtx           uc;


   public RunSwingAbstract() {
      this.uc = new UCtx();
      this.sc = new SwingCtx(uc);
      this.c5 = new C5Ctx(uc);

      //#debug
      this.sc.toStringSetRoot(this);
   }

   protected abstract void addI18n(List<String> list);


   protected void initSkinner() {
      //load the look and feel before any Swing component
   }

   protected abstract void initUIThreadInside();

   public void initUIThreadOutside() {
      toStringSetupLogger(uc);

      //setup preferences
      Preferences preferences = Preferences.userRoot().node(this.getClass().getSimpleName());
      //windows bug
      //WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) 
      // Solution Create new key HKEY_LOCAL_MACHINE\Software\JavaSoft\Prefs

      IPrefs iprefs = new SwingPrefs(sc, preferences);
      sc.setPrefs(iprefs);

      System.out.println("Preferences at Start for class " + this.getClass().getName());
      System.out.println(iprefs);

      ArrayList<String> list = new ArrayList<String>();
      addI18n(list);
      sc.setBundleList(list);

      String language = "en";
      String country = "US";
      Locale currentLocale = new Locale(language, country);
      sc.setLocale(currentLocale);

      //#debug
      toDLog().pFlow("language=" + language + "country=" + country, this, RunSwingAbstract.class, "initUIThreadOutside", LVL_05_FINE, true);

   }

   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "RunSwingAbstract");
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
      dc.root1Line(this, "RunSwingAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   /**
    * setup the logger at. sub class may override.
    * Default opens all at finest level
    */
   protected void toStringSetupLogger(UCtx uc) {
      BaseDLogger loggerFirst = (BaseDLogger) uc.toDLog();
      IConfig config = loggerFirst.getDefault().getConfig();
      config.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      config.setFlagPrint(ITechConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
   }

   //#enddebug

}
