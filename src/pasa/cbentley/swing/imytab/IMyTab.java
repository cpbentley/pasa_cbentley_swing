package pasa.cbentley.swing.imytab;

import java.awt.Component;

import javax.swing.Icon;

import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Each tab can be created on demand, using the {@link IMyTab#initTab()}
 * <br>
 * After a while, each tab is nullified so components can be reclaimed.
 * <br>
 * 
 * It has a default position, after X, Before Y
 * <br>
 * It must be of type {@link Component}.
 * <br>
 * 
 * An {@link IMyTab} may have specific menu actions
 * 
 * @author Charles Bentley
 *
 */
public interface IMyTab extends IStringable {

   public static final int TITLE_0_SHOW         = 0;

   public static final int TITLE_1_HIDE_IF_ICON = 1;

   public static final int TITLE_2_HIDE         = 2;

   /**
    * A IMyTab can be in several mode:
    * <li> Docked in Root TabPane
    * <li> Docked in 2nd line Tab
    * <li> Framed (Root in a frame)
    *
   public int getMode();
   /**
    * Tab was deselected
    */
   public void tabLostFocus();

   public SwingCtx getSwingCtx();

   /**
    * Tab was selected by user
    */
   public void tabGainFocus();

   public String getTabTitle();

   /**
    * Returns the {@link Icon} of the {@link IMyTab} based on the <code>themeID</code>.
    * <br>
    * <br>
    * @param size {@link IconFamily#ICON_SIZE_0_SMALLEST}
    * @return
    */
   public Icon getTabIcon(int size);

   /**
    * 
    * @param size
    * @param mode
    * @return null if none
    */
   public Icon getTabIcon(int size, int mode);

   public String getTabTip();

   /**
    * Sets the Init flag to false. Force initialization again
    */
   public void setDisposed();

   public TabPosition getTabPosition();

   /**
    * Internal name id
    * @return
    */
   public String getTabInternalID();

   /**
    * The page object representing this tab
    * @return
    */
   public TabPage getTabPage();

   /**
    * True when init was called
    * @return
    */
   public boolean isInitialized();

   /**
    * Tab has been moved to another
    */
   public void positionUpdate();

   /**
    * Creates new component. Set state to init
    */
   //public void initTab();

   /**
    * Checks if the Tab was initialized. If not, initializes it
    */
   public void initCheck();

   /**
    * 
    * Clear the tab of objects. Sets to null all components. Next time it is shown, {@link IMyTab#initTab()}
    * must be called
    */
   public void disposeTab();

   /**
    * The tab will be hidden by the given {@link IMyTab}
    * 
    * Return true if current tabs accept this fact.
    * Return false if tab refuses because something important must be done before by the user.
    * When the action has been done by the user, tab will call on its owner
    * 
    * @param newSelectedTab
    */
   public boolean tabWillBeHiddenByAnotherTab(IMyTab newSelectedTab);
}
