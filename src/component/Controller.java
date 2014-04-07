package component;
import engine.*;
import message.*;
import port.*;
import wire.*;

public class Controller <T> extends BaseComponent{

	public Controller(int inputs, int outputs, ComponentEngine engine)
	{
		super(inputs, outputs, engine);
	}
	
}
