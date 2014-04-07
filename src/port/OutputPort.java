/**
 * An output port.
 */

package port;

// get the value of the output port

public interface OutputPort<T> extends Port<T> {
   public void setName(String sName);
   public T getValue();
   public void setValue(T value);
   public String getName();
}
