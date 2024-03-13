package pasa.cbentley.swing.widgets.b;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * 
 * @author Charles Bentley
 *
 */
public class BComboBoxPanel extends JPanel implements IMyGui {

   class ComboKeyHandler extends KeyAdapter {
      private final JComboBox<String> comboBox;

      private final List<String>      list = new ArrayList<String>();

      private boolean                 shouldHide;

      protected ComboKeyHandler(JComboBox<String> combo) {
         super();
         this.comboBox = combo;
         for (int i = 0; i < comboBox.getModel().getSize(); i++) {
            list.add(comboBox.getItemAt(i));
         }
      }

      @Override
      public void keyPressed(KeyEvent e) {
         JTextField textField = (JTextField) e.getComponent();
         String text = textField.getText();
         shouldHide = false;
         switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
               for (String s : list) {
                  if (s.startsWith(text)) {
                     textField.setText(s);
                     return;
                  }
               }
               break;
            case KeyEvent.VK_ENTER:
               if (!list.contains(text)) {
                  list.add(text);
                  Collections.sort(list);
                  // setSuggestionModel(comboBox, new DefaultComboBoxModel(list), text);
                  setSuggestionModel(comboBox, getSuggestedModel(list, text), text);
               }
               shouldHide = true;
               break;
            case KeyEvent.VK_ESCAPE:
               shouldHide = true;
               break;
            default:
               break;
         }
      }

      @Override
      public void keyTyped(final KeyEvent e) {
         EventQueue.invokeLater(new Runnable() {
            public void run() {

               String text = ((JTextField) e.getComponent()).getText();

               ComboBoxModel<String> m;

               if (text.isEmpty()) {
                  m = new DefaultComboBoxModel<String>(list.toArray(new String[0]));
                  setSuggestionModel(comboBox, m, "");
                  comboBox.hidePopup();
               } else {
                  m = getSuggestedModel(list, text);
                  if (m.getSize() == 0 || shouldHide) {
                     comboBox.hidePopup();
                  } else {
                     setSuggestionModel(comboBox, m, text);
                     comboBox.showPopup();
                  }
               }
            }
         });
      }
   }

   /**
    * We will show a Help Panel on the GlassPane Just above the GlassPane.
    * 
    * A Check Box is there to not show it.
    * <br>
    * F1 to show it 
    * @return
    */
   class HelpPanel extends JPanel {

      HelpPanel() {
         JPanel lp = new JPanel(new GridLayout(2, 1, 2, 2));
         lp.add(new JLabel("Tab: Iterate"));
         lp.add(new JLabel("Escape: hide Popup"));

         JPanel rp = new JPanel(new GridLayout(2, 1, 2, 2));
         rp.add(new JLabel("Right: Completion"));
         rp.add(new JLabel("F1: Add/Selection"));

         this.setLayout(new GridBagLayout());
         this.setBorder(BorderFactory.createTitledBorder("Help"));

         GridBagConstraints c = new GridBagConstraints();
         c.insets = new Insets(0, 5, 0, 5);
         c.fill = GridBagConstraints.BOTH;
         c.weighty = 1d;

         c.weightx = 1d;
         this.add(lp, c);

         c.weightx = 0d;
         this.add(new JSeparator(SwingConstants.VERTICAL), c);

         c.weightx = 1d;
         this.add(rp, c);

      }

   }

   private static ComboBoxModel<String> getSuggestedModel(List<String> list, String text) {
      DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
      for (String s : list) {
         if (s.startsWith(text)) {
            m.addElement(s);
         }
      }
      return m;
   }


   private static void setSuggestionModel(JComboBox<String> comboBox, ComboBoxModel<String> mdl, String str) {
      comboBox.setModel(mdl);
      comboBox.setSelectedIndex(-1);
      ((JTextField) comboBox.getEditor().getEditorComponent()).setText(str);
   }

   private SwingCtx     sc;

   private String       titleKey;

   private String       keyHint;

   private TitledBorder mainBorder;

   /**
    * The model is dynamically updated by
    * @param sc
    */
   public BComboBoxPanel(SwingCtx sc, String titleKey, ComboBoxModel<String> model) {
      super(new BorderLayout());
      this.sc = sc;
      this.titleKey = titleKey;

      JComboBox<String> combo = new JComboBox<String>(model);
      combo.setEditable(true);
      combo.setSelectedIndex(-1);

      JTextField field = (JTextField) combo.getEditor().getEditorComponent();
      field.setText("");
      field.addKeyListener(new ComboKeyHandler(combo));

      JPanel p = new JPanel(new BorderLayout());
      mainBorder = BorderFactory.createTitledBorder("Auto-Completion ComboBox");
      p.setBorder(mainBorder);
      p.add(combo, BorderLayout.NORTH);

      setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
   }

   public void showHelpBox() {
      //display this above on the glasspane
      HelpPanel help = new HelpPanel();
      //show it on the glasspane..
   }
   
   public void guiUpdate() {
      if (titleKey != null) {
         mainBorder.setTitle(sc.getResString(titleKey));
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BComboBox");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BComboBox");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
