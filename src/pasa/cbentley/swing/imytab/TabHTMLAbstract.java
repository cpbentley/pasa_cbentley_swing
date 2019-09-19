package pasa.cbentley.swing.imytab;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class TabHTMLAbstract extends AbstractMyTab {

   /**
    * 
    */
   private static final long serialVersionUID = 6131298489936875477L;

   private JEditorPane       editorPane;

   private String            htmlPath;

   public TabHTMLAbstract(SwingCtx sc, String ID) {
      super(sc, ID);
   }

   public void tabLostFocus() {
   }

   public abstract String getPathHTML();

   public void tabGainFocus() {

   }

   public void disposeTab() {
   }

   protected void initTab() {
      this.setLayout(new BorderLayout());

      //JEditorPane supports display/editing of HTML.
      //JTextPane is an extension of JEditorPane which provides word processing features like fonts, text styles, colors, e

      editorPane = new JEditorPane();
      editorPane.setEditable(false);//so its not editable
      editorPane.setOpaque(true);//so we dont see whit background

      htmlPath = getPathHTML();
      
      java.net.URL helpURL = TabHTMLAbstract.class.getResource(htmlPath);
      if (helpURL != null) {
         try {
            editorPane.setPage(helpURL);
         } catch (IOException e) {
            //#debug
            toDLog().pInit("Attempted to read a bad URL: " + helpURL, this, TabHTMLAbstract.class, "initTab", LVL_05_FINE, true);
         }
      } else {
         //#debug
         toDLog().pInit("Couldn't find file: " + htmlPath, this, TabHTMLAbstract.class, "initTab", LVL_05_FINE, true);
      }

      JScrollPane jsp = new JScrollPane(editorPane);
      jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      this.add(jsp, BorderLayout.CENTER);

      editorPane.addHyperlinkListener(new HyperlinkListener() {
         
         public void hyperlinkUpdate(HyperlinkEvent linkEvent) {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(linkEvent.getEventType())) {
               Desktop desktop = Desktop.getDesktop();
               try {
                  desktop.browse(linkEvent.getURL().toURI());
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }
         }
      });

   }

}
