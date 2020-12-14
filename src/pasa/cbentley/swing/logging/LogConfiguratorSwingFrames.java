package pasa.cbentley.swing.logging;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.core.src4.logging.ITechTags;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.task.TaskExitHard;
import pasa.cbentley.swing.task.TaskExitSmoothIfNoFrames;
import pasa.cbentley.swing.window.CBentleyFrame;

//#mdebug
public class LogConfiguratorSwingFrames implements ILogConfigurator {


   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      
      log.setFlagPrint(ITechConfig.MASTER_FLAG_03_ONLY_POSITIVES, true);
      log.setFlagPrint(ITechConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
      

      log.setClassPositives(CBentleyFrame.class, true);
      log.setClassPositives(TaskExitHard.class, true);
      log.setClassPositives(TaskExitSmoothIfNoFrames.class, true);
   }

}
//#enddebug
