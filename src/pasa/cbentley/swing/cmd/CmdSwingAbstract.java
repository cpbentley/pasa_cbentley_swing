package pasa.cbentley.swing.cmd;

public abstract class CmdSwingAbstract<T> {

   public abstract String getCmdString();

   public abstract String getCmdStringTip();

   public abstract void executeWith(T t);
}
