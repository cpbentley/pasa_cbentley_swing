package pasa.cbentley.swing.menu;

import static java.awt.event.KeyEvent.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.KeyStroke;

import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BMenuLazy;

public class MenuDebug extends BMenuLazy implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = -4428915250891847654L;

   private BMenuItem         itemGuiUpdate;

   private BMenuItem         itemRevalidateTree;

   private BMenuItem         itemToStringRoot;

   private BMenuItem         itemToStringTab;

   private BMenu             menuToString;

   public MenuDebug(SwingCtx sc) {
      super(sc, "menu.debug");
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == itemToStringRoot) {
         //String of the whole application

         //#debug
         sc.toDLog().pAlways(sc.toStringRunnerAll(), null, MenuDebug.class, "actionPerformed", ITechLvl.LVL_05_FINE, false);

      } else if (src == itemToStringTab) {
         IMyTab focusedTab = sc.getFocusedTab();
         //#debug
         sc.toDLog().pAlways("Debug Focused tab", focusedTab, MenuDebug.class, "actionPerformed", ITechLvl.LVL_05_FINE, false);
      } else if (src == itemGuiUpdate) {
         sc.guiUpdate();
      } else if (src == itemRevalidateTree) {
         sc.revalidateSwingTree();
      }
   }

   protected void initMenu() {

      itemGuiUpdate = new BMenuItem(sc, this, "menu.debug.guiupdate");
      itemGuiUpdate.setMnemonic(VK_G);
      itemGuiUpdate.setAccelerator(KeyStroke.getKeyStroke(VK_G, modCtrlShift));

      itemRevalidateTree = new BMenuItem(sc, this, "menu.debug.revalidate");
      itemRevalidateTree.setMnemonic(VK_R);
      itemRevalidateTree.setAccelerator(KeyStroke.getKeyStroke(VK_R, modCtrlShift));

      menuToString = new BMenu(sc, "menu.tostring");
      menuToString.setMnemonic(VK_S);

      itemToStringRoot = new BMenuItem(sc, this, "menu.debug.tostring.root");
      itemToStringRoot.setMnemonic(VK_A);
      itemToStringRoot.setAccelerator(KeyStroke.getKeyStroke(VK_A, modCtrlAltShift));

      itemToStringTab = new BMenuItem(sc, this, "menu.debug.tostring.tabcurrent");
      itemToStringTab.setMnemonic(VK_T);
      itemToStringTab.setAccelerator(KeyStroke.getKeyStroke(VK_T, modCtrlAltShift));

      menuToString.add(itemToStringRoot);
      menuToString.add(itemToStringTab);

      this.add(itemGuiUpdate);
      this.add(itemRevalidateTree);
      this.add(menuToString);

   }

}
