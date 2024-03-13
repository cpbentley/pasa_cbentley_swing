package pasa.cbentley.swing.imytab;

import javax.swing.Icon;
import javax.swing.JPanel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.interfaces.IStringPrefIDable;

/**
 * A IMyTab can be in several mode:
 * <li> Docked in Root TabPane
 * <li> Docked in 2nd line Tab
 * <li> Framed (Root in a frame)
 * @author Charles Bentley
 *
 */
public abstract class AbstractMyTab extends JPanel implements IMyTab, IMyGui, IStringPrefIDable {
   /**
    * 
    */
   private static final long serialVersionUID = 7938775673277222736L;

   private String            interID;

   private boolean           isInit           = false;

   private int               mode;

   /**
    * Set by the {@link TabbedBentleyPanel} when the 
    */
   private final TabPosition position;

   protected final SwingCtx  sc;

   private TabPage           tapPage;

   protected ITabOwner         owner;

   public AbstractMyTab(SwingCtx sc, String internalID) {
      this.sc = sc;
      this.interID = internalID;
      this.position = new TabPosition(sc, this);
   }

   public int getMode() {
      return mode;
   }

   public ITabOwner getTabOwner() {
      return owner;
   }

   public void setTabOwner(ITabOwner owner) {
      this.owner = owner;

   }

   public String getSelectorKeyPrefID() {
      return interID;
   }

   public SwingCtx getSwingCtx() {
      return sc;
   }

   public String getTabExitSound() {
      return sc.getResSound(interID, "tab", "exit");
   }

   /**
    * Returns an {@link Icon} based on {@link SwingCtx} tab parameters.
    * We have to determine our position. and if selected.
    * Icon can change if selected
    * <li> {@link SwingCtx} theme and language
    * <li> Size of icon is decided by {@link SwingCtx} based on current theme
    * <li> Also level of parenting with. 16px icon when lvl 2+
    * <li> Tab icon
    * 
    * <br>
    * Another solution is to have a
    */
   public Icon getTabIcon(int size) {
      return sc.getResIcon(interID, "tab", size);
   }

   public Icon getTabIcon(int size, int mode) {
      return sc.getResIcon(interID, "tab", size, mode);
   }

   public String getTabInternalID() {
      if (interID == null) {
         interID = this.getClass().getSimpleName();
      }
      return interID;
   }

   /**
    * Path to the sound to be played when the Tab is selected.
    * <br>
    * 
    * @return
    */
   public String getTabIntroSound() {
      //depends on the sound theme.
      return sc.getResSound(interID, "tab", "intro");
   }

   /**
    * Returns a different page everytime?
    * @return
    */
   public TabPage getTabPage() {
      if (tapPage == null) {
         tapPage = new TabPage(sc, this);
      }
      return tapPage;
   }

   public TabPosition getTabPosition() {
      return position;
   }

   /**
    * Returns the Tab tip
    */
   public String getTabTip() {
      String str = sc.buildStringUISerial("tab.", interID, ".tip");
      return sc.getResString(str);
   }

   /**
    * Returns the Tab title.
    */
   public String getTabTitle() {
      String str = sc.buildStringUISerial("tab.", interID, ".title");
      return sc.getResString(str);
   }

   public void guiUpdate() {
      sc.guiUpdateOnChildren(this);
   }

   public void initCheck() {
      if (!isInitialized()) {
         initTab();
         isInit = true;
         subTabDidFinishInitCheck();
      }
   }

   /**
    * Called by subclass when tab has been fully initialized
    */
   public void initFinished() {
      isInit = true;
   }

   /**
    * Will be called by {@link AbstractMyTab#initCheck()}
    * <br>
    * Users of {@link AbstractMyTab} only have to call {@link AbstractMyTab#initCheck()}
    * just before displaying it
    */
   protected abstract void initTab();

   public boolean isInitialized() {
      return isInit;
   }

   public void positionUpdate() {
   }

   public void setDisposed() {
      this.isInit = false;
   }

   public void setMode(int mode) {
      this.mode = mode;
   }

   protected void subTabDidFinishInitCheck() {
      //#debug
      toDLog().pFlow(getClass().getSimpleName() + "", this, AbstractMyTab.class, "subTabDidFinishInitCheck", ITechLvl.LVL_05_FINE, true);
   }

   public boolean shouldTabBeHiddenByAnotherTab(IMyTab newSelectedTab) {
      return true;
   }

   //#mdebug
   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractMyTab");
      dc.appendVarWithSpace("internalID", interID);
      dc.appendVarWithSpace("isInit", isInit);
      dc.appendVarWithSpace("mode", mode);
      dc.nlLvlTitleIfNull(position, "TabPosition");
      dc.nlLvlTitleIfNull(tapPage, "TapPage");
      dc.nlLvl(owner, "ITabOwner");
      sc.toSD().d(this, dc.newLevel());
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "AbstractMyTab");
      dc.append(' ');
      dc.append('\'');
      dc.append(interID);
      dc.append('\'');
      dc.appendVarWithSpace("isInit", isInit);
      dc.append(' ');
      dc.sameLine1("pos", position);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug
}