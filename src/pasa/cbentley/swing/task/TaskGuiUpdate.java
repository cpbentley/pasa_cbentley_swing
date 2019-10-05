package pasa.cbentley.swing.task;

import pasa.cbentley.swing.ctx.SwingCtx;

public class TaskGuiUpdate implements Runnable {

   protected final SwingCtx sc;
   public TaskGuiUpdate(SwingCtx sc) {
      this.sc = sc;
      
   }
   public void run() {
      sc.guiUpdate();
   }

}
