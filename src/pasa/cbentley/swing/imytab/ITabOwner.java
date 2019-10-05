package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Interface through which an {@link IMyTab} can talk to its parent
 * 
 * Owner sets itself 
 * @author Charles Bentley
 *
 */
public interface ITabOwner extends IStringable {

   public void tabIsFinished(IMyTab tab);
}
