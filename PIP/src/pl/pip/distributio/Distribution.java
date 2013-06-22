package pl.pip.distributio;

import jsc.distributions.Bernoulli;
import jsc.distributions.Pareto;
//import jsc.distributions.Poisson;
import pl.pip.config.TimeUtils;
import pl.pip.host.CLUSTER_TYPE;



public class Distribution {


	private double writeProbablity[] = {0.5,0.5,0.5};
	int recountingTime = 0;
	
	private double lambda[] = {100,200,400};
	
	
	double lambdaD[] = {10,15,80};
	
	private Pareto paretoDist;
	
	//private Poisson pd[];
	private Bernoulli bd[]; 
	int counter = 0;
	
	public Distribution()
	{
		bd = new Bernoulli[3];
		//pd = new Poisson[3];
		for(int i = 0;i< 3;i++)
		{
			bd[i] = new Bernoulli(writeProbablity[i]);
			
			//pd[i] = new Poisson(lambda[i]);
		
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
	 * @throws Exception 
	 */
	public double generateEventUs(CLUSTER_TYPE ct) throws Exception {
		int currentTime = (int) Math.round(TimeUtils.CURRENT_TIME);
		if(currentTime !=recountingTime && (currentTime % (8  * 1000) == 0))
		{
			double multi = 1;
			if(Math.random() < 0.3)  multi = 1.2;
		
			
			counter++;
			if(counter % 16 == 0) lambdaD[0] = -1 * lambdaD[0] * multi;
			if(counter % 7 == 0 ) lambdaD[1] = -1 * lambdaD[1] * multi;
			if(counter % 19 == 0 ) lambdaD[2] = -1 * lambdaD[2] * multi;
		
			
			for(int i=0;i<lambda.length;i++)
			{
					lambda[i]+= lambdaD[i];
					//pd[i].setMean(lambda[i]);
			}
			recountingTime = currentTime;
		}
		//Double ret = pd[ct.ordinal()].random();
		Double ret = (double) 1 -  Math.log(Math.random()) / -(1/ lambda[ct.ordinal()]) ;
		if(ret < 0) 
			{
			//throw new Exception("DUPA poison");
			return (-1) * ret;
			//	return generateEventUs(ct);
				
				
			}
		else return ret;
	}
	
	
	public int getPareto()
	{
		int pareto = (int) Math.round(paretoDist.random());
		if(pareto > 20) return  getPareto();
		else return pareto;
	
	}
	
	

	
	
	
	
	
	
}
