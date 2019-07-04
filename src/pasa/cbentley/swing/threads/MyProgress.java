package pasa.cbentley.swing.threads;

import javax.swing.JProgressBar;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src4.thread.IBProgessable;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * {@link JProgressBar} that fits into the {@link IMProgessable} interface.
 * 
 * @author Charles Bentley
 *
 */
public class MyProgress extends JProgressBar implements IBProgessable, IStringable {

   private IBRunnable runner;

   private String     label;

   private IUserLog     log;

   private int        max;

   private SwingCtx   sc;

   private TaskPanel  taskPanel;

   public MyProgress(SwingCtx sc, IUserLog log) {
      this.sc = sc;
      this.log = log;
      this.setStringPainted(true);
   }

   public String getTitle() {
      return "";
   }

   public void setTitle(String title) {
      //no titel
      log.consoleLog("Progress:" + title);
   }

   public void setMaxValue(int mv) {
      this.setMaximum(mv);
      this.max = mv;
   }

   public IBRunnable getRunner() {
      return runner;
   }

   public void set(String title, String info, String label, int maxValue, int level) {
      this.setMaximum(maxValue);
   }

   public void setLabel(String s) {
      this.setString(s);
      label = s;
   }

   public void setRunnable(IBRunnable runner) {
      this.runner = runner;
   }

   public int getLvl() {
      return 0;
   }

   public void error(String msg) {
      log.consoleLogError(msg);
   }

   public void setValue(int value) {
      super.setValue(value);
      this.setString(label + " " + value + "/" + max);
   }

   public void setLvl(int lvl) {
   }

   public void setInfo(String info) {
   }

   public void increment(int value) {
      int val = this.getValue();
      this.setValue(val + value);
      this.setString(label + " " + val + "/" + max);
   }

   public void newRunnableState(int state) {
      if (taskPanel != null) {
         taskPanel.runnerNewState(runner, state);
      }
   }

   public void setListener(TaskPanel taskPanel) {
      this.taskPanel = taskPanel;

   }

   public void setTimeLeft(long timeLeft) {
      long minute = timeLeft / 60000;
      long secs = (timeLeft / 1000) % 60;
      String str = " Time left is " + minute + " min " + secs + " secs";
      this.setToolTipText(str);
   }

   /**
    * No child possible.
    */
   public IBProgessable getChild() {
      return this;
   }

   public IBProgessable getChild(IBRunnable runnable) {
      // TODO Auto-generated method stub
      return null;
   }

   public void close(String msg) {
      log.consoleLog("ProgressClose:" + msg);
   }
   public void close() {
      
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "MyProgress");
      dc.appendVarWithSpace("max", max);
      dc.appendVarWithSpace("label", label);
      dc.nlLvl(runner);
      dc.nlLvl(log);

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "MyProgress");
      dc.appendVarWithSpace("max", max);
      dc.appendVarWithSpace("label", label);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug



}
