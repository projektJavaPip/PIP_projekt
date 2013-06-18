/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pip.event;
import pl.pip.host.VM_STATUS;

/**
 *
 * @author supp
 */
public class EventVM extends Event{
    
    VM_STATUS vm_status;
    long id_vm;
    long to_host;
    long from_host;
    
    public EventVM(double t, VM_STATUS vs, long id_vm)
    {
        super(t);
        this.vm_status = vs;
        this.id_vm = id_vm;
        
    }
    
    
    
    public EventVM(double t, VM_STATUS vs, long id_vm, long fromh, long toh)
    {
        super(t);
        this.vm_status = vs;
        this.id_vm = id_vm;
        this.from_host = fromh;
        this.to_host = toh;
        
    }
    
    public long getIdVm()
    {
        return id_vm;
    }
    
    public VM_STATUS getStatus()
    {
        return vm_status;
    }
    
    public long getFromIdHost() throws Exception
    {
        if( vm_status == VM_STATUS.MIGRATION)
        {
            return from_host;
        }
        else
        {
            throw new Exception("to nie jest migracja.");
        }
    }
        
         public long getToIdHost() throws Exception
    {
        if( vm_status == VM_STATUS.MIGRATION)
        {
            return to_host;
        }
        else
        {
            throw new Exception("to nie jest migracja.");
        }
    }
            
    
}
