package pasa.cbentley.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BMenu;
import pasa.cbentley.swing.widgets.b.BMenuItem;
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
   private static final long serialVersionUID       = 3993125157226169609L;

   public static final int   TYPE_0_FS              = 0;

   public static final int   TYPE_1_MAX             = 1;

   public static final int   TYPE_2_MID             = 2;

   public static final int   TYPE_3_HALF_LEFT       = 3;

   public static final int   TYPE_4_HALF_RIGHT      = 4;

   public static final int   TYPE_5_HALF_TOP        = 5;

   public static final int   TYPE_6_HALF_BOTTOM     = 6;

   public static final int   TYPE_7_3RD_VERT_1_LFT  = 7;

   public static final int   TYPE_8_3RD_VERT_2_MID  = 8;

   public static final int   TYPE_9_3RD_VERT_3_RIT  = 9;

   public static final int   TYPE_10_3RD_HORI_1_TOP = 10;

   public static final int   TYPE_11_3RD_HORI_2_MID = 11;

   public static final int   TYPE_12_3RD_HORI_3_BOT = 12;

   private BMenu             menuMidTo;

   private BMenu             menuMaxTo;

   private BMenu             menuFullscreenTo;

   private BMenuItem         menuItemClose;

   private BMenuItemScreen   currentMid;

   private BMenuItemScreen   currentFS;

   private BMenuItemScreen   currentMax;

   private BMenuItemScreen   currentHalfLeft;

   private BMenuItemScreen   currentHalfRight;

   private BMenuItemScreen   current3rdLeft;

   private BMenuItemScreen   current3rdRight;

   private BMenuItemScreen   current3rdMid;

   public MenuWindow(SwingCtx sc) {
      super(sc, "menu.window");

      this.addMenuListener(this);

      currentFS = new BMenuItemScreen(sc, this, "cmd.windowto.fs", TYPE_0_FS, -1);
      currentMax = new BMenuItemScreen(sc, this, "cmd.windowto.max", TYPE_1_MAX, -1);
      currentMid = new BMenuItemScreen(sc, this, "cmd.windowto.mid", TYPE_2_MID, -1);
      currentHalfLeft = new BMenuItemScreen(sc, this, "cmd.windowto.hleft", TYPE_3_HALF_LEFT, -1);
      currentHalfRight = new BMenuItemScreen(sc, this, "cmd.windowto.hright", TYPE_4_HALF_RIGHT, -1);

      current3rdLeft = new BMenuItemScreen(sc, this, "cmd.windowto.3left", TYPE_7_3RD_VERT_1_LFT, -1);
      current3rdRight = new BMenuItemScreen(sc, this, "cmd.windowto.3right", TYPE_9_3RD_VERT_3_RIT, -1);
      current3rdMid = new BMenuItemScreen(sc, this, "cmd.windowto.3mid", TYPE_8_3RD_VERT_2_MID, -1);

      menuMidTo = new BMenu(sc, "menu.midto");
      menuMaxTo = new BMenu(sc, "menu.maxto");
      menuFullscreenTo = new BMenu(sc, "menu.fullscreento");

   }

   /**
    * Called by menu factory when to include a menu item.
    */
   public void addCloseItem() {
      menuItemClose = new BMenuItem(sc, this, "menu.close");
   }

   public void buildDefault(int numScreens) {
      this.removeAll();
      menuMidTo.removeAll();
      menuMaxTo.removeAll();
      menuFullscreenTo.removeAll();

      //fullscreen support without a menu not done yet
      //this.add(currentFS);
      this.add(currentMax);
      this.add(currentMid);
      this.add(currentHalfLeft);
      this.add(currentHalfRight);
      this.addSeparator();
      this.add(current3rdLeft);
      this.add(current3rdMid);
      this.add(current3rdRight);

      if (numScreens > 1) {
         this.addSeparator();
         this.add(menuMidTo);
         this.add(menuMaxTo);
         //this.add(menuFullscreenTo);
      }
      if (menuItemClose != null) {
         this.addSeparator();
         this.add(menuItemClose);
      }
   
   }

   public void menuCanceled(MenuEvent arg0) {

   }

   public void menuDeselected(MenuEvent arg0) {
   }

   public void menuSelected(MenuEvent e) {
      //populate sub menus
      List<Screen> screens = sc.getUtils().getScreens();
      int numScreens = screens.size();
      //after sub menus have been built
      buildDefault(numScreens);
      if (numScreens > 1) {
         for (Iterator it = screens.iterator(); it.hasNext();) {
            Screen screen = (Screen) it.next();
            int i = screen.getId();
            String scrname = screen.getName();
            BMenuItemScreen jiMid = new BMenuItemScreen(sc, this, null, TYPE_2_MID, i);
            jiMid.setText(scrname);
            menuMidTo.add(jiMid);

            BMenuItemScreen jiMax = new BMenuItemScreen(sc, this, null, TYPE_1_MAX, i);
            jiMax.setText(scrname);
            menuMaxTo.add(jiMax);

            //         ScreenMenuItem jiFs = new ScreenMenuItem(scr, TYPE_0_FS, i);
            //         jiFs.addActionListener(this);
            //         menuFullscreenTo.add(jiFs);
         }
      }

      
      sc.guiUpdateOnChildrenMenu(this);
   }

   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == menuItemClose) {
         CBentleyFrame frame = (CBentleyFrame) sc.getFrame(this);
         frame.cmdClose();
      } else if (e.getSource() instanceof BMenuItemScreen) {
         BMenuItemScreen smi = (BMenuItemScreen) e.getSource();
         int type = smi.getType();
         int screenID = smi.getScreenID();
         JFrame frame = sc.getFrame(this);
         if (frame != null) {
            Screen screen = sc.getUtils().getScreen(screenID);
            if (screen == null) {
               //get the screen on which the frame is
               screen = sc.getUtils().getScreen(frame);
            }
            if (screen != null) {
               applyType(type, screenID, frame, screen);
            }
         }
         //position frame
      }
   }

   private void applyType(int type, int screenID, JFrame frame, Screen screen) {
      if (type == MenuWindow.TYPE_0_FS) {
         if (frame instanceof CBentleyFrame) {
            CBentleyFrame cf = (CBentleyFrame) frame;
            cf.setFullScreenTrue(screenID);
         }
      } else if (type == MenuWindow.TYPE_1_MAX) {
         frame.setSize(screen.getWidth(), screen.getHeight());
         frame.setLocation(screen.getX(), screen.getY());

         sc.getLog().consoleLog("Frame " + frame.getTitle() + " maximized to " + screen.getName());
      } else if (type == MenuWindow.TYPE_2_MID) {
         int nw = screen.getWidth() * 3 / 4;
         int nh = screen.getHeight() * 3 / 4;
         int nx = screen.getX() + (screen.getWidth() - nw) / 2;
         int ny = screen.getY() + (screen.getHeight() - nh) / 2;
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);

         sc.getLog().consoleLog("Frame " + frame.getTitle() + " centered to " + screen.getName());
      } else if (type == MenuWindow.TYPE_3_HALF_LEFT) {
         int nw = screen.getWidth() / 2;
         int nh = screen.getHeight();
         int nx = screen.getX();
         int ny = screen.getY();
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);

         sc.getLog().consoleLog("Frame " + frame.getTitle() + " half left to " + screen.getName());
      } else if (type == MenuWindow.TYPE_4_HALF_RIGHT) {
         int nw = screen.getWidth() / 2;
         int nh = screen.getHeight();
         int nx = screen.getX() + screen.getWidth() / 2;
         int ny = screen.getY();
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);
         sc.getLog().consoleLog("Frame " + frame.getTitle() + " half right to " + screen.getName());
      } else if (type == MenuWindow.TYPE_7_3RD_VERT_1_LFT) {
         int nw = screen.getWidth() / 3;
         int nh = screen.getHeight();
         int nx = screen.getX();
         int ny = screen.getY();
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);
         sc.getLog().consoleLog("Frame " + frame.getTitle() + " 3rd left of " + screen.getName());
      } else if (type == MenuWindow.TYPE_8_3RD_VERT_2_MID) {
         int nw = screen.getWidth() / 3;
         int nh = screen.getHeight();
         int nx = screen.getX() + nw;
         int ny = screen.getY();
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);
         sc.getLog().consoleLog("Frame " + frame.getTitle() + " 3rd mid to " + screen.getName());
      } else if (type == MenuWindow.TYPE_9_3RD_VERT_3_RIT) {
         int nw = screen.getWidth() / 3;
         int nh = screen.getHeight();
         int nx = screen.getX() + 2*nw;
         int ny = screen.getY();
         frame.setSize(nw, nh);
         frame.setLocation(nx, ny);
         sc.getLog().consoleLog("Frame " + frame.getTitle() + " 3rd right to " + screen.getName());
      }
   }
}
