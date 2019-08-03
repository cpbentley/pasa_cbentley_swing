package pasa.cbentley.swing.interfaces;

import pasa.cbentley.core.src4.logging.IStringable;

/**
 * Provides an class ID selector for getting a preference value.
 * 
 * Can be implemented by a class or you can use a dummy container for a key with
 * 
 * @author Charles Bentley
 *
 */
public interface IStringPrefIDable extends IStringable {

   
   public String getSelectorKeyPrefID();
}
