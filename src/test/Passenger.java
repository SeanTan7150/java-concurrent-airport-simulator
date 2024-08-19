package test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Passenger extends Thread {
    private Plane plane;
    public int disembarkQty;
    public int embarkQty;
    
    public Passenger(Plane plane, int disembarkQty, int embarkQty) {
        this.plane = plane;
        this.disembarkQty = disembarkQty;
        this.embarkQty = embarkQty;
    }
    
    public Plane getPlane() {
        return plane;
    }
    
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public int getDisembarkQty() {
        return disembarkQty;
    }

    public void setDisembarkQty(int disembarkQty) {
        this.disembarkQty = disembarkQty;
    }
    
    public int getEmbarkQty() {
        return embarkQty;
    }
    
    public void setEmbarkQty(int embarkQty) {
        this.embarkQty = embarkQty;
    }
    
    public void disembark() throws InterruptedException {
        Random random = new Random();
        for (int i = 10; i <= disembarkQty; i += 10) {
            Thread.sleep(random.nextInt(1000));
            System.out.println("Plane " + plane.getPlaneID() + 
                    " has disembarked " + i + " passengers.");
        }
        System.out.println("Plane " + plane.getPlaneID() + 
                " finish disembarking passengers, staff are getting ready.");
    }
    
    public void embark() throws InterruptedException {
        Random random = new Random();
        for (int i = 10; i <= disembarkQty; i += 10) {
            Thread.sleep(random.nextInt(1000));
            System.out.println("Plane " + plane.getPlaneID() + 
                    " has embarked " + i + " passengers.");
        }
        System.out.println("Plane " + plane.getPlaneID() + 
                " finish embarking passengers.");
        plane.setPassengerAllOnBoard(true);
    }
    
    @Override
    public synchronized void run() {
        try {
            disembark();
            AircraftStaff staff = new AircraftStaff(plane);
            staff.cleanAndRefillSupply();
            
            while (plane.getCleanComplete() != true || plane.getRefillSupplyComplete() != true) {
                Thread.sleep(500);
            }
            
            embark();
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
