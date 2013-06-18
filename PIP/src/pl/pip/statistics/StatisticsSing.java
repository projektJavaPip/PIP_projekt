package pl.pip.statistics;

import java.util.ArrayList;

import pl.pip.host.Request;


public class StatisticsSing {

	
	private ArrayList<Request> requestArr;

	private static volatile StatisticsSing instance = null;

	
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
	
	
	
}
