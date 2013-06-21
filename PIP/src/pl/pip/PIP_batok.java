package pl.pip;

import pl.pip.distributio.Distribution;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.Cluster;
import pl.pip.host.HardwareLayerSingleton;

public class PIP_batok {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Distribution distr = new Distribution();
		
		

		/*for(int j=0;j<20;j++)
		{
			HardwareLayerSingleton.getInstance().countPowerUtilitiFrom(i * j);
		}
		HardwareLayerSingleton.getInstance().showPowerCalculations();*/
		
		Integer j[] = new Integer[25];
		for(int i =0;i<25;i++) 
			{
			
			j[i] = new Integer(0);
			}
		
		for(int i =0;i< 1000;i++)
		{
			//System.out.print(distr.generateEventUs(CLUSTER_TYPE.GOLD) + " ");
			//System.out.println(distr.readWriteOption(CLUSTER_TYPE.GOLD));
			int tmp = distr.getPareto();
	
			j[tmp]++;
		}
		
		for(int i=0;i<20;i++)
		{
			System.out.println(j[i]);
		}

	}
	
	

}
