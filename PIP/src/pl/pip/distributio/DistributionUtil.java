package pl.pip.distributio;

public class DistributionUtil {

	
	public static Boolean readWriteOption()
	{
		if(Math.random() > 0.5) return true;
		else return false;
	}
	
	/**
	 * 
	 * @param lambda - �redni czas nadej�cia pakietu w uS
	 * @return
	 */
	public static double generateEventUs(double lambda) {
		Double ret = (double) Math.round(1-  Math.log(Math.random()) / -(1/lambda)) ;
		if(ret < 0) return -1 * ret;
		else return ret;
	}
}
