package message;

import java.util.HashMap;


public class SystemMessage  extends Message
{

	//For SystemMessages, we will send the entire HashMap of properties to the controller for processing
	public SystemMessage()
	{
		super();
	}
	
	public SystemMessage(String name, String from, String att, Object val)
	{
		super(name,from,att,val);
	}
	public String toString()
	{
		//return "Message \"" + name + "\":" + "\nType: " + fromComponent + "\nAttribute: " + attribute + "\nValue: " + value;
		return "SystemMessage " + name;
	}
	public String messageDetails()
	{
		return "Message \"" + name + "\":" + "\nType: " + fromComponent + "\nAttribute: " + attribute + "\nValue: " + value;
	}
}
