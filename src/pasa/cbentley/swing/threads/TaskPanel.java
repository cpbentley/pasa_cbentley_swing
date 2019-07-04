package pasa.cbentley.swing.threads;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.core.src4.thread.IBProgessable;
import pasa.cbentley.core.src4.thread.IBRunnable;
import pasa.cbentley.core.src4.thread.IBRunnableListener;
import pasa.cbentley.swing.ctx.SwingCtx;

public class TaskPanel extends JPanel implements ActionListener, IBRunnableListener {

   private JButton            butCancel;

   private JButton            butPause;

   private JButton            butStop;

   private MyProgress         progress;

   private SwingCtx           sc;

   private IBRunnable         task;

   public TaskPanel(SwingCtx sc, IUserLog log) {
      this.sc = sc;

      progress = new MyProgress(sc, log);
      progress.setListener(this);
      getProgress().setPreferredSize(new Dimension(250, 60));

      butStop = new JButton("Stop");
      butStop.addActionListener(this);

      butCancel = new JButton("Cancel");
      butCancel.addActionListener(this);
      butPause = new JButton("Pause");
      butPause.addActionListener(this);

      this.add(getProgress());
   }

   public void actionPerformed(ActionEvent e) {
      Object src = e.getSource();
      if (src == butStop) {
         task.requestNewState(STATE_3_STOPPED);
      } else if (src == butCancel) {
         task.requestNewState(STATE_2_CANCELED);
      } else if (src == butPause) {
         if (task.getState() == STATE_0_RUNNING) {
            task.requestNewState(STATE_1_PAUSED);
         } else if (task.getState() == STATE_1_PAUSED) {
            task.requestNewState(STATE_0_RUNNING);
         }
      }
   }

   public void setTask(IBRunnable task) {
      this.task = task;
      progress.setRunnable(task);

      if (task.hasRunFlag(FLAG_02_STOPPABLE)) {
         this.add(butStop);
      } else {
         this.remove(butStop);
      }
      if (task.hasRunFlag(FLAG_04_CANCELABLE)) {
         this.add(butCancel);
      } else {
         this.remove(butCancel);
      }
      if (task.hasRunFlag(FLAG_03_PAUSABLE)) {
         this.add(butPause);
      } else {
         this.remove(butPause);
      }
   }

   public MyProgress getProgress() {
      return progress;
   }

   public void runnerException(IBRunnable runner, Throwable e) {
      e.printStackTrace();
   }

   public void runnerNewState(IBRunnable runner, int newState) {
      if(newState == STATE_0_RUNNING) {
         butPause.setEnabled(true);
         butPause.setText("Pause");
      } else if(newState == STATE_1_PAUSED) {
         butPause.setText("Unpause");
      }
   }

   public void setProgress(MyProgress progress) {
      this.progress = progress;
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }
   
   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TaskPanel");
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TaskPanel");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }
   //#enddebug
}
