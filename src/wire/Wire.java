/**
 * A wire that connects an output port to one or more input ports. 
 */

package wire;

import port.InputPort;
import port.OutputPort;

public interface Wire<T> {
   public OutputPort<T> getSourcePort();
   public int  getNumberOfTargetPorts();
   public InputPort<T>  getTargetPort(int index);

   public void propagateSignal();
   public void propagateSignal(int i);
}
