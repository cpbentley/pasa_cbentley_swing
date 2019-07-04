package pasa.cbentley.swing.data;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * Adapted From 
 * https://stackoverflow.com/questions/25919083/how-to-print-a-large-jpanel-in-several-page
 * 
 * @author Charles Bentley
 *
 */
public class PrintUtil implements Printable, Pageable {
   private Component  componentToBePrinted;

   private PageFormat format;

   private int        numPages;

   public PrintUtil(Component componentToBePrinted) {
      this.componentToBePrinted = componentToBePrinted;
      Dimension page = this.componentToBePrinted.getPreferredSize();
      numPages = (int) Math.ceil(page.height / format.getImageableY());
   }

   public void print() {
      PrinterJob printJob = PrinterJob.getPrinterJob();
      printJob.setPrintable(this);
      printJob.setPageable(this);
      format = printJob.defaultPage();

      if (printJob.printDialog())
         try {
            printJob.print();
         } catch (PrinterException pe) {
            System.out.println("Error printing: " + pe);
         }
   }

   public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
      if ((pageIndex < 0) | (pageIndex >= numPages)) {
         return (NO_SUCH_PAGE);
      } else {
         Graphics2D g2d = (Graphics2D) g;
         double ty = pageFormat.getImageableY() - pageIndex * componentToBePrinted.getPreferredSize().height;
         double tx = pageFormat.getImageableX();
         g2d.translate(tx, ty);
         disableDoubleBuffering(componentToBePrinted);
         componentToBePrinted.paint(g2d);
         enableDoubleBuffering(componentToBePrinted);
         return (PAGE_EXISTS);
      }
   }

   public static void disableDoubleBuffering(Component c) {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(false);
   }

   public static void enableDoubleBuffering(Component c) {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(true);
   }

   public int getNumberOfPages() {
      return numPages;
   }

   public PageFormat getPageFormat(int arg0) throws IndexOutOfBoundsException {
      return format;
   }

   public Printable getPrintable(int arg0) throws IndexOutOfBoundsException {
      return this;
   }
}