package pl.pip.host;

public class Request {
	
	double arrivaTime;
	double leftTime;
	long packetId;
	int vmId;
	
	
	public Request(double arrTime,long pid) {
		
		arrivaTime = arrTime;
		packetId = pid;
		vmId = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void setVmId(int id)
	{
		vmId = id;
	}
	
	

}