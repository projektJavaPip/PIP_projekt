package pl.pip.event;

import pl.pip.host.CLUSTER_TYPE;

public class EventAddSession extends Event {
    
    private CLUSTER_TYPE clusterType;

    
    public EventAddSession(double t,CLUSTER_TYPE ct)
    {
        super(t);
        this.clusterType = ct;
  
    }

    public CLUSTER_TYPE getClusterType()
    {
        return clusterType;
    }

}
