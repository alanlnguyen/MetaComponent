package component;

import wire.*;
import port.*;
import message.*;

public class Cluster<Message> extends MetaComponent<Message>{

	public Cluster()
	{
		super();
	}

	public void process()
	{
		System.out.println("Processing for " + this.sName);
		convertExternaltoInternal();	//Move data from external InputPorts to internal OutputPorts
		propagateSignals();	//Make the data flow
		processSubComponents();	//do calculations
		propagateSignals();	//Make the data flow again
		convertInternaltoExternal();	//Move data from internal InputPorts to external OutputPorts
		propagateSignals();		//DO IT AGAIN!!!
	}
	
	protected void propagateSignals()
	{
		for (Wire<Message> w : wires) 
		{
			if(w.getSourcePort().getValue() !=null)
			{
				if(w.getSourcePort().getValue() instanceof ControllerMessage)	//This logic is only for ControllerMessages
				{
					ControllerMessage msg = (ControllerMessage) w.getSourcePort().getValue();
					String destComp = msg.getComponent();
					if( destComp == sName)	//if the Message is targeting an entire Cluster, pass the Message to the cluster
					{
						for(int i = 0; i < w.getNumberOfTargetPorts(); i++)
						{
							w.propagateSignal();
						}
					}
					else	//otherwise, only pass it to the particular component
					{	
						for(int i = 0; i < w.getNumberOfTargetPorts(); i++)
						{
							InputPort<Message> ip = w.getTargetPort(i);
							Component<Message> targetComp = ip.getParent();
							
							if(targetComp.getName() == destComp)
							{
								w.propagateSignal(i);
							}
						}
					}
				}
				else		//This logic applies to SystemMessages
				{
					for(int i = 0; i < w.getNumberOfTargetPorts(); i++)
					{
						w.propagateSignal();
					}
				}
				
			}
		}
	}
}
