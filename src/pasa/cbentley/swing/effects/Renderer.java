package pasa.cbentley.swing.effects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

import pasa.cbentley.swing.ctx.SwingCtx;

public class Renderer extends JComponent implements Runnable, MouseMotionListener {

   private static final long serialVersionUID = 1L;

   private long              before           = 0;

   //float framespd = 0;
   private int               fps              = 0;

   private int               frameCounter;

   private long              now              = 0;

   private boolean           showFps          = true;

   private MemoryImageSource source;

   private Image             vramBundle;

   private SwingCtx          sc;

   private IModeler          modeler;

   private RasterOffscreen   raster;

   /**
    * Sets the component to the size of the raster
    * @param sc
    * @param modeler
    * @param raster
    */
   public Renderer(SwingCtx sc, IModeler modeler, RasterOffscreen raster) {
      this.sc = sc;
      this.modeler = modeler;
      this.raster = raster;
      init();
   }

   public void init() {
      int rendererdWidth = raster.getWidth();
      int rendererdHeight = raster.getHeight();
      this.setSize(rendererdWidth, rendererdHeight);

      setBackground(Color.black);

      ColorModel colorModel = new DirectColorModel(32, 0xff0000, 0x00ff00, 0x0000ff, 0);

      source = new MemoryImageSource(rendererdWidth, rendererdHeight, colorModel, raster.offScreenRaster, 0, rendererdWidth);
      source.setAnimated(true);
      source.setFullBufferUpdates(true);

      vramBundle = createImage(source);

      this.addMouseMotionListener(this);

      //this.modeler = new Tunnel(this);
      //this.modeler = new FreeTunnel(this);
      //this.modeler = new FreeInterpolatedTunnel(this);
   }

   public void paint(Graphics g) {

      frameCounter++;
      this.modeler.drawOffScreen();

      source.newPixels();
      g.drawImage(vramBundle, 0, 0, getWidth(), getHeight(), this);

      if (showFps) {
         g.setColor(Color.white);
         g.drawString("fps : " + fps, 5, 15);
      }
   }

   public void run() {
      before = (int) System.currentTimeMillis();

      while (true) {
         repaint();
         now = (int) System.currentTimeMillis();
         if (now - before > 1000) {
            fps = frameCounter;
            before = now;
            frameCounter = 0;
         }

         System.out.println("Paint");
         try {
            Thread.sleep(100L);
         } catch (InterruptedException interruptedexception) {
         }

      }
   }

   public void start() {
      new Thread(this).start();
   }

   public void update(Graphics g) {
      paint(g);
   }

   public void mouseDragged(MouseEvent e) {

   }

   public void mouseMoved(MouseEvent e) {
      modeler.addMoveAt(e.getX(), e.getY());
   }

}
