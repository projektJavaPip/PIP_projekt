package pl.pip.host;

import java.util.LinkedHashMap;
import java.util.Vector;

import pl.pip.host.HOST_TYPE;
import pl.pip.host.Host;
import pl.pip.host.VM;

public class HardwareLayerSingleton {

	
private static volatile HardwareLayerSingleton instance = null;
private static int maxHosts = 5;
private Vector<Host> hostsArray;

private Vector<PowerCalculation> powerUtilization;





	
	public static HardwareLayerSingleton getInstance()  {
		if (instance == null)  {
			synchronized (HardwareLayerSingleton.class) {
				if(instance == null) {
					instance = new HardwareLayerSingleton();
				}
			}
		}
		return instance;
	}
	
	private HardwareLayerSingleton()
	{
		hostsArray = new Vector<Host>(2);
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_2950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_2950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		hostsArray.elementAt(0).setState(HOST_STATUS.ON);
		hostsArray.elementAt(1).setState(HOST_STATUS.ON);
		
		powerUtilization = new Vector<PowerCalculation>(0);
		
	}
	
	
	/**
	 * Zwraca liczbe maszyn wirtualnych przypisanych do konkretnego klastra
	 * @param ct
	 * @return
	 */
	public Vector<VM> getVMsforCluster(CLUSTER_TYPE ct)
	{
		Vector<VM> returnVector = new Vector<VM>(1);
		for(Host h : hostsArray)
		{
			Vector<VM> tmp = h.getVMforCluster(ct);
			for(VM v : tmp)
			{
				returnVector.add(v);
			}
		}
		return returnVector;
		
	}
	

	
	
	
	public void countPowerUtilitiFrom(double toTime)
	{
		double fromTime;
		double lastMeasuere;
		if (powerUtilization.isEmpty()) 
		{
			fromTime = 0.0;
			lastMeasuere = 0.0;
			
		}
		else
		{
			PowerCalculation tmp  =  powerUtilization.get(powerUtilization.size() -1);
			fromTime = tmp.getLastTime();
			lastMeasuere = tmp.powerValue;
		}
		
		double timeForCalculation = toTime - fromTime;
		double newPowerUtilization =0.0;
		double tmpPowerUtilization =0.0;
		for(Host h :  hostsArray )
		{
			tmpPowerUtilization = 0;
			switch(h.getState())
			{
				case  SHUTDOWN:
				{
					if(h.getHostType() == HOST_TYPE.POWEREDGE_1950 ) tmpPowerUtilization =  213.0;
					else tmpPowerUtilization = 228.0;
					break;
				}
				case  BOOT:
				{
					if(h.getHostType() == HOST_TYPE.POWEREDGE_1950 ) tmpPowerUtilization =  218.0;
					else tmpPowerUtilization = 228.0;
					break;
				}
				case OFF:
				{
					if(h.getHostType() == HOST_TYPE.POWEREDGE_1950 ) tmpPowerUtilization =  18.0;
					else tmpPowerUtilization = 20.0;
					break;
				}
				case ON:
				{
					int powerOnVMs = h.getVmCounter();
					
					switch(powerOnVMs)
					{
						case 0:
						{
							tmpPowerUtilization =0.0; break;
						}
						case 1:
						{
							tmpPowerUtilization = 15.0;
						}
						case 2:
						{
							tmpPowerUtilization = 23.0;
						}
						case 3:
						{
							tmpPowerUtilization = 32.0;
						}
						case 4:
						{
							tmpPowerUtilization = 38.0;
						}
						case 5:
						{
							tmpPowerUtilization = 47.0;
						}
		
					}
					if(h.getHostType() == HOST_TYPE.POWEREDGE_1950 ) tmpPowerUtilization = tmpPowerUtilization *1.21 + 208 ;
					else tmpPowerUtilization = tmpPowerUtilization * 1.16 + 224;
			
				}
			}
			newPowerUtilization += tmpPowerUtilization;
		}
		newPowerUtilization = tmpPowerUtilization * timeForCalculation / 3600 / 1000;
		powerUtilization.add(new PowerCalculation(toTime, lastMeasuere + newPowerUtilization));
		
		
	
	}
	
	
	
	public void showPowerCalculations()
	{
		for(PowerCalculation pc : powerUtilization) System.out.println(pc.getLastTime() + " : " + pc.getPowerLevel());
	}
}
