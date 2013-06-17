/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip;

import pl.pip.event.*;


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
        
      Heap heapEvent = new Heap();
      
      heapEvent.addElement(new Event(9));
       heapEvent.addElement(new Event(6));
        heapEvent.addElement(new Event(4));
        heapEvent.addElement(new Event(7));
     
        
          
          
            while( !heapEvent.isEmpty())
            {
                  System.out.println(heapEvent.getElment().getTime());
            }
            
                  
        
    }
}
