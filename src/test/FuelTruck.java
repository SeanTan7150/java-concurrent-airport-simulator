package test;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class FuelTruck extends Thread {
    private final Semaphore fuelTruckSemaphore;
    
    public FuelTruck(Semaphore fuelTruckSemaphore) {
        this.fuelTruckSemaphore = fuelTruckSemaphore;
    }
    
    public void refill(Plane plane) {
        double i = 1;
        double progress;
        DecimalFormat df = new DecimalFormat("#.00");
        
        try {
            fuelTruckSemaphore.acquire();
            plane.setWaitFuelTruckEndTime(System.currentTimeMillis());
            Random random = new Random();
            while (i < plane.getPlaneFuelVolume()) {
                double convertedFuelVolume = (double) plane.getPlaneFuelVolume();
                progress = i / convertedFuelVolume * 100;
                System.out.println("Plane " + plane.getPlaneID() + 
                        " refill fuel progress >>> " + df.format(progress) + "%");
                Thread.sleep(random.nextInt(1000));
                i += 1;
            }
            System.out.println("Plane " + plane.getPlaneID() + 
                    " refill fuel progress >>> 100%");
            plane.setFuelComplete(true);
        }
        catch (InterruptedException e) {
        }
        fuelTruckSemaphore.release();
    }
}
