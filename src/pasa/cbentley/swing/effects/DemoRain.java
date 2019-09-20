package pasa.cbentley.swing.effects;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.window.CBentleyFrame;

public class DemoRain {

   private SwingCtx      sc;

   private CBentleyFrame frame;

   public DemoRain(SwingCtx sc) {
      this.sc = sc;
   }

   public static void main(final String[] args) {
      UCtx uc = new UCtx();
      SwingCtx sc = new SwingCtx(uc);
      final DemoRain demo = new DemoRain(sc);
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            demo.createAndShowGUI();
         }
      });
   }

   protected void createAndShowGUI() {
      frame = new CBentleyFrame(sc);
      frame.setDefExitProcedure();
      frame.setTitle("Image Animation Demo");
      frame.setPrefID("anim_demo");

      Renderer renderer;
      try {
         renderer = getRenderer();
         frame.add(renderer, BorderLayout.CENTER);

         frame.positionFrame();

         renderer.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private Renderer getRenderer() throws IOException {
      Image textureImage = sc.createImageFromStream("/background2.png");

      int width = 400;
      int height = 300;
      RasterOffscreen raster = new RasterOffscreen(sc, width, height);

      Rain rainModeler = new Rain(sc, raster, textureImage);
      Tunnel tunnel = new Tunnel(sc, raster, textureImage);

      Renderer renderer = new Renderer(sc, rainModeler, raster);
      return renderer;
   }
}
