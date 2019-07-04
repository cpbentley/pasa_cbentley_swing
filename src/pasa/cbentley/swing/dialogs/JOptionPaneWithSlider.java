package pasa.cbentley.swing.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JOptionPaneWithSlider extends JOptionPane {

   private JOptionPaneWithSlider owner;

   public JOptionPaneWithSlider() {
      owner = this;
      JSlider slider = getSlider(this);
      //use the label as a sizer
      JLabel lab = new JLabel(new Icon() {
         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
            g.setColor(Color.RED);
            g.drawString("" + owner.getInputValue(), x + getIconWidth() / 2, y + getIconHeight() / 2);
         }

         public int getIconWidth() {
            return 450;
         }

         public int getIconHeight() {
            return 40;
         }
      });
      Object[] data = new Object[] { "Adjust sound volume: ", slider, lab };
      this.setMessage(data);
      this.setMessageType(JOptionPane.QUESTION_MESSAGE);
      this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
      this.setIcon(new Icon() {
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
   }

   public void show(JFrame parent) {
      JDialog dialog = this.createDialog(parent, "Adjust Volume");
      dialog.setVisible(true);
   }

   /**
    * Simple test method
    * @param args
    */
   public static void main(final String[] args) {
      JFrame parent = new JFrame();
      parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JOptionPaneWithSlider ops = new JOptionPaneWithSlider();

      ops.show(parent);

      System.out.println("Final Input: " + ops.getInputValue());
   }

   private JSlider getSlider(final JOptionPane optionPane) {
      JSlider slider = new JSlider();
      slider.setMajorTickSpacing(10);
      slider.setPaintTicks(true);
      slider.setPaintLabels(true);
      ChangeListener changeListener = new ChangeListener() {
         public void stateChanged(ChangeEvent changeEvent) {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            optionPane.setInputValue(new Integer(theSlider.getValue()));
            if (!theSlider.getValueIsAdjusting()) {
               System.out.println("Value is " + theSlider.getValue());
            } else {

            }
         }
      };
      slider.addChangeListener(changeListener);
      return slider;
   }

}