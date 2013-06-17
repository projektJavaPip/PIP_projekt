package pl.pip.host;

import java.util.Vector;

import pl.pip.host.HOST_TYPE;
import pl.pip.host.Host;
import pl.pip.host.VM;

public class HardwareLayerSingleton {

	
private static volatile HardwareLayerSingleton instance = null;
private static int maxHosts = 5;
private Vector<Host> hostsArray;

private Vector<Integer> powerUtilization;


	
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
	
	public void addHost()
	{
		
	}
	
	
	
	public double countPowerUtilitiFrom(double toTime)
	{
		for(Host h :  hostsArray )
		{
			
		}
		return 0.0;
	
	}
}
