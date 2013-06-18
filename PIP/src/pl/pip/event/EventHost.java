/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.event;

import pl.pip.host.HOST_STATUS;

/**
 *
 * @author supp
 */
public class EventHost extends Event{
    
    private HOST_STATUS h_status;
    private int id_host;
    
    public EventHost(double t, HOST_STATUS hs, int id)
    {
        super(t);
        this.h_status = hs;
        this.id_host = id;
        
    }
    
    public HOST_STATUS getStatus()
    {
        return h_status;
    }
    
    public  int getIdHost()
    {
        return id_host;
    } 
    
    
}
