/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.host;

import java.util.Vector;

import org.omg.CORBA.TIMEOUT;
import pl.pip.config.TimeUtils;
import pl.pip.distributio.KalmanFilter;
import pl.pip.event.EventHost;
import pl.pip.event.EventVM;
import pl.pip.event.Heap;
import pl.pip.statistics.StatisticsSing;
import pl.pip.statistics.SecondCounter;

/**
 *
 * @author supp
 */
public class Optimizer {

	KalmanFilter filterForOpt[];
    int lastPackets[];
    int clusterLoad[];
    Vector<VM> vms;
    double maxClusterPower[];
    
    public Optimizer()
    {
    	filterForOpt = new KalmanFilter[3];
    	lastPackets = new int[3];
    	maxClusterPower = new double[3];
    	clusterLoad = new int[3];
    	for(int i=0;i<3;i++)
    	{
    		filterForOpt[i] = new KalmanFilter();
    		maxClusterPower[i] = 0;
    		clusterLoad[i] = 0;
    	}
    	vms = null;
        
    }
    
    public void optimize()
    {
       StatisticsSing statistics = StatisticsSing.getInstance();
       SecondCounter sc = statistics.getSlaStats();
       Vector<Vector<VM>> hosts = HardwareLayerSingleton.getInstance().getSystemState();
       
       System.out.println(TimeUtils.CURRENT_TIME);
       for (CLUSTER_TYPE ct : CLUSTER_TYPE.values()) 
		{
    	   lastPackets[ct.ordinal()] = sc.getCounter(ct.ordinal());
    	   filterForOpt[ct.ordinal()].compute(lastPackets[ct.ordinal()]);
       
    	   vms = HardwareLayerSingleton.getInstance().getVMsforCluster(ct);
    	   
    	   for(VM v: vms) //zliczanie teoretycznej liczby requestów jaka jest w stanie obsluzyc danych klaster
    	   {
    		  maxClusterPower[ct.ordinal()]+=  v.maxCpu / (TimeUtils.TIME_PER_GHZ[ct.ordinal()] / v.performance);
    		   
    	   }
    	   maxClusterPower[ct.ordinal()] *= TimeUtils.TIME_OPTIMIZE;
    	   
    	   clusterLoad[ct.ordinal()] = (int) Math.round((filterForOpt[ct.ordinal()].getPrediction() /  maxClusterPower[ct.ordinal()]) * 100 );
    	   
    	   
    	   int expectedEfficency = (int) Math.round(filterForOpt[ct.ordinal()].getPrediction()); //Oczekujemy, ze ruch moze byc dodatkowo wiekszy o 10 procent
    	   
    	   
    	   
    	   //o te wartosc musimy zmienic
    	   int diversityLoad =  (int) (0.9 * maxClusterPower[ct.ordinal()] - expectedEfficency);

    	   System.out.print( ct.name() + " " + lastPackets[ct.ordinal()] + " : " + filterForOpt[ct.ordinal()].getPrediction() );
    	   System.out.print(":" + Math.round(maxClusterPower[ct.ordinal()]) + " : " +  clusterLoad[ct.ordinal()] +"%" + " "  + diversityLoad);
    	   System.out.println(" : " + sc.getSlaCounter(ct.ordinal()));
    	   
    	
    	   
    	   if(diversityLoad > 0)
    		{
    		   for(int i=hosts.size()-1;i>=0;i--)
    		   {
    			   
    			   
    			   System.out.println("LICZBA VM NA HOŚCIE " + i + " " + hosts.get(i).size());
    			   for(VM v : hosts.get(i))
    			   {
    				   if(v.cluster == ct)
    				   {
	    				   int vmPower = (int) ( ( v.maxCpu /  (TimeUtils.TIME_PER_GHZ[ct.ordinal()] / v.performance)) * TimeUtils.TIME_OPTIMIZE);
	    				   System.out.println(vmPower);
	    				   if(diversityLoad - vmPower  > 0) //Wylaczamy wirtualke
	    				   {
	   	            			Heap.getInstance().addElement(new EventVM( TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_OFF_VM, VM_STATUS.SHUTDOWN,v.id));
	   	            		   v.setState(VM_STATUS.SHUTDOWN);
	   	            		   System.out.println("VM :" + v.id + " DOWN" );
	    					   diversityLoad -= vmPower;
	    				   }
    				   }
    			   }
    			   
    		   }
    		   System.out.println(ct.name() + " " + HardwareLayerSingleton.getInstance().getVMsforCluster(ct).size());
    		   
    		   System.out.println();
    		   //Wylaczamy co sie da
    		   
    		}
    	   else
    	   {
    		   System.out.println("DODAWANIE SPRZETU");
    		   	Vector<Host> hv = HardwareLayerSingleton.getInstance().getHosts();
    		   	for(Host h : hv)
    		   	{
    		   		int i=0;
    		   		if(h.getFreeCpus() > 0)
    		   		{
    		   			int freeCpu = 0;
    		   			if(h.getFreeCpus() > 4) freeCpu=4;
    		   			else freeCpu = h.getFreeCpus();
    		   			int newVMid = HardwareLayerSingleton.getInstance().getVmId();
    		   			if(h.addVM(ct, freeCpu, newVMid))
    		   			{
    		   				Heap.getInstance().addElement(new EventVM( TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_VM, VM_STATUS.TURNING_ON,newVMid));
    		   				System.out.println(ct.name() + " " + "Dodaje VM o ID " + newVMid + "na hoście " + i );
    		   				break;
    		   			}


    		   		}
    		   		i++;
    		   	}
    	   }
    			   
    			   
    	   //Wylaczamy Hosty gdy nie ma na nich wirtualek
    	   
    	   
    	   for(int i=hosts.size()-1;i>=0;i--)
		   {

			   HOST_STATUS hs = HardwareLayerSingleton.getInstance().getHostState(i);
			   if(hs != HOST_STATUS.ON) continue;
			   
			   if(hosts.get(i).size() == 0)
			   {
				   
				   System.out.println("A teraz wyłącze HOSTA " + i);
				   Heap.getInstance().addElement(new EventHost(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_OFF_HOST, HOST_STATUS.OFF, i));
				   HardwareLayerSingleton.getInstance().setHostState(i, HOST_STATUS.SHUTDOWN);
			   }
		   }
    	   
    	   maxClusterPower[ct.ordinal()]=0;
    	   
       }
       
       
       
       
       
    
       HardwareLayerSingleton.getInstance().countPowerUtilitiFrom(TimeUtils.CURRENT_TIME);
       
       
    }
    
        
       
        
    
}
