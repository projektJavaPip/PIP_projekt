/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.event;

/**
 *
 * @author supp
 */
public class Event implements EventInterface{

    private double time;
    
    public double getTime() {
        return time;
    }

    public Event(double t)
    {
        time = t;
    }
    
    
    public int compareTo(Event e) {
      
        if (this == e)
        {
            return 0;
        }
        
        if(this.time == e.getTime())
        {
            return 0;
        }
        
      return this.time < e.getTime()? 1 : -1; 
    
    }

    
        
    
}
