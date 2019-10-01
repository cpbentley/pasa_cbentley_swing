package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.IEventsCore;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IEventsSwing extends IEventsCore {
   /**
    * Number of statically defined events
    */
   public static final int BASE_EVENTS               = 4;

   public static final int EID_01_SWING_0_ANY        = 0;

   public static final int EID_01_SWING_ZZ_NUM       = 1;

   public static int       EID_02_UI_00_ANY          = 0;

   public static int       EID_02_UI_01_CHANGE       = 1;

   public static final int EID_02_UI_ZZ_NUM          = 2;

   public static int       EID_03_CMD_0_ANY          = 0;

   public static int       EID_03_CMD_1_STATE_CHANGE = 1;

   public static int       EID_03_CMD_ZZ_NUM         = 2;

   public static int       PID_01_SWING              = 1;

   public static int       PID_02_UI                 = 2;

   public static int       PID_03_CMD                = 3;

}
