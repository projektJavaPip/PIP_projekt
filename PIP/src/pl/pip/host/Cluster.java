package pl.pip.host;

import java.util.LinkedList;
import java.util.Vector;

import pl.pip.event.Event;
import pl.pip.event.EventAddRequest;
import pl.pip.event.EventRemoveRequest;

public class Cluster {
	
	private LinkedList<Request> requestQuee;
	private Vector<Request> actualProcessedRequest;
	CLUSTER_TYPE type;
	

	public Cluster(CLUSTER_TYPE  ct)
	{
		type = ct;
		requestQuee = new LinkedList<Request>();
		Vector<VM> cluseterVms = HardwareLayerSingleton.getInstance().getVMsforCluster(type);
		
		System.out.println("VMs " + cluseterVms.size());
		
	}
	
	public void pushToQuee(Request r)
	{
		requestQuee.add(r);
	}
	
	
	public void getFromQuee(Request r)
	{
		requestQuee.removeFirst();
	}
	
	
	public void receive(Event e)
	{
		if(e instanceof EventAddRequest) 
		{
	//		if(actualProcessedRequest.size() < )	requestQuee.add(new Request(e.getTime()));
		}
		else if(e instanceof EventRemoveRequest)
		{
			
		}
	
	}
	
	
	

}
