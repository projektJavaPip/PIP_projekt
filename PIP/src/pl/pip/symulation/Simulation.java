/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.symulation;

import pl.pip.config.*;
import pl.pip.event.*;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.HOST_STATUS;
import pl.pip.host.VM_STATUS;
import pl.pip.distributio.DistributionUtil;

/**
 *
 * @author supp
 */
public class Simulation {
    
    
    double max_time;
    
    public Simulation(double t)
    {
        max_time = t;
    }
    
    
    
    public void init()
    {
        TimeUtils.CURRENT_TIME = 0;
        Heap heap = Heap.getInstance();
        
        heap.clear();
        
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 0));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 1));
        heap.addElement(new EventHost(0, HOST_STATUS.BOOT, 2));
        
        heap.addElement(new EventClaster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.GOLD));
        heap.addElement(new EventClaster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.SILVER));
        heap.addElement(new EventClaster(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST, CLUSTER_TYPE.BRONZE));
        
        heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.GOLD, true));
        heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.SILVER, true));
        heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME + TimeUtils.TIME_DELAY_POWER_ON_HOST + TimeUtils.TIME_DELAY_POWER_ON_VM, CLUSTER_TYPE.BRONZE, true));
        
        
    }
    
    public void start()
    {
        Heap heap = Heap.getInstance();
        Event e;
        
        
        while(TimeUtils.CURRENT_TIME < max_time)
        {
            e = heap.getElment();
            
            TimeUtils.CURRENT_TIME = e.getTime();
            
            System.out.println(TimeUtils.CURRENT_TIME);
           // System.out.println(e.getClass().getName());
            
            
            if(e instanceof EventAddRequest)
            {
    
                heap.addElement(new EventAddRequest(TimeUtils.CURRENT_TIME+ DistributionUtil.generateEventUs(40) , ((EventAddRequest)e).getClusterType(), DistributionUtil.readWriteOption()));
                
                System.out.println("Nowy request: " + ((EventAddRequest)e).getClusterType());
                
            }   
            
            
            if(e instanceof EventRemoveRequest)
            {
                
            }
            
            
        }
    }
    
}
