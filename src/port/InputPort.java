/**
 *  ==========================================================
 *  An input port.
 *  ==========================================================
 */

package port;

// Set the name and value of the input port ...

public interface InputPort<T> extends Port<T> {
   public void setName(String sName);
   public void setValue(T value);
   public T getValue();
   public String getName();
} 
