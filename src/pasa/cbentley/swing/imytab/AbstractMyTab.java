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

   protected final SwingCtx  sc;

   /**
    * Set by the {@link TabbedBentleyPanel} when the 
    */
   private final TabPosition position;

   private TabPage           tapPage;

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

   public SwingCtx getSwingCtx() {
      return sc;
   }
   
   public String getSelectorKeyPrefID() {
      return interID;
   }

   public AbstractMyTab(SwingCtx sc, String internalID) {
      this.sc = sc;
      this.interID = internalID;
      this.position = new TabPosition(sc, this);
   }

   public int getMode() {
      return mode;
   }

   public void positionUpdate() {
   }

   public TabPosition getTabPosition() {
      return position;
   }

   public String getTabInternalID() {
      if (interID == null) {
         interID = this.getClass().getSimpleName();
      }
      return interID;
   }

   public void guiUpdate() {
      sc.guiUpdateOnChildren(this);
   }

   /**
    * Will be called by {@link AbstractMyTab#initCheck()}
    * <br>
    * Users of {@link AbstractMyTab} only have to call {@link AbstractMyTab#initCheck()}
    * just before displaying it
    */
   protected abstract void initTab();

   /**
    * Called by subclass when tab has been fully initialized
    */
   public void initFinished() {
      isInit = true;
   }

   public void initCheck() {
      if (!isInitialized()) {
         initTab();
         isInit = true;
         subTabDidFinishInitCheck();
      }
   }

   protected void subTabDidFinishInitCheck() {
      //#debug
      toDLog().pFlow(getClass().getSimpleName() + "", this, AbstractMyTab.class, "subTabDidFinishInitCheck", ITechLvl.LVL_05_FINE, true);
   }

   /**
    * Returns the Tab tip
    */
   public String getTabTip() {
      return sc.getResString("tab." + interID + ".tip");
   }

   public Icon getTabIcon(int size, int mode) {
      return sc.getResIcon(interID, "tab", size, mode);
   }

   /**
    * Returns the Tab title.
    */
   public String getTabTitle() {
      return sc.getResString("tab." + interID + ".title");
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

   public String getTabExitSound() {
      return sc.getResSound(interID, "tab", "exit");
   }

   public boolean isInitialized() {
      return isInit;
   }

   public void setDisposed() {
      this.isInit = false;
   }

   public void setMode(int mode) {
      this.mode = mode;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public IDLog toDLog() {
      return sc.toDLog();
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "AbstractMyTab");
      dc.appendVarWithSpace("internalID", interID);
      dc.appendVarWithSpace("isInit", isInit);
      dc.appendVarWithSpace("mode", mode);
      dc.nlLvlTitleIfNull(position, "TabPosition");
      sc.toSD().d(this, dc.nLevel());
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
      return sc.getUCtx();
   }
   //#enddebug
}