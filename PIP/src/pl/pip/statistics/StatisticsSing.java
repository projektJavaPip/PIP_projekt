package pl.pip.statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedMap;
import pl.pip.config.TimeUtils;

import pl.pip.distributio.KalmanFilter;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.Request;


public class StatisticsSing {

	
	private ArrayList<Request> requestArr;
	private LinkedList<SecondCounter> inputRequestsStats;
        private LinkedList<SecondCounter> slaStats;
	private static volatile StatisticsSing instance = null;
	
	
	private int EventRequestCouner = 0;
	
	long requestId = 0L;
	
	public static StatisticsSing getInstance()  {
		if (instance == null)  {
			synchronized (StatisticsSing.class) {
				if(instance == null) {
					instance = new StatisticsSing();
				}
			}
		}
		return instance;
	}
	
	
	private StatisticsSing()
	{
		requestArr = new ArrayList<>();
	}
	
	public void addRequest(Request r)
	{
		requestArr.add(r);
	}
	
	
        public SecondCounter getSlaStats()
        {
            double time_serch = TimeUtils.CURRENT_TIME - TimeUtils.TIME_CONTROL_SAMPLING_PREODIC;
            int index = requestArr.size() - 1 ;
            Request r;
            SecondCounter s = new SecondCounter(0);

            while(  requestArr.get(index).leftTime > time_serch)
            {
                r =  requestArr.get(index);
                --index;
                s.addCounter(r.getClusterType().ordinal());
                if ( r.leftTime  - r.getArrivalTime()  > TimeUtils.SLA[r.getClusterType().ordinal()])
                {
                    s.addSlaCounter(r.getClusterType().ordinal());
                }
                
                
            }
            return s;
        }
       
	public void getStats()
	{
		System.out.println("Wygenerowane: " + EventRequestCouner);
		System.out.println("Obsluzone: " +requestArr.size());
		inputRequestsStats = new LinkedList<>();
		int second =0;
		int secondPointer = 0;
		boolean added = false;
			
			for(Request r : requestArr)
			{
				
				second =  (int) Math.round(r.getArrivalTime()/1000);
				
				if(second % 60 == 0 && secondPointer != second)
				{
					secondPointer = second;
					inputRequestsStats.add(new SecondCounter(secondPointer));
                                        
				}
				
				if(inputRequestsStats.isEmpty())
				{
					inputRequestsStats.add(new SecondCounter(0));
					secondPointer =0;
				}			
				for(SecondCounter s : inputRequestsStats )
				{
					if(secondPointer == s.getSecond()) 
					{
							s.addCounter(r.getClusterType().ordinal());

                                                        
                                                        if ( r.leftTime  - r.getArrivalTime()  > TimeUtils.SLA[r.getClusterType().ordinal()])
                                                        {
                                                            s.addSlaCounter(r.getClusterType().ordinal());

                                                        }
                                                        



							//added  = true;
					}
				}
			}
		
		}
		
	
	
	public void saveInputStats()
	{
		
		try {
			PrintWriter inputRequestsFile = new PrintWriter("output/inputStats.csv");
		
			KalmanFilter kf[] = new KalmanFilter[3];
			
			for(int i=0;i<3;i++)	kf[i] =	new KalmanFilter();
			inputRequestsFile.print("SECOND;");
			
			for (CLUSTER_TYPE ct : CLUSTER_TYPE.values()) 
			{
				inputRequestsFile.print(ct.name() + ";");
				inputRequestsFile.print(ct.name() + "_KALMAN;");
			}
			inputRequestsFile.println();
			
		for(SecondCounter s : inputRequestsStats)
		{
			
			inputRequestsFile.print(s.getSecond() + ";");
			for (CLUSTER_TYPE ct : CLUSTER_TYPE.values()) 
			{
				inputRequestsFile.print(s.getCounter(ct.ordinal()) + ";");
				inputRequestsFile.print(kf[ct.ordinal()].compute(s.getCounter(ct.ordinal()))+ ";");
			}
			inputRequestsFile.println();
			//inputRequestsFile.print(s.getCounter(CLUSTER_TYPE.SILVER.ordinal()) + ";");
			//inputRequestsFile.println(s.getCounter(CLUSTER_TYPE.BRONZE.ordinal()) + ";");
			
		}

		inputRequestsFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void addEventRequestCounter()
	{
		EventRequestCouner++;
	}
	
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
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
}
