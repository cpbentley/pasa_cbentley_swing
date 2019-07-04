package pasa.cbentley.swing.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class SphereFactory {

   
   
   public void drawSphere(Graphics2D g2, int width, int height, SphereColors colors) {
   // Retains the previous state
      Paint oldPaint = g2.getPaint();

      // Fills the circle with solid blue color
      g2.setColor(colors.solidCircle);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Adds shadows at the top
      Paint p;
      p = new GradientPaint(0, 0, colors.gradientShadow1, 0, height, colors.gradientShadow2);
      g2.setPaint(p);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Adds highlights at the bottom 
      p = new GradientPaint(0, 0, colors.gradientBottom1, 0, height, colors.gradientBottom2);
      g2.setPaint(p);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Creates dark edges for 3D effect
      Point2D.Double centerBot = new Point2D.Double(width / 2.0, height / 2.0);
      float[] fractionsEdge = new float[] { 0.0f, 1.0f };
      Color[] colorsEdges = new Color[] { colors.gradientEdge1, colors.gradientEdge2 };
      p = new RadialGradientPaint(centerBot, width / 2.0f, fractionsEdge, colorsEdges);
      g2.setPaint(p);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Adds oval inner highlight at the bottom
      Point2D.Double center = new Point2D.Double(width / 2.0, height * 1.5);
      float radius = width / 2.3f;
      Point2D.Double focus = new Point2D.Double(width / 2.0, height * 1.75 + 6);
      float[] fractions = new float[] { 0.0f, 0.8f };
      Color[] colorsRadialGradTop = new Color[] { colors.radialGradTop1, colors.radialGradTop2 };
      p = new RadialGradientPaint(center, radius, focus, fractions, colorsRadialGradTop, RadialGradientPaint.CycleMethod.NO_CYCLE, RadialGradientPaint.ColorSpaceType.SRGB, AffineTransform.getScaleInstance(1.0, 0.5));
      g2.setPaint(p);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Adds oval specular highlight at the top left
      Color[] colorsRadialGradBot = new Color[] { colors.radialGradBot1, colors.radialGradBot2 };
      float radiusBot = width / 1.4f;
      Point2D.Double focusBot = new Point2D.Double(45.0, 25.0);
      float[] fractionsBot = new float[] { 0.0f, 0.5f };
      p = new RadialGradientPaint(centerBot, radiusBot, focusBot, fractionsBot, colorsRadialGradBot, RadialGradientPaint.CycleMethod.NO_CYCLE);
      g2.setPaint(p);
      g2.fillOval(0, 0, width - 1, height - 1);

      // Restores the previous state
      g2.setPaint(oldPaint);

   }
   
   public BufferedImage getSphere(int width, int height, SphereColors colors) {
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = (Graphics2D) bi.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      drawSphere(g2, width, height, colors);
      g2.dispose();
      return bi;
   }
}
