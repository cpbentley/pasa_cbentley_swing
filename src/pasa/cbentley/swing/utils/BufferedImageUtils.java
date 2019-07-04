package pasa.cbentley.swing.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import pasa.cbentley.swing.ctx.SwingCtx;

public class BufferedImageUtils {

   private SwingCtx sc;
   public BufferedImageUtils(SwingCtx sc) {
      this.sc = sc;
   }
   public List<BufferedImage> getFrames(String gif) throws IOException {
      List<BufferedImage> frames = new ArrayList<BufferedImage>();
      ImageInputStream stream = null;
      File input = new File(gif);
      if (input.exists()) {
         stream = ImageIO.createImageInputStream(input);
      } else {
         InputStream in = getClass().getResourceAsStream(gif);
         stream = ImageIO.createImageInputStream(in);
      }
      ImageReader reader = ImageIO.getImageReaders(stream).next();
      reader.setInput(stream);

      int count = reader.getNumImages(true);
      for (int index = 0; index < count; index++) {
         frames.add(reader.read(index));
      }
      return frames;
   }
}
