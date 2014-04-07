import component.*;
import engine.*;
import message.*;
import port.*;
import wire.*;

public class CitySystemMain {

	public static void main(String[] args) {
		
		//****************Set up overall City*************************
		MetaComponent<Message> citySystem = new MetaComponent<Message>();
		citySystem.setName("Washington, D.C.");
		
		//Set up City Ports
		InputPort<Message> cityInputPort = new InputPortImpl <Message>(citySystem);
		cityInputPort.setName("cityInputPort");
		
		OutputPort<Message> cityOutputPort = new OutputPortImpl <Message>(citySystem);
		cityOutputPort.setName("cityOutputPort");
		
		citySystem.addExternalPort(cityInputPort);
		citySystem.addExternalPort(cityOutputPort);
		
		//****************Transportation System Setup*************************
		MetaComponent<Message> transSystem = new MetaComponent<Message>();
		transSystem.setName("DC Metro");
		
		//Set up Transportation Ports
		InputPort<Message> transInputPort = new InputPortImpl <Message>(citySystem);
		transInputPort.setName("transInputPort");
		
		OutputPort<Message> transOutputPort = new OutputPortImpl <Message>(citySystem);
		transOutputPort.setName("transOutputPort");
		
		transSystem.addExternalPort(transInputPort);
		transSystem.addExternalPort(transOutputPort);
		
		//Add it to the City
		citySystem.addSubComponent(transSystem);
		
		//Add connection from City to trans
		citySystem.addConnection(cityInputPort, transInputPort);		
		citySystem.addConnection(transOutputPort, cityOutputPort);
		
		
		//****************Green Line Setup*************************
		Cluster<Message> greenLine = new Cluster<Message>();
		greenLine.setName("Green Line Cluster");
		//Add cluster Ports
		InputPort<Message> greenLineIport = new InputPortImpl<Message>(greenLine);
		greenLineIport.setName("greenLineIPort");
		OutputPort<Message> greenLineOport = new OutputPortImpl<Message>(greenLine);
		greenLineOport.setName("greenLineOPort");
		
		greenLine.addExternalPort(greenLineIport);
		greenLine.addExternalPort(greenLineOport);
		
		
		//Create stations
		StationNode<Message> greenbeltStation = new StationNode<Message>("Greenbelt");
		StationNode<Message> collegeParkStation = new StationNode<Message>("College Park");
		StationNode<Message> pgPlazaStation = new StationNode<Message>("Prince George's Plaza");
		StationNode<Message> wHyattStation = new StationNode<Message>("West Hyattsville");
		StationNode<Message> fortTottenStation = new StationNode<Message>("Fort Totten");
		
		//add to Cluster
		greenLine.addSubComponent(greenbeltStation);
		greenLine.addSubComponent(collegeParkStation);
		greenLine.addSubComponent(pgPlazaStation);
		greenLine.addSubComponent(wHyattStation);
		greenLine.addSubComponent(fortTottenStation);
		
		//add Input and Output Ports to each station
		InputPort<Message> gbStationIport = new InputPortImpl<Message>(greenbeltStation);
		gbStationIport.setName("gbStationIPort");
		OutputPort<Message> gbStationOport = new OutputPortImpl<Message>(greenbeltStation);
		gbStationOport.setName("gbStationOPort");
		
		greenbeltStation.addExternalPort(gbStationIport);
		greenbeltStation.addExternalPort(gbStationOport);
		
		InputPort<Message> cpStationIport = new InputPortImpl<Message>(collegeParkStation);
		cpStationIport.setName("cpStationIPort");
		OutputPort<Message> cpStationOport = new OutputPortImpl<Message>(collegeParkStation);
		cpStationOport.setName("cpStationOPort");
		
		collegeParkStation.addExternalPort(cpStationIport);
		collegeParkStation.addExternalPort(cpStationOport);
		
		InputPort<Message> pgStationIport = new InputPortImpl<Message>(pgPlazaStation);
		pgStationIport.setName("pgStationIPort");
		OutputPort<Message> pgStationOport = new OutputPortImpl<Message>(pgPlazaStation);
		pgStationOport.setName("pgStationOPort");
		
		pgPlazaStation.addExternalPort(pgStationIport);
		pgPlazaStation.addExternalPort(pgStationOport);
		
		InputPort<Message> whStationIport = new InputPortImpl<Message>(wHyattStation);
		whStationIport.setName("whStationIPort");
		OutputPort<Message> whStationOport = new OutputPortImpl<Message>(wHyattStation);
		whStationOport.setName("whStationOPort");
		
		wHyattStation.addExternalPort(whStationIport);
		wHyattStation.addExternalPort(whStationOport);
		
		InputPort<Message> ftStationIport = new InputPortImpl<Message>(fortTottenStation);
		ftStationIport.setName("ftStationIPort");
		OutputPort<Message> ftStationOport = new OutputPortImpl<Message>(fortTottenStation);
		ftStationOport.setName("ftStationOPort");
		
		fortTottenStation.addExternalPort(whStationIport);
		fortTottenStation.addExternalPort(whStationOport);
		
		//Now let's add connections from station to cluster
		greenLine.addConnection(greenLineIport, gbStationIport);
		greenLine.addConnection(greenLineIport, cpStationIport);
		greenLine.addConnection(greenLineIport, pgStationIport);
		greenLine.addConnection(greenLineIport, whStationIport);
		greenLine.addConnection(greenLineIport, ftStationIport);
		
		greenLine.addConnection(gbStationOport, greenLineOport);
		greenLine.addConnection(cpStationOport, greenLineOport);
		greenLine.addConnection(pgStationOport, greenLineOport);
		greenLine.addConnection(whStationOport, greenLineOport);
		greenLine.addConnection(ftStationOport, greenLineOport);
		
		
		//Finally, add greenLine to Trans and add Connections
		transSystem.addSubComponent(greenLine);
		transSystem.addConnection(transInputPort, greenLineIport);
		transSystem.addConnection(greenLineOport, transOutputPort);
		
		
		//****************WTC Controller Setup*************************
		Controller<Message> wtcController = new Controller<Message>(1,1, new WTCEngine<Message>());		
		wtcController.setName("WTC Controller");
		citySystem.addSubComponent(wtcController);
		
		//Add connection from trans to Controller
		transSystem.addConnection(transOutputPort, wtcController.getInputPort(0));
		//Add connection from Controller to trans
		transSystem.addConnection(wtcController.getOutputPort(0), transInputPort);
		
		//Create the flood
		Message flood = new ControllerMessage("Rain", "Sandy", "waterAcc", (Double) 3.0, "College Park");
		cityInputPort.setValue(flood);
		
		citySystem.process();
		greenbeltStation.listAttributes();
		collegeParkStation.listAttributes();
		pgPlazaStation.listAttributes();
		wHyattStation.listAttributes();
		fortTottenStation.listAttributes();
		
		//citySystem.printMetaComponentDetails();
		//transSystem.printMetaComponentDetails();
		//greenLine.printMetaComponentDetails();
		
	}

}
