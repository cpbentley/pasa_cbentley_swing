package pasa.cbentley.swing.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;

public class DocRefresher implements DocumentListener {

   protected final SwingCtx sc;
   private Timer timer;
   private ICommandableRefresh refresh;

   public DocRefresher(SwingCtx sc, ICommandableRefresh refresh) {
      this.sc = sc;
      this.refresh = refresh;

   }

   public void refresher() {
      TimerTask timerTask = new TimerTask() {
         public void run() {
            sc.execute(new Runnable() {
               public void run() {
                  refresh.cmdRefresh(DocRefresher.this);
               }
            });
         }
      };

      if (timer != null) {
         timer.cancel();
      }
      timer = new Timer();
      timer.schedule(timerTask, 500);
   }

   public void insertUpdate(DocumentEvent e) {
      refresher();
   }

   public void removeUpdate(DocumentEvent e) {
      refresher();
   }

   public void changedUpdate(DocumentEvent e) {
      refresher();
   }

}
