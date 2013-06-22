/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.host;

import org.omg.CORBA.TIMEOUT;
import pl.pip.config.TimeUtils;
import pl.pip.statistics.StatisticsSing;
import pl.pip.statistics.StatisticsSing.SecondCounter;

/**
 *
 * @author supp
 */
public class Optimizer {
    private static volatile Optimizer instance = null;
    
    public static Optimizer getInstance()  {
		if (instance == null)  {
			synchronized (Optimizer.class) {
				if(instance == null) {
					instance = new Optimizer();
				}
			}
		}
		return instance;
	}
    
    
    
    private Optimizer()
    {
        
       
        
    }
    
    public void run()
    {
       StatisticsSing statistics = StatisticsSing.getInstance();
       SecondCounter sc = statistics.getSlaStats();
       System.out.println(TimeUtils.CURRENT_TIME + " : " + sc.getSlaCounter(0) / sc.getCounter(0) + " : " + sc.getSlaCounter(1)  / sc.getCounter(1)+ " : " + sc.getSlaCounter(2)  / sc.getCounter(2) );
       
    }
    
        
       
        
    
}
