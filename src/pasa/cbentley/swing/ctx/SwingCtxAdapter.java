package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src5.ctx.C5Ctx;

public class SwingCtxAdapter extends SwingCtx  {

   /**
    * Real ctx
    */
   private SwingCtx sc;

   public SwingCtxAdapter(C5Ctx c5, SwingCtx sc) {
      super(c5);
      this.sc = sc;
      
   }
}
