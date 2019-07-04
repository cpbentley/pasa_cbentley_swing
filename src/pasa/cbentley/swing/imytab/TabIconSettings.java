package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.IconFamily;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Track user preferences for tab icons.
 * <br>
 * 
 * Each tab may have a specific icon when selected, when frame selected.
 * We may use a filter on the icon
 * 
 * User may want smaller icons or bigger icons.
 * <br>
 * 
 * User mapping for 
 * 
 * {@link TabbedBentleyPanel} has its own policy
 * 
 * Extand this class and set it to the {@link SwingCtx}
 * @author Charles Bentley
 *
 */
public class TabIconSettings implements IStringable {

   private final String themeTitle;

   /**
    * Theme families
    */
   private String[]     themeTitles;

   private String       rootDir = "/icons/";

   public String getThemeID() {
      return themeTitle;
   }

   private final String[] iconSizeMapping;

   private final SwingCtx sc;

   public TabIconSettings(SwingCtx sc, String themeTitle, String[] sizeMappings) {
      this.sc = sc;
      this.themeTitle = themeTitle;
      iconSizeMapping = sizeMappings;

   }

   public String getPathIcon(String id, String category, int size, int mode) {
      String modeSuffix = null;
      if (mode == IconFamily.ICON_MODE_1_SELECTED) {
         modeSuffix = "sel";
      }
      String sizeSuffix = iconSizeMapping[size];
      StringBBuilder sb = new StringBBuilder(64);
      sb.append(rootDir);
      sb.append(themeTitle);
      sb.append('/');
      sb.append(category);
      sb.append('/');
      sb.append(id);
      sb.append('_');
      sb.append(sizeSuffix);
      if (modeSuffix != null) {
         sb.append('_');
         sb.append(modeSuffix);
      }
      sb.append(".png");
      return sb.toString();
   }
   
   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TabIconSettings");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabIconSettings");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
   

}
