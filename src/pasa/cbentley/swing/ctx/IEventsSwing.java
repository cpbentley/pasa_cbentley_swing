package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.IEventsCore;

/**
 * 
 * @author Charles Bentley
 *
 */
public interface IEventsSwing extends IEventsCore {

   public static int       PID_02_UI           = 2;

   public static int       EID_02_UI_01_CHANGE = 1;

   public static final int EID_02_UI_ZZ_NUM    = 2;

   /**
    * Number of statically defined events
    */
   int                     BASE_EVENTS         = 4;

}
