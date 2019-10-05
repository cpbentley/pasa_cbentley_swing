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
import javax.swing.JFrame;

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
      if (icon == null) {
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

   /**
    * Return the {@link Screen} for the given ID in this current configurations.
    * 
    * ID might change when screens are dis/connected
    * @param screenID
    * @return
    */
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

   /**
    * Get the screen on which this frame majority area
    * choose the best Screen for this frame
    * @param frame
    * @return null if headless
    */
   public Screen getScreen(JFrame frame) {
      int fx = frame.getX();
      int fy = frame.getY();
      int fw = frame.getWidth();
      int fh = frame.getHeight();
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gds = ge.getScreenDevices();
      if (gds == null || gds.length == 0) {
         return null;
      }
      GraphicsDevice gd = gds[0];
      Rectangle bounds = gd.getDefaultConfiguration().getBounds();
      int screenIndex = 0;
      for (int i = 1; i < gds.length; i++) {
         Rectangle ibounds = gds[i].getDefaultConfiguration().getBounds();
         int screenX = (int) ibounds.getX();
         int screenY = (int) ibounds.getY();
         int screenW = (int) ibounds.getWidth();
         int screenH = (int) ibounds.getHeight();
         if (screenX <= fx && fx < screenX + screenW) {
            if (screenY <= fy && fy < screenY + screenH) {
               screenIndex = i;
               bounds = ibounds;
            }
         }
         //check size in the scree
      }
      Screen screen = new Screen(screenIndex, bounds.x, bounds.y, bounds.width, bounds.height);
      return screen;
   }

}
