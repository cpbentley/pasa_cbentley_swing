package pasa.cbentley.swing.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.swing.ctx.SwingCtx;

public abstract class ComboBoxModelSortedHashMapAbstract<E extends INameable<V>, V> extends DefaultComboBoxModel<String> implements IStringable {
   /**
    * 
    */
   private static final long      serialVersionUID = -8626585985100434315L;

   protected Comparator<E>        comparator;

   protected HashMap<String, E>   pks;

   protected final SwingCtx       sc;

   protected boolean              isUpdating       = false;

   /**
    * When set to true when creator explicitely knows
    * all the data is loaded into the model
    */
   protected boolean              isDataLoaded     = false;

   protected IModelLoadedListener listener;

   public boolean isUpdating() {
      return isUpdating;
   }

   /**
    *  Create an empty model that will use the natural sort order of the item
    */
   public ComboBoxModelSortedHashMapAbstract(SwingCtx sc) {
      super();
      this.sc = sc;
      pks = new HashMap<String, E>();
   }

   /**
    *  Create an empty model that will use the specified Comparator
    */
   public ComboBoxModelSortedHashMapAbstract(SwingCtx sc, Comparator<E> comparator) {
      super();
      this.sc = sc;
      this.comparator = comparator;
   }

   @Override
   public void addElement(String element) {
      insertElementAt(element, 0);
   }

   public void addNamer(E pk) {
      String name = pk.getNameableString();
      pks.put(name, pk);
      addElement(name);
   }

   public boolean isDataLoaded() {
      return isDataLoaded;
   }

   public void addNamers(List<E> rows) {
      Iterator<E> it = rows.iterator();
      while (it.hasNext()) {
         E pk = (E) it.next();
         addNamer(pk);
      }
   }

   public void setListenerModelLoader(IModelLoadedListener listener) {
      this.listener = listener;
   }

   public void notifyFinishLoading() {
      if (listener != null) {
         listener.modelDidFinishLoading(this);
      }
   }

   /**
    * Clone model keeping data reference
    * @return
    */
   public ComboBoxModelSortedHashMapAbstract<E, V> cloneUpperModel() {
      ComboBoxModelSortedHashMapAbstract<E, V> newModel = createNewInstance();
      newModel.pks = this.pks;
      newModel.comparator = this.comparator;
      for (String name : pks.keySet()) {
         newModel.addElement(name);
      }
      return newModel;
   }

   protected abstract ComboBoxModelSortedHashMapAbstract<E, V> createNewInstance();

   public void cloneReplaceData(ComboBoxModelSortedHashMapAbstract<E, V> rootModel) {
      isUpdating = true;
      //remove all elements because we will add again all the elements from rootModel
      this.removeAllElements();
      this.pks = rootModel.pks;
      for (String name : pks.keySet()) {
         this.addElement(name);
      }
      isUpdating = false;
   }

   /**
    * 
    * @param str
    * @return
    */
   public int findElementIndex(String str) {
      return getIndexOf(str);
   }

   /**
    * Null if there is no selection
    * @return
    */
   public E getSelectedObject() {
      String keyString = (String) getSelectedItem();
      return pks.get(keyString);
   }

   @Override
   public void insertElementAt(String element, int index) {
      isUpdating = true;
      int size = getSize();
      E baseE = pks.get(element);
      //  Determine where to insert element to keep model in sorted order
      for (index = 0; index < size; index++) {
         if (comparator != null) {
            String strAtIndex = getElementAt(index);
            E eAtIndex = pks.get(strAtIndex);
            if (comparator.compare(eAtIndex, baseE) > 0) {
               break;
            }
         } else {
            String c = getElementAt(index);
            if (c.compareTo(element) > 0)
               break;
         }
      }
      super.insertElementAt(element, index);

      //  Select an element when it is added to the beginning of the model
      if (index == 0 && element != null) {
         //we have to select a key otherwise it returns null
         //a selection event will be generated (actionEvent) use flag updating 
         //setSelectedItem(element);
      }
      isUpdating = false;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "ComboBoxModelSortedHashMapAbstract");
      toStringPrivate(dc);
      dc.nlLvlO(comparator, "comparator");
      sc.getC5().toStringHashMap1Line(dc, pks, "HashMap<String,INameble>");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isUpdating", isUpdating);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ComboBoxModelSortedHashMapAbstract");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}