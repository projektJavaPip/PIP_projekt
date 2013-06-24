package pl.pip.statistics;

public class SecondCounter
{
	int second;
	int[] counter;
            int[] sla_counter;
	
	public SecondCounter(int sec)
	{
		second = sec;
		counter = new int[3];
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
                    
                    sla_counter = new int[3];
		sla_counter[0] = 0;
		sla_counter[1] = 0;
		sla_counter[2] = 0;
	}
	
	public SecondCounter(int sec,int i)
	{
		second = sec;
		counter = new int[3];
		counter[0] = 0;
		counter[1] = 0;
		counter[2] = 0;
		counter[i]++;
                    
                    sla_counter = new int[3];
		sla_counter[0] = 0;
		sla_counter[1] = 0;
		sla_counter[2] = 0;
                    counter[i]++;
                    
                    
                    
	}
	
	public int getSecond()
	{
		return second;
	}
	
	public int getCounter(int i)
	{
		return counter[i];
	}
	
            public int getSlaCounter(int i)
	{
		return sla_counter[i];
	}
            
	public void addCounter(int i)
	{
		counter[i]++;
	}
            
            public void addSlaCounter(int i)
	{
		sla_counter[i]++;
	}
}