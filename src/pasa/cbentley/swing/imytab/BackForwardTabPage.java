package pasa.cbentley.swing.imytab;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.actions.BackAction;
import pasa.cbentley.swing.actions.ForwardAction;
import pasa.cbentley.swing.actions.IBackForwardable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Implements the Back and Forward actions on {@link IMyTab}
 * 
 * @author Charles Bentley
 *
 */
public class BackForwardTabPage implements IBackForwardable {

   private BackAction          actionBack;

   private ForwardAction       actionForward;

   /**
    * 
    */
   private TabPage             currentActiveTab;

   private LinkedList<TabPage> historyBack    = new LinkedList<TabPage>();

   /**
    * 
    */
   private LinkedList<TabPage> historyForward = new LinkedList<TabPage>();

   /**
    * Interface to the class that will handle the back and forward event commands
    */
   private ITabPageSettable    setter;

   private SwingCtx            sc;

   /**
    * By default back/forward actions are disabled
    * @param sc
    * @param setter
    */
   public BackForwardTabPage(SwingCtx sc, ITabPageSettable setter) {
      super();
      this.sc = sc;
      this.setter = setter;
      actionBack = new BackAction(sc, this, KeyEvent.VK_LEFT);
      actionForward = new ForwardAction(sc, this, KeyEvent.VK_RIGHT);

      actionBack.setEnabled(false);
      actionForward.setEnabled(false);
   }

   /**
    * When a new tab is being activated using the normal ui.
    * <br>
    * This should not be called when a page is set because of a Back/Forward action.
    * The user of {@link BackForwardTabPage} is responsible to call this method.
    * <br>
    * Handle the fact that external user may call several times this method
    * with the same page.
    * @param tab
    */
   public void addTabPage(TabPage tab) {
      //#debug
      sc.toDLog().pFlow("", tab, BackForwardTabPage.class, "addTabPage", ITechLvl.LVL_05_FINE, true);

      //#debug
      sc.toDLog().pFlow("", this, BackForwardTabPage.class, "addTabPage", ITechLvl.LVL_05_FINE, true);
      //setting the same tab as back.. or misuse of API.
      if (tab == null || tab == currentActiveTab) {
         return;
      }

      //this will be null at the first start
      if (currentActiveTab != null) {
         //we have a new tab. add it to history back
         historyBack.add(currentActiveTab);
      }

      //unless we are setting the last back
      actionBack.setEnabled(true);

      //set tab as active
      currentActiveTab = tab;
   }

   /**
    * <li> Reads from back list
    * <li> add current page to forward list
    */
   public void cmdNavBack() {
      if (!historyBack.isEmpty()) {
         //put currently active to the forward queue
         if (currentActiveTab != null) {
            historyForward.add(currentActiveTab);
            //enable actionForward
            actionForward.setEnabled(true);
         }
         TabPage newCurrentPage = historyBack.removeLast();
         //for the purpose of this class. this tab is now active
         currentActiveTab = newCurrentPage;

         //#debug
         sc.toDLog().pCmd(" to ", newCurrentPage, BackForwardTabPage.class, "cmdNavBack", ITechLvl.LVL_05_FINE, true);

         //make it active for the user as well
         setter.setActivePage(newCurrentPage);
      } else {
         actionBack.setEnabled(false);
      }
   }

   public void cmdNavForward() {
      if (!historyForward.isEmpty()) {
         TabPage newCurrentPage = historyForward.removeLast();
         if (currentActiveTab != null) {
            historyBack.add(currentActiveTab);
            actionBack.setEnabled(true);
            //disable button if history is empty
            actionForward.setEnabled(!historyForward.isEmpty());
         }
         currentActiveTab = newCurrentPage;
         //#debug
         sc.toDLog().pCmd(" to ", newCurrentPage, BackForwardTabPage.class, "cmdNavForward", ITechLvl.LVL_05_FINE, true);
         setter.setActivePage(newCurrentPage); //this will set active through the addTabPage
      }
   }

   public BackAction getActionBack() {
      return actionBack;
   }

   public ForwardAction getActionForward() {
      return actionForward;
   }

   public TabPage getCurrentTab() {
      return currentActiveTab;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BackForwardTabPage");
      dc.nlLvl("currentActive", currentActiveTab);

      dc.nl();
      dc.append("History Forward #" + historyForward.size());
      int count = 1;
      for (TabPage page : historyForward) {
         dc.nlLvlOneLine("page#" + count, page);
         count++;
      }

      dc.nl();
      dc.append("History Back #" + historyBack.size());
      count = 1;
      for (TabPage page : historyBack) {
         dc.nlLvlOneLine("page#" + count, page);
         count++;
      }
      dc.nlLvl("ITabPageSettable", setter);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BackForwardTabPage");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
