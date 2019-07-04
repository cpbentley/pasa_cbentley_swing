package pasa.cbentley.swing.filter;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Accepts Double
 * @author Charles Bentley
 *
 */
public class FilterDoubleOrEmpty extends FilterXXXOrEmpty {
   
   public FilterDoubleOrEmpty(SwingCtx sc) {
      super(sc);
   }

   protected boolean testParsing(String text) {
      try {
         Double.parseDouble(text);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}