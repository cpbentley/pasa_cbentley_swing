package pasa.cbentley.swing.widgets;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Add key listener to change the TAB behavior in  JTextArea to transfer focus to other component forward 
 * or backward.
 * @author Charles Bentley
 *
 */
public class TabKeyAdapter implements KeyListener {

   private JComponent c;

   public TabKeyAdapter(SwingCtx sc, JComponent c) {
      this.c = c;
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_TAB) {
         if (e.getModifiers() > 0) {
            c.transferFocusBackward();
         } else {
            c.transferFocus();
         }
         e.consume();
      }
   }

   public void keyReleased(KeyEvent e) {
   }
}
