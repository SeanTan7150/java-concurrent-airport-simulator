package test;

import java.util.concurrent.Semaphore;

public class Runway {
    private final Semaphore airportSemaphore;
    public final Semaphore runwaySemaphore;
    
    public Runway(Semaphore airportSemaphore, Semaphore runwaySemaphore) {
        this.airportSemaphore = airportSemaphore;
        this.runwaySemaphore = runwaySemaphore;
    }
    
    public void enter() throws InterruptedException {
        airportSemaphore.acquire();
    }
    
    public void leave() throws InterruptedException {
        airportSemaphore.release();
    }
    
    public void land(Plane plane) throws InterruptedException {
        runwaySemaphore.acquire();
        System.out.println("Plane " + plane.getPlaneID() + " is landing...");
        Thread.sleep(1000);
        System.out.println("Plane " + plane.getPlaneID() + " has landed successfully!");
        runwaySemaphore.release();
    }
    
    public void depart(Plane plane) throws InterruptedException {
        runwaySemaphore.acquire();
        System.out.println("Plane " + plane.getPlaneID() + " is departing.");
        Thread.sleep(1000);
        System.out.println("Plane " + plane.getPlaneID() + " has departed successfully!");
        runwaySemaphore.release();
    }
}
