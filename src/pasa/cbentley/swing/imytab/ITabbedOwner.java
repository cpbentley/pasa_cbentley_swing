package pasa.cbentley.swing.imytab;

/**
 * Recipient of a {@link TabbedBentleyPanel}
 * @author Charles Bentley
 *
 */
public interface ITabbedOwner {

   
   /**
    * <li> {@link TabPosition#POS_1_TOP}
    * @param tab
    * @param position
    */
   public void moveTo(IMyTab tab, int position);
}
