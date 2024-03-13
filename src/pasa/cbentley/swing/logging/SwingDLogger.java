package pasa.cbentley.swing.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.BaseDLogger;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Adds the capability of having several loggers
 * <li> Register a logger for a Level
 * @author Charles Bentley
 *
 */
public class SwingDLogger extends BaseDLogger {

   //#mdebug
   private SwingCtx sc;

   public SwingDLogger(SwingCtx sc) {
      super(sc.getUC());
      this.sc = sc;
   }

   private Hashtable openLogs = new Hashtable();

   /**
    * Create a File log for the given parameters.
    * The log file will be located in the logger directory defined in .. TODO
    * <br>
    * If file is already open, increment number
    * @param flag
    * @param cl
    * @param method
    * @param fileDest
    */
   public void addFileLog(int flag, Class cl, String method, String fileDest) {
      String logKey = "";
      if (cl != null) {
         logKey = cl.getName();
         if (method != null) {
            logKey += method;
         }
      }
      if (flag != -1) {
         logKey += flag;
      }
      try {
         File f = new File(fileDest);
         //do we have access?
         if (f.canWrite()) {
            OutputStream dos = new FileOutputStream(f);
            OutputStreamWriter osw = new OutputStreamWriter(dos, "UTF-8");
            openLogs.put(logKey, osw);
         } else {
            //try with another name

         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   //#enddebug
}
