package pasa.cbentley.swing.data;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.UIManager;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.swing.ctx.IEventsSwing;
import pasa.cbentley.swing.ctx.SwingCtx;

public class UIData implements IStringable, PropertyChangeListener {

   public static void main(String[] args) {
      UCtx uc = new UCtx();
      C5Ctx c5 = new C5Ctx(uc);
      SwingCtx sc = new SwingCtx(c5);
      System.out.println(sc.getUIData());
      System.out.println(javax.swing.UIManager.getDefaults().getFont("Label.font"));
   }

   private SwingCtx sc;

   public UIData(SwingCtx sc) {
      this.sc = sc;

      //we want to listen to changes in UI for all our classes. 
      //we forward our own kind of event
      UIManager.addPropertyChangeListener(this);
   }

   public Color getCLabelBackground() {
      return UIManager.getColor("Label.background");
   }

   public Color getCLabelForeground() {
      return UIManager.getColor("Label.foreground");
   }

   public Color getCPanelBackground() {
      return UIManager.getColor("Panel.background");
   }

   public Color getCTextFieldForeground() {
      return UIManager.getColor("TextField.foreground");
   }

   public List<UIDataElement> getElementsAll() {
      List<UIDataElement> fontKeys = new ArrayList<UIDataElement>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         UIDataElement ui = new UIDataElement(sc);
         String keyElement = entry.getKey().toString();
         if (keyElement.endsWith(".font")) {
            Object key = entry.getKey();
            Font font = javax.swing.UIManager.getDefaults().getFont(key);
            ui.setKey(keyElement);
            ui.setFont(font);
         } else if (entry.getValue() instanceof Color) {
            ui.setKey(keyElement);
            ui.setColor((Color) entry.getValue());
         } else {
            ui.setKey(keyElement);
            ui.setValueStr(entry.getValue().toString());
         }
         fontKeys.add(ui);
      }

      return fontKeys;
   }

   public List<UIDataElement> getElementsColors() {
      List<UIDataElement> colorElements = new ArrayList<UIDataElement>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         if (entry.getValue() instanceof Color) {
            UIDataElement ui = new UIDataElement(sc);
            ui.setKey((String) entry.getKey());
            ui.setColor((Color) entry.getValue());

            colorElements.add(ui);
         }
      }
      return colorElements;
   }

   public List<UIDataElement> getElementsFonts() {
      List<UIDataElement> fontKeys = new ArrayList<UIDataElement>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         if (entry.getKey().toString().endsWith(".font")) {
            Object key = entry.getKey();
            UIDataElement ui = new UIDataElement(sc);
            Font font = javax.swing.UIManager.getDefaults().getFont(key);

            ui.setKey((String) entry.getKey());
            ui.setFont(font);

            fontKeys.add(ui);
         }
      }

      return fontKeys;
   }

   public Font getFontLabel() {
      return UIManager.getDefaults().getFont("Label.font");
   }

   public List<String> getKeysAll() {
      List<String> keys = new ArrayList<String>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         Object key = entry.getKey();
         String str = key.toString() + " " + entry.getValue();
         keys.add(str);
      }

      // sort the color keys
      Collections.sort(keys);
      return keys;
   }

   public List<String> getKeysColor() {
      List<String> colorKeys = new ArrayList<String>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         if (entry.getValue() instanceof Color) {
            colorKeys.add((String) entry.getKey());
         }
      }

      // sort the color keys
      Collections.sort(colorKeys);
      return colorKeys;
   }

   public List<String> getKeysFont() {
      List<String> fontKeys = new ArrayList<String>();
      Set<Entry<Object, Object>> entries = UIManager.getLookAndFeelDefaults().entrySet();
      for (Entry entry : entries) {
         if (entry.getKey().toString().endsWith(".font")) {
            Object key = entry.getKey();
            String str = key.toString() + " " + entry.getValue();
            Font font = javax.swing.UIManager.getDefaults().getFont(key);
            str += font.toString();
            fontKeys.add(str);
         }
      }

      // sort the color keys
      Collections.sort(fontKeys);
      return fontKeys;
   }

   public boolean isLabelForegroundDark() {
      Color labelFg = getCLabelForeground();
      //analyse color of label
      int r = labelFg.getRed();
      int g = labelFg.getGreen();
      int b = labelFg.getBlue();
      int add = r + g + b;
      //average is 128*3 = 384
      if (add < 400) {
         return true;
      } else {
         return false;
      }
   }

   public void propertyChange(PropertyChangeEvent e) {
      String name = e.getPropertyName();
      if (name.equals("lookAndFeel")) {
         //send event to my classes
         BusEvent be = sc.getEventBusSwing().createEvent(IEventsSwing.PID_02_UI, IEventsSwing.EID_02_UI_01_CHANGE, this);
         //add a few things
         be.setParamO1(e);
         sc.getEventBusSwing().putOnBus(be);

      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "UIData");
      dc.nl();
      sc.getC5().toStringListString(dc, getKeysColor(), "Colors");
      dc.nl();
      sc.getC5().toStringListString(dc, getKeysFont(), "Fonts");
      dc.nl();
      sc.getC5().toStringListString(dc, getKeysAll(), "All");

   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "UIData");
   }

   //#enddebug

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

}
