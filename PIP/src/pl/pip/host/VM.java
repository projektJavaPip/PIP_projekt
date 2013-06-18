package pl.pip.host;



public class VM {

	int performance;
	int freeCpu;
	int maxCpu;
	VM_STATUS status;
	CLUSTER_TYPE cluster;
	
	public VM(CLUSTER_TYPE ct, int _maxCpu)
	{
		maxCpu = _maxCpu;
		freeCpu = _maxCpu;
		status = VM_STATUS.TURNING_ON;
		cluster = ct;
		//Dodanie Eventu zmiany statusu na Free
	}
	
	
	public CLUSTER_TYPE getCluster()
	{
		return cluster;
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
}
