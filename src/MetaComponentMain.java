//Simple MetaComponent Test involving integer addition

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import port.InputPort;
import port.InputPortImpl;
import port.OutputPort;
import port.OutputPortImpl;
import component.*;
import engine.AdditionEngine;

public class MetaComponentMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/**********************************************
		 * 	Version 1: 1 MetaComponent, 1 Base Component   * 
		 **********************************************/
		System.out.println("*****************Version 1: 1 MetaComponent, 1 Base Component*************\n");
		
		//Setting up MetaComponents
		MetaComponent<Integer> mcDest = new MetaComponent();
		mcDest.setName("Destination Component");
		
		//Set up Ports
		InputPort <Integer> destInputPort = new InputPortImpl <Integer>(mcDest);
		destInputPort.setName("mcSourceInputPort");
		
		OutputPort <Integer> destOutputPort = new OutputPortImpl <Integer>(mcDest);
		destOutputPort.setName("mcDestOutputPort");
		
		//For some reason we need to register it again with the MetaComponent
		mcDest.addExternalPort(destInputPort);
		mcDest.addExternalPort(destOutputPort);
		
		//Set up BaseComponent with 2 inputPorts and 1 outputPort
		BaseComponent<Integer> additionComponent = new BaseComponent<Integer>(2,1, new AdditionEngine<Integer>());
		additionComponent.setName("additionComponent");
				
		//Add it to the destination MetaComponent
		mcDest.addSubComponent(additionComponent);
		
		//Connect External Inputs to Base Component Inputs
		for(int i = 0; i < additionComponent.getInputSize() ; i++)
			mcDest.addConnection(destInputPort, additionComponent.getInputPort(i));
		
		//Connect Base Component Outputs to External Outputs
		for(int i = 0; i < additionComponent.getOutputSize() ; i++)
			mcDest.addConnection(additionComponent.getOutputPort(i), destOutputPort);

		//Pass info to destination
		/*destInputPort.setValue(7);
		
		mcDest.process();
		
		//Now check to see what the output port contains in the BaseComponent
		mcDest.printMetaComponentDetails();*/
		
		
		
		/***********************************************
		 * 	Version 2: Version 1, with another MetaComponent feeding data to mcSource  * 
		 **********************************************/
		
		System.out.println("*****************Version 2: Version 1, with another MetaComponent feeding data to mcSource*************\n");
		
		MetaComponent<Integer> mcSource = new MetaComponent<Integer>();
		mcSource.setName("Source Component");
		
		
		InputPort <Integer> srcInputPort = new InputPortImpl <Integer>(mcSource);
		srcInputPort.setName("mcSourceInputPort");
		mcSource.addExternalPort(srcInputPort);
		
		OutputPort <Integer> srcOutputPort = new OutputPortImpl <Integer>(mcSource);
		srcOutputPort.setName("mcSourceOutputPort");
		mcSource.addExternalPort(srcOutputPort);

		//Add new connection from OutputPort2 to InputPort
		mcSource.addConnection(srcOutputPort, destInputPort);
		
		//Pass info to destination
		/*srcOutputPort.setValue(17);
		
		mcSource.process();
		mcDest.process();
		mcSource.printMetaComponentDetails();
		mcDest.printMetaComponentDetails();*/
		
		//***********************************************
		// * 	Version 3: MetaComponent wrapping around Version 2  * 
		// **********************************************//*
		
		//Setting up Wrapper MetaComponents
		MetaComponent<Integer> mcWrapper = new MetaComponent();
		mcWrapper.setName("Wrapper Component");
		
		//Set up Wrapper Ports
		InputPort <Integer> wrapperInputPort = new InputPortImpl <Integer>(mcDest);
		wrapperInputPort.setName("mcWrapperInputPort");
		
		OutputPort <Integer> wrapperOutputPort = new OutputPortImpl <Integer>(mcDest);
		wrapperOutputPort.setName("mcWrapperOutputPort");
		
		mcWrapper.addExternalPort(wrapperInputPort);
		mcWrapper.addExternalPort(wrapperOutputPort);
		
		//Let's add another AdditionEngine to mcSource
		BaseComponent<Integer> additionComponent2 = new BaseComponent<Integer>(2,1, new AdditionEngine<Integer>());
		additionComponent2.setName("additionComponent2");
		mcSource.addSubComponent(additionComponent2);
		
		//Connect External Inputs to Base Component Inputs
		for(int i = 0; i < additionComponent2.getInputSize() ; i++)
			mcSource.addConnection(srcInputPort, additionComponent2.getInputPort(i));
				
		//Connect Base Component Outputs to External Outputs
		for(int i = 0; i < additionComponent2.getOutputSize() ; i++)
			mcSource.addConnection(additionComponent2.getOutputPort(i), srcOutputPort);
		
		//Add our subcomponents
		mcWrapper.addSubComponent(mcSource);
		mcWrapper.addSubComponent(mcDest);
		
		//Now let's connect the subcomponents to the wrapper
		//Connect wrapper inputPort to sourceInputPort
		mcWrapper.addConnection(wrapperInputPort, srcInputPort);
		//Connect wrapper outputPort to destOutputPort
		mcWrapper.addConnection(destOutputPort, wrapperOutputPort);
		
		//Load 7 into the wrapper Input.  The output should be 28.
		wrapperInputPort.setValue(7);
		mcWrapper.process();
		mcWrapper.printMetaComponentDetails();
		mcSource.printMetaComponentDetails();
		mcDest.printMetaComponentDetails();
		
	}

}
