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
public class EventRemoveRequest extends Event{
    
    private long id_request;
    private CLUSTER_TYPE clusterType;
    
    public EventRemoveRequest(double t, CLUSTER_TYPE ct, long id)
    {
        super(t);
        this.clusterType = ct;
        this.id_request = id;
        
    }
    
    public long getIdRequest()
    {

        return id_request;
    }
    
     public CLUSTER_TYPE getClusterType()
    {

        return clusterType;
    }
    
}
