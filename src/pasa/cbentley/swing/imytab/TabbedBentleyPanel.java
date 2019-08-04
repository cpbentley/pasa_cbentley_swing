package pasa.cbentley.swing.imytab;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.TabbedPaneUI;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.utils.BitUtils;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.actions.IBackForwardable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Tracks which {@link IMyTab} are Framed, so framed tabs can be brought back to their home
 * tabbedPane when closed.
 * <br>
 * 
 * <li> {@link TabbedBentleyPanel#addMyTab(IMyTab)} 
 * 
 * <br>
 * The user can move the tab to another place.
 * <br> 
 * @author Charles Bentley
 *
 */
public abstract class TabbedBentleyPanel extends AbstractMyTab implements IMyTab, IMyGui, ChangeListener, MouseListener, MouseWheelListener, KeyListener {

   /**
    * 
    */
   private static final long        serialVersionUID = -399245366602803407L;

   public static final int          TABS_BOTTOM      = JTabbedPane.BOTTOM;

   public static final int          TABS_LEFT        = JTabbedPane.LEFT;

   public static final int          TABS_RIGHT       = JTabbedPane.RIGHT;

   public static final int          TABS_TOP         = JTabbedPane.TOP;

   private BorderLayout             borderLayout;

   private int                      flagTblr         = 0;

   protected ArrayList<FrameIMyTab> framedTabs       = new ArrayList<FrameIMyTab>();

   private int                      iconPolicy;

   private int                      iconSize         = IconFamily.ICON_SIZE_1_SMALL;

   protected JTabbedPane            jtabbePane;

   protected ArrayList<IMyTab>      managedPanels    = new ArrayList<IMyTab>();

   /**
    * This class has its own pointer to the current {@link IMyTab}.
    * <br>
    * It allows change state when a Tab is framed out.
    */
   private IMyTab                   myCurrentTab;

   private SwingCtx                 sc;

   private TabPopupMenu             tabPopupMenu;

   public TabbedBentleyPanel(SwingCtx sc, String id) {
      super(sc, id);
      this.sc = sc;
      borderLayout = new BorderLayout();
      this.setLayout(borderLayout);

      jtabbePane = new JTabbedPane();

      int tabPlace = getTabPlacePref();
      jtabbePane.setTabPlacement(tabPlace);

      //intercept right clicks
      jtabbePane.addChangeListener(this);

      jtabbePane.addMouseListener(this);
      jtabbePane.addMouseWheelListener(this);
      jtabbePane.addKeyListener(this);

      this.add(jtabbePane, BorderLayout.CENTER);

      noRightClik(jtabbePane);

      //TODO implement popup
      //jtabbePane.setComponentPopupMenu(popup);

   }

   protected void changeEventEnable() {
      jtabbePane.addChangeListener(this);
   }

   protected void changeEventDisable() {
      jtabbePane.removeChangeListener(this);
   }

   /**
    * Update the {@link TabPosition} of this {@link IMyTab}.
    * <br>
    * hard code its position in the array of {@link IMyTab}
    * <br>
    * Adding a tab to the first time will generate a selection event through the event change listener
    * {@link TabbedBentleyPanel#stateChanged(ChangeEvent)}
    * <br>
    * <br>
    * Attention! Inserting a tab to an empty {@link JTabbedPane} will select it and fire and event
    * 
    * @param tab
    */
   public void addMyTab(IMyTab tab) {
      //#debug
      sanityCheckParent(tab);

      addIMyTabPrivate(tab);

      int count = jtabbePane.getTabCount();

      int iconSize = getIconSizeTab();
      //swing method that link the tab
      jtabbePane.addTab(tab.getTabTitle(), tab.getTabIcon(iconSize), (Component) tab, tab.getTabTip());

      tab.getTabPosition().setIndex(count);
      //compute sisters
      if (count > 0) {
         int leftIndex = count - 1;
         IMyTab leftSister = (IMyTab) jtabbePane.getComponentAt(leftIndex);
         tab.getTabPosition().setLeft(leftSister);
         leftSister.getTabPosition().setRight(tab);
      }

   }

   /**
    * Will be called by {@link AbstractMyTab#initCheck()}
    * <br>
    * Users of {@link AbstractMyTab} only have to call {@link AbstractMyTab#initCheck()}
    * just before displaying it
    */
   public final void initTab() {
      changeEventDisable();
      initTabs();
      //select tab from preference here
      String tabID = sc.getPrefs().get(getPrefKeySelectedTab(), "");
      if (tabID != null && !tabID.equals("")) {
         selectTabChildByID(tabID);
      }
      changeEventEnable();
   }

   /**
    * Create the tabs shells. This method is called by {@link TabbedBentleyPanel#initTab()}
    * 
    * after disabling events. so adding tabs don't create changeEvent.
    * 
    * <br>
    * The selection of a tab will occur
    * <li> by the page setting process
    * <li> by the saved id of the tab
    */
   public abstract void initTabs();

   /**
    * Sets Parent to {@link TabPosition}
    * Add to managed panel list
    * @param tab
    */
   private void addIMyTabPrivate(IMyTab tab) {
      tab.getTabPosition().setParent(this); //set parent
      managedPanels.add(tab);
   }

   /**
    * Only be called once per construction. its will be default position.
    * <br>
    * <br>
    * <li> {@link TabPosition#POS_1_TOP}
    * <li> {@link TabPosition#POS_2_BOTTOM}
    * <li> {@link TabPosition#POS_3_LEFT}
    * <li> {@link TabPosition#POS_4_RIGHT}
    * 
    * @param tab
    * @param position
    */
   public void addMyTab(IMyTab tab, int position) {
      //#debug
      sanityCheckParent(tab);

      addIMyTabPrivate(tab);
      int count = jtabbePane.getTabCount();
      tab.getTabPosition().setPositionDefault(position);

      cmdTabToPosition(tab, position);

      tab.getTabPosition().setIndex(count);
   }

   /**
    * Try to duplicate by create a new independant unit
    * @param focusedTab
    */
   public void cmdDuplicateTabToFrame(IMyTab focusedTab) {
      IMyTab newTab = this.createNewInstance(focusedTab.getTabInternalID(), focusedTab);
      if (newTab != null) {
         FrameIMyTab frame = sc.showInNewFrame(newTab, false);
         // register GUI since its not hosted by TabbedPanel
         //sc.addAllFrames(frame); //already added by CBentleyFrame
         //sc.guiRegister(frame); //already added by CBentleyFrame
         //#debug
         toDLog().pFlow("", frame, TabbedBentleyPanel.class, "cmdDuplicateTabToFrame", LVL_05_FINE, false);
      }
      sc.getLog().consoleLog("New tab" + focusedTab.getTabTitle() + " was duplicated");
   }

   public void cmdNextTab() {
      int count = jtabbePane.getTabCount();
      int selected = jtabbePane.getSelectedIndex();
      if (selected + 1 < count) {
         jtabbePane.setSelectedIndex(selected + 1);
      }
   }

   public void cmdPreviousTab() {
      int selected = jtabbePane.getSelectedIndex();
      if (selected - 1 >= 0) {
         jtabbePane.setSelectedIndex(selected - 1);
      }
   }

   /**
    * Bring back the Tab to its previous position
    */
   public void cmdTabToBackHistory(IMyTab tab) {
      int pos = tab.getTabPosition().getHistoryPositionPrevious();
      cmdTabToPosition(tab, pos);
   }

   public void cmdTabToBottom(IMyTab tab) {
      cmdTabToPosition(tab, TabPosition.POS_2_BOTTOM);
   }

   public void cmdTabToCenter(IMyTab tab) {
      cmdTabToPosition(tab, TabPosition.POS_0_CENTER);
   }

   /**
    * 
    * @param tab
    * @param index when -1.. it will takes its default position
    */
   public void cmdTabToCenter(IMyTab tab, int index) {
      cmdTabToPosition(tab, TabPosition.POS_0_CENTER);
   }

   /**
    * Move back Tab to its factory position
    */
   public void cmdTabToDefault(IMyTab tab) {
      cmdTabToPosition(tab, tab.getTabPosition().getPositionDefault());
   }

   /**
    * Put current {@link IMyTab} into its own frame.
    * <br>
    * The populate the Menu with specific 
    */
   public void cmdTabToFrame() {
      Component c = jtabbePane.getSelectedComponent();
      jtabbePane.remove(c);
      this.sc.revalidateFrame(this);

      FrameIMyTab ft = sc.showInNewFrame((IMyTab) c, false);
      framedTabs.add(ft);
   }

   /**
    * Associate.
    * If the Tab does not belong 
    * @param tab
    */
   public void cmdTabToFrame(IMyTab tab) {
      cmdTabToFrame(tab, false);
   }

   public void cmdTabToFrame(IMyTab tab, boolean fullScreen) {
      if (tab instanceof Component) {
         //this will select a new tab if its the selected tab
         jtabbePane.removeChangeListener(this);
         jtabbePane.remove((Component) tab);
         jtabbePane.addChangeListener(this);
         //update all indexes
         computeTabIndexes();
      } else {
         throw new IllegalArgumentException("Must be Component ");
      }
      FrameIMyTab ft = sc.showInNewFrame(tab, fullScreen);
      framedTabs.add(ft);
   }

   public void cmdTabToLeft(IMyTab tab) {
      cmdTabToPosition(tab, TabPosition.POS_3_LEFT);
   }

   /**
    * Moves Tab relative to parent
    * @param tab
    * @param direction
    */
   public void cmdTabToPosition(IMyTab tab, int direction) {
      //if currently framed. close it
      tab.getTabPosition().finishTheFrameIfAny();
      //a tab can change its position within the center
      if (tab.getTabPosition().isCentered()) {
         //
         if (direction == TabPosition.POS_0_CENTER) {
            //we have a new index
            //swap index with. this is done w

         } else {
            //
            jtabbePane.removeChangeListener(this);
            jtabbePane.remove((Component) tab); //we don't want to recieve this event
            jtabbePane.addChangeListener(this);
         }
      }
      switch (direction) {
         case TabPosition.POS_0_CENTER:
            cmdXTabToCenter(tab);
            break;
         case TabPosition.POS_1_TOP:
            cmdXTabToNorth(tab);
            break;
         case TabPosition.POS_2_BOTTOM:
            cmdXTabToSouth(tab);
            break;
         case TabPosition.POS_3_LEFT:
            cmdXTabToWest(tab);
            break;
         case TabPosition.POS_4_RIGHT:
            cmdXTabToEast(tab);
            break;
         case TabPosition.POS_5_FRAMED:
            cmdTabToFrame(tab);
            break;

         default:
            break;
      }
   }

   public void cmdTabToRight(IMyTab tab) {
      cmdTabToPosition(tab, TabPosition.POS_4_RIGHT);
   }

   public void cmdTabToTop(IMyTab tab) {
      cmdTabToPosition(tab, TabPosition.POS_1_TOP);
   }

   /**
    * Do the effective link
    * @param tab
    */
   private void cmdXTabToCenter(IMyTab tab) {
      //get it back into its default position next to its brother
      TabPosition pos = tab.getTabPosition();
      int numTabs = jtabbePane.getTabCount();
      boolean isInserted = false;
      for (int i = 0; i < numTabs - 1; i++) {
         //ignore last
         //do not confused. jtabbePane.getTabComponentAt(i); is the custom component for the tab 
         //must implemente my tab
         IMyTab tabIter = (IMyTab) jtabbePane.getComponentAt(i);
         if (tabIter == pos.getLeft()) {
            //insert here
            int index = i + 1;
            jtabbePane.insertTab(tab.getTabTitle(), tab.getTabIcon(iconSize), (Component) tab, tab.getTabTip(), index);
            isInserted = true;
            break;
         }
         if (tabIter == pos.getRight()) {
            int index = i;
            jtabbePane.insertTab(tab.getTabTitle(), tab.getTabIcon(iconSize), (Component) tab, tab.getTabTip(), index);
            isInserted = true;
            break;
         }
      }
      if (!isInserted) {
         jtabbePane.addTab(tab.getTabTitle(), tab.getTabIcon(iconSize), (Component) tab, tab.getTabTip());
      }
   }

   private void cmdXTabToEast(IMyTab tab) {
      cmdXTabToXXX(tab, BorderLayout.EAST, TabPosition.POS_4_RIGHT, TabPosition.TBLR_FLAG_4_RIGHT);
   }

   private void cmdXTabToNorth(IMyTab tab) {
      cmdXTabToXXX(tab, BorderLayout.NORTH, TabPosition.POS_1_TOP, TabPosition.TBLR_FLAG_1_TOP);
   }

   /**
    * Moves the tab to the south. if any tab is located there. it is brought back to the center
    * @param tab
    */
   private void cmdXTabToSouth(IMyTab tab) {
      cmdXTabToXXX(tab, BorderLayout.SOUTH, TabPosition.POS_2_BOTTOM, TabPosition.TBLR_FLAG_2_BOT);
   }

   private void cmdXTabToWest(IMyTab tab) {
      cmdXTabToXXX(tab, BorderLayout.WEST, TabPosition.POS_3_LEFT, TabPosition.TBLR_FLAG_3_LEFT);
   }

   /**
    * Only used for TBLR
    * @param tab
    * @param borderID
    * @param posID
    * @param flag
    */
   private void cmdXTabToXXX(IMyTab tab, String borderID, int posID, int flag) {
      if (posID == TabPosition.POS_0_CENTER || posID == TabPosition.POS_5_FRAMED) {
         throw new IllegalArgumentException();
      }
      Component c = (Component) tab;
      //component already located at our position... what do we do? send it back to center
      Component layedC = borderLayout.getLayoutComponent(borderID);
      if (layedC != null) {
         IMyTab tabC = (IMyTab) layedC;
         this.remove(layedC);
         //put the component to the center
         cmdTabToCenter(tabC);
      }
      this.add(c, borderID);
      flagTblr = BitUtils.setFlag(flagTblr, flag, true);

      TabPosition pos = tab.getTabPosition();
      pos.setPosition(posID);

      //if was framed close frame

      //repaint et revalidate this and frame of root
      this.sc.revalidateFrame(this);

   }

   public void computeTabIndexes() {
      for (IMyTab tab : managedPanels) {
         int numTabs = jtabbePane.getTabCount();
         int foundIndex = -1;
         for (int i = 0; i < numTabs; i++) {
            //must implemente my tab
            IMyTab itab = (IMyTab) jtabbePane.getComponentAt(i);
            if (itab == tab) {
               foundIndex = i;
               continue;
            }
         }
         tab.getTabPosition().setIndex(foundIndex);
      }
   }

   /**
    * 
    * @param id cannot be null
    * @param template if not null. current data is used to create a new instance with similar state
    * @return
    */
   public IMyTab createNewInstance(String id, IMyTab template) {
      return null;
   }

   /**
    * Null if tab is not managed by this {@link TabbedBentleyPanel}
    * @param internalID
    * @return
    */
   public IMyTab findTab(String internalID) {
      for (Iterator iterator = managedPanels.iterator(); iterator.hasNext();) {
         IMyTab tab = (IMyTab) iterator.next();
         if (tab.getTabInternalID().equals(internalID)) {
            return tab;
         }
      }
      return null;
   }

   public int getIconSizeTab() {
      int riconSize = iconSize;
      if (iconPolicy == IconFamily.ICON_POLICY_0_AS_PARENT) {
         if (isInsideAnother()) {
            riconSize = this.getTabPosition().getParent().getIconSizeTab();
         }
         return riconSize;
      } else {
         //put smallest if we are inside another tab pane
         if (isInsideAnother()) {
            riconSize = IconFamily.ICON_SIZE_0_SMALLEST;
         }
      }
      return riconSize;

   }

   public TabPage getPageFirst(String id) {
      for (IMyTab tab : managedPanels) {
         if (id.equals(tab.getTabInternalID())) {
            return tab.getTabPage();
         } else {
            //try deep if itself a tab pane
            if (tab instanceof TabbedBentleyPanel) {
               TabPage page = ((TabbedBentleyPanel) tab).getPageFirst(id);
               if (page != null) {
                  return page;
               }
            }
         }
      }
      return null;
   }

   public TabPopupMenu getPopMenu() {
      if (tabPopupMenu == null) {
         tabPopupMenu = new TabPopupMenu(sc);
      }
      return tabPopupMenu;
   }

   public SwingCtx getSwingCtx() {
      return sc;
   }

   public IMyTab getTab(String id) {
      for (IMyTab iMyTab : managedPanels) {
         if (iMyTab.getTabInternalID().equals(id)) {
            return iMyTab;
         }
      }
      return null;
   }

   public JTabbedPane getTabPane() {
      return jtabbePane;
   }

   public int getTabPlacePref() {
      return sc.getPrefs().getInt(getTabInternalID() + "_tabplace", TABS_TOP);
   }

   /**
    * {@link IMyTab} is a {@link IMyGui}
    * This method {@link IMyGui#guiUpdate()} is called recursively on the component tree by
    * {@link SwingCtx#guiUpdate()}
    * 
    * This method must be called when a new tab is created.
    * 
    * 
    * 
    */
   public void guiUpdate() {
      // update tab placement etc get saved tab policy for this
      int tabPolicy = getTabPlacePref();
      jtabbePane.setTabPlacement(tabPolicy);
      int titlePolicy = sc.getPrefs().getInt(getTabInternalID() + "_title", IMyTab.TITLE_0_SHOW);
      int numTabs = jtabbePane.getTabCount();
      int selectedIndex = jtabbePane.getSelectedIndex();
      for (int i = 0; i < numTabs; i++) {
         //must implemente my tab
         IMyTab tab = (IMyTab) jtabbePane.getComponentAt(i);
         if (tab != null) {

            int sel = IconFamily.ICON_MODE_0_DEFAULT;
            if (i == selectedIndex) {
               sel = IconFamily.ICON_MODE_1_SELECTED;
            }
            int sizeID = getIconSizeTab();
            //we call, to let the OO paradigm. tab may decide to always always display an icon
            Icon tabIcon = tab.getTabIcon(sizeID, sel);
            jtabbePane.setIconAt(i, tabIcon);
            if (titlePolicy == IMyTab.TITLE_0_SHOW) {
               jtabbePane.setTitleAt(i, tab.getTabTitle());
            } else if (titlePolicy == IMyTab.TITLE_1_HIDE_IF_ICON && tabIcon == null) {
               jtabbePane.setTitleAt(i, tab.getTabTitle());
            } else {
               jtabbePane.setTitleAt(i, "");
            }
            jtabbePane.setToolTipTextAt(i, tab.getTabTip());

         }
      }

      for (IMyTab tabs : managedPanels) {
         if (tabs instanceof IMyGui) {
            ((IMyGui) tabs).guiUpdate();
         }
      }
      //frames will be updated with the SwingCtx framework frame management
   }

   /**
    * 
    */
   public void initCheck() {
      super.initCheck();
      //TODO check if needed for bug . nope
      //this.revalidate();
      //this.repaint();
   }

   public boolean isInsideAnother() {
      return this.getTabPosition().getParent() != null;
   }

   public boolean isSelected(IMyTab tab) {
      return jtabbePane.getSelectedComponent() == tab;
   }

   /**
    * Tells whether this Tab is currently "user" active.
    * <br>
    * <li> Framed in a visible Frame
    * <li> Selected tab in a visible hierarchy
    * @return
    */
   public boolean isUserActive() {
      TabPosition pos = getTabPosition();
      if (pos != null) {
         if (pos.isFramed() || (pos.getParent() != null && pos.getParent().isSelected(this))) {
            return true;
         }
      }
      return false;
   }

   public void iterateTabPlacementCCW() {
      int p = jtabbePane.getTabPlacement();
      if (p == TABS_TOP) {
         jtabbePane.setTabPlacement(TABS_LEFT);
      } else if (p == TABS_LEFT) {
         jtabbePane.setTabPlacement(TABS_BOTTOM);
      } else if (p == TABS_BOTTOM) {
         jtabbePane.setTabPlacement(TABS_RIGHT);
      } else if (p == TABS_RIGHT) {
         jtabbePane.setTabPlacement(TABS_TOP);
      }
      setTabPlace(jtabbePane.getTabPlacement());
   }

   public void iterateTabPlacementCW() {
      int p = jtabbePane.getTabPlacement();
      if (p == TABS_TOP) {
         jtabbePane.setTabPlacement(TABS_RIGHT);
      } else if (p == TABS_RIGHT) {
         jtabbePane.setTabPlacement(TABS_BOTTOM);
      } else if (p == TABS_BOTTOM) {
         jtabbePane.setTabPlacement(TABS_LEFT);
      } else if (p == TABS_LEFT) {
         jtabbePane.setTabPlacement(TABS_TOP);
      }
      setTabPlace(jtabbePane.getTabPlacement());
   }

   /**
    * Invoked when a key has been pressed.
    * See the class description for {@link KeyEvent} for a definition of
    * a key pressed event.
    */
   public void keyPressed(KeyEvent e) {
      //#debug
      toDLog().pEvent("" + sc.toSD().d1(e), null, TabbedBentleyPanel.class, "keyPressed", ITechLvl.LVL_05_FINE, true);

      if (e.getKeyCode() == KeyEvent.VK_F9) {
         //debug current 
         //#debug
         toDLog().pAlways("Current TabbedBentleyPanel", this, TabbedBentleyPanel.class, "keyPressed_F9", ITechLvl.LVL_08_INFO, false);
      }
      if (e.getKeyCode() == KeyEvent.VK_F8) {
         //debug current 
         //#debug
         toDLog().pAlways("BackForwardTabPage", sc.getBackForward(), TabbedBentleyPanel.class, "keyPressed_F9", ITechLvl.LVL_08_INFO, false);
      }
   }

   /**
    * Invoked when a key has been released.
    * See the class description for {@link KeyEvent} for a definition of
    * a key released event.
    */
   public void keyReleased(KeyEvent e) {

   }

   /**
    * Invoked when a key has been typed.
    * See the class description for {@link KeyEvent} for a definition of
    * a key typed event.
    */
   public void keyTyped(KeyEvent e) {

   }

   /**
    * {@inheritDoc}
    */
   public void mouseClicked(MouseEvent me) {
      if (me.getButton() == 3) {
         TabPopupMenu menu = getPopMenu();
         if (menu != null) {
            int tabNr = ((TabbedPaneUI) jtabbePane.getUI()).tabForCoordinate(jtabbePane, me.getX(), me.getY());
            Component clickedTab = jtabbePane.getComponentAt(tabNr);
            IMyTab tab = (IMyTab) clickedTab;
            menu.setTab(tab);
            //show popup menu
            menu.show(this, me.getX(), me.getY());
         }
      }
   }

   /**
    * {@inheritDoc}
    * @since 1.6
    */
   public void mouseDragged(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    */
   public void mouseEntered(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    */
   public void mouseExited(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    * @since 1.6
    */
   public void mouseMoved(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    */
   public void mousePressed(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    */
   public void mouseReleased(MouseEvent e) {
   }

   /**
    * {@inheritDoc}
    * @since 1.6
    */
   public void mouseWheelMoved(MouseWheelEvent e) {
      if (e.isControlDown()) {
         if (e.getWheelRotation() > 0) {
            iterateTabPlacementCW();
         } else {
            iterateTabPlacementCCW();
         }
      } else if (e.isAltDown()) {
         if (e.getWheelRotation() > 0) {
            IBackForwardable backforwardable = sc.getBackforwardable();
            if (backforwardable != null) {
               backforwardable.cmdNavBack();
            }
         } else {
            IBackForwardable backforwardable = sc.getBackforwardable();
            if (backforwardable != null) {
               backforwardable.cmdNavForward();
            }
         }
      }
   }

   /**
    * Prevents selection of a tab from right clicking on it
    * @param tabbedPane
    */
   private void noRightClik(JTabbedPane tabbedPane) {
      MouseListener[] ml = (MouseListener[]) tabbedPane.getListeners(MouseListener.class);

      for (int i = 0; i < ml.length; i++) {
         tabbedPane.removeMouseListener(ml[i]);
      }

      tabbedPane.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            JTabbedPane tabbedPane = (JTabbedPane) e.getSource();

            // added
            if (!tabbedPane.isEnabled() || SwingUtilities.isRightMouseButton(e)) {

               return;
            }

            // int tabIndex = getTabAtLocation(e.getX(), e.getY()); // changed
            int tabIndex = tabbedPane.getUI().tabForCoordinate(tabbedPane, e.getX(), e.getY());

            if (tabIndex >= 0 && tabbedPane.isEnabledAt(tabIndex)) {
               if (tabIndex == tabbedPane.getSelectedIndex()) {
                  if (tabbedPane.isRequestFocusEnabled()) {
                     tabbedPane.requestFocus();

                     //tabbedPane.repaint(getTabBounds(tabPane, tabIndex)); // changed
                     tabbedPane.repaint(tabbedPane.getUI().getTabBounds(tabbedPane, tabIndex));
                  }
               } else {
                  tabbedPane.setSelectedIndex(tabIndex);
               }
            }
         }
      });
   }

   public void removeAlltabs() {
      jtabbePane.removeAll();
      managedPanels.clear();
   }

   private void sanityCheckParent(IMyTab tab) {
      if (tab.getTabPosition().getParent() != null) {
         throw new IllegalStateException("Only call with null parents");
      }
   }

   /**
    * 
    * @param tab
    */
   private void selectTab(IMyTab tab) {
      boolean b = setSelected(tab);
      if (!b) {
         //not a tab but a frame or else
         for (FrameIMyTab framedTabs : framedTabs) {
            if (framedTabs.getTab() == tab) {
               framedTabs.setVisible(true);
               framedTabs.toFront();
               break;
            }
         }
      }
   }

   /**
    * Smaller if below, or same size as parent.
    * 
    * @param policy
    */
   public void setIconSizePolicy(int policy) {
      this.iconPolicy = policy;
   }

   public void setIconSizeTab(int size) {
      iconSize = size;
   }

   /**
    * True if tab set as selected component
    * @param tab
    * @return
    */
   public boolean setSelected(IMyTab tab) {
      int count = jtabbePane.getTabCount();
      for (int i = 0; i < count; i++) {
         Component c = jtabbePane.getComponentAt(i);
         if (c == tab) {
            jtabbePane.setSelectedComponent(c);
            return true;
         }
      }
      return false;
   }

   public void setTabPlace(int val) {
      sc.getPrefs().putInt(getTabInternalID() + "_tabplace", val);
   }

   /**
    * <li> {@link TabbedBentleyPanel#TABS_TOP}
    * <li> {@link TabbedBentleyPanel#TABS_BOTTOM}
    * <li> {@link TabbedBentleyPanel#TABS_LEFT}
    * <li> {@link TabbedBentleyPanel#TABS_RIGHT}
    * @param placement
    */
   public void setTabsPlacement(int placement) {
      jtabbePane.setTabPlacement(placement);
   }

   public void showChildByID(String id) {
      for (IMyTab tab : managedPanels) {
         if (id.equals(tab.getTabInternalID())) {
            selectTab(tab);
            break;
         }
      }
   }

   /**
    * Select the Tab with the given ID and initCheck it
    * @param id
    */
   private void selectTabChildByID(String id) {
      for (IMyTab tab : managedPanels) {
         if (id.equals(tab.getTabInternalID())) {
            tab.initCheck();
            selectTab(tab);
            break;
         }
      }
   }

   public void showTab(IMyTab tab) {
      selectTab(tab);
   }

   public void showPage(String page) {
      IMyTab tab = findTab(page);
      if (tab != null) {
         selectTab(tab);
      }
   }

   public void showPage(TabPage page) {
      IMyTab tab = page.getTab();
      if (tab != null) {
         //select visually the tab
         selectTab(tab);
      }
   }

   public void showTabPageStrings(PageStrings ps) {

      String id = ps.getNextString();
      //#debug
      //toDLog().pFlow("nextString=" + id, ps, TabbedBentleyPanel.class, "showTabPageStrings", IDLog.LVL_05_FINE, true);
      if (id == null) {
         return;
      }
      for (IMyTab tab : managedPanels) {

         //#debug
         //toDLog().pFlow("Looking at " + id + " =? " + tab.getTabInternalID(), ps, TabbedBentleyPanel.class, "showTabPageStrings", IDLog.LVL_05_FINE, true);

         if (id.equals(tab.getTabInternalID())) {
            //make sure it is initialized.
            //we have to manually do this because we want to "selectTab" at the end
            //to avoid giving focus to the first tab 
            initCheckTabBeforeFocus(tab);

            //if tab is itself another root, ask it to select its tab before going selected
            if (tab instanceof TabbedBentleyPanel) {
               ((TabbedBentleyPanel) tab).showTabPageStrings(ps);
            }
            //TODO selecting a TabbedBentleyPanel will give focus
            //to default selected tab.. we don't want that ?
            //or a tab selects its last select tab as a setting instead of
            //a top down approach with pages? this behavior is when a user select himself a tab
            //nah pages must work too because page are used to fire up

            //in the case of page selection, the selection on the mother tab is done last.
            //the right sub tab has already been selected.
            selectTab(tab);
            break;
         }
      }
   }

   private IMyTab newSelectedTabInWaiting;

   private IMyTab oldSelectedTabInWaiting;

   /**
    * This method is used when tab is selected by a user action or by a set
    * 
    * <br>
    * Otherwise, if it is selected by construction. nothing happens.
    * <br>
    * 
    */
   public void stateChanged(ChangeEvent e) {
      //if its the first panel
      //      if (!isInitialized()) {
      //         //#debug
      //         toDLog().pFlow("Not yet initialized", this, TabbedBentleyPanel.class, "stateChanged", IDLog.LVL_05_FINE, true);
      //         return;
      //      }

      //#debug
      toDLog().pFlow("" + sc.toSD().d1(e), this, TabbedBentleyPanel.class, "stateChanged", ITechLvl.LVL_05_FINE, true);

      Component newSelectedTab1 = jtabbePane.getSelectedComponent();
      IMyTab newSelectedTab = null;
      if (newSelectedTab1 instanceof IMyTab) {
         newSelectedTab = (IMyTab)newSelectedTab1;
      } else {
         throw new IllegalArgumentException("Only IMyTabs");
      }

      //update current tab data with sss if not done already
      if (myCurrentTab != null) {
         if(myCurrentTab == oldSelectedTabInWaiting) {
            //case of tab requests user action before being hidden by another
            return;
         }
         
         //when tab is selected and sent to a frame. we come here
         //when tab is moving to another TBLR position as well
         if (myCurrentTab.getTabPosition().isFramed()) {
            //#debug
            toDLog().pEvent("Previous tab is frame TODO", myCurrentTab, TabbedBentleyPanel.class, "stateChanged", ITechLvl.LVL_04_FINER, true);
         } else {

            //TODO we want to give the current tab a last chance to interact with the user
            //in case something important must be dealt with (saving file or stopping on going task)
            //unfortunately JTabbedPane does not send an event before the fact
            //hack by selecting currentTab and keep new 
            boolean isAccepted = myCurrentTab.tabWillBeHiddenByAnotherTab(newSelectedTab);
            if (!isAccepted) {
               newSelectedTabInWaiting = newSelectedTab;
               oldSelectedTabInWaiting = myCurrentTab;
               //disable events otherwise it will loop
               setSelected(myCurrentTab);
            }
            myCurrentTab.tabLostFocus();
            //index is still valid? NO. if 
            int index = myCurrentTab.getTabPosition().getIndex();
            Icon icon = myCurrentTab.getTabIcon(getIconSizeTab(), IconFamily.ICON_MODE_0_DEFAULT);
            jtabbePane.setIconAt(index, icon);
         }
         // play exit sound of tab with a fade out.. 
         //TODO sound ctx
         //get access from Swing ? Playing tab sounds
         //order.. exit sounds are played. deepest tab first.. highest tap last
         // entry sounds are queued after exit, highest first. deepest last
      }
      boolean isNew = stateChangeITab(newSelectedTab);
      newTabLayoutValidations(isNew, (IMyTab) newSelectedTab);
   }

   /**
    * Called after {@link IMyTab#tabWillBeHiddenByAnotherTab(IMyTab)}
    * @param tab
    */
   public void tabAcceptBeingHidden(IMyTab tab) {
      if (newSelectedTabInWaiting != null) {
         setSelected(newSelectedTabInWaiting);
      }
   }

   /**
    * Revalidates and update if new tab
    * @param isNew
    * @param newSelectedTab
    */
   private void newTabLayoutValidations(boolean isNew, IMyTab newSelectedTab) {
      if (isNew) {
         //#debug
         toDLog().pFlow(" for " + newSelectedTab.getTabInternalID(), this, TabbedBentleyPanel.class, "newTabLayoutValidations", LVL_05_FINE, true);
         //this.invalidate();
         sc.revalidateFrame(this);
         sc.guiUpdate();
      }
   }

   protected String getPrefKeySelectedTab() {
      return "ui.selectedtab." + this.getTabInternalID();
   }

   private boolean stateChangeITab(IMyTab selectedTab) {
      //make sure the tab has been initialized
      boolean isNew = initCheckTabBeforeFocus(selectedTab);
      //notify tab that user focus is now active on it
      //if tab is itself a TabbedPanel, it will select a tab
      selectedTab.tabGainFocus();

      //we want our context of navigation.. there could be several
      if (selectedTab instanceof TabbedBentleyPanel) {
         //do not add if tab is a node.. only add leaves.
         //TODO set selection of last selected tab if empty
         //((TabbedBentleyPanel)selectedTab).guiUpdate(); //redo update for graphical glitch
      } else {
         //save
         BackForwardTabPage backForward = sc.getBackForward();
         if (backForward != null) {
            backForward.addTabPage(selectedTab.getTabPage());
         }
         //set the deepest focused tab
         sc.setFocusedTab(selectedTab);
      }

      //in all case save selected tab for tab
      sc.getPrefs().put(getPrefKeySelectedTab(), selectedTab.getTabInternalID());

      int index = jtabbePane.getSelectedIndex();
      jtabbePane.setIconAt(index, selectedTab.getTabIcon(getIconSizeTab(), IconFamily.ICON_MODE_1_SELECTED));

      myCurrentTab = selectedTab;

      //#debug
      toDLog().pEvent("After Selection", selectedTab, TabbedBentleyPanel.class, "stateChanged", ITechLvl.LVL_04_FINER, true);
      return isNew;
   }

   /**
    * 
    * @param tabAboutToBeFocused
    */
   private boolean initCheckTabBeforeFocus(IMyTab tabAboutToBeFocused) {
      if (!tabAboutToBeFocused.isInitialized()) {
         tabAboutToBeFocused.initCheck();
         return true;
      }
      return false;
   }

   /**
    * When a {@link TabbedBentleyPanel} gets the focus, it forward focus
    * to its selected tab.
    * 
    * By doing it so, it must again make sure the tab is initialized.
    * A tab can be selected but not initialzied
    */
   public void tabGainFocus() {
      IMyTab selectedTab = ((IMyTab) jtabbePane.getSelectedComponent());
      if (selectedTab != null) {
         //when gaining focus, make sure the tab is initialzied
         boolean isNew = initCheckTabBeforeFocus(selectedTab);
         selectedTab.tabGainFocus();
         newTabLayoutValidations(isNew, (IMyTab) selectedTab);
      }
   }

   /**
    * When root loses the focus, its child loses it as well
    */
   public void tabLostFocus() {
      IMyTab selectedTab = ((IMyTab) jtabbePane.getSelectedComponent());
      if (selectedTab != null) {
         selectedTab.tabLostFocus();
      }
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "TabbedBentleyPanel");
      dc.appendVarWithSpace("isUserActive", isUserActive());
      super.toString(dc.sup());
      dc.nl();
      sc.toSD().d(jtabbePane, dc);
      dc.nl();
      dc.nlLvlOneLine("myCurrentTab", myCurrentTab);
      dc.nl();
      dc.append("Managed Tabs");
      for (IMyTab tab : managedPanels) {
         dc.nlLvlOneLine(tab);
      }
      dc.nl();
      dc.append("Framed Tabs #" + framedTabs.size());
      dc.tab();
      for (FrameIMyTab tab : framedTabs) {
         dc.nlLvlOneLine(tab);
      }
      dc.tabRemove();
      dc.nl();
      int numTabs = jtabbePane.getTabCount();
      dc.append("Center Tabs #" + numTabs);
      dc.tab();
      for (int i = 0; i < numTabs; i++) {
         dc.nl();
         //must implemente my tab
         dc.append(i);
         dc.append(":");
         dc.append(jtabbePane.getTitleAt(i));

         IMyTab tab = (IMyTab) jtabbePane.getComponentAt(i);
         if (tab != null) {
            dc.append(tab.getTabTitle());
         } else {
            dc.append("Tab is null");
         }

      }
      dc.tabRemove();
      dc.nlLvlO(borderLayout.getLayoutComponent(BorderLayout.NORTH), "Top");
      dc.nlLvlO(borderLayout.getLayoutComponent(BorderLayout.SOUTH), "Bottom");
      dc.nlLvlO(borderLayout.getLayoutComponent(BorderLayout.WEST), "Left");
      dc.nlLvlO(borderLayout.getLayoutComponent(BorderLayout.EAST), "Right", sc);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabbedBentleyPanel");
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   /**
    * Debug specific calls to validate from our classes.
    */
   public void validate() {
      //#debug
      //toDLog().pFlow(""+getTabInternalID(), null, TabbedBentleyPanel.class, "validate", IDLog.LVL_03_FINEST, true);
      super.validate();
   }

   /**
    * Called when {@link Container#validateTree} is called.
    * @param flag
    * @param str
    */
   private void validateTBLR(int flag, String str) {
      if (BitUtils.hasFlag(flagTblr, flag)) {
         Component c = borderLayout.getLayoutComponent(str);
         if (c instanceof IMyTab) {
            ((IMyTab) c).initCheck();
         }
         if (c instanceof IMyGui) {
            IMyGui gui = ((IMyGui) c);
            gui.guiUpdate();
         }

      }
   }

   /**
    * Request validation of TBLR {@link IMyTab}.
    */
   protected void validateTree() {
      //#debug
      //toDLog().pFlow(""+getTabInternalID(), null, TabbedBentleyPanel.class, "validateTree", IDLog.LVL_03_FINEST, true);
      validateTBLR(TabPosition.TBLR_FLAG_1_TOP, BorderLayout.NORTH);
      validateTBLR(TabPosition.TBLR_FLAG_2_BOT, BorderLayout.SOUTH);
      validateTBLR(TabPosition.TBLR_FLAG_3_LEFT, BorderLayout.WEST);
      validateTBLR(TabPosition.TBLR_FLAG_4_RIGHT, BorderLayout.EAST);
      super.validateTree();
   }

}