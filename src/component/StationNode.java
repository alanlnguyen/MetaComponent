package component;

public class StationNode<Message> extends Node<Message>{

	//stationName, stationLocation, serviceRate, stationElevation, isStationAboveGround, stationElectricBy, stationElectricReq, stationStatus
	public StationNode()
	{
		super();
		nodeProperties.put("stationName", null);
		nodeProperties.put("stationLocation", null);
		nodeProperties.put("serviceRate", null);
		nodeProperties.put("stationElevation", null);
		nodeProperties.put("isStationAboveGround", null);
		nodeProperties.put("stationElectricBy", null);
		nodeProperties.put("stationElectricReq", null);
		nodeProperties.put("stationStatus", null);	
		nodeProperties.put("waterAcc", null);
	}
	public StationNode(String name)
	{
		super();
		sName = name;
		nodeProperties.put("stationName", null);
		nodeProperties.put("stationLocation", null);
		nodeProperties.put("serviceRate", null);
		nodeProperties.put("stationElevation", null);
		nodeProperties.put("isStationAboveGround", null);
		nodeProperties.put("stationElectricBy", null);
		nodeProperties.put("stationElectricReq", null);
		nodeProperties.put("stationStatus", null);
		nodeProperties.put("waterAcc", null);
	}
	
	public String toString()
	{
		return "Station Node: " + sName;
	}
}
