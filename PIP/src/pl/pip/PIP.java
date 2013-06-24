/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip;

import pl.pip.config.TimeUtils;
import pl.pip.event.*;
import pl.pip.simulation.Simulation;
import pl.pip.simulation.Simulation2;


/**
 *
 * @author supp
 */
public class PIP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Simulation2 s = new Simulation2(TimeUtils.SIMULATION_FINISH_TIME);
        
        s.init();
        
        s.start();
            
                  
        
    }
}
