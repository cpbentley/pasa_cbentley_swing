package pasa.cbentley.swing.interfaces;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Unifying interface between {@link JMenu} and {@link JPopupMenu}
 * 
 * @author Charles Bentley
 *
 */
public interface IMenuSwing {

   public void addSeparator();
   
   public JMenuItem add(JMenuItem menuItem);
}
