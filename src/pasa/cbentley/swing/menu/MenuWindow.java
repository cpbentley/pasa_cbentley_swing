package pasa.cbentley.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.window.CBentleyFrame;
import pasa.cbentley.swing.window.Screen;

/**
 * The WindowMenu talks to the {@link CBentleyFrame} of?
 * @author Charles Bentley
 *
 */
public class MenuWindow extends BMenu implements MenuListener, ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = 3993125157226169609L;

   public static final int TYPE_0_FS  = 0;

   public static final int TYPE_1_MAX = 1;

   public static final int TYPE_2_MIN = 2;

   private BMenu           menuMidTo;

   private BMenu           menuMaxTo;

   private BMenu           menuFullscreenTo;

   public MenuWindow(SwingCtx sc) {
      super(sc, "menu.window");

      this.addMenuListener(this);

      menuMidTo = new BMenu(sc, "menu.midto");
      menuMaxTo = new BMenu(sc, "menu.maxto");
      menuFullscreenTo = new BMenu(sc, "menu.fullscreento");

      this.add(menuMidTo);
      this.add(menuMaxTo);
      //this.add(menuFullscreenTo);
   }

   public void menuCanceled(MenuEvent arg0) {

   }

   public void menuDeselected(MenuEvent arg0) {
   }

   public void menuSelected(MenuEvent arg0) {
      //populate sub menus

      menuMidTo.removeAll();
      menuMaxTo.removeAll();
      menuFullscreenTo.removeAll();
      List<Screen> screens = sc.getUtils().getScreens();

      for (Iterator it = screens.iterator(); it.hasNext();) {
         Screen screen = (Screen) it.next();
         int i = screen.getId();
         String scr = screen.getName();
         ScreenMenuItem jiMid = new ScreenMenuItem(scr, TYPE_2_MIN, i);
         jiMid.addActionListener(this);
         menuMidTo.add(jiMid);

         ScreenMenuItem jiMax = new ScreenMenuItem(scr, TYPE_1_MAX, i);
         jiMax.addActionListener(this);
         menuMaxTo.add(jiMax);

         //         ScreenMenuItem jiFs = new ScreenMenuItem(scr, TYPE_0_FS, i);
         //         jiFs.addActionListener(this);
         //         menuFullscreenTo.add(jiFs);
      }

   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof ScreenMenuItem) {
         ScreenMenuItem smi = (ScreenMenuItem) e.getSource();
         int type = smi.getType();
         int screenID = smi.getScreenID();
         JFrame frame = sc.getFrame(this);
         if (frame != null) {
            Screen screen = sc.getUtils().getScreen(screenID);
            if (screen != null) {
               if (type == MenuWindow.TYPE_0_FS) {
                  if (frame instanceof CBentleyFrame) {
                     CBentleyFrame cf = (CBentleyFrame) frame;
                     cf.setFullScreenTrue(screenID);
                  }
               } else if (type == MenuWindow.TYPE_1_MAX) {
                  frame.setSize(screen.getWidth(), screen.getHeight());
                  frame.setLocation(screen.getX(), screen.getY());

                  sc.getLog().consoleLog("Frame " + frame.getTitle() + " maximized to " + screen.getName());
               } else if (type == MenuWindow.TYPE_2_MIN) {
                  int nw = screen.getWidth() * 3 / 4;
                  int nh = screen.getHeight() * 3 / 4;
                  int nx = screen.getX() + (screen.getWidth() - nw) / 2;
                  int ny = screen.getY() + (screen.getHeight() - nh) / 2;
                  frame.setSize(nw, nh);
                  frame.setLocation(nx, ny);

                  sc.getLog().consoleLog("Frame " + frame.getTitle() + " centered to " + screen.getName());
               }
            }
         }
         //position frame
      }
   }
}
