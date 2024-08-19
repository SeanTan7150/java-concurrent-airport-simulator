package test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Supplier extends Thread {
    private final Plane plane;
    
    public Supplier(Plane plane) {
        this.plane = plane;
    }
    
    public void refillSupply() throws InterruptedException {
        Random random = new Random();
        System.out.println("Plane " + plane.getPlaneID() + " is under refilling supplies process.");
        Thread.sleep(random.nextInt(10000));
        System.out.println("Plane " + plane.getPlaneID() + " supplies are refilled completely.");
        plane.setRefillSupplyComplete(true);
    }
    
    @Override
    public synchronized void run() {
        try {
            refillSupply();
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
