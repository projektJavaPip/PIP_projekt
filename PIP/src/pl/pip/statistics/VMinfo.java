package pl.pip.statistics;

public class VMinfo {

	
	int[] vms;
	int time;
	
	public VMinfo(double t, int[] vm)
	{
		time =  (int) t / 1000/60;
		vms = vm;
	}
	
	String getData()
	{
		return Integer.toString(time) + ";" + Integer.toString(vms[0]) + ";" + Integer.toString(vms[1]) + ";" +Integer.toString(vms[2]); 
	}
	
	
}
