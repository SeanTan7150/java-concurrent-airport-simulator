package test;

import java.util.concurrent.Semaphore;

public class ATC {
    private static final int NUMBER_OF_PLANES = 6;
    private static final int RUNWAY_CAP = 1;
    private static final int AIRPORT_CAP = 3;
    private static final int MAX_PASSENGERS = 50;
    private static final int FUEL_TRUCK_CAP = 1;
    
    private static final Semaphore airportSemaphore = new Semaphore(AIRPORT_CAP);
    public static final Semaphore runwaySemaphore = new Semaphore(RUNWAY_CAP);
    public static final Semaphore fuelTruckSemaphore = new Semaphore(FUEL_TRUCK_CAP);
    
    public static final Gate gateA = new Gate("A");
    public static final Gate gateB = new Gate("B");
    public static final Gate gateC = new Gate("C");
    
    public static void main(String[] args) throws InterruptedException {
        FinalReport.startTotalExecutionTime = System.currentTimeMillis();
        Runway runway = new Runway(airportSemaphore, runwaySemaphore);
        FuelTruck fuelTruck = new FuelTruck(fuelTruckSemaphore);
        PlaneGenerator planeGenerator = new PlaneGenerator(runway, NUMBER_OF_PLANES, 
                fuelTruck, MAX_PASSENGERS);
        planeGenerator.generate();
    }
}
