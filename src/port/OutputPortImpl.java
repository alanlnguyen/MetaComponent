/**
 * ====================================================================
 * A basic implementation of output port, using the PortImpl as a base.
 * ====================================================================
 */

package port;

import component.Component;

public class OutputPortImpl<T> extends PortImpl<T> implements OutputPort<T> {
	
	public OutputPortImpl(Component<T> parent) {
		super(parent);
	}
	

}
