package pasa.cbentley.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;

public class SwingCtxAdapter extends SwingCtx  {

   /**
    * Real ctx
    */
   private SwingCtx sc;

   public SwingCtxAdapter(UCtx uc, SwingCtx sc) {
      super(uc);
      this.sc = sc;
      
   }
}
