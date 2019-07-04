package pasa.cbentley.swing.effects;

import java.util.Random;

public class RainGenerator extends Thread {

   private int[][] pointer;

   Random          rando = null;

   private int     width;

   private int     height;

   private Rain    rain;

   public RainGenerator(Rain rain, int[][] pointer, int width, int height) {
      this.rain = rain;
      this.pointer = pointer;
      this.width = width;
      this.height = height;
      rando = new Random(System.currentTimeMillis());
   }

   public void run() {
      int it = 1;
      while (true)

         try {
            rain.addRain(pointer, Math.abs(rando.nextInt()) % width, Math.abs(rando.nextInt()) % height);

            it++;
            sleep(10);
            if (it % 5 == 0)
               sleep(rando.nextInt(1000));

         } catch (Exception e) {
            e.printStackTrace(System.out);
         }
   }
}
