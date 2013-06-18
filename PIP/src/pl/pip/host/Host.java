package pl.pip.host;

import java.util.Vector;

public class Host {

	private int maxCPU;;
	private int freeCpu;
	
	private HOST_STATUS state;
	private HOST_TYPE hostType;
	private int maxVM;
	private int coreEfficency;
	private Vector<VM> vmarray;

	

	
	public Host(HOST_TYPE ht)
	{
		
		if(ht == HOST_TYPE.POWEREDGE_1950) 
		{
			maxVM = 4;
			maxCPU = 8;
			coreEfficency = 100;
		}
		else 
		{
			maxVM=9;
			maxCPU = 8;
			coreEfficency = 190;
		}
		freeCpu = maxCPU;
		vmarray = new Vector<VM>(0);
		hostType = ht;
		state = HOST_STATUS.OFF;
		
		//Wstawienie Eventu, ze uruchomi się po czasie HOST_START;
	}
	
	
	public int getVmCounter() { return vmarray.size();}
	
	
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
	
	
	public  boolean addVM(CLUSTER_TYPE ct,int cpus,int vMid)
	{

		if(freeCpu > 0)
		{
			if(cpus > freeCpu) return false;
			else
			{
				freeCpu-= cpus;
				vmarray.add(new VM(ct, cpus,vMid,hostType));
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
	
	
	public HOST_TYPE getHostType() { return hostType;}
	
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
