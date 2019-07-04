package pasa.cbentley.swing.filter;

import pasa.cbentley.swing.ctx.SwingCtx;

public class FilterIntOrEmpty extends FilterXXXOrEmpty {

   public FilterIntOrEmpty(SwingCtx sc) {
      super(sc);
   }

   protected boolean testParsing(String text) {
      try {
         Integer.parseInt(text);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}