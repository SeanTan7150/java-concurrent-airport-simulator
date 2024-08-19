package test;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaneGenerator {
    private final Runway runway;
    private final int totalPlanes;
    private final FuelTruck fuelTruck;
    public static ArrayList<Plane> planeList = new ArrayList<>();
    private final int passengerQty;
    
    public PlaneGenerator(Runway runway, int totalPlanes, FuelTruck fuelTruck, int passengerQty) {
        this.runway = runway;
        this.totalPlanes = totalPlanes;
        this.fuelTruck = fuelTruck;
        this.passengerQty = passengerQty;
    }
    
    public void generate() {
        Random random = new Random();
        int[] fuelVolume = new int[6];
        int emergencyLandingIndex = 0;
        
        for (int i = 3; i < totalPlanes; i++) {
            fuelVolume[i] = random.nextInt(7) + 4;
        }
        
        // First, second and third plane no need emergency landing
        fuelVolume[0] = random.nextInt(3) + 8;
        fuelVolume[1] = random.nextInt(3) + 8;
        fuelVolume[2] = random.nextInt(3) + 8;
        
        try {
            for (int i = 0; i < totalPlanes; i++) {
                Thread.sleep(random.nextInt(3000));
                Plane plane = new Plane(runway, fuelTruck);
                plane.setPlaneID(i + 1);
                plane.setPlaneFuelVolume(fuelVolume[i]);
                plane.setPlanePassenger(passengerQty);
                planeList.add(plane);
            }
            
            for (int i = 0; i < fuelVolume.length; i++) {
                if (fuelVolume[i] < fuelVolume[emergencyLandingIndex]) {
                    emergencyLandingIndex = i;
                }
            }
            
            for (Plane plane : planeList) {
                if (plane.getPlaneID() == emergencyLandingIndex + 1) {
                    plane.setPriority(Thread.MAX_PRIORITY);
                    plane.setEmergencyLanding(true);
                }
                plane.start();
            }
            
            for (Plane plane : planeList) {
                plane.join();
            }
            
            FinalReport.checkGateEmpty();
            FinalReport.endTotalExecutionTime = System.currentTimeMillis();
            FinalReport.planeList = planeList;
            FinalReport.displayFinalReport();
        }
        catch (InterruptedException ex) {
            Logger.getLogger(PlaneGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
