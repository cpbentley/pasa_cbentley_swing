package pasa.cbentley.swing.imytab;

import java.util.ArrayList;
import java.util.Collections;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Locates a Tab in a multi layered {@link TabbedBentleyPanel}
 * <br>
 * Gives all the info for the root {@link TabbedBentleyPanel} to gives the user focus
 * on the {@link IMyTab}.
 * <br>
 * When state is saved in preferences, we need:
 * <li>
 * 
 * @author Charles Bentley
 *
 */
public class TabPage implements IStringable {

   /**
    * Page path as a collection of internal ids.
    * <br>
    * It does not change over the life of the
    */
   private PageStrings pageStrings;

   private SwingCtx    sc;

   /**
    * Each TabPage has a owner. Cannot be null
    */
   private IMyTab      tab;

   public TabPage(SwingCtx sc, IMyTab owner) {
      this.sc = sc;
      if (owner == null) {
         throw new NullPointerException();
      }
      this.tab = owner;
   }

   public PageStrings getPageStrings() {
      if (pageStrings == null) {
         pageStrings = new PageStrings(sc, getStrings());
      }
      return pageStrings;
   }

   /**
    * String of IDs 
    * @return
    */
   public String[] getStrings() {
      ArrayList<String> strs = new ArrayList<String>();
      String id = tab.getTabInternalID();
      strs.add(id);
      boolean isContinue = true;
      TabPosition pos = tab.getTabPosition();
      while (isContinue) {
         String toAdd = null;
         if (pos != null) {
            TabbedBentleyPanel panel = pos.getParent();
            if (panel != null) {
               toAdd = panel.getTabInternalID();
               pos = panel.getTabPosition();
            } else {
               pos = null;
            }
         }

         if (toAdd != null) {
            strs.add(toAdd);
         } else {
            isContinue = false;
         }
      }
      Collections.reverse(strs);
      String[] array = new String[strs.size()];
      return strs.toArray(array);
   }

   public IMyTab getTab() {
      return tab;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TabPage");
      //we want 
      dc.appendVarWithSpace("code", this.hashCode());
      dc.nlLvlTitleIfNull(pageStrings, "pageString");
      dc.nlLvl(tab, "IMyTab");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabPage");
      dc.nlLvlTitleIfNull(pageStrings, "pageString");
      dc.append(' ');
      dc.nlLvl1Line(tab, "");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug

}
