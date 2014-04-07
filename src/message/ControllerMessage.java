package message;


public class ControllerMessage extends Message {
	
	private String destComponent;	//holds the affected component in CitySystem
	
	public ControllerMessage(String name, String from, String att, Object val, String dest)
	{
		super(name, from, att, val);
		destComponent = dest;
	}
	public String toString()
	{
		//return "ControllerMessage \"" + name + "\":" + "\nTo: " + destComponent + "\nAttribute: " + attribute + "\nValue: " + value;
		return "ControllerMessage " + name;
	}
	
	public String getComponent()
	{
		return destComponent;
	}
	
	public void setComponent(String dest)
	{
		destComponent = dest;
	}

}
