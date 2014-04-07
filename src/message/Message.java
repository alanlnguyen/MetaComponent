package message;
import java.util.*;

public abstract class Message {
	
	protected String name;
	protected String fromComponent;	//holds where the message originated from 
	protected String attribute;		//name of attribute
	protected Object value;  			//value of attribute
	private String destComponent;	//holds the affected component in CitySystem

	//Constructors
	Message(){};
	Message(String name, String from, String att, Object val)
	{
		this.name = name;
		fromComponent = from;
		attribute = att;
		value = val;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getfromComponent()
	{
		return fromComponent;
	}
	
	public void setfromComponent(String from)
	{
		fromComponent = from;
	}
	
	
	public void modifyAttribute(String att)
	{
		attribute = att;
	}
	
	public void modifyValue(Object val)
	{
		value = val;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public String getAttribute()
	{
		return attribute;
	}
	public abstract String toString();
	
}
