/**
 *  ====================================================================
 *  A basic implementation of output port, using the PortImpl as a base.
 *  ====================================================================
 */

package port;

import component.Component;

public class InputPortImpl<T> extends PortImpl<T> implements InputPort<T> {

	public InputPortImpl(Component<T> parent) {
		super(parent);
	}
}
