package pasa.cbentley.swing.cache;

import java.util.HashMap;

import javax.swing.Icon;

import pasa.cbentley.swing.ctx.SwingCtx;

public class IconCache implements IIconCache {

   private HashMap<String, Icon> cache = new HashMap<String, Icon>();
   private SwingCtx sc;
   
   public IconCache(SwingCtx sc) {
      this.sc = sc;
   }
   public Icon getIcon(String key) {
      Icon ic = cache.get(key);
      if(ic == null) {
         //create icon 
         ic = createIcon(key);
         cache.put(key, ic);
      }
      return ic;
   }

   public Icon createIcon(String key) {
      return sc.createImageIcon(key, "");
   }
}
