package pasa.cbentley.swing.logging;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Object parameters must never be null.
 * <br>
 * <br>
 * Must be checked before calling using {@link Dctx#nlLvlO(Object, String, pasa.cbentley.core.src4.ctx.ICtx)}
 * <br>
 * <br>
 * 
 * @author Charles Bentley
 *
 */
public class SwingDebug {

   //#mdebug

   private SwingCtx sc;

   public SwingDebug(SwingCtx sc) {
      this.sc = sc;
   }

   public String d1(ItemEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d1(WindowEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d(PropertyChangeEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d(e, dc);
      return dc.toString();
   }

   public void d1(PropertyChangeEvent e, Dctx dc) {
      dc.root1Line(e, "PropertyChangeEvent");
      dc.appendVarWithSpace("name", e.getPropertyName());
      dc.appendVarWithSpace("New", e.getNewValue());
      dc.appendVarWithSpace("Old", e.getOldValue());
      if (e.getPropagationId() != null) {
         dc.appendVarWithSpace("id", e.getPropagationId());
      }
      dc.sameLineO1(e.getSource(), " Source", sc);
   }

   public void d(PropertyChangeEvent e, Dctx dc) {
      dc.root(e, "PropertyChangeEvent");
      dc.appendWithSpace(e.getPropertyName());
      dc.appendVarWithSpace("id", e.getPropagationId());
      dc.appendVarWithSpace("New", e.getNewValue());
      dc.appendVarWithSpace("Old", e.getOldValue());
      dc.sameLineO1(e.getSource(), "Source", sc);
   }

   public String d1(PropertyChangeEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d(WindowEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d(e, dc);
      return dc.toString();
   }

   public String d1(ActionEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d(ActionEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d(e, dc);
      return dc.toString();
   }

   public String d1(MouseEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d(MouseEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d(e, dc);
      return dc.toString();
   }

   public String toStringStateWindow(int state) {
      switch (state) {
         case Frame.NORMAL:
            return "Normal";
         case Frame.MAXIMIZED_VERT:
            return "MaxVerti";
         case Frame.MAXIMIZED_HORIZ:
            return "MaxHoriz";
         case Frame.ICONIFIED:
            return "Iconified";
         case Frame.MAXIMIZED_BOTH:
            return "MaxBoth";
         default:
            return "UnknownWindowState" + state;
      }
   }

   /**
    * Utiliy method for debugging a {@link JFrame}
    * @param f
    * @param dc
    */
   public void toStringFrame(JFrame f, Dctx dc) {
      dc.root(f, "JFrame");
      dc.appendVarWithSpace("Title", f.getTitle());
      dc.append(' ');
      dc.append('[');
      dc.append(f.getX());
      dc.append(',');
      dc.append(f.getY());
      dc.append('-');
      dc.append(f.getWidth());
      dc.append(',');
      dc.append(f.getHeight());
      dc.append(']');
      dc.nl();
      dc.appendVar("isActive", f.isActive());
      dc.appendVar("isAlwaysOnTop", f.isAlwaysOnTop());
      dc.appendVar("isDoubleBuffered", f.isDoubleBuffered());
      dc.appendVar("isFocusable", f.isFocusable());
      dc.appendVar("isVisible", f.isVisible());
      dc.appendVar("isResizable", f.isResizable());
      dc.nl();
      dc.appendVar("isCursorSet", f.isCursorSet());
      dc.appendVar("isLightweight", f.isLightweight());
      dc.appendVar("isLocationByPlatform", f.isLocationByPlatform());
      dc.appendVar("isOpaque", f.isOpaque());
      dc.appendVar("isUndecorated", f.isUndecorated());
   }

   public void toStringFrame1Line(JFrame f, Dctx dc) {
      dc.root1Line(f, "JFrame");
      dc.appendVarWithSpace("Title", f.getTitle());
      dc.append(' ');
      dc.append('[');
      dc.append(f.getX());
      dc.append(',');
      dc.append(f.getY());
      dc.append('-');
      dc.append(f.getWidth());
      dc.append(',');
      dc.append(f.getHeight());
      dc.append(']');
   }

   public boolean toString(Object o, Dctx dc) {
      if (o instanceof WindowEvent) {
         d((WindowEvent) o, dc);
         return true;
      }
      return false;
   }

   public void toStringLocale(Locale loc, Dctx dc) {
      dc.root(loc, "Locale");
      dc.appendVarWithSpace("country", loc.getCountry());
      dc.appendVarWithSpace("language", loc.getLanguage());
      dc.appendVarWithSpace("DisplayCountry", loc.getDisplayCountry());
      dc.appendVarWithSpace("DisplayLanguage", loc.getDisplayLanguage());
   }

   public String toStringMouseEventID(MouseEvent e) {
      int id = e.getID();
      switch (id) {
         case MouseEvent.MOUSE_CLICKED:
            return "Clicked";
         case MouseEvent.MOUSE_DRAGGED:
            return "Dragged";
         case MouseEvent.MOUSE_ENTERED:
            return "Entered";
         case MouseEvent.MOUSE_EXITED:
            return "Exited";
         case MouseEvent.MOUSE_MOVED:
            return "Moved";
         case MouseEvent.MOUSE_PRESSED:
            return "Pressed";
         case MouseEvent.MOUSE_RELEASED:
            return "Released";
         case MouseEvent.MOUSE_WHEEL:
            return "Wheel";
         default:
            return "UnknownEvent" + id;
      }
   }

   public void d1(MouseEvent e, Dctx dc) {
      dc.root1Line(e, "MouseEvent");
      dc.append(" " + toStringMouseEventID(e));
      dc.append('[');
      dc.append(e.getX());
      dc.append(',');
      dc.append(e.getY());
      dc.append(' ');
      dc.append(" but=");
      dc.append(e.getButton());
      dc.append(" mods=");
      dc.append(MouseEvent.getMouseModifiersText(e.getModifiers()));
      dc.append(']');
      dc.appendVarWithSpace("isConsumed", e.isConsumed());

   }

   public void d(JPanel panel, Dctx dc) {
      dc.root(panel, "JPanel");
      //JComponent specific data
      dc.nl();
      this.d((JComponent) panel, dc);
   }

   public void d(Container c, Dctx dc) {
      dc.root(c, "Container");
      dc.appendVarWithSpace("ComponentCount", c.getComponentCount());
      dc.nl();
      this.d((Component) c, dc);
   }

   public String d1(Component c) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(c, dc);
      return dc.toString();
   }

   public void d1(Component c, Dctx dc) {
      dc.root(c, "Component");
      dc.appendVarWithSpace("Name", c.getName());
      dc.append(" ");
      d1(c.getBounds());
   }

   public void d(Component c, Dctx dc) {
      dc.root(c, "Component");
      dc.appendVarWithSpace("Name", c.getName());
   }

   public void d(JComponent c, Dctx dc) {
      dc.root(c, "JComponent");
      dc.appendVarWithSpace("x", c.getX());
      dc.appendVarWithSpace("y", c.getY());
      dc.appendVarWithSpace("w", c.getWidth());
      dc.appendVarWithSpace("h", c.getHeight());
      dc.appendVarWithSpace("pw", c.getPreferredSize().getWidth());
      dc.appendVarWithSpace("ph", c.getPreferredSize().getHeight());
      dc.nl();
      dc.append(" Located at " + c.getLocation().x + "," + c.getLocation().y);
      dc.nl();
      this.d((Container) c, dc);
   }

   public void d1(ActionEvent e, Dctx dc) {
      dc.root1Line(e, "ActionEvent");
      dc.append(" ActionCommand=" + e.getActionCommand());
      dc.append(" ID=" + e.getID());
      dc.append(" Source = " + e.getSource().getClass().getSimpleName());
   }

   public void d(ActionEvent e, Dctx dc) {
      dc.root1Line(e, "ActionEvent");
      dc.append(" ActionCommand=" + e.getActionCommand());
      dc.append(" ID=" + e.getID());
      dc.append(" Source = " + e.getSource().getClass().getSimpleName());
   }

   public void d1(JComponent c, Dctx dc) {
      dc.root1Line(c, "JComponent");
      dc.append('[');
      dc.append(c.getX());
      dc.append(',');
      dc.append(c.getY());
      dc.append(' ');
      dc.append(c.getWidth());
      dc.append(',');
      dc.append(c.getHeight());
      dc.append(']');
      dc.append(" Located at " + c.getLocation().x + "," + c.getLocation().y);
   }

   public void d(MouseEvent e, Dctx dc) {
      dc.root(e, "MouseEvent");
   }

   public void d1(WindowEvent e, Dctx dc) {
      dc.root1Line(e, "WindowEvent");
      dc.appendVarWithSpace("oldState", toStringStateWindow(e.getOldState()));
      dc.appendVarWithSpace("newState", toStringStateWindow(e.getNewState()));
      dc.sameLineO1(e.getWindow(), "Window", sc);
   }

   public void d(WindowEvent e, Dctx dc) {
      dc.root(e, "WindowEvent");
      dc.appendVarWithSpace("oldState", toStringStateWindow(e.getOldState()));
      dc.appendVarWithSpace("newState", toStringStateWindow(e.getNewState()));
      dc.nlLvlO(e.getWindow(), "Window", sc);
   }

   public void d1(BufferedImage image, Dctx dc) {
      dc.root1Line(image, "BufferedImage");
      dc.append(' ');
      dc.append(image.getWidth());
      dc.append(':');
      dc.append(image.getHeight());

   }

   public String d(JTabbedPane t) {
      Dctx dc = new Dctx(sc.getUCtx());
      d(t, dc);
      return dc.toString();
   }

   public void d(JTabbedPane e, Dctx dc) {
      dc.root(e, "JTabbedPane");
      dc.appendVarWithSpace("selectedIndex", e.getSelectedIndex());
      dc.appendVarWithSpace("tabCount", e.getTabCount());
      dc.appendVarWithSpace("tabRunCount", e.getTabRunCount());
      dc.appendVarWithSpace("tabPlacement", e.getTabPlacement());
      dc.appendVarWithSpace("tabLayoutPolicy", e.getTabLayoutPolicy());
      Component sel = e.getSelectedComponent();
      dc.nlLvlO(sel, "TabbedPaneSelectedComponent", sc);
      dc.nl();
      this.d((JComponent) e, dc);
   }

   public String d1(KeyEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public void d1(KeyEvent e, Dctx dc) {
      dc.root1Line(e, "KeyEvent");
      dc.appendVarWithSpace("code", e.getKeyCode());
      dc.appendVarWithSpace("char", e.getKeyChar());
   }

   public String d1(ChangeEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public String d1(Color color) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(color, dc);
      return dc.toString();
   }

   public String d1(Color color, Dctx dc) {
      dc.root1Line(color, "Color");
      if(color == null) {
         dc.append(" is null");
      } else {
         dc.appendVarWithSpace("r", color.getRed());
         dc.appendVarWithSpace("g", color.getGreen());
         dc.appendVarWithSpace("b", color.getBlue());
         dc.appendVarWithSpace("a", color.getAlpha());
      }
      return dc.toString();
   }

   public void d1(ChangeEvent e, Dctx dc) {
      dc.root1Line(e, "ChangeEvent");
      dc.sameLineO1(e.getSource(), "Source", sc);
   }

   public void d1(ItemEvent e, Dctx dc) {
      dc.root1Line(e, "ItemEvent");
      dc.sameLineO1(e.getSource(), "Source", sc);
   }

   public String d1(Shape s) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(s, dc);
      return dc.toString();
   }

   public void d1(Rectangle b, Dctx dc) {
      dc.root1Line(b, "Rectangle"); //null check
      dc.appendVarWithSpace("x", b.x);
      dc.appendVarWithSpace("y", b.y);
      dc.appendVarWithSpace("w", b.width);
      dc.appendVarWithSpace("h", b.height);
   }

   public void d1(Shape e, Dctx dc) {
      dc.root1Line(e, "Shape");
      dc.append("Bounds=");
      d1(e.getBounds(), dc);
   }

   public void d1(ListSelectionEvent e, Dctx dc) {
      dc.root1Line(e, "ListSelectionEvent"); //null check
      dc.append("index=[" + e.getFirstIndex() + " " + e.getLastIndex() + " ]");
      dc.appendVarWithSpace("isAdjusting", e.getValueIsAdjusting());
   }

   public String d1(ListSelectionEvent e) {
      Dctx dc = new Dctx(sc.getUCtx());
      d1(e, dc);
      return dc.toString();
   }

   public void d(DefaultComboBoxModel model, Dctx dc) {
      dc.root(model, "DefaultComboBoxModel");
      dc.appendVarWithSpace("size", model.getSize());
      dc.appendVarWithSpace("selectedObject", model.getSelectedItem());
      for (int i = 0; i < model.getSize(); i++) {
         dc.nl();
         dc.appendVar(i + "", model.getElementAt(i));
      }
   }

   //#enddebug
}
