package pasa.cbentley.swing;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.Screen;

public class SwingUtilsBentley {

   private SwingCtx sc;

   public SwingUtilsBentley(SwingCtx sc) {
      this.sc = sc;
   }

   public List<Screen> getScreens() {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      ArrayList<Screen> screens = new ArrayList<Screen>(gds.length);
      for (int i = 0; i < gds.length; i++) {
         GraphicsDevice gd = gds[i];
         Rectangle bounds = gd.getDefaultConfiguration().getBounds();

         Screen screen = new Screen(i, bounds.x, bounds.y, bounds.width, bounds.height);

         screens.add(screen);
      }
      return screens;
   }

   /**
    * Transforms an {@link Icon} into an {@link Image}
    * <br>
    * If icon is null, returns null
    * @param icon
    * @return
    */
   public Image iconToImage(Icon icon) {
      if(icon ==null) {
         return null;
      }
      if (icon instanceof ImageIcon) {
         return ((ImageIcon) icon).getImage();
      } else {
         int w = icon.getIconWidth();
         int h = icon.getIconHeight();
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         GraphicsDevice gd = ge.getDefaultScreenDevice();
         GraphicsConfiguration gc = gd.getDefaultConfiguration();
         BufferedImage image = gc.createCompatibleImage(w, h);
         Graphics2D g = image.createGraphics();
         icon.paintIcon(null, g, 0, 0);
         g.dispose();
         return image;
      }
   }

   public Screen getScreen(int screenID) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      Screen screen = null;
      if (screenID >= 0 && screenID < gds.length) {
         Rectangle bounds = gds[screenID].getDefaultConfiguration().getBounds();
         screen = new Screen(screenID, bounds.x, bounds.y, bounds.width, bounds.height);
      }
      return screen;
   }

}
