/**
 *  ==================================================================================
 *  @author brian
 *  ==================================================================================
 */

package component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import port.InputPort;
import port.InputPortImpl;
import port.OutputPort;
import port.OutputPortImpl;
import wire.Wire;
import wire.WireImpl;

public class MetaComponent<T> implements Component<T> {
   protected String sName;

   public void setName ( String sName ) {
      this.sName = sName;
   }
   
   public String getName () {
	  return sName;
   }

   public interface MetaVisitor<T> {
      public void visit(Component<T> component);
   }
	
   protected HashMap<OutputPort<T>,WireImpl<T>> sourceWireMap;
   protected ArrayList<Wire<T>> wires;		//This list is only for wires with source = OutputPort();
   protected ArrayList<Component<T>>  subComponents;		//anguyen: Changed from HashSet to ArrayList because order matters.  Need to account for duplicates (at a later date)
   protected ArrayList<InputPort<T>>  externalInputs  = new ArrayList<InputPort<T>>();
   protected ArrayList<OutputPort<T>> externalOutputs = new ArrayList<OutputPort<T>>();
   
   //anguyen: This next set of variables is to facilitate passing information between metacomponents.  All this should be done in the background, and logic should be hidden from user.
   
   protected ArrayList<InputPort<T>> internalInputs = new ArrayList<InputPort<T>>();		//This list will contain all internal inputPorts and convert to OutputPorts for passing to other MCs
   protected ArrayList<OutputPort<T>> internalOutputs = new ArrayList<OutputPort<T>>();	//This list will contain all internal outputPorts and take Inputs to Outputs for internal processing
   protected HashMap<InputPort<T>, OutputPort<T>> externalInputToInternalOutputMap = new HashMap<InputPort<T>, OutputPort<T>>();	//Maps External InputPorts to Internal OutputPorts 
   protected HashMap<OutputPort<T>, InputPort<T>> externalOutputToInternalInputMap = new HashMap<OutputPort<T>, InputPort<T>>();	//Maps Internal InputPorts to External OutputPorts
   

   public void accept( MetaVisitor<T> v ) {
      for (Component<T> item : subComponents) {
           v.visit(item);
           
      }
   }

   //Constructor
   public MetaComponent() {
      subComponents = new ArrayList<Component<T>>();
      wires = new ArrayList<Wire<T>>();
      sourceWireMap = new HashMap<OutputPort<T>,WireImpl<T>>();
   }
   
   //Retrieval methods
   public int getInputSize() {
      return externalInputs.size();
   }

   public int getOutputSize() {
      return externalOutputs.size();
   }
	
   public InputPort<T> getInputPort(int index) {
      return externalInputs.get(index);
   }

   public OutputPort<T> getOutputPort(int index) {
      return externalOutputs.get(index);
   }
   
   //Modification methods
   public void addExternalPort(InputPort<T> port) {
	      //subComponents.add(port.getParent());
	      externalInputs.add(port);
	      
	      //Create Internal OutputPort and map it to the external InputPort
	      OutputPort<T> internalOutputPort = new OutputPortImpl<T>(this);
	      internalOutputPort.setName("Internal OutputPort of " + port.getName());
	      
	      externalInputToInternalOutputMap.put(port, internalOutputPort);
	   }
		
   public void addExternalPort(OutputPort<T> port) {
      //subComponents.add(port.getParent());
      externalOutputs.add(port);
      
      //Create Internal InputPort and map it to the external OutputPort
      InputPort<T> internalInputPort = new InputPortImpl<T>(this);
      internalInputPort.setName("Internal InputPort of " + port.getName());
      
      //System.out.println("Adding InputPort " + internalInputPort);
      externalOutputToInternalInputMap.put(port, internalInputPort);
      
  
   }
   public void addSubComponent (Component<T> subComp)
   {
	  subComponents.add(subComp);
   }
   
   public void addConnection(OutputPort<T> src, InputPort<T>... targets) {
      for (InputPort<T> target : targets) {
         addConnection(src, target);
      }
   }
	 
   public void addConnection(OutputPort<T> src, InputPort<T> target) {

      //subComponents.add(src.getParent());
      //subComponents.add(target.getParent());

      WireImpl<T> srcWire = sourceWireMap.get(src);

      // Make a new wire

      if (srcWire == null) {
         srcWire = new WireImpl<T>(src);
         
         //anguyen: Need to add target to the wire
         srcWire.addTargetPort(target);

         sourceWireMap.put(src, srcWire);
         //anguyen: Add wire to wire list
         wires.add(srcWire);
         
      }
      // add the target port to the wire
      else
      {
	      srcWire.addTargetPort(target);
      }
   }
   //This version of addConnection allows user to connect subComponents to External InputPorts.  
   public void addConnection(InputPort<T> src, InputPort<T> target) {

	 //Try to find the corresponding Internal OutputPort associated with External Input
	   OutputPort<T> srcOutputPort = externalInputToInternalOutputMap.get(src);
	   if(srcOutputPort != null)
	   {
		   WireImpl<T> srcWire = sourceWireMap.get(srcOutputPort);
	
		   // Make a new wire
	
		   if (srcWire == null) {
		      srcWire = new WireImpl<T>(srcOutputPort);
		      
		      //anguyen: Need to add target to the wire
		      srcWire.addTargetPort(target);
	
		      sourceWireMap.put(srcOutputPort, srcWire);
		      //anguyen: Add wire to wire list
		      wires.add(srcWire);
		         
		   }
		   // add the target port to the wire
		   else
		   {
			  srcWire.addTargetPort(target);
		   }
	   }
	   else
	   {
		   System.out.println("Could not find InputPort.  Please make sure the InputPort is registered with the MetaComponent.");
	   }
	 }
   
 //This version of addConnection allows user to connect subComponents to External OutputPorts.  
   public void addConnection(OutputPort<T> src, OutputPort<T> target) {

	 //Try to find the corresponding Internal InputPort associated with External Output
	   InputPort<T> srcInputPort = externalOutputToInternalInputMap.get(target);
	   if(srcInputPort != null)
	   {
		   WireImpl<T> srcWire = sourceWireMap.get(src);
	
		   // Make a new wire
	
		   if (srcWire == null) {
		      srcWire = new WireImpl<T>(src);
		      
		      //anguyen: Need to add target to the wire
		      srcWire.addTargetPort(srcInputPort);
	
		      sourceWireMap.put(src, srcWire);
		      //anguyen: Add wire to wire list
		      wires.add(srcWire);
		         
		   }
		   // add the target port to the wire
		   else
		   {
			  srcWire.addTargetPort(srcInputPort);
		   }
	   }
	   else
	   {
		   System.out.println("Could not find OutputPort.  Please make sure the OutputPort is registered with the MetaComponent.");
	   }
	 }

 //Printing Methods
   
   public void listInputPorts()
   {
	   System.out.println("InputPorts in Component " + sName);
	   System.out.println("====================================================");
	   for(InputPort<T> iPort: externalInputs)
	   {
		   System.out.println(iPort);
	   }
	   System.out.println("\n");
   }
   
   public void listOutputPorts()
   {
	   System.out.println("OutputPorts in Component " + sName);
	   System.out.println("====================================================");
	   for(OutputPort<T> oPort: externalOutputs)
	   {
		   System.out.println(oPort);
	   }
	   System.out.println("\n");
   }
   
   public void listConnections()
   {
	   System.out.println("Connections on all OutputPorts in Component " + sName);
	   System.out.println("====================================================");
	   for(OutputPort<T> oPort: sourceWireMap.keySet())
	   {
		   for(int i = 0; i < sourceWireMap.get(oPort).getNumberOfTargetPorts(); i++)
		   {
			   System.out.println("OutputPort: " + oPort + " -->" + "InputPort " + sourceWireMap.get(oPort).getTargetPort(i));
		   }
	   }
	   System.out.println("\n");
   }
   
   public void listWires()
   {
	   System.out.println("Wires connected to " + sName);
	   System.out.println("====================================================");
	   
	   for (Wire w : wires)
	   {
		   for(int i = 0; i < w.getNumberOfTargetPorts(); i++)
		   {
			   System.out.println("OutputPort " + w.getSourcePort() + " to InputPort " + w.getTargetPort(i));
		   }
	   }
	   System.out.println("\n");
   }
   
   public void listSubComponents()
   {
	   System.out.println("SubComponents of " + sName);
	   System.out.println("====================================================");
	   for (Component <T> comp : subComponents)
	   {
		   System.out.println(comp.toString());
	   }
	   System.out.println("\n");
   }
   
   public String toString()
   {
	   String ret = "MetaComponent " + sName;
	   return ret;
   }
   public void printMetaComponentDetails()
   {
	   listSubComponents();
	   listInputPorts();
	   listOutputPorts();
	   listConnections();
	   listWires();
   } 

 //Processing Methods
   protected void propagateSignals() {
      for (Wire<T> w : wires) {
         
    	  w.propagateSignal();
      }
   }
	
   protected void processSubComponents() {
      for (Component<T> item : subComponents) {
         item.process();
      }
   }
	
   public void process() {
	  System.out.println("Processing for " + this.sName);
	  convertExternaltoInternal();	//Move data from external InputPorts to internal OutputPorts 
      propagateSignals();	//Make the data flow
	  processSubComponents();	//do calculations
	  propagateSignals();	//Make the data flow again
      convertInternaltoExternal();	//Move data from internal InputPorts to external OutputPorts
      propagateSignals();		//DO IT AGAIN!!!
   }
	
   protected void convertExternaltoInternal()
   {
	   //For each external InputPort, set its corresponding internal OutputPort to its value
	   for(InputPort<T> ip : externalInputToInternalOutputMap.keySet())
	   {
		   if(ip.getValue() != null)
		   {
			   OutputPort<T> op = externalInputToInternalOutputMap.get(ip);
			   System.out.println("Moving " + ip.getValue() + "from " + ip.getName() + " to " + op.getName());
			   op.setValue(ip.getValue());
		   }
	   }
   }
   
   protected void convertInternaltoExternal()
   {
	   //For each external InputPort, set its corresponding internal OutputPort to its value
	   for(OutputPort<T> op : externalOutputToInternalInputMap.keySet())
	   {  
		   InputPort<T> ip = externalOutputToInternalInputMap.get(op);
		   if(ip.getValue() != null)
			   op.setValue(ip.getValue());
	   }
   }
   public void processRepeat(int count) {
      if (count <= 0) {
         return;
      }

      for (int i=0; i<count; i++) {
         process();
      }
   }

}
