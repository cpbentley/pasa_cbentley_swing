package pasa.cbentley.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabAction;
import pasa.cbentley.swing.imytab.TabPage;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BMenuItem;

/**
 * The navigate Menu is added to most Frames using the {@link IMyTab} framework.
 * <br>
 * When a {@link TabbedBentleyPanel} is active, it registers possible tabs in the Navigation Menu
 * of its owner Frame.
 * <br>
 * {@link MenuNavigate} has a reference to current {@link TabPage}. This gives the tree context.
 * <br>
 * owner->MenuBar->NavigationMenu
 * @author Charles Bentley
 *
 */
public class MenuNavigate extends BMenu implements ActionListener {

   private SwingCtx       sc;

   private BMenuItem      jmiTabToFrame;

   private JMenu          menuTabGoTo;

   private BMenuItem      jmiTabToTop;

   private BMenuItem      jmiTabToCenter;

   private BMenuItem      jmiTabToRight;

   private BMenuItem      jmiTabToLeft;

   private BMenuItem      jmiTabToBottom;

   private BMenuItem      jmiTabToDefault;

   private BMenuItem      jmiTabToBackHistory;

   private ActionListener myListener;

   private BMenuItem      duplicateTabToFrame;

   public MenuNavigate(SwingCtx sc) {
      super(sc, "menu.navigation");
      this.sc = sc;

      //TODO try actions here?
      TabAction ac = new TabAction();
      //
    
      this.add(sc.getBackForward().getActionBack());
      this.add(sc.getBackForward().getActionForward());
      this.addSeparator();

      //is update with tab name
      menuTabGoTo = new BMenu(sc, "menu.navigation.movetab");
      this.add(menuTabGoTo);

      duplicateTabToFrame = new BMenuItem(sc, this, "menu.navigation.duplicatetab");
      this.add(duplicateTabToFrame);

      
      jmiTabToFrame = new BMenuItem(sc, this, "menu.tab.toframe");
      jmiTabToCenter = new BMenuItem(sc, this, "menu.tab.tocenter");
      jmiTabToDefault = new BMenuItem(sc, this, "menu.tab.todefault");

      jmiTabToTop = new BMenuItem(sc, this, "menu.tab.totop");
      jmiTabToBottom = new BMenuItem(sc, this, "menu.tab.tobottom");
      jmiTabToLeft = new BMenuItem(sc, this, "menu.tab.toleft");
      jmiTabToRight = new BMenuItem(sc, this, "menu.tab.toright");
      jmiTabToBackHistory = new BMenuItem(sc, this, "menu.tab.back");

      
      menuTabGoTo.add(jmiTabToFrame);
      menuTabGoTo.add(jmiTabToCenter);
      menuTabGoTo.add(jmiTabToDefault);
      menuTabGoTo.addSeparator();
      menuTabGoTo.add(jmiTabToTop);
      menuTabGoTo.add(jmiTabToBottom);
      menuTabGoTo.add(jmiTabToLeft);
      menuTabGoTo.add(jmiTabToRight);
      menuTabGoTo.addSeparator();
      menuTabGoTo.add(jmiTabToBackHistory);
      

      enableDefaultActioners();

   }

   private void enableDefaultActioners() {

   }

   /**
    * Listener that will listener to actions
    * @param actionListener
    */
   public void setMyListener(ActionListener actionListener) {
      this.myListener = actionListener;
   }

   public void actionPerformed(ActionEvent e) {
      if (myListener != null) {
         myListener.actionPerformed(e);
      } else {
         Object src = e.getSource();
         IMyTab focusedTab = sc.getFocusedTab();
         if (focusedTab == null) {
            return;
         }
         if (src == jmiTabToFrame) {
            focusedTab.getTabPosition().getParent().cmdTabToFrame(focusedTab);
         } else if (src == jmiTabToDefault) {
            focusedTab.getTabPosition().getParent().cmdTabToDefault(focusedTab);
         } else if (src == jmiTabToCenter) {
            focusedTab.getTabPosition().getParent().cmdTabToCenter(focusedTab);
         } else if (src == jmiTabToTop) {
            focusedTab.getTabPosition().getParent().cmdTabToTop(focusedTab);
         } else if (src == jmiTabToBottom) {
            focusedTab.getTabPosition().getParent().cmdTabToBottom(focusedTab);
         } else if (src == jmiTabToLeft) {
            focusedTab.getTabPosition().getParent().cmdTabToLeft(focusedTab);
         } else if (src == jmiTabToRight) {
            focusedTab.getTabPosition().getParent().cmdTabToRight(focusedTab);
         } else if (src == jmiTabToRight) {
            focusedTab.getTabPosition().getParent().cmdTabToBackHistory(focusedTab);
         } else if (src == duplicateTabToFrame) {
            focusedTab.getTabPosition().getParent().cmdDuplicateTabToFrame(focusedTab);
         }
      }
   }

   public void guiUpdate() {
      super.guiUpdate();
   }

}
