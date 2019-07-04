package pasa.cbentley.swing.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Change the gain 
 * @author Charles Bentley
 *
 */
public class VolumeJSliderOptionPane {
   
   public static void main(final String[] args) {
      JFrame parent = new JFrame();

      final JOptionPane optionPane = new JOptionPane();
      
      JSlider slider = getSlider(optionPane);
      JLabel lab = new JLabel(new Icon() {
         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
         }

         public int getIconWidth() {
            return 450;
         }

         public int getIconHeight() {
            return 20;
         }
      });
      Object[] data = new Object[] { "Adjust sound volume: ", slider, lab };
      optionPane.setMessage(data);
      optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
      optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
      optionPane.setIcon(new Icon() {
         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.blue);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
         }

         public int getIconWidth() {
            return 48;
         }

         public int getIconHeight() {
            return 48;
         }
      });
      JDialog dialog = optionPane.createDialog(parent, "Adjust Volume");

      //was it canceled
      dialog.addComponentListener(new ComponentListener() {
         public void componentShown(ComponentEvent e) {
         }

         public void componentResized(ComponentEvent e) {
         }

         public void componentMoved(ComponentEvent e) {
         }

         public void componentHidden(ComponentEvent e) {
            //null when the X is pressed
            Object value = optionPane.getValue();
            System.out.println("value=" + value);
            int val = ((Integer)value).intValue();
            if (val == JOptionPane.OK_OPTION) {
               System.out.println("OK");
               // do YES stuff...
            } else if (val == JOptionPane.CANCEL_OPTION) {
               // do CANCEL stuff...
               System.out.println("Cancel");
            } else {
               throw new IllegalStateException("Unexpected Option");
            }
         }
      });
      dialog.setVisible(true);
     
      System.out.println("Input: " + optionPane.getInputValue());
   }

   static JSlider getSlider(final JOptionPane optionPane) {
      JSlider slider = new JSlider();
      slider.setMajorTickSpacing(10);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      ChangeListener changeListener = new ChangeListener() {
         public void stateChanged(ChangeEvent changeEvent) {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            if (!theSlider.getValueIsAdjusting()) {
               //do the intermediate callback. e.g. play a sound with new volume
               System.out.println("Value is " + theSlider.getValue());
               optionPane.setInputValue(new Integer(theSlider.getValue()));
            }
         }
      };
      slider.addChangeListener(changeListener);
      return slider;
   }

}