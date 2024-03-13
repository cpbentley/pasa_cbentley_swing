package pasa.cbentley.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.widgets.b.BMenuItem;
import pasa.cbentley.swing.widgets.b.BMenuLazy;
import pasa.cbentley.swing.widgets.b.BRadioButtonMenuItem;

/**
 * The Menu Language
 * @author Charles Bentley
 *
 */
public class MenuLanguage extends BMenuLazy implements ActionListener {

   private class LangMenu {
      String country;

      String language;

      String menuTitle;

      public LangMenu(String menuTitle, String language, String country) {
         super();
         this.menuTitle = menuTitle;
         this.language = language;
         this.country = country;
      }
   }

   /**
    * 
    */
   private static final long serialVersionUID = 248410497345033297L;

   private boolean                         isSupportActions;

   private boolean                         isSupportHelp;

   private BRadioButtonMenuItem            jmiDebugMissing;

   private BMenuItem                       jmiHelpTranslate;

   private BRadioButtonMenuItem            jmiLogAllTranslation;

   private ArrayList<JRadioButtonMenuItem> langRadioButtons = new ArrayList<JRadioButtonMenuItem>();

   private ArrayList<LangMenu>             langs            = new ArrayList<LangMenu>();

   public MenuLanguage(SwingCtx sc) {
      super(sc, "menu.language");
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      int index = langRadioButtons.indexOf(src);
      if (index != -1) {
         LangMenu menu = langs.get(index);
         sc.localeUpdate(menu.language, menu.country);

         //generate event
         IEventBus eventBusRoot = sc.getUC().getEventBusRoot();
         BusEvent event = eventBusRoot.createEvent(IEventsCore.PID_01_FRAMEWORK, IEventsCore.PID_01_FRAMEWORK_2_LANGUAGE_CHANGED, null);
         eventBusRoot.putOnBus(event);
      } else {
         //delegate to listeners the cmd id
      }

   }

   public void addLanguage(String menuTitle, String language, String country) {
      LangMenu lang = new LangMenu(menuTitle, language, country);
      langs.add(lang);
   }

   protected void initMenu() {
      ButtonGroup group = new ButtonGroup();

      for (LangMenu lang : langs) {
         JRadioButtonMenuItem langRadioButton = new JRadioButtonMenuItem(lang.menuTitle);
         langRadioButton.setSelected(true);
         langRadioButton.addActionListener(this);
         langRadioButtons.add(langRadioButton);
         this.add(langRadioButton);
         group.add(langRadioButton);
      }

      if (isSupportActions) {
         jmiDebugMissing = new BRadioButtonMenuItem(sc, this, "menu.lang.debug");
         jmiLogAllTranslation = new BRadioButtonMenuItem(sc, this, "menu.lang.logall");
         jmiHelpTranslate = new BMenuItem(sc, this, "menu.lang.help");

         this.addSeparator();
         this.add(jmiDebugMissing);
         this.add(jmiLogAllTranslation);
         this.addSeparator();
         this.add(jmiHelpTranslate);
      }
   }

   public boolean isSupportActions() {
      return isSupportActions;
   }

   public boolean isSupportHelp() {
      return isSupportHelp;
   }

   public void setSupportActions(boolean isSupportActions) {
      this.isSupportActions = isSupportActions;
   }

   public void setSupportHelp(boolean isSupportHelp) {
      this.isSupportHelp = isSupportHelp;
   }

}
