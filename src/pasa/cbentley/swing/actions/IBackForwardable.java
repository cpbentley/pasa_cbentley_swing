package pasa.cbentley.swing.actions;

import pasa.cbentley.core.src4.logging.IStringable;

public interface IBackForwardable extends IStringable {

   public void cmdNavBack();

   public void cmdNavForward();

}
