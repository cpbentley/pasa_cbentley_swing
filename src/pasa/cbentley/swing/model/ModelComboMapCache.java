package pasa.cbentley.swing.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src5.interfaces.INameable;
import pasa.cbentley.core.src5.utils.HashMapCache;
import pasa.cbentley.swing.ctx.SwingCtx;

/**
 * 
 * @author Charles Bentley
 *
 * @param <E>
 * @param <V>
 */
public abstract class ModelComboMapCache<E extends INameable<V>, V extends IStringable> extends DefaultComboBoxModel<String> implements IStringable {
   /**
    * 
    */
   private static final long      serialVersionUID = -8626585985100434315L;

   protected Comparator<E>        comparator;

   protected HashMapCache<E, V>   hashMapCache;

   protected final SwingCtx       sc;

   protected boolean              isUpdating       = false;

   /**
    * When set to true when creator explicitely knows
    * all the data is loaded into the model
    */
   protected boolean              isDataLoaded     = false;

   public boolean isUpdating() {
      return isUpdating;
   }

    
   /**
    * Removes existing elements and sets the whole data for the model 
    * @param map
    */
   public void setMap(HashMapCache<E, V> map) {
      this.removeAllElements();
      this.hashMapCache = map;
      for (String name : map.getKeySet()) {
         addElement(name);
      }
      isDataLoaded = true;
   }
   
   /**
    *  Create an empty model that will use the natural sort order of the item
    */
   public ModelComboMapCache(SwingCtx sc) {
      super();
      this.sc = sc;
      hashMapCache = new HashMapCache<E, V>(sc.getC5());
   }

   /**
    *  Create an empty model that will use the specified Comparator
    */
   public ModelComboMapCache(SwingCtx sc, Comparator<E> comparator) {
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
      hashMapCache.put(name, pk);
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

   /**
    * Clone model keeping data reference
    * @return
    */
   public ModelComboMapCache<E, V> cloneUpperModel() {
      ModelComboMapCache<E, V> newModel = createNewInstance();
      newModel.hashMapCache = this.hashMapCache;
      newModel.comparator = this.comparator;
      for (String name : hashMapCache.getKeySet()) {
         newModel.addElement(name);
      }
      return newModel;
   }

   protected abstract ModelComboMapCache<E, V> createNewInstance();

   public void cloneReplaceData(ModelComboMapCache<E, V> rootModel) {
      isUpdating = true;
      //remove all elements because we will add again all the elements from rootModel
      this.removeAllElements();
      this.hashMapCache = rootModel.hashMapCache;
      for (String name : hashMapCache.getKeySet()) {
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
      return hashMapCache.getValue(keyString);
   }

   @Override
   public void insertElementAt(String element, int index) {
      isUpdating = true;
      int size = getSize();
      E baseE = hashMapCache.getValue(element);
      //  Determine where to insert element to keep model in sorted order
      for (index = 0; index < size; index++) {
         if (comparator != null) {
            String strAtIndex = getElementAt(index);
            E eAtIndex = hashMapCache.getValue(strAtIndex);
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
      dc.root(this, "ModelComboMapCache");
      toStringPrivate(dc);
      dc.nl();
      sc.toSD().d((DefaultComboBoxModel)this,dc);
      dc.nlLvlO(comparator, "comparator");
      dc.nlLvl(hashMapCache, "HashMapCache<E,V>");
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendVarWithSpace("isUpdating", isUpdating);
      dc.appendVarWithSpace("isDataLoaded", isDataLoaded);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "ModelComboMapCache");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return sc.getUCtx();
   }

   //#enddebug

}