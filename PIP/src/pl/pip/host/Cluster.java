package pl.pip.host;

import java.util.LinkedList;
import java.util.Vector;

import pl.pip.event.Event;
import pl.pip.event.EventAddRequest;

public class Cluster {
	
	private LinkedList<Request> requestQuee;
	CLUSTER_TYPE type;
	
	Cluster(CLUSTER_TYPE  ct)
	{
		type = ct;
		requestQuee = new LinkedList<Request>();
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
		if(e instanceof EventAddRequest) requestQuee.add(new Request(e.getTime()));
		//if(e instanceof removeRequest)
	
	}
	
	
	

}
