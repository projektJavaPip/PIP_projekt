package pl.pip;

import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.Cluster;
import pl.pip.host.HardwareLayerSingleton;

public class PIP_batok {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		
		int i = 3600;
		/*for(int j=0;j<20;j++)
		{
			HardwareLayerSingleton.getInstance().countPowerUtilitiFrom(i * j);
		}
		HardwareLayerSingleton.getInstance().showPowerCalculations();*/

		Cluster gold = new Cluster(CLUSTER_TYPE.GOLD);
		Cluster bronze = new Cluster(CLUSTER_TYPE.BRONZE);
	}

}