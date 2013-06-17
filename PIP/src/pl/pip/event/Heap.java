/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.event;
import java.util.ArrayList;


/**
 *
 * @author supp
 */
public class Heap {
    private ArrayList<Event> list;
    private static volatile Heap instance = null;

    
    public static Heap getInstance()  {
		if (instance == null)  {
			synchronized (Heap.class) {
				if(instance == null) {
					instance = new Heap();
				}
			}
		}
		return instance;
	}
    
    
    private Heap()
    {
        
        list = new ArrayList<>();
        
    }
    
    public void addElement(Event e)
    {
       list.add(e);
       trickleUp(list.size()-1);
    }

    private void trickleUp(int index) {
        int parent = (index - 1) / 2;
        Event bottom = list.get(index);
        
        while( index > 0 && list.get(parent).compareTo(bottom) < 0 )
        {
            list.set(index,list.get(parent));
            index = parent;
            parent = (index - 1) / 2;
        }
        list.set(index, bottom);
        
    }
    
    public Event getElment()
    {
        Event e = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1 );
        
        if(list.size() > 0)
        { 
            trickleDown(0);
        }
        
    
        return e;
        
    }

    private void trickleDown(int i) {
         int largerChild;
         Event top = list.get(i);
         
         while( i < list.size() / 2)
         {
              int leftChild = 2 * i +1;
              int rightChild = leftChild + 1;
             
              if( rightChild <= list.size() -1  && list.get(leftChild).compareTo(list.get(rightChild)) < 0 )
              {
                  largerChild = rightChild;
              }
              else
              {
                  largerChild = leftChild;
              }
              if(top.compareTo(list.get(largerChild)) >= 0)
              {
                  break;
              }
              list.set(i, list.get(largerChild));
              i = largerChild;
         }
         list.set(i, top);
         
    }

    public boolean isEmpty()
    {
        return list.size() == 0 ? true : false; 
    }
    
    
    
}
