package test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cleaner extends Thread {
    private final Plane plane;
    
    public Cleaner(Plane plane) {
        this.plane = plane;
    }
    
    public void clean() throws InterruptedException {
        Random random = new Random();
        System.out.println("Plane " + plane.getPlaneID() + " is under cleaning process.");
        Thread.sleep(random.nextInt(10000));
        System.out.println("Plane " + plane.getPlaneID() + " is cleaned completely.");
        plane.setCleanComplete(true);
    }
    
    @Override
    public synchronized void run() {
        try {
            clean();
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Cleaner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
