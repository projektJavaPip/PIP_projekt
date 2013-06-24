package pl.pip.statistics;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.Vector;

import pl.pip.config.TimeUtils;
import pl.pip.distributio.KalmanFilter;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.HardwareLayerSingleton;
import pl.pip.host.PowerCalculation;
import pl.pip.host.Request;


public class StatisticsSing {

	
	private ArrayList<Request> requestArr;
	private ArrayList<Request> newRequestsArr;
	private LinkedList<SecondCounter> inputRequestsStats;
	private LinkedList<VMinfo> vmStatus;
	
	
	private static volatile StatisticsSing instance = null;
	
	
	public void addvmStatus(VMinfo v)
	{
		vmStatus.add(v);
	}
	
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
		newRequestsArr = new ArrayList<>();
		vmStatus = new LinkedList<>();
	}
	
	public void addRequest(Request r)
	{
		requestArr.add(r);
	}
	
	
	public void addNewRequest(Request r)
	{
		newRequestsArr.add(r);
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
				
				if(second % 180 == 0 && secondPointer != second)
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
                               

					}
				}
			}
		
		}
		
	
	public void getVMstatus()
	{
		
	}
	
	
    public SecondCounter getSlaStats()
    {
        double time_serch = TimeUtils.CURRENT_TIME - TimeUtils.TIME_OPTIMIZE;
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
		System.out.println("Zapisano statystyki");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void savePowerUtilization()
	{
		Vector<PowerCalculation> powerUti = HardwareLayerSingleton.getInstance().getPowerCalculation();
		try {
			PrintWriter inputRequestsFile = new PrintWriter("output/powertCalculations.csv");
			
			
			for(PowerCalculation pc : powerUti)
			{
				
				inputRequestsFile.println( (int) pc.getLastTime() /1000/60 + ";" + (int) pc.getPowerLevel());
			}
			
			
			inputRequestsFile.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void saveVMstats()
	{
		
		try {
			PrintWriter inputRequestsFile = new PrintWriter("output/vmStats.csv");
			
			
			for(VMinfo vm : vmStatus)
			{
				
				inputRequestsFile.println(vm.getData());
			}
			
			
			inputRequestsFile.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void addEventRequestCounter()
	{
		EventRequestCouner++;
	}
	
	
}
