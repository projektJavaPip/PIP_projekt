package pl.pip.distributio;

public class DistributionUtil {

	
	public static String readWriteOption()
	{
		if(Math.random() > 0.5) return "w";
		else return "r";
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
