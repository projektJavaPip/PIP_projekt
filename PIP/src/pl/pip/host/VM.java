package pl.pip.host;



public class VM {

	double performance;
	int freeCpu;
	int maxCpu;
	int id;
	double frequency;
	VM_STATUS status;
	CLUSTER_TYPE cluster;
	HOST_TYPE vm_ht;
	
	public VM(CLUSTER_TYPE ct, int _maxCpu,int vmId,HOST_TYPE ht)
	{
		maxCpu = _maxCpu;
		freeCpu = _maxCpu;
		status = VM_STATUS.TURNING_ON;
		vm_ht = ht;
		cluster = ct;
		id = vmId; 
		double core = 0;
		if(ht == HOST_TYPE.POWEREDGE_1950) 
		{
				performance = 0.03;
				core = 1.6;
		}
		else 
		{
			performance = 0.025;
			core = 2.3;
		}
		
		
		frequency = maxCpu * core;
		
		//Dodanie Eventu zmiany statusu na Free
	}
	
	
	public CLUSTER_TYPE getCluster()
	{
		return cluster;
	}
	
	

	
	
	public void getInfo()
	{
		System.out.println("ID: " + id);
		System.out.println("KLASTER: " + cluster);
		System.out.println("TYP_HOSTA: " + vm_ht);
		System.out.println("LICZBA CPU: " + maxCpu);
		System.out.println("STATUS: " + status.name());
		System.out.println("FREQ: " + frequency);
		System.out.println("");
	}
	
	
	public boolean isFree()
	{
		if(freeCpu > 0) return true;
		else return false;
	}
	
	public void freCpu()
	{
		freeCpu++;
	}
	
	public void addCpu()
	{
		maxCpu++;
		freeCpu++;
		status = VM_STATUS.FREE;
	}
	
	
	
	public void setState(VM_STATUS st)
	{
		status = st;
	}
	
	
	public VM_STATUS getState()
	{
		return status;
	}
	
	
	/**
	 * 
	 * @return true gdy zadanie zostanie ustawione na VM, false gdy nie ma wolnych zasob�w
	 */
	public boolean doRequest()
	{
		if(freeCpu == 0) return false;
		else
		{
			//Dodaj zadanie do kolejki
			//Dopisz czas wykonania zadania na podstawie wka�nika performence
			freeCpu--;
			if(freeCpu == 0) status = VM_STATUS.BUSY;
			return true;
		}
	}
	
	
	public boolean finishRequest()
	{
		freeCpu++;
		return true;
	}
}
