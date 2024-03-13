package pasa.cbentley.swing.utils;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.cmd.ICommandableRefresh;
import pasa.cbentley.swing.ctx.SwingCtx;

public class DocRefresher implements DocumentListener, IStringable {

   private boolean             isEnabled = true;

   private ICommandableRefresh refresh;

   protected final SwingCtx    sc;

   private Timer               timer;

   public DocRefresher(SwingCtx sc, ICommandableRefresh refresh) {
      this.sc = sc;
      this.refresh = refresh;

   }

   public void changedUpdate(DocumentEvent e) {
      //#debug
      toDLog().pFlow("calling refresher", this, DocRefresher.class, "changedUpdate", LVL_04_FINER, true);
      refresher();
   }

   public void insertUpdate(DocumentEvent e) {
      //#debug
      toDLog().pFlow("calling refresher" + sc.toSD().d1(e), this, DocRefresher.class, "insertUpdate", LVL_04_FINER, true);
    
      refresher();
   }

   public boolean isEnabled() {
      return isEnabled;
   }

   public void refresher() {
      if (!isEnabled()) {
         return;
      }
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

   public void removeUpdate(DocumentEvent e) {
      //#debug
      toDLog().pFlow("calling refresher", this, DocRefresher.class, "removeUpdate", LVL_04_FINER, true);
    
      refresher();
   }

   public void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "DocRefresher");
      toStringPrivate(dc);
      sc.getC5().to5D().d(timer,dc);
      dc.nlLvl(refresh, "refresh");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "DocRefresher");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isEnabled", isEnabled);
   }

   //#enddebug

}
