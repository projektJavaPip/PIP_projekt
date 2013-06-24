package pl.pip.statistics;

public class SecondCounter
{
	int second;
	int[] inputCounter;
    int[] sla_counter;
	
	public SecondCounter(int sec)
	{
		second = sec;
		inputCounter = new int[3];
		inputCounter[0] = 0;
		inputCounter[1] = 0;
		inputCounter[2] = 0;
                    
        sla_counter = new int[3];
		sla_counter[0] = 0;
		sla_counter[1] = 0;
		sla_counter[2] = 0;
	}
	
	public SecondCounter(int sec,int i)
	{
		second = sec;
		inputCounter = new int[3];
		inputCounter[0] = 0;
		inputCounter[1] = 0;
		inputCounter[2] = 0;
		inputCounter[i]++;
                    
                    sla_counter = new int[3];
		sla_counter[0] = 0;
		sla_counter[1] = 0;
		sla_counter[2] = 0;
                    inputCounter[i]++;
                    
                    
                    
	}
	
	public int getSecond()
	{
		return second;
	}
	
	public int getCounter(int i)
	{
		return inputCounter[i];
	}
	
            public int getSlaCounter(int i)
	{
		return sla_counter[i];
	}
            
	public void addCounter(int i)
	{
		inputCounter[i]++;
	}
            
            public void addSlaCounter(int i)
	{
		sla_counter[i]++;
	}
}