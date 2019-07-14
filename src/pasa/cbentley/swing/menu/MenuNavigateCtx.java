package pasa.cbentley.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyTab;
import pasa.cbentley.swing.imytab.TabPage;
import pasa.cbentley.swing.imytab.TabbedBentleyPanel;

/**
 * The navigate Menu is added to most Frames using the {@link IMyTab} framework.
 * <br>
 * When a {@link TabbedBentleyPanel} is active, it registers possible tabs in the Navigation Menu
 * of its owner Frame.
 * <br>
 * {@link MenuNavigateCtx} has a reference to current {@link TabPage}. This gives the tree context.
 * <br>
 * owner->MenuBar->NavigationMenu
 * 
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class MenuNavigateCtx extends JMenu implements ActionListener {

   private SwingCtx  sc;

   private JMenuItem jmiTabToFrame;

   private JMenu menuTabGoTo;

   private JMenu menuTabbedFrames;

   public MenuNavigateCtx(SwingCtx sc) {
      this.sc = sc;
      //
      jmiTabToFrame = new JMenuItem("Tab to Frame");
      jmiTabToFrame.addActionListener(this);
      
      menuTabGoTo = new JMenu("Go To Tab");
      
      //list all tab frame
      menuTabbedFrames = new JMenu("Close a Frame");
      
   }

   
   
   public void updateGUI() {
      jmiTabToFrame.setText(sc.getResString("menu.fullscreento"));
   }

   public void actionPerformed(ActionEvent e) {

   }

}
