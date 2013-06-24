package pl.pip.host;

import java.util.LinkedList;
import java.util.Vector;

import pl.pip.config.TimeUtils;
import pl.pip.event.Event;
import pl.pip.event.EventAddRequest;
import pl.pip.event.EventRemoveRequest;
import pl.pip.event.Heap;
import pl.pip.statistics.StatisticsSing;

public class Cluster {
	
	private LinkedList<Request> requestQuee;
	private Vector<Request> actualProcessedRequest;
	public Vector<VM >clusterVms; 
	CLUSTER_TYPE type;
	int performence;

	public Cluster(CLUSTER_TYPE  ct)
	{
		type = ct;
		requestQuee = new LinkedList<Request>();
		actualProcessedRequest = new Vector<>();
		clusterVms = HardwareLayerSingleton.getInstance().getVMsforCluster(type);
		for(VM cwm : clusterVms)
		{
			performence += cwm.freeCpu;
		}
	}

	
	/**
	 * Metoda wywoływana gdy zajdną zmiany w konfiguracji sprzętowej danego klastra
	 * po wywołaniu algorytmu LLC
	 */
	public void actualizeVms()
	{
		performence = 0;
		clusterVms = HardwareLayerSingleton.getInstance().getVMsforCluster(type);
		for(VM cwm : clusterVms)
		{
			performence += cwm.freeCpu;
		}
	}
	
	
	private int getFreeVmId()
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
			long packetId = RequestNumberGeneratorSing.getInstance().getRequestId(); //pobieram nastepny numer pakietu
			Request nr = new Request(e.getTime(),packetId,((EventAddRequest) e).isWrite(),type); 
		
		
			
			if(freeVmId == -1 ) requestQuee.add(nr);  //jesli nie ma zadnej wolnej VM to wstaw pakiet na kolejke oczekujacych
			else 
			{
				VM designetedVM = clusterVms.elementAt(freeVmId);
				int VmId = designetedVM.id;
				double vmPerformance  = designetedVM.performance;
				nr.setVmId(VmId);
				actualProcessedRequest.add(nr); //przypisz VM do pakietu
				if(!clusterVms.elementAt(freeVmId).doRequest()) System.out.println("Something's wrong in add Request to VM");
				
				double executionTime;
					/*	if(nr.isWrite) executionTime = 2 * vmPerformance * (Math.random() * 2 + 2); //mnożymy czas obslugi zadania zapisu w zakresie od 1 do 3
						else executionTime = vmPerformance * (Math.random() * 2 + 1);
				*/
				
				if(nr.isWrite) executionTime = TimeUtils.TIME_PER_GHZ[type.ordinal()] / vmPerformance ;
				else executionTime = TimeUtils.TIME_PER_GHZ[type.ordinal()] / vmPerformance /2; 
				
						
				EventRemoveRequest err = new EventRemoveRequest(TimeUtils.CURRENT_TIME + executionTime,type,packetId); //- wpychamy na stog eventa kiedy pakiet zostanie obsluzony
				Heap.getInstance().addElement(err); //umieszczam zakonczenie requesta na stogu
			}
			
			
		}
		else if(e instanceof EventRemoveRequest)
		{
			for(int i=0;i<actualProcessedRequest.size();i++)
			{
				if(actualProcessedRequest.elementAt(i).packetId == ((EventRemoveRequest) e).getIdRequest())
				{
					Request r = actualProcessedRequest.elementAt(i);
					r.setLeftTime(TimeUtils.CURRENT_TIME);
					StatisticsSing.getInstance().addRequest(r);
					actualProcessedRequest.removeElementAt(i); //konczymy przetwarzanie requesta
					
					for(VM v : clusterVms)
					{
						if(v.id == r.vmId) 
							{
								//System.out.println(v.id);
								v.finishRequest();
							}
					}
				}
			}
			if(requestQuee.size() > 0) //Tu musimy pobrac element z kolejki oczekujacych na wykonanie, sprawdzic VMke i wrzucic requesta na liste aktualnie wykonywanych
			{
				
				
				int freeVmId = getFreeVmId();
				
					Request oldr = requestQuee.removeFirst(); //pobieramy pierwszy z kolejki
					
					VM designetedVM = clusterVms.elementAt(freeVmId);
					int VmId = designetedVM.id;
					double vmPerformance  = designetedVM.performance;
					oldr.setVmId(VmId);
					actualProcessedRequest.add(oldr); //przypisz VM do pakietu
					if(!clusterVms.elementAt(freeVmId).doRequest()) System.out.println("Something's wrong in add Request to VM");
					
					double executionTime;
					if(oldr.isWrite) executionTime = TimeUtils.TIME_PER_GHZ[type.ordinal()] / vmPerformance ;
					else executionTime = TimeUtils.TIME_PER_GHZ[type.ordinal()] / vmPerformance /2; 
					
							
					EventRemoveRequest err = new EventRemoveRequest(TimeUtils.CURRENT_TIME + executionTime,type,oldr.packetId); //- wpychamy na stog eventa kiedy pakiet zostanie obsluzony
					Heap.getInstance().addElement(err); //umieszczam zakonczenie requesta na stogu
				}
			
			
		}
	
	}
	
	
	

}
