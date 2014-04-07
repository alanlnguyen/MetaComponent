package component;

import java.util.*;

import port.InputPort;
import port.InputPortImpl;
import port.OutputPort;
import port.OutputPortImpl;
import wire.Wire;
import wire.WireImpl;
import message.*;

public class Node<Message> implements Component<Message> {
	public String sName;
	
	private HashMap<OutputPort<Message>,WireImpl<Message>> sourceWireMap;
	private ArrayList<Wire<Message>> wires;		//This list is only for wires with source = OutputPort();

	private ArrayList<InputPort<Message>>  externalInputs  = new ArrayList<InputPort<Message>>();
	private ArrayList<OutputPort<Message>> externalOutputs = new ArrayList<OutputPort<Message>>();
	
	protected HashMap<String, Object> nodeProperties;
	
	//Constructor
	public Node()
	{
		sourceWireMap = new HashMap<OutputPort<Message>,WireImpl<Message>>();
		wires = new ArrayList<Wire<Message>>();
		nodeProperties = new HashMap<String, Object>();
	}
	public Node(String name)
	{
		sourceWireMap = new HashMap<OutputPort<Message>,WireImpl<Message>>();
		wires = new ArrayList<Wire<Message>>();
		nodeProperties = new HashMap<String, Object>();
		sName = name;
	}
	
	//Modifier methods
	
	public void addConnection(OutputPort<Message> src, InputPort<Message> target) 
	{
		WireImpl<Message> srcWire = sourceWireMap.get(src);
	
		// Make a new wire
	
		if (srcWire == null)
		{
			srcWire = new WireImpl<Message>(src);
	     
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
	
	public void addExternalPort(InputPort<Message> port)
	{
		externalInputs.add(port);
	}
	
	public void addExternalPort(OutputPort<Message> port)
	{
		externalOutputs.add(port);
	}
	public void addProperty(String propertyName, Object propertyVal)
	{
		nodeProperties.put(propertyName, propertyVal);
	}
	public void setName( String sName )
	{
		this.sName = sName;
	}

	//Retrieval Methods
	public InputPort<Message> getInputPort(int index)
	{
		 return externalInputs.get(index);
	}

	public int getInputSize()
	{
		return externalInputs.size();
	}

	public String getName ()
	{
		return sName;
	}
	public OutputPort<Message> getOutputPort(int index)
	{
		return externalOutputs.get(index);
	}
	
	public int getOutputSize()
	{
		return externalOutputs.size();
	}

	/// perform the component's processing
	public void process()
	{
		System.out.println("Processing for " + sName);
		for(InputPort<Message> ip : externalInputs)
		{
			if(ip.getValue() !=null)
			{
				Message tempMessage = ip.getValue();
				String attribute = ((ControllerMessage) tempMessage).getAttribute();
				Object val = ((ControllerMessage) tempMessage).getValue();
				System.out.println("Changing Attribute: " + attribute + " to " + val);
				nodeProperties.put(attribute, val);
				createSystemMessage();
			}
		}
	}

	private void propagateSignals()
	{
		for (Wire<Message> w : wires) {
			w.propagateSignal();
		}
	}
	protected void createSystemMessage()
	{
		System.out.println("Creating SysMessage for " + sName);
		SystemMessage sysMessage = new SystemMessage(sName+"SysMessage",this.sName,"Attributes",nodeProperties);
		for(OutputPort op: externalOutputs)
		{
			op.setValue(sysMessage);
		}
	}
	
	//print Node Information
	public String toString()
	{
		return "Node: " + sName;
	}
	
	public void listAttributes()
	{
		System.out.println("Attributes of " + sName);
		System.out.println("====================================================");
		
		for(String att: nodeProperties.keySet())
		{
			System.out.println("Attribute: " + att + " = " + nodeProperties.get(att));
		}
	}
	
	public void listConnections()
    {
	   System.out.println("Connections on all OutputPorts in Node " + sName);
	   System.out.println("====================================================");
	   for(OutputPort<Message> oPort: sourceWireMap.keySet())
	   {
		   for(int i = 0; i < sourceWireMap.get(oPort).getNumberOfTargetPorts(); i++)
		   {
			   System.out.println("OutputPort: " + oPort + " -->" + "InputPort " + sourceWireMap.get(oPort).getTargetPort(i));
		   }
	   }
	   System.out.println("\n");
    }
	public void listInputPorts()
    {
	   System.out.println("InputPorts in Component " + sName);
	   System.out.println("====================================================");
	   for(InputPort<Message> iPort: externalInputs)
	   {
		   System.out.println(iPort);
	   }
	   System.out.println("\n");
    }
	public void listOutputPorts()
    {
	   System.out.println("OutputPorts in Node " + sName);
	   System.out.println("====================================================");
	   for(OutputPort<Message> oPort: externalOutputs)
	   {
		   System.out.println(oPort);
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
	
	public void listProperties()
	{
		System.out.println("Properties of Node " + sName);
		System.out.println("====================================================");
		System.out.println("Property\t\t\tNode");
		System.out.println("========\t\t\t=====");
		
		for(String k: nodeProperties.keySet())
		{
			System.out.println(k + "\t\t\t" + nodeProperties.get(k));
		}
	}
	
	
}
