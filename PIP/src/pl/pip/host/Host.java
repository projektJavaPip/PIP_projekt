package pl.pip.host;

import java.util.Vector;

public class Host {

	int maxCPU;;
	int freeCpu;
	
	HOST_STATUS state;
	HOST_TYPE hostType;
	int maxVM;
	
	VM vmarray[];
	
	
	public Host(HOST_TYPE ht)
	{
		
		if(ht == HOST_TYPE.POWEREDGE_1950) maxVM = 4;
		else maxVM=9;
		vmarray = new VM[maxVM];
		hostType = ht;
		for(int i=0;i<maxVM;i++)  vmarray[i] = null;
	}
	
	public Vector<VM> getVMforCluster(CLUSTER_TYPE ct)
	{
		Vector<VM> returnVector = new Vector<VM>(1);
		for(VM v : vmarray)
		{
			if(v.getCluster().equals(ct)) returnVector.add(v);
		}
		return returnVector;
	}
	
	
	
	public int getFreeCpus()
	{
		return freeCpu;
	}
	
	
	public  boolean addVM(CLUSTER_TYPE ct,int cpus)
	{
		if(freeCpu > 0)
		{
			if(cpus > freeCpu) return false;
			else
			{
				freeCpu-= cpus;
				for(int i=0;i<maxVM;i++)
				{
					if(vmarray[i].equals(null)) vmarray[i] = new VM(ct, cpus);
				}
				return true;
			}
		}
		else return false;
	}
	
	public HOST_STATUS getState()
	{
		return state;
	}
	
	
	public void setState(HOST_STATUS st)
	{
		state = st;
	}
	
	
	public boolean turnOn()
	{
		if(state == HOST_STATUS.OFF)
		{
			state = HOST_STATUS.BOOT;
			//Dodaj zadanie do kolejki z informacja o czasie zakonczenia - typ uruchomienie hosta
			return true;
		}
		else return false;
	}
	
	public boolean turnOff()
	{
		if(state == HOST_STATUS.ON)
		{
			state = HOST_STATUS.SHUTDOWN;
			//Dodaj zadanie do kolejki - wylaczenie hosta
		}
		return false;
	}
	
	
}
