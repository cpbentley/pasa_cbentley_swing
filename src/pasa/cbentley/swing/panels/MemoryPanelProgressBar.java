package pasa.cbentley.swing.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import pasa.cbentley.core.src4.ctx.IEventsCore;
import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.core.src4.event.IEventBus;
import pasa.cbentley.core.src4.helpers.StringBBuilder;
import pasa.cbentley.core.src4.logging.IUserLog;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Shows the memory used.
 * <br>
 * Double click generates an event for user requested garbage collection.
 * <br>
 * This notifies registered 
 * @author Charles Bentley
 *
 */
public class MemoryPanelProgressBar extends JPanel implements ActionListener, MouseListener {

   /**
    * 
    */
   private static final long      serialVersionUID = -2033745258783044021L;

   private final JProgressBar     pBar             = new JProgressBar();

   private final SwingCtx         sc;

   private MemoryPanelProgressBar myself;

   public MemoryPanelProgressBar(SwingCtx sc) {
      super(new FlowLayout());
      this.sc = sc;
      this.myself = this;
      this.pBar.setStringPainted(true);
      this.pBar.setString("");
      this.pBar.setMinimum(0);
      this.pBar.setMaximum(100);
      this.pBar.addMouseListener(this);
      add(this.pBar);
      Timer t = new Timer(1000, this);
      t.start();
      update();
   }

   private void update() {
      Runtime jvmRuntime = Runtime.getRuntime();
      long totalMemory = jvmRuntime.totalMemory();
      long maxMemory = jvmRuntime.maxMemory();
      long usedMemory = totalMemory - jvmRuntime.freeMemory();
      long totalMemoryInMebibytes = totalMemory / (1024 * 1024);
      long maxMemoryInMebibytes = maxMemory / (1024 * 1024);
      long usedMemoryInMebibytes = usedMemory / (1024 * 1024);
      int usedPct = (int) ((100 * usedMemory) / totalMemory);

      String s1 = String.valueOf(usedMemoryInMebibytes);
      String s2 = " / ";
      String s3 = String.valueOf(totalMemoryInMebibytes);
      String s4 = " Mbs";
      String textToShow = sc.buildStringUISerial(s1, s2, s3, s4);
      this.pBar.setValue(usedPct);
      this.pBar.setString(textToShow);
      this.pBar.setToolTipText(sc.getResString("text.memory.tip"));
   }

   public void actionPerformed(ActionEvent e) {
      update();
   }

   public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2) {
         Runtime jvmRuntime = Runtime.getRuntime();
         long totalMemory = jvmRuntime.totalMemory();
         final long usedMemoryBeforeGC = totalMemory - jvmRuntime.freeMemory();

         //produce an event on the utils Bus, but people listen on the UtilsBus.
         IEventBus bus = MemoryPanelProgressBar.this.sc.getEventBusRoot();
         BusEvent be = bus.createEvent(IEventsCore.PID_03_MEMORY, IEventsCore.PID_03_MEMORY_2_USER_REQUESTED_GC, this);
         be.setUserEvent();
         bus.putOnBus(be);
         update();

         //after we call 

         Runtime.getRuntime().gc();
         Runtime.getRuntime().gc();

         //compute current 
         TimerTask task = new TimerTask() {

            public void run() {
               Runtime jvmRuntime = Runtime.getRuntime();
               long totalMemory = jvmRuntime.totalMemory();
               long usedMemoryAfterGC = totalMemory - jvmRuntime.freeMemory();

               long diff = usedMemoryBeforeGC - usedMemoryAfterGC;
               String msg = diff + " bytes freed";
               sc.publishUILog(IUserLog.consoleLog, msg);
            }
         };
         java.util.Timer timer = new java.util.Timer();
         timer.schedule(task, 1000);
      }
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }
}