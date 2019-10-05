package pasa.cbentley.swing.imytab;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JMenuBar;

import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Shows a frame for an {@link IMyTab} at the same position as it was used before.. if possible.
 * <br>
 * <br>
 * 
 * Send the focus when user gives the focus
 * 
 * @author Charles Bentley
 *
 */
public class FrameIMyTab extends CBentleyFrame implements ITabOwner {

   /**
    * 
    */
   private static final long serialVersionUID = -3157445885360459014L;

   private FrameReference    frameReference;

   private final SwingCtx    sc;

   /**
    * No null
    */
   private final IMyTab      tab;

   /**
    * <li>Custom close listener. Uses the 
    * <li>Close event is hard wired to the {@link TabbedBentleyPanel}.
    * We want a specific event for the underlying tab
    * 
    * Frame is registered to {@link SwingCtx#guiRegister(IMyGui)}
    * 
    * @param tabbedpane
    * @param tab
    */
   public FrameIMyTab(final IMyTab tab) {
      super(tab.getSwingCtx());
      this.tab = tab;
      this.tab.setTabOwner(this);
      sc = tab.getSwingCtx();
      aInitConstructor(tab);
      //make sure the frame has its GUI up to date
      this.guiUpdate();
   }

   /**
    * @param tab
    */
   private void aInitConstructor(IMyTab tab) {
      this.setTitle(tab.getTabTitle());
      
      //TODO what if there is several of the same frames? Like showing several 
      this.setPrefID("frame_" + tab.getTabInternalID());

      //but we want the title to be the tab

      //maps the ICON of frame to a Size.. could be changed in ctx
      int size = sc.getIconSize(IconFamily.ICON_SIZE_FRAME);
      Icon tabIcon = tab.getTabIcon(size, IconFamily.ICON_MODE_1_SELECTED);
      Image ii = sc.getUtils().iconToImage(tabIcon);

      this.setIconImage(ii);

      this.getContentPane().add((Component) tab);

      tab.getTabPosition().setFrame(this);

      //make sure the tab is initialized
      tab.initCheck();

      //
      JMenuBar mb = tab.getSwingCtx().getMenuBar(tab, this);
      if (mb != null) {
         this.setJMenuBar(mb);
      }
   }

   /**
    * Called when the Frame has been closed by the user.
    * 
    * This means the tab can be disposed.
    */
   private void closeCleanUpTab() {
      TabbedBentleyPanel tabbedpane = tab.getTabPosition().getParent();
      //frame belongs to a TabPane
      if (tabbedpane != null) {
         //remove tab from 
         tabbedpane.cmdTabToBackHistory(tab);
         //save to preferences, its size and position
         IPrefs prefs = tabbedpane.getSwingCtx().getPrefs();
         savePrefs(prefs);
      } else {
         tab.disposeTab();
      }
   }

   /**
    * If it is the last window standing, the application will exit
    */
   protected void closeCleanUp() {
      if (frameReference != null) {
         frameReference.setFrame(null);
      }
      closeCleanUpTab();
      //once we have done our job.. we can ask super to effectively destroy it
      super.closeCleanUp();
   }

   public JMenuBar getIMyTabMenuBar() {
      return this.getJMenuBar();
   }

   public IMyTab getTab() {
      return tab;
   }

   public String getTitleFrame() {
      return tab.getTabTitle();
   }

   /**
    * The frame reference to set to null
    * @param frameReference
    */
   public void setFrameReference(FrameReference frameReference) {
      this.frameReference = frameReference;
      frameReference.setFrame(this);
   }

   public void tabIsFinished(IMyTab tab) {
      //#mdebug
      if (this.tab != tab) {
         throw new IllegalArgumentException();
      }
      //#enddebug
      cmdClose();
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "FrameIMyTab");
      super.toString(dc.sup());
      dc.nlLvl(tab);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "FrameIMyTab");
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   public void windowGainedFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameIMyTab.class, "windowGainedFocus", LVL_04_FINER, true);
      //we act as the owner so we have the responsability to initCheck
      tab.initCheck();
      tab.tabGainFocus();
   }

   public void windowLostFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameIMyTab.class, "windowLostFocus", LVL_04_FINER, true);
      tab.tabLostFocus();
   }

}
