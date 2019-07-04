package pasa.cbentley.swing.window;

public class Screen {

   private int id;

   private int x;

   private int y;

   private int width;

   private int height;

   public Screen(int id, int x, int y, int width, int height) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public int getId() {
      return id;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public String getName() {
      String screenName = "";
      if (id == 0) {
         screenName = "Main";
      } else {
         if (y < -100) {
            screenName += "Top";
         } else if (y > 100) {
            screenName += "Bottom";
         }
         if (x < -100) { //
            screenName += "Left";
         } else if (x > 100) {
            screenName += "Right";
         } else {
            //nothing
         }

      }
      String scr = screenName + " screen #" + id + " [" + x + "," + y + "-" + width + " " + height + "]";
      return scr;
   }

}
