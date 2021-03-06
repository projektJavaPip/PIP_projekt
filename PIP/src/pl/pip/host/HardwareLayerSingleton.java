package pl.pip.host;

import java.util.LinkedHashMap;
import java.util.Vector;

import pl.pip.config.TimeUtils;
import pl.pip.event.EventHost;
import pl.pip.event.Heap;
import pl.pip.host.HOST_TYPE;
import pl.pip.host.Host;
import pl.pip.host.VM;

public class HardwareLayerSingleton {

	
private static volatile HardwareLayerSingleton instance = null;
private static int maxHosts = 5;
private static Vector<Host> hostsArray;

private Vector<PowerCalculation> powerUtilization;


static int VMid =1;



	private int getVmId()
	{
		return VMid++;
	}
	
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
		hostsArray = new Vector<Host>(5);
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_2950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_2950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		hostsArray.add(new Host(HOST_TYPE.POWEREDGE_1950));
		powerUtilization = new Vector<PowerCalculation>(0);
		
	}
	
	
	public boolean startHost(int Id)
	{
		if(hostsArray.elementAt(Id).getState() == HOST_STATUS.OFF)
		{
			hostsArray.elementAt(Id).setState(HOST_STATUS.BOOT);
			Heap.getInstance().addElement(new EventHost(TimeUtils.TIME_DELAY_POWER_ON_HOST, HOST_STATUS.ON, Id));
			return true;
		}
		else return false;
	}
	
	
	/**
	 * Uruchomienie 
	 * @param Id
	 * @param ct
	 * @param cpus
	 * @return
	 */
	public int startVMonHost(int Id,CLUSTER_TYPE ct, int cpus)
	{
		
		int vmId =  getVmId();
		if(hostsArray.elementAt(Id).addVM(ct, cpus, vmId)) return vmId;
		else return 0;
		//Sprawdzenie czy HostMa status uruchomiony
		//Tu trzeba sprawdzic czy mozna utworzyć wirtualke
	}
	

	
	
	
	public VM getVM(int vmId)
	{
		for(Host h : hostsArray)
		{
			
			for(VM v : h.getVms())
			{
				if(v.id == vmId) return v;
			}
		}
		return null;
	}
	
	
	public void removeVM(int vmId)
	{
		for(Host h : hostsArray)
		{
			for(VM v : h.getVms())
			{
				if(v.id == vmId)  h.removeVM(vmId);
			}
		}
	}
	
	
	public void setVM(VM migVm,int hid) 
	{
		hostsArray.elementAt(hid).addVM(migVm);
	}
		

	
	
	
	/**
	 * Zwraca maszyny wirtuale przypisane do konkretnego klastra
	 * @param ct
	 * @return
	 */
	public Vector<VM> getVMsforCluster(CLUSTER_TYPE ct)
	{
		Vector<VM> returnVector = new Vector<VM>(1);
		for(Host h : hostsArray)
		{
			for(VM v : h.getVMforCluster(ct))
			{
				//v.getInfo();
				if(v.getState() == VM_STATUS.BUSY || v.getState() == VM_STATUS.FREE ) //VMki są dostępne dla klastra tylko wtedy gdy są uruchomione
				returnVector.add(v);
			}
		}
		return returnVector;
		
	}
	

	//public int getFreeCpusForCluest()

	
	
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
