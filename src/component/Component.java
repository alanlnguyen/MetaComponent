
/**
 * ================================================================================
 * A generic interface for components that process any type of data.
 * A component has an specific number of input and output ports that connect
 * to other components, and performs some process that converts inputs into outputs.
 * ================================================================================
 */

package component;

import port.InputPort;
import port.OutputPort;

public interface Component<T> {

	/// set component name ...
	public void setName( String sName );
	public String getName();

	/// get the number of input ports
	public int getInputSize();

	/// get the number of output ports
	public int getOutputSize();

	/// get the nth input port
	public InputPort<T>  getInputPort(int index);

	///get the nth output port
	public OutputPort<T> getOutputPort(int index);

	/// perform the component's processing
	public void process();
	
	//print out component
	public String toString();
}
