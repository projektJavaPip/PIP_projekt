/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.symulation;

import pl.pip.config.*;
import pl.pip.event.*;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.Cluster;
import pl.pip.host.HOST_STATUS;
import pl.pip.host.HardwareLayerSingleton;
import pl.pip.host.VM;
import pl.pip.host.VM_STATUS;
import pl.pip.distributio.DistributionUtil;

/**
 *
 * @author supp
 */
public class Simulation {
    
    
    double max_time;
    
    public Simulation(double t)
    {
        max_time = t;
    }
    
    
    
   
    
    
    public void init()
    {
        TimeUtils.CURRENT_TIME = 0;
        Heap heap = Heap.getInstance();
        
        heap.clear();
        
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 0));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 1));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 2));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 3));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 4));

        
        heap.addElement(new EventCreateCluster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.GOLD));
          heap.addElement(new EventCreateCluster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.SILVER));
        heap.addElement(new EventCreateCluster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.BRONZE));
       /*
       heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.GOLD, true));
        heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.SILVER, true));
        heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.BRONZE, true));
        */
        
    }
    
    public void start()
    {
        Heap heap = Heap.getInstance();
        Event e;
        Cluster clusters[] = new Cluster[3];
        
        try
        {
        
	        while(TimeUtils.CURRENT_TIME < max_time)
	        {
	        	
	            e = heap.getElment();
	            
	            
	            TimeUtils.CURRENT_TIME = e.getTime();
	            
	            System.out.println(TimeUtils.CURRENT_TIME);
	           // System.out.println(e.getClass().getName());
	            
	            
	            if(e instanceof EventAddRequest) //Odebranie zapytania
	            {
	    
	                heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME+ DistributionUtil.generateEventUs(40) , ((EventAddRequest)e).getClusterType(), DistributionUtil.readWriteOption()));
	                
	                System.out.println("Nowy request: " + ((EventAddRequest)e).getClusterType());
	                
	                int ct = ((EventAddRequest)e).getClusterType().ordinal();
	                clusters[ct].receive(e);
	                
	            }
	            else if(e instanceof EventRemoveRequest)
	            {
	            	   int ct = ((EventAddRequest)e).getClusterType().ordinal();
		               clusters[ct].receive(e);
	            }
	            else if(e instanceof EventVM)
	            {
	            	int vmId = (((EventVM) e).getIdVm());
	            	VM tmpVM = HardwareLayerSingleton.getInstance().getVM(vmId);
	            	
	            	if(heap.debug) System.out.println("Zdarzenie VM " + vmId + " " + ((EventVM) e).getStatus());
	            	tmpVM.getInfo();
	            	
	            	switch(((EventVM) e).getStatus())
	            	{
					case BUSY:
						break;
					case FREE:
						break;
					case MIGRATION:
						try {
							int migTo = ((EventVM) e).getToIdHost();
							VM migVm = HardwareLayerSingleton.getInstance().getVM(vmId);
							HardwareLayerSingleton.getInstance().setVM(migVm,migTo);
							HardwareLayerSingleton.getInstance().removeVM(vmId);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case SHUTDOWN:
						HardwareLayerSingleton.getInstance().removeVM(vmId);
						break;
					case TURNING_ON:
						tmpVM.setState(VM_STATUS.FREE);
						break;
					case WAIT_FOR_HOST:
						//@todo co robimy gdy VM czeka na Hosta
						break;
					default:
						break;
	            	
	            	}
	            	
	            	clusters[tmpVM.getCluster().ordinal()].actualizeVms();
	            	
	            	
	            }
	            else if(e instanceof EventHost)
	            {
	            	int hId = ((EventHost) e).getIdHost();
	            	HardwareLayerSingleton.getInstance().startHost(hId);
	            	if(heap.debug) System.out.println("Zdarzenie Hosta " + hId + " "  + ((EventHost) e).getStatus());
	            }
	            else if(e instanceof EventCreateCluster)
	            {
	            	int vmId;
	            	CLUSTER_TYPE ct =  ((EventCreateCluster) e).getClusterType();
	            	int cluseterIdentyficator = ct.ordinal();
	            	clusters[cluseterIdentyficator] = new Cluster(ct);
	            	
	       
	            	System.out.println("Zdarzenie Cluster " + ((EventCreateCluster) e).getClusterType().name() + " " +  ((EventCreateCluster) e).getClusterType().ordinal());
	                if(ct == CLUSTER_TYPE.GOLD)
	                {
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(0, ct, 3);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(0, ct, 3);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(2, ct, 4);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	                }
	                else if(ct == CLUSTER_TYPE.SILVER)
	                {
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(1, ct, 3);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		HardwareLayerSingleton.getInstance().getVM(vmId).getInfo();
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(1, ct, 3);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(2, ct, 4);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	                }
	                else if(ct == CLUSTER_TYPE.BRONZE)
	                {
	                	vmId = HardwareLayerSingleton.getInstance().startVMonHost(3, ct, 4);
	                	if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	                	heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(3, ct, 4);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	            		vmId = HardwareLayerSingleton.getInstance().startVMonHost(4, ct, 4);
	            		if(heap.debug) System.out.println("Dodałem VM " + ct.name() + " o id "  + vmId);
	            		heap.addElement(new EventVM(TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON, vmId));
	                }
	            		
	            	
	            }
	           
	            
	            HardwareLayerSingleton.getInstance().countPowerUtilitiFrom(e.getTime());
	          
	            
	            
	        }
        }
        catch(NullPointerException ne)
        {
        	System.out.println("ERROR ! - Stóg jest pusty ");
        }
        
    }
    
}