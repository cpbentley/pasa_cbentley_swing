package pasa.cbentley.swing.image;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Timer;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Class that manages the cursor on a {@link Component}.
 * <br>
 * It is able to handle animated GIFs or any list of {@link BufferedImage}
 * @author Charles Bentley
 *
 */
public class CursorSwing {
   private int            currentIndex;

   private int            id;

   private Component      component;

   private Timer          timer;

   private final SwingCtx sc;

   public CursorSwing(SwingCtx sc, Component c) {
      this.sc = sc;
      this.component = c;
   }

   /**
    * 
    * @param s
    * @return
    */
   public boolean updateCursor(Object s) {
      currentIndex = 0;
      if (timer != null) {
         timer.stop();
      }
      if (s == null) {
         component.setCursor(null);
         return true;
      } else {
         if (s instanceof String) {
            String str = (String) s;
            final List<Cursor> cursorsList = new ArrayList<Cursor>();
            try {
               List<BufferedImage> frames = sc.getBufImgUtils().getFrames(str);
               for (BufferedImage image : frames) {
                  cursorsList.add(Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "cursor image"));
               }
               if (cursorsList.size() > 1) {
                  timer = new Timer(50, new ActionListener() {
                     public void actionPerformed(ActionEvent actionEvent) {
                        component.setCursor(cursorsList.get(currentIndex++));
                        if (currentIndex >= cursorsList.size())
                           currentIndex = 0;
                     }
                  });
                  timer.start();
               } else {
                  component.setCursor(cursorsList.get(0));
               }
            } catch (IOException e) {
               e.printStackTrace();
               return false;
            }
            return true;
         }
      }
      return false;
   }

}
