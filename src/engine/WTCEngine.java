package engine;
import java.util.HashMap;

import port.*;
import message.*;

public class WTCEngine<T extends Message> implements ComponentEngine<T> {
	
	public void process( PortImpl<T>[] in, PortImpl<T>[] out )
	{
		
		for (PortImpl<T> i : in)
		{
			if(i.getValue() !=null)
			{
				Message inMessage = i.getValue();			
				checkRules(inMessage);
			}
		}

	}
	
	private void checkRules(Message inMessage)
	{
		
		SystemMessage sysMessage = (SystemMessage) inMessage;
		HashMap<String,Object> attributes = (HashMap<String,Object>) sysMessage.getValue();
		for(String att: attributes.keySet())
		{
			switch(att)
			{
				case "waterAcc": 
				{
					Double v =  (Double) attributes.get(att);
					if(v > 2.5)
					{
						System.out.println("Closing station: " + sysMessage.getfromComponent());
						Message outMessage = new ControllerMessage("WaterLevelMessage","TTController", "stationStatus", "closed", sysMessage.getfromComponent());
					}
				}
				default:
					//System.out.println("No attribute found");
			}
		}
	}

}

