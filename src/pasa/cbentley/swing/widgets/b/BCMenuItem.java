/*
 * (c) 2018-2019 Charles-Philip Bentley
 * This code is licensed under CC by-nc-nd 4.0 (see LICENSE.txt for details)
 * Contact author for uses outside of the NonCommercial-NoDerivatives clauses.   
 */
package pasa.cbentley.swing.widgets.b;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventConsumer;
import pasa.cbentley.core.src4.interfaces.ICommandable;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.swing.cmd.CmdSwingAbstract;
import pasa.cbentley.swing.ctx.SwingCtx;
import pasa.cbentley.swing.imytab.IMyGui;

/**
 * A special {@link JMenuItem} for context mouse menus with a linked {@link CmdSwingAbstract}
 * 
 * @author Charles Bentley
 *
 * @param <T>
 * 
 * @see ICommandable
 */
public class BCMenuItem<T extends ICommandable> extends JMenuItem implements IMyGui, ActionListener, IEventConsumer {

   /**
    * 
    */
   private static final long   serialVersionUID = -643480505038526391L;

   private int                 commandID        = -1;

   private SwingCtx            sc;

   private CmdSwingAbstract<T> cmd;

   private T                   t;

   /**
    * 
    * @param sc
    * @param al will provide context to the cmd execution
    * @param cmd
    */
   public BCMenuItem(SwingCtx sc, T t, CmdSwingAbstract<T> cmd) {
      //#mdebug
      if (t == null) {
         throw new NullPointerException();
      }
      if (cmd == null) {
         throw new NullPointerException();
      }
      //#enddebug
      this.sc = sc;
      this.t = t;
      this.cmd = cmd;
      this.addActionListener(this);
      
      setEnableStateFromCmd();  
   }

   private void setEnableStateFromCmd() {
      this.setEnabled(cmd.isEnabled());
   }

   public CmdSwingAbstract<T> getCmd() {
      return cmd;
   }

   public void startListeningToCmdChanges() {
      sc.getEventBusSwing().addConsumer(this, PID_03_CMD, EID_03_CMD_1_STATE_CHANGE);
   }

   public void consumeEvent(BusEvent e) {
      //#debug
      e.checkSanity(sc, PID_03_CMD, EID_03_CMD_1_STATE_CHANGE);

      setEnableStateFromCmd(); 
   }

   public void actionPerformed(ActionEvent e) {
      cmd.executeWith(t);
   }

   public void guiUpdate() {
      this.setText(cmd.getCmdString());
      //tip is computed based on command parameters
      this.setToolTipText(cmd.getCmdStringTip());
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "BCMenuItem");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "BCMenuItem");
   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }
   //#enddebug

}
