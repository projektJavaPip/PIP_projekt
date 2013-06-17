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
      
      heapEvent.addElement(new Event(0.23));
       heapEvent.addElement(new Event(3));
        heapEvent.addElement(new Event(4));
         heapEvent.addElement(new Event(1));
          heapEvent.addElement(new Event(6));
          heapEvent.addElement(new Event(0.1));
           heapEvent.addElement(new Event(0.0));
           heapEvent.addElement(new Event(100.0));
            heapEvent.addElement(new EventAddRequest(45.0));
          
          
          System.out.println(heapEvent.getElment().getTime());
          System.out.println(heapEvent.getElment().getTime());
          System.out.println(heapEvent.getElment().getTime());
          System.out.println(heapEvent.getElment().getTime());
          System.out.println(heapEvent.getElment().getTime());
          System.out.println(heapEvent.getElment().getTime());
           System.out.println(heapEvent.getElment().getTime());
            System.out.println(heapEvent.getElment().getTime());
          
        
        
    }
}
