package pasa.cbentley.swing.image;

import java.awt.Color;
import java.util.Random;

import pasa.cbentley.swing.ctx.SwingCtx;

public class SphereColors {

   
   private SwingCtx sc;



   public SphereColors(SwingCtx sc) {
      this.sc = sc;
      
   }
   
   public Color solidCircle     = new Color(0x0153CC);

   public Color gradientShadow1 = new Color(0.0f, 0.0f, 0.0f, 0.4f);

   public Color gradientShadow2 = new Color(0.0f, 0.0f, 0.0f, 0.0f);

   public Color gradientBottom1 = new Color(1.0f, 1.0f, 1.0f, 0.0f);

   public Color gradientBottom2 = new Color(1.0f, 1.0f, 1.0f, 0.4f);

   public Color radialGradTop1  = new Color(255, 142, 203, 255);

   public Color radialGradTop2  = new Color(64, 142, 203, 0);

   public Color radialGradBot1  = new Color(1.0f, 1.0f, 1.0f, 0.4f);

   public Color radialGradBot2  = new Color(1.0f, 1.0f, 1.0f, 0.0f);

   public Color gradientEdge1   = new Color(6, 76, 160, 127);

   public Color gradientEdge2   = new Color(0.0f, 0.0f, 0.0f, 0.8f);

   
   
   public void random(boolean alpha) {
      Random r = sc.getUC().getRandom();
      
      solidCircle = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientShadow1 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientShadow2 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientBottom1 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientBottom2 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      radialGradTop1 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      radialGradTop2 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      radialGradBot1 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      radialGradBot2 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientEdge1 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
      gradientEdge2 = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256), (alpha) ? r.nextInt(256) : 255);
   }
}
