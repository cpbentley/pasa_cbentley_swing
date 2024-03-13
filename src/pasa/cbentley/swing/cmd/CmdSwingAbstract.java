package pasa.cbentley.swing.cmd;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.IEventsSwing;
import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class CmdSwingAbstract<T> implements IStringable, IEventsSwing {

   private boolean          isEnabled = true;

   protected final SwingCtx sc;

   public CmdSwingAbstract(SwingCtx sc) {
      this.sc = sc;
   }

   public abstract void executeWith(T t);

   public abstract String getCmdString();

   public abstract String getCmdStringTip();

   public boolean isEnabled() {
      return isEnabled;
   }

   public void setEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;

      //fire gui event 
      sc.getEventBusSwing().sendNewEvent(PID_03_CMD, EID_03_CMD_1_STATE_CHANGE, this);
   }

   public void setEnableFalse() {
      setEnabled(false);
   }

   public void setEnableTrue() {
      setEnabled(true);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CmdSwingAbstract");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CmdSwingAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
