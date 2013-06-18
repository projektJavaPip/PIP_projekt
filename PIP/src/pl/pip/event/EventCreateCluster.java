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
public class EventCreateCluster extends Event{
    CLUSTER_TYPE cluster_typ;
    
    public EventCreateCluster(double t,CLUSTER_TYPE ct )
    {
        super(t);
        this.cluster_typ = ct;
    }
    
    public CLUSTER_TYPE getClusterType()
    {
        return cluster_typ;
    }
}
