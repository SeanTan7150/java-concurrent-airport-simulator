package test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gate {
    private final String gateID;
    public final Lock gateLock;
    public final AtomicInteger planeInGateCount;
    
    public Gate(String gateID) {
        this.gateID = gateID;
        this.gateLock = new ReentrantLock();
        this.planeInGateCount = new AtomicInteger(0);
    }
    
    public String getGateID() {
        return gateID;
    }
    
    public void park(Plane plane) throws InterruptedException {
        planeInGateCount.incrementAndGet();
        System.out.println("Plane " + plane.getPlaneID() + " has parked at Gate " + this.gateID);
    }
    
    public void exit(Plane plane) throws InterruptedException {
        planeInGateCount.decrementAndGet();
        gateLock.unlock();
        System.out.println("Plane " + plane.getPlaneID() + " exiting from Gate " + this.gateID + ".");
    }
}
