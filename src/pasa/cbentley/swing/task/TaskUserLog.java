package pasa.cbentley.swing.task;

import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.swing.ctx.SwingCtx;

public class TaskUserLog implements Runnable {

   protected final SwingCtx sc;

   private String           str;

   private int              type;

   public TaskUserLog(SwingCtx sc, int type, String str) {
      this.sc = sc;
      this.type = type;
      this.str = str;

   }

   public void run() {
      switch (type) {
         case IUserLog.consoleLog:
            sc.getLog().consoleLog(str);
            break;
         case IUserLog.consoleLogDate:
            sc.getLog().consoleLogDate(str);
            break;
         case IUserLog.consoleLogDateGreen:
            sc.getLog().consoleLogDateGreen(str);
            break;
         case IUserLog.consoleLogDateRed:
            sc.getLog().consoleLogDateRed(str);
            break;
         case IUserLog.consoleLogError:
            sc.getLog().consoleLogError(str);
            break;
         case IUserLog.consoleLogGreen:
            sc.getLog().consoleLogGreen(str);
            break;
         default:
            break;
      }
   }
}
