package pl.pip.distributio;

public class KalmanFilter {

	
	
	private double Q = 0.00001;
	private double R = 0.0001;
	private double P = 1, X = 0, K;
	 
	
	private int lastComputation =0;
	
	private void measurementUpdate()
	{
		K = (P + Q) / (P + Q + R);
		P = R * (P + Q) / (R + P + Q);
	}
	 
	public double update(double measurement)
	{
		measurementUpdate();
		//double result = X + (measurement - X) * K;
		double result = K *measurement + (1-K) *X;
		X = result;
		return result;
	}
	 
	
	
	public KalmanFilter() 
	{
		
	}
	
	
	public int compute(int input)
	{
		lastComputation = (int) Math.round(update(input));
		return lastComputation;
	}
	
	public int getPrediction()
	{
		return lastComputation;
	}
	
	
	
}
