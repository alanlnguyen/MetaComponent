package engine;

import port.PortImpl;

public class AdditionEngine<T extends Number> implements ComponentEngine<T> {
	
	public void process( PortImpl<T>[] in, PortImpl<T>[] out )
	{
		int sum = 0;
		
		for (PortImpl<T> i : in)
		{
			Integer temp = (Integer) i.getValue();
			sum += (java.lang.Integer) temp;
		}
		
		for (PortImpl<T> o : out)
		{
			Integer setVal = new Integer(sum);
			o.setValue((T) setVal);
		}
	}

}
