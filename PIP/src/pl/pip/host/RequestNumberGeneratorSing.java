package pl.pip.host;

public class RequestNumberGeneratorSing {
	
	
	
	private static volatile RequestNumberGeneratorSing instance = null;

	
	long requestId = 0L;
	
	public static RequestNumberGeneratorSing getInstance()  {
		if (instance == null)  {
			synchronized (RequestNumberGeneratorSing.class) {
				if(instance == null) {
					instance = new RequestNumberGeneratorSing();
				}
			}
		}
		return instance;
	}
	
	
	private RequestNumberGeneratorSing()
	{
		requestId = 0;
	}
	
	public long getRequestId()
	{
		return requestId++;
	}
	
	
	public long getRequestCurentRequestId()
	{
		return requestId;
	}

}
