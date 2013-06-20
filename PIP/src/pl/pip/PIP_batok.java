package pl.pip;

import pl.pip.distributio.DistributionUtil;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.Cluster;
import pl.pip.host.HardwareLayerSingleton;

public class PIP_batok {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		

		/*for(int j=0;j<20;j++)
		{
			HardwareLayerSingleton.getInstance().countPowerUtilitiFrom(i * j);
		}
		HardwareLayerSingleton.getInstance().showPowerCalculations();*/
		for(int i =0;i< 100;i++)
		{
			System.out.println(DistributionUtil.generateEventUs(5));
		}

	}
	
	

}
