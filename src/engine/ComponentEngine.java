/**
 *  ======================================================================
 *  An interface for the processing engine of a component. 
 *  Its job is to convert inputs into outputs.
 *  Note: This is a kind of functor but does not follow the JGA convention. 
 *  ======================================================================
 */

package engine;

import port.PortImpl;

public interface ComponentEngine<T> {
   public void process( PortImpl<T>[] in, PortImpl<T>[] out );
}
