/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.config;

/**
 *
 * @author supp
 */
public class TimeUtils {
/**
 * CZASY SYMULACJI
 */
    public final static double TIME_DELAY_POWER_ON_VM = 105; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_VM = 45; //45s
    public final static double TIME_DELAY_VM_MIGRATION = 30; //30s
    
    public final static double TIME_DELAY_POWER_ON_HOST = 175000; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_HOST = 90000; //45s
    
    public final static double TIME_CONTROL_SAMPLING_PREODIC = 120000; //1m 45s
    
    
    public final static double SIMULATION_FINISH_TIME = 24 * 3600 * 1000;
    
    public static double CURRENT_TIME;
    
    public static double[]  TIME_PER_GHZ = { 200,111.111,100};
    public static double[] SLA = {3000,2000,5000};
 
    

    
    
    
    
}
