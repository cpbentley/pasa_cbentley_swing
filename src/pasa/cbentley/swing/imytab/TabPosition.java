package pasa.cbentley.swing.imytab;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntBuffer;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * Tracks {@link IMyTab} position in the hierarchy.
 * <br>
 * original Index in parent {@link TabbedBentleyPanel}
 * <br>
 * It is currently framed.
 * <br>
 * The parent owner.
 * Original left and right siblings
 * <br>
 * 
 * <br>
 * @author Charles Bentley
 *
 */
public class TabPosition implements IStringable {
   public static final int    POS_0_CENTER      = 0;

   public static final int    POS_1_TOP         = 1;

   public static final int    POS_2_BOTTOM      = 2;

   public static final int    POS_3_LEFT        = 3;

   public static final int    POS_4_RIGHT       = 4;

   public static final int    POS_5_FRAMED      = 5;

   public static final int    TBLR_FLAG_1_TOP   = 1 << 0;

   public static final int    TBLR_FLAG_2_BOT   = 1 << 1;

   public static final int    TBLR_FLAG_3_LEFT  = 1 << 2;

   public static final int    TBLR_FLAG_4_RIGHT = 1 << 3;

   /**
    * When null, we have the root {@link TabbedBentleyPanel} in a Frame hierarchy. ?
    * 
    * When a Tab is framed alone, why should it lose its real hierarchy? The Back command
    * can still give focus to the previous page.
    * 
    * So when framed alone, a
    */
   private TabbedBentleyPanel parent;

   /**
    * Possibly null
    */
   private IMyTab             left;

   /**
    * Possibly null
    */
   private IMyTab             right;

   /**
    * Set as default. Can be changed.. Should only be set once
    * from user settings or factory settings.
    */
   private int                positionDefault;

   /**
    * Index position in the {@link TabbedBentleyPanel} parent.
    * <br>
    * Used when position is {@link TabPosition#POS_0_CENTER}
    */
   private int                index;

   /**
    * Set when {@link IMyTab} is extracted from its parent to a frame
    */
   private FrameIMyTab        frame;

   /**
    * When position of tab is {@link TabPosition#POS_0_CENTER}, the tab 
    * is inside the {@link TabbedBentleyPanel}.
    * <br>
    * When {@link TabPosition#POS_2_BOTTOM}, the tab is located at the south of the {@link TabbedBentleyPanel}.
    * 
    * This will depends on the CTBLR implementation of the component that owns the {@link TabbedBentleyPanel}.
    * <br>
    * @see TabPosition#getPosition()
    */
   private int                position;

   /**
    * History of the tab positions
    */
   private IntBuffer          historyPosition;

   private SwingCtx           sc;

   private IMyTab             owner;

   private IntBuffer          historyForward;

   public TabPosition(SwingCtx sc, IMyTab tab) {
      this.sc = sc;
      this.owner = tab;
      historyPosition = new IntBuffer(sc.getUC());
      historyForward = new IntBuffer(sc.getUC());

   }

   public IMyTab getOwner() {
      return owner;
   }

   /**
    * Finish the frame if not null
    */
   public void finishTheFrameIfAny() {
      if (frame != null) {
         frame.setVisible(false);
         frame.dispose();
         frame = null;
      }

   }

   /**
    * Could be null if not set
    * @return
    */
   public TabbedBentleyPanel getParent() {
      return parent;
   }

   public IMyTab getLeft() {
      return left;
   }

   public IMyTab getRight() {
      return right;
   }

   /**
    * The index of this position in the owner in the {@link TabbedBentleyPanel}.
    * <br>
    * This value is no more valid when a tab is framed out of the {@link TabbedBentleyPanel}
    * @return
    */
   public int getIndex() {
      return index;
   }

   public void setParent(TabbedBentleyPanel parent) {
      this.parent = parent;
   }

   public void setLeft(IMyTab left) {
      this.left = left;
   }

   public void setRight(IMyTab right) {
      this.right = right;
   }

   /**
    * Set the index when a Tab is hosted inside its parent {@link TabbedBentleyPanel}
    * @param index
    */
   public void setIndex(int index) {
      this.index = index;
   }

   public boolean isFramed() {
      return frame != null;
   }

   public boolean isCentered() {
      return position == POS_0_CENTER;
   }

   /**
    * Retursn the reference to the {@link FrameIMyTab}
    * @return
    */
   public FrameIMyTab getFrame() {
      return frame;
   }

   /**
    * Beware
    * @param frame
    */
   public void setFrame(FrameIMyTab frame) {
      this.frame = frame;
   }

   /**
    * <li> {@link TabPosition#POS_0_CENTER}
    * <li> {@link TabPosition#POS_1_TOP}
    * <li> {@link TabPosition#POS_2_BOTTOM}
    * <li> {@link TabPosition#POS_3_LEFT}
    * <li> {@link TabPosition#POS_4_RIGHT}
    * @return
    */
   public int getPosition() {
      return position;
   }

   /**
    * Definition of Position
    * When position of tab is {@link TabPosition#POS_0_CENTER}, the tab 
    * is inside the {@link TabbedBentleyPanel}.
    * <br>
    * When {@link TabPosition#POS_2_BOTTOM}, the tab is located at the south of the {@link TabbedBentleyPanel}.
    * 
    * This will depends on the CTBLR implementation of the component that owns the {@link TabbedBentleyPanel}.
    * <br>
    * @param position
    */
   public void setPosition(int position) {
      this.position = position;
      //history is kept when backed..
      historyPosition.addInt(position);
   }

   public int getHistoryPositionNext() {
      if (historyForward.getSize() == 0) {
         return position;
      } else {
         int v = historyForward.removeLast();
         historyPosition.addInt(v);
         return v;
      }
   }

   public int getHistoryBackSize() {
      return historyPosition.getSize();
   }

   public int getPositionDefault() {
      return positionDefault;
   }

   public void setPositionDefault(int positionDefault) {
      this.positionDefault = positionDefault;
   }

   /**
    * Current index if none
    * @return
    */
   public int getHistoryPositionPrevious() {
      if (historyPosition.getSize() == 0) {
         return position;
      } else {
         int v = historyPosition.removeLast();
         historyForward.addInt(v);
         return historyPosition.getLast();
      }
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "TabPosition");
      dc.appendVarWithSpace("index", index);
      dc.appendVarWithSpace("Position", toStringTabPos(position));
      dc.appendVarWithSpace("PositionDefault", toStringTabPos(positionDefault));

      dc.nlLvl(historyPosition, "HistoryBackward");
      dc.nlLvl(historyForward, "HistoryForward");

      dc.nlLvl1Line(left, "LeftTab");
      dc.nlLvl1Line(right, "RightTab");
      dc.nlLvl1Line(owner, "Owner");

      dc.nlLvl1Line(frame, "frame");

   }

   public UCtx toStringGetUCtx() {
      return sc.getUC();
   }

   public String toStringTabPos(int pos) {
      switch (pos) {
         case POS_0_CENTER:
            return "Center";
         case POS_1_TOP:
            return "Top";
         case POS_2_BOTTOM:
            return "Bottom";
         case POS_3_LEFT:
            return "Left";
         case POS_4_RIGHT:
            return "Right";
         case POS_5_FRAMED:
            return "Frame";
         default:
            return "UnknownPos:" + pos;
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "TabPosition");
      dc.appendVarWithSpace("index", index);
      dc.appendVarWithSpace("pos", toStringTabPos(position));
      dc.appendVarWithSpace("posDef", toStringTabPos(positionDefault));
   }

   //#enddebug
}
