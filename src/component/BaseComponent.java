/**
 *  ========================================================================
 *  BaseComponent.java: This is a pass-through (data flow) implementation of
 *  the Component interface.  
 *  ========================================================================
 */

package component;

import port.InputPort;
import port.InputPortImpl;
import port.OutputPort;
import port.OutputPortImpl;
import port.PortImpl;
import engine.ComponentEngine;

public class BaseComponent<T> implements Component<T> {
   protected String sName;

   protected int inSize, outSize;
   protected InputPortImpl<T>[]   inports;
   protected OutputPortImpl<T>[] outports;

   // the function performed by this component

   protected ComponentEngine<T> function;

   /**
     * 
     * @param inputs Number of input ports
     * @param outputs Number of output ports
     * @param f The function to perform the processing
     */

   public BaseComponent(int inputs, int outputs, ComponentEngine<T> f ) {
      inSize   = inputs;
      outSize  = outputs;
      function = f;

      inports = new InputPortImpl[inSize];
      for (int i = 0; i < inSize; i++) {
         inports[i] = new InputPortImpl<T>(this);
      }

      outports = new OutputPortImpl[outSize];
      for (int i = 0; i < outSize; i++) {
         outports[i] = new OutputPortImpl<T>(this);
      }
   }

   public void setName ( String sName ) {
           this.sName = sName;
           for (int i = 0; i < inSize; i++) {
               inports[i].setName("BC(" + sName + ").in[" + i + "]");
           }
           for (int i = 0; i < outSize; i++) {
               outports[i].setName("BC(" + sName + ").out[" + i + "]");
           }
    }
   public String getName () {
		  return sName;
	   }

    public int getInputSize() {
       return inSize;
    }

    public int getOutputSize() {
       return outSize;
    }

    public InputPort<T> getInputPort(int index) {
       return inports[index];
    }

    public OutputPort<T> getOutputPort(int index) {
       return outports[index];
    }

    /**
      * Delegate to the engine to do the processing.
      */

    public void process() {
       System.out.println("Processing for " + sName);
       function.process(inports, outports);
    }

    // Detailed string representation ....

    public String toString() {
       return "BaseComponent " + sName;
    }
    
}
