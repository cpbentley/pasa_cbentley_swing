/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.swing.panels;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.interfaces.IPrefs;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.AbstractMyTab;
import pasa.cbentley.swing.widgets.b.BButton;

public class PreferenceTab extends AbstractMyTab implements ActionListener {

   /**
    * 
    */
   private static final long serialVersionUID = 8829211373123833374L;

   private BButton           butClear;

   private BButton           butRefresh;

   private BButton           butSave;

   private TextArea          textArea;

   public PreferenceTab(SwingCtx sc) {
      super(sc, "preferences");
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butRefresh) {
         fillProps();
      } else if (src == butClear) {
         cmdClear();
      } else if (src == butSave) {
         cmdSave();
      }
   }

   private void cmdClear() {
      IPrefs prefs = sc.getPrefs();
      prefs.clear();
   }

   private void cmdSave() {

   }

   public void disposeTab() {

   }

   private void fillProps() {

      IPrefs prefs = sc.getPrefs();
      IntToStrings its = prefs.getKeys();
      if (its != null) {
         its.sort(true);
         for (int i = 0; i < its.nextempty; i++) {
            String key = its.strings[i];
            int type = its.ints[i]; //TODO no type yet. its only string.
            //you need to try if its int or double
            textArea.append(key + "\t = ");
            textArea.append(prefs.get(key, "Invalid Preference Key"));
            textArea.append("\n");
         }
      }

   }

   public void initTab() {
      this.setLayout(new BorderLayout());

      JPanel north = new JPanel();

      butSave = new BButton(sc, this);
      butSave.setTextKey("but.save");
      north.add(butSave);

      butClear = new BButton(sc, this);
      butClear.setTextKey("but.clear");
      north.add(butClear);

      butRefresh = new BButton(sc, this);
      butRefresh.setTextKey("but.refresh");
      north.add(butRefresh);

      textArea = new TextArea();
      fillProps();

      this.add(textArea, BorderLayout.CENTER);
      this.add(north, BorderLayout.NORTH);

   }

   public void tabGainFocus() {
   }

   public void tabLostFocus() {

   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "PreferenceTab");
      toStringPrivate(dc);
      super.toString(dc.sup());
      //TODO tag in color as title
      dc.append("Text of TextArea");
      dc.nl();
      dc.append(textArea.getText());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "PreferenceTab");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
