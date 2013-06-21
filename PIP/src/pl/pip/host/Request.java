package pl.pip.host;

public class Request {
	
	private double arrivaTime;
	double leftTime;
	long packetId;
	int vmId;
	boolean isWrite;
	CLUSTER_TYPE clusterType;
	
	public Request(double arrTime,long pid,boolean iw,	CLUSTER_TYPE ct) {
		
		arrivaTime = arrTime;
		packetId = pid;
		vmId = 0;
		isWrite = iw;
		clusterType = ct;
		// TODO Auto-generated constructor stub
	}
	
	public void setVmId(int id)
	{
		vmId = id;
		isWrite = false;
	}
	
	public CLUSTER_TYPE getClusterType()
	{
		return clusterType;
	}
	
	public void setLeftTime(double lt)
	{
		leftTime = lt;
	}
	
	public double getArrivalTime()
	{
		return arrivaTime;
	}

	
	

}
