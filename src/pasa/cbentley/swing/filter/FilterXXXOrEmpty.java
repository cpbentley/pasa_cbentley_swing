package pasa.cbentley.swing.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Accepts
 * @author Charles Bentley
 *
 */
public abstract class FilterXXXOrEmpty extends DocumentFilter {

   private SwingCtx sc;

   public FilterXXXOrEmpty(SwingCtx sc) {
      this.sc = sc;

   }

   public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.insert(offset, string);
      if (testString(sb.toString())) {
         super.insertString(fb, offset, string, attr);
      } else {
         logString("Text is not valid " + sb.toString());
      }
   }

   private void logString(String str) {
      sc.getLog().consoleLogError(str);
   }

   private boolean testString(String text) {
      if (text == null || text.equals("")) {
         return true;
      } else {
         return testParsing(text);
      }

   }

   protected abstract boolean testParsing(String text);

   public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.replace(offset, offset + length, text);

      if (testString(sb.toString())) {
         super.replace(fb, offset, length, text, attrs);
      } else {
         logString("Text is not valid " + sb.toString());
      }

   }

   public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
      Document doc = fb.getDocument();
      StringBuilder sb = new StringBuilder();
      sb.append(doc.getText(0, doc.getLength()));
      sb.delete(offset, offset + length);
      if (testString(sb.toString())) {
         super.remove(fb, offset, length);
      } else {
         logString("Text is not valid " + sb.toString());
      }

   }
}