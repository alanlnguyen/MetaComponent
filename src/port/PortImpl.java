/**
 *  ===============================================================
 *  A base for building port implementations. It can function 
 *  either as an input or output port, or both. 
 *
 *    @see com.wickedcooljava.sci.component.InputPortImpl
 *    @see com.wickedcooljava.sci.component.OutputPortImpl
 *  ===============================================================
 */

package port;

import component.Component;

public class PortImpl<T> implements Port<T> {
   private boolean initialized = false;
   public String        sName;
   private Component<T> parent;
   private T value;

   public PortImpl(Component<T> par) {
      parent = par;
   }

   public Component<T> getParent() {
      return parent;
   }

   public void setName( String sName ) {
      this.sName = sName;
   }

   public void setValue(T val) {
      this.initialized = true;
      value = val;
   }

   public T getValue() {
      return value;
   }

   public boolean getInitialized() {
      return this.initialized;
   }

   public String toString() {
      return sName + ".value=" + value;
   }
   
   public String getName()
   {
	   return sName;
   }
   
   
}
