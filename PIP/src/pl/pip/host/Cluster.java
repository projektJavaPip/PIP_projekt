package pl.pip.host;

import java.util.LinkedList;
import java.util.Vector;

import pl.pip.event.Event;
import pl.pip.event.EventAddRequest;
import pl.pip.event.EventRemoveRequest;

public class Cluster {
	
	private LinkedList<Request> requestQuee;
	private Vector<Request> actualProcessedRequest;
	public Vector<VM >clusterVms; 
	CLUSTER_TYPE type;
	int clusterType;

	public Cluster(CLUSTER_TYPE  ct)
	{
		type = ct;
		requestQuee = new LinkedList<Request>();
		clusterVms = HardwareLayerSingleton.getInstance().getVMsforCluster(type);
		System.out.println("VMs " + clusterVms.size());
		
	}
	
	public void pushToQuee(Request r)
	{
		requestQuee.add(r);
	}
	
	
	public void getFromQuee(Request r)
	{
		requestQuee.removeFirst();
	}
	
	
	/**
	 * Metoda wywoływana gdy zajdną zmiany w konfiguracji sprzętowej danego klastra
	 * po wywołaniu algorytmu LLC
	 */
	public void actualizaVms()
	{
		clusterVms = HardwareLayerSingleton.getInstance().getVMsforCluster(type);
	}
	
	
	public int getFreeVmId()
	{
		for(int i=0;i<clusterVms.size();i++)
		{
			if(clusterVms.elementAt(i).isFree()) return i; 
		}
		return -1;
	}
	
	public void receive(Event e)
	{
		if(e instanceof EventAddRequest) 
		{
			int freeVmId = getFreeVmId();
			long packetId = 0L;
			Request nr = new Request(e.getTime(),packetId); 
			if(freeVmId == -1 ) requestQuee.add(nr);
			else 
			{
				actualProcessedRequest.add(nr);
				int VmId = clusterVms.elementAt(freeVmId).id;
				nr.setVmId(VmId);
				if(!clusterVms.elementAt(freeVmId).doRequest()) System.out.println("Something's wrong in add Request to VM");
				//EventRemoveRequest err = new EventRemoveRequest() - wpychamy na stog eventa kiedy pakiet zostanie obsluzony
			}
		}
		else if(e instanceof EventRemoveRequest)
		{
			for(int i=0;i<actualProcessedRequest.size();i++)
			{
				if(actualProcessedRequest.elementAt(i).packetId == ((EventRemoveRequest) e).getIdRequest())
				{
					//Tu musimy wrzucic requesta do statystyk z dodanym czasem wykonania
					actualProcessedRequest.remove(i); //konczymy przetwarzanie requesta
				}
			}
			//Tu musimy pobrac element z kolejki oczekujacych na wykonanie, sprawdzic VMke i wrzucic requesta na liste aktualnie wykonywanych
		}
	
	}
	
	
	

}
