/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.config;

import pl.pip.event.EventPrediction;

/**
 *
 * @author supp
 */
public class TimeUtils {
/**
 * CZASY SYMULACJI
 */
    public final static double TIME_DELAY_POWER_ON_VM = 105 * 1000; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_VM = 45 *1000; //45s
    public final static double TIME_DELAY_VM_MIGRATION = 30 * 1000; //30s
    
    public final static double TIME_DELAY_POWER_ON_HOST = 105 * 1000  ; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_HOST = 45 * 1000; //45s
    
    
    
    public final static double SIMULATION_FINISH_TIME = 24 * 3600 * 1000;
	public static final double TIME_OPTIMIZE = 6 * 60 * 1000 ;
    
    public static double CURRENT_TIME;
    
    public static double[]  TIME_PER_GHZ = {400,211.111,200};//{ 200,111.111,100};
    public static double[] SLA = {3000,2000,5000};
 
    

    
    
    
    
}
