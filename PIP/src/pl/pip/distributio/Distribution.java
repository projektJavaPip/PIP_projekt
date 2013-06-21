package pl.pip.distributio;

import jsc.distributions.Bernoulli;
import jsc.distributions.Pareto;
import pl.pip.host.CLUSTER_TYPE;



public class Distribution {


	private double writeProbablity[] = {0.5,0.5,0.5};
	
	
	private double lambda[] = {100,50,80};
	
	
	private Pareto paretoDist;
	
	private Bernoulli bd[]; 
	
	public Distribution()
	{
		bd = new Bernoulli[3];
		for(int i = 0;i< 3;i++)
		{
			bd[i] = new Bernoulli(writeProbablity[i]);
			System.out.println(bd[i].toString());
		}	
		paretoDist = new Pareto(2, 1.8);
	}
	
	public  Boolean readWriteOption(CLUSTER_TYPE ct)
	{
		
		//System.out.println(bd[ct.ordinal()].random());
	 if (bd[ct.ordinal()].random() == 1.0) return true;
	 else return false;
	}
	
	/**
	 * 
	 * @param lambda - �redni czas nadej�cia pakietu w uS
	 * @return
	 */
	public double generateEventUs(CLUSTER_TYPE ct) {
		Double ret = (double) 1-  Math.log(Math.random()) / -(1/ lambda[ct.ordinal()]) ;
		if(ret < 0) return -1 * ret;
		else return ret;
	}
	
	
	public int getPareto()
	{
		int pareto = (int) Math.round(paretoDist.random());
		if(pareto > 20) return  getPareto();
		else return pareto;
	
	}
	
	

	
	
	
	
	
	
}
