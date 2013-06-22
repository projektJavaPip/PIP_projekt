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
    public final static double TIME_DELAY_POWER_ON_VM = 105000; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_VM = 45000; //45s
    public final static double TIME_DELAY_VM_MIGRATION = 30000; //30s
    
    public final static double TIME_DELAY_POWER_ON_HOST = 105000; //1m 45s
    public final static double TIME_DELAY_POWER_OFF_HOST = 45000; //45s
    
    public final static double TIME_CONTROL_SAMPLING_PREODIC = 120000; //2 m
    
    
    public final static double SIMULATION_FINISH_TIME = 5 * 3600 * 1000;
    
    public static double CURRENT_TIME;
    
    public static double[]  TIME_PER_GHZ = { 200,111.111,100};
    public static double[] SLA = {3000,2000,5000};
 
    

    
    
    
    
}
