package pasa.cbentley.swing.data;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * This class makes it easy to drag and drop files from the operating
 * system to a Java program. Any <tt>Component</tt> can be
 * dropped onto, but only <tt>javax.swing.JComponent</tt>s will indicate
 * the drop event with a changed border.
 * <p/>
 * To use this class, construct a new <tt>FileDrop</tt> by passing
 * it the target component and a <tt>Listener</tt> to receive notification
 * when file(s) have been dropped. Here is an example:
 * <p/>
 * <code><pre>
 *      JPanel myPanel = new JPanel();
 *      new FileDrop( myPanel, new FileDrop.Listener()
 *      {   public void filesDropped( java.io.File[] files )
 *          {   
 *              // handle file drop
 *              ...
 *          }   // end filesDropped
 *      }); // end FileDrop.Listener
 * </pre></code>
 * <p/>
 * You can specify the border that will appear when files are being dragged by
 * calling the constructor with a <tt>javax.swing.border.Border</tt>. Only
 * <tt>JComponent</tt>s will show any indication with a border.
 * <p/>
 * You can turn on some debugging features by passing a <tt>PrintStream</tt>
 * object (such as <tt>System.out</tt>) into the full constructor. A <tt>null</tt>
 * value will result in no extra debugging information being output.
 * <p/>
 *
 * <p>I'm releasing this code into the Public Domain. Enjoy.
 * </p>
 * <p><em>Original author: Robert Harder, rharder@usa.net</em></p>
 * <p>2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.</p>
 *
 * @author  Robert Harder
 * @author  rharder@users.sf.net
 * @version 1.0.1
 */
public class FileDrop implements DropTargetListener {
   
   private transient javax.swing.border.Border normalBorder;

   private transient DropTargetListener        dropListener;

   /** Discover if the running JVM is modern enough to have drag and drop. */
   private Boolean                             supportsDnD;

   // Default border color
   private Color                               defaultBorderColor = new Color(0f, 0f, 1f, 0.25f);

   private Border                              dragBorder;

   private Component                           c;

   /**
    * Constructs a {@link FileDrop} with a default light-blue border
    * and, if <var>c</var> is a {@link Container}, recursively
    * sets all elements contained within as drop targets, though only
    * the top level container will change borders.
    *
    * @param c Component on which files will be dropped.
    * @param listener Listens for <tt>filesDropped</tt>.
    * @since 1.0
    */
   public FileDrop(Component c, Listener listener) {
      this(null, // Logging stream
            c, // Drop target
            null, // Drag border
            true, // Recursive
            listener);
   }

   /**
    * Constructor with a default border and the option to recursively set drop targets.
    * If your component is a <tt>Container</tt>, then each of its children
    * components will also listen for drops, though only the parent will change borders.
    *
    * @param c Component on which files will be dropped.
    * @param recursive Recursively set children as drop targets.
    * @param listener Listens for <tt>filesDropped</tt>.
    * @since 1.0
    */
   public FileDrop(Component c, boolean recursive, Listener listener) {
   }

   public void dragEnter(DropTargetDragEvent evt) {
      // Is this an acceptable drag event?
      if (isDragOk(evt)) {
         // If it's a Swing component, set its border
         if (c instanceof javax.swing.JComponent) {
            javax.swing.JComponent jc = (javax.swing.JComponent) c;
            normalBorder = jc.getBorder();
            jc.setBorder(dragBorder);
         } // end if: JComponent   

         // Acknowledge that it's okay to enter
         //evt.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
         evt.acceptDrag(DnDConstants.ACTION_COPY);
      } else {
         evt.rejectDrag();
      }
   }

   public void dragOver(DropTargetDragEvent evt) { // This is called continually as long as the mouse is
      // over the drag target.
   } // end dragOver

   /**
    * Full constructor with a specified border and debugging optionally turned on.
    * With Debugging turned on, more status messages will be displayed to
    * <tt>out</tt>. A common way to use this constructor is with
    * <tt>System.out</tt> or <tt>System.err</tt>. A <tt>null</tt> value for
    * the parameter <tt>out</tt> will result in no debugging output.
    *
    * @param out PrintStream to record debugging info or null for no debugging.
    * @param c Component on which files will be dropped.
    * @param dragBorder Border to use on <tt>JComponent</tt> when dragging occurs.
    * @param recursive Recursively set children as drop targets.
    * @param listener Listens for <tt>filesDropped</tt>.
    * @since 1.0
    */
   public FileDrop(final java.io.PrintStream out, final Component c, javax.swing.border.Border dragBorder, final boolean recursive, final Listener listener) {

      if (dragBorder == null) {
         this.dragBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, defaultBorderColor);
      } else {
         this.dragBorder = dragBorder;
      }
      if (supportsDnD()) { // Make a drop listener

         // Make the component (and possibly children) drop targets
         makeDropTarget(c, recursive);
      } // end if: supports dnd
      else {
         //log(out, "FileDrop: Drag and drop is not supported with this JVM");
      }
   }

   public void dragExit(DropTargetEvent evt) {
      // If it's a Swing component, reset its border
      if (c instanceof javax.swing.JComponent) {
         javax.swing.JComponent jc = (javax.swing.JComponent) c;
         jc.setBorder(normalBorder);
      }
   }

   public void dropActionChanged(DropTargetDragEvent evt) {
      if (isDragOk(evt)) {
         //evt.acceptDrag( DnDConstants.ACTION_COPY_OR_MOVE );
         evt.acceptDrag(DnDConstants.ACTION_COPY);
      } else {
         evt.rejectDrag();
      }
   }

   /**
    * True if DnD is supported
    * @return
    */
   private boolean supportsDnD() {
      if (supportsDnD == null) {
         boolean support = false;
         try {
            Class arbitraryDndClass = Class.forName("DnDConstants");
            support = true;
         } catch (Exception e) {
            support = false;
         }
         supportsDnD = new Boolean(support);
      }
      return supportsDnD.booleanValue();
   }

   // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
   private String   ZERO_CHAR_STRING = "0";

   private Listener listener;

   private File[] createFileArray(BufferedReader bReader) {
      try {
         List list = new ArrayList();
         java.lang.String line = null;
         while ((line = bReader.readLine()) != null) {
            try {
               // kde seems to append a 0 char to the end of the reader
               if (ZERO_CHAR_STRING.equals(line))
                  continue;

               java.io.File file = new java.io.File(new java.net.URI(line));
               list.add(file);
            } catch (Exception ex) {
               // log(out, "Error with " + line + ": " + ex.getMessage());
            }
         }

         return (java.io.File[]) list.toArray(new File[list.size()]);
      } catch (IOException ex) {
         // log(out, "FileDrop: IOException");
      }
      return new File[0];
   }
   // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.

   public void drop(DropTargetDropEvent evt) {
      //log(out, "FileDrop: drop event.");
      try { // Get whatever was dropped
         Transferable tr = evt.getTransferable();

         // Is it a file list?
         if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            // Say we'll take it.
            //evt.acceptDrop ( DnDConstants.ACTION_COPY_OR_MOVE );
            evt.acceptDrop(DnDConstants.ACTION_COPY);

            //log(out, "FileDrop: file list accepted.");

            // Get a useful list
            List fileList = (List) tr.getTransferData(DataFlavor.javaFileListFlavor);
            Iterator iterator = fileList.iterator();

            // Convert list to array
            java.io.File[] filesTemp = new java.io.File[fileList.size()];
            fileList.toArray(filesTemp);
            final java.io.File[] files = filesTemp;

            // Alert listener to drop.
            if (listener != null) {
               listener.filesDropped(files);
            }

            // Mark that drop is completed.
            evt.getDropTargetContext().dropComplete(true);
            // log(out, "FileDrop: drop complete.");
         } else {
            // Thanks, Nathan!
            // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            boolean handled = false;
            for (int zz = 0; zz < flavors.length; zz++) {
               if (flavors[zz].isRepresentationClassReader()) {
                  // Say we'll take it.
                  //evt.acceptDrop ( DnDConstants.ACTION_COPY_OR_MOVE );
                  evt.acceptDrop(DnDConstants.ACTION_COPY);
                  // log(out, "FileDrop: reader accepted.");

                  Reader reader = flavors[zz].getReaderForText(tr);

                  BufferedReader br = new BufferedReader(reader);

                  if (listener != null)
                     listener.filesDropped(createFileArray(br));

                  // Mark that drop is completed.
                  evt.getDropTargetContext().dropComplete(true);
                  //log(out, "FileDrop: drop complete.");
                  handled = true;
                  break;
               }
            }
            if (!handled) {
               //log(out, "FileDrop: not a file list or reader - abort.");
               evt.rejectDrop();
            }
            // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
         }
      } catch (java.io.IOException io) {
         // log(out, "FileDrop: IOException - abort:");
         // io.printStackTrace(out);
         evt.rejectDrop();
      } catch (UnsupportedFlavorException ufe) {
         evt.rejectDrop();
      } finally {
         // If it's a Swing component, reset its border
         if (c instanceof javax.swing.JComponent) {
            javax.swing.JComponent jc = (javax.swing.JComponent) c;
            jc.setBorder(normalBorder);
         }
      }
   }

   private void makeDropTarget(final Component c, boolean recursive) {
      // Make drop target
      final DropTarget dt = new DropTarget();
      try {
         dt.addDropTargetListener(dropListener);
      } // end try
      catch (TooManyListenersException e) {
         e.printStackTrace();
      } // end catch

      // Listen for hierarchy changes and remove the drop target when the parent gets cleared out.
      c.addHierarchyListener(new HierarchyListener() {
         public void hierarchyChanged(HierarchyEvent evt) {
            Component parent = c.getParent();
            if (parent == null) {
               c.setDropTarget(null);
            } // end if: null parent
            else {
               new DropTarget(c, dropListener);
            } // end else: parent not null
         } // end hierarchyChanged
      }); // end hierarchy listener
      if (c.getParent() != null) {
         new DropTarget(c, dropListener);
      }

      if (recursive && (c instanceof Container)) {
         // Get the container
         Container cont = (Container) c;

         // Get it's components
         Component[] comps = cont.getComponents();

         for (int i = 0; i < comps.length; i++) {
            makeDropTarget(comps[i], recursive);
         }
      }
   }

   /** Determine if the dragged data is a file list.
    */
   private boolean isDragOk(final DropTargetDragEvent evt) {
      boolean ok = false;

      // Get data flavors being dragged
      DataFlavor[] flavors = evt.getCurrentDataFlavors();

      // See if any of the flavors are a file list
      int i = 0;
      while (!ok && i < flavors.length) {
         // BEGIN 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
         // Is the flavor a file list?
         final DataFlavor curFlavor = flavors[i];
         if (curFlavor.equals(DataFlavor.javaFileListFlavor) || curFlavor.isRepresentationClassReader()) {
            ok = true;
         }
         // END 2007-09-12 Nathan Blomquist -- Linux (KDE/Gnome) support added.
         i++;
      } // end while: through flavors

      // If logging is enabled, show data flavors
      //      if (out != null) {
      //         if (flavors.length == 0)
      //            log(out, "FileDrop: no data flavors.");
      //         for (i = 0; i < flavors.length; i++)
      //            log(out, flavors[i].toString());
      //      }

      return ok;
   }

   /**
    * Removes the drag-and-drop hooks from the component and optionally
    * from the all children. You should call this if you add and remove
    * components after you've set up the drag-and-drop.
    * This will recursively unregister all components contained within
    * <var>c</var> if <var>c</var> is a {@link Container}.
    *
    * @param c The component to unregister as a drop target
    * @since 1.0
    */
   public boolean remove(Component c) {
      return remove(null, c, true);
   }

   /**
    * Removes the drag-and-drop hooks from the component and optionally
    * from the all children. You should call this if you add and remove
    * components after you've set up the drag-and-drop.
    *
    * @param out Optional {@link java.io.PrintStream} for logging drag and drop messages
    * @param c The component to unregister
    * @param recursive Recursively unregister components within a container
    * @since 1.0
    */
   public boolean remove(java.io.PrintStream out, Component c, boolean recursive) { // Make sure we support dnd.
      if (supportsDnD()) {
         //log(out, "FileDrop: Removing drag-and-drop hooks.");
         c.setDropTarget(null);
         if (recursive && (c instanceof Container)) {
            Component[] comps = ((Container) c).getComponents();
            for (int i = 0; i < comps.length; i++)
               remove(out, comps[i], recursive);
            return true;
         } // end if: recursive
         else
            return false;
      } // end if: supports DnD
      else
         return false;
   } // end remove

}
