package pl.pip.host;

public class Request {
	
	private double arrivaTime;
	double leftTime;
	long packetId;
	int vmId;
	boolean isWrite;
	
	public Request(double arrTime,long pid,boolean iw) {
		
		arrivaTime = arrTime;
		packetId = pid;
		vmId = 0;
		isWrite = iw;
		// TODO Auto-generated constructor stub
	}
	
	public void setVmId(int id)
	{
		vmId = id;
		isWrite = false;
	}
	
	public void setLeftTime(double lt)
	{
		leftTime = lt;
	}
	
	

}
