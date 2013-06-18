/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip;

import pl.pip.event.*;
import pl.pip.host.CLUSTER_TYPE;
import pl.pip.host.HOST_STATUS;


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
        
      Heap heapEvent = Heap.getInstance();
      
      heapEvent.addElement(new EventAddRequest(0, CLUSTER_TYPE.GOLD, true));
      heapEvent.addElement(new EventAddRequest(0, CLUSTER_TYPE.SILVER, true));
      heapEvent.addElement(new EventAddRequest(0, CLUSTER_TYPE.BRONZE, true));
        heapEvent.addElement(new EventHost(0, HOST_STATUS.BOOT, 0));
        heapEvent.addElement(new EventHost(0, HOST_STATUS.BOOT, 1));
        heapEvent.addElement(new EventHost(0, HOST_STATUS.BOOT, 2));
       heapEvent.addElement(new Event(6));
        heapEvent.addElement(new Event(4));
        heapEvent.addElement(new Event(7));
     
        
          
          
            while( !heapEvent.isEmpty())
            {
                  System.out.println(heapEvent.getElment().getTime());
            }
            
                  
        
    }
}
