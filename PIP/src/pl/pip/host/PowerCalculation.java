package pl.pip.host;

public class PowerCalculation {
	
	double lastCalculationTime;
	double powerValue;
	
	
	PowerCalculation(Double t, Double p)
	{
		powerValue = p;
		lastCalculationTime = t;
	}
	
	
	public double getLastTime() { return lastCalculationTime;}
	public double getPowerLevel() { return powerValue;}

}
