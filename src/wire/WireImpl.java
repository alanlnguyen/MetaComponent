/**
 *  ===========================================================================
 *  A basic implementation of a Wire. The number of target ports can grow 
 *  dynamically, but for performance reasons it will use a single target object 
 *  when the first one is added. As more targets are added, an ArrayList is used
 *  and is populated with them. 
 *  ===========================================================================
 */

package wire;

import java.util.ArrayList;

import port.InputPort;
import port.OutputPort;

public class WireImpl<T> implements Wire<T> {
   private OutputPort<T> source;

   // lazy target array

   private ArrayList<InputPort<T>> targetList = new ArrayList<InputPort<T>>();
   //private InputPort<T> target;
  // private int count = 0;

   public WireImpl(OutputPort<T> src) {
      source = src;
   }

   public OutputPort<T> getSourcePort() {
      return source;
   }

   public int getNumberOfTargetPorts() {
      return targetList.size();
   }

   public InputPort<T> getTargetPort(int index) {
     

     /* if (target != null) {
          return target;
      }*/

      return targetList.get(index);
   }
   
   public void addTargetPort(InputPort<T> tgt) {
	  
	   if (!targetList.contains(tgt)) {
           targetList.add(tgt);
	   }
   }

   public void propagateSignal() {
      T value = source.getValue();

   
      for (InputPort<T> tgt : targetList) {
          //System.out.println("Passing value " + value + " from " + this.source + "to " + tgt.toString());
    	  System.out.println("Moving " + this.source.getValue() + " from " + this.source.getName() + " to " + tgt.getName());
    	  tgt.setValue(value);
     }
      
   }
   //Propagate signal to only one connection in the wire
   public void propagateSignal(int i)
   {
	   T value = source.getValue();
	   InputPort<T> tgt = targetList.get(i);   
	   tgt.setValue(value);     
   }
}
