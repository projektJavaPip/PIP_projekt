/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.event;
import pl.pip.host.CLUSTER_TYPE;

/**
 *
 * @author supp
 */
public class EventAddRequest  extends Event {
    
    private CLUSTER_TYPE clusterType;
    private Boolean request_write;
    
    public EventAddRequest(double t,CLUSTER_TYPE ct, boolean w)
    {
        super(t);
        this.clusterType = ct;
        this.request_write = w;
    }
    public boolean isWrite()
    {
        return request_write;
    }
    public CLUSTER_TYPE getClusterType()
    {
        return clusterType;
    }
}
