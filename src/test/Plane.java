package test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Plane extends Thread {
    private final Runway runway;
    private final FuelTruck fuelTruck;
    private Gate gate;
    
    private int planeID;
    private int planeFuelVolume;
    private int planePassenger;
    private boolean passengerAllOnBoard;
    private boolean fuelComplete;
    private boolean cleanComplete;
    private boolean refillSupplyComplete;
    private boolean emergencyLanding;
    
    private long totalStartTime;
    private long totalEndTime;
    private long totalTime;
    
    private long waitGateStartTime;
    private long waitGateEndTime;
    private long totalWaitGateTime;
    
    private long waitFuelTruckStartTime;
    private long waitFuelTruckEndTime;
    private long totalWaitFuelTruckTime;
    
    public Plane(Runway runway, FuelTruck fuelTruck) {
        this.runway = runway;
        this.fuelTruck = fuelTruck;
        
        this.passengerAllOnBoard = false;
        this.fuelComplete = false;
        this.cleanComplete = false;
        this.refillSupplyComplete = false;
        this.emergencyLanding = false;
    }
    
    public int getPlaneID() {
        return planeID;
    }
    
    public void setPlaneID(int planeID) {
        this.planeID = planeID;
    }
    
    public Gate getGate() {
        return gate;
    }
    
    public void setGate(Gate gate) {
        this.gate = gate;
    }
    
    public int getPlaneFuelVolume() {
        return planeFuelVolume;
    }
    
    public void setPlaneFuelVolume(int planeFuelVolume) {
        this.planeFuelVolume = planeFuelVolume;
    }
    
    public int getPlanePassenger() {
        return planePassenger;
    }
    
    public void setPlanePassenger(int passengerOnBoard) {
        this.planePassenger = passengerOnBoard;
    }
    
    public boolean getPassengerAllOnBoard() {
        return passengerAllOnBoard;
    }
    
    public void setPassengerAllOnBoard(boolean passengerAllOnBoard) {
        this.passengerAllOnBoard = passengerAllOnBoard;
    }
    
    public boolean getFuelComplete() {
        return fuelComplete;
    }
    
    public void setFuelComplete(boolean fuelComplete) {
        this.fuelComplete = fuelComplete;
    }
    
    public boolean getCleanComplete() {
        return cleanComplete;
    }
    
    public void setCleanComplete(boolean cleanComplete) {
        this.cleanComplete = cleanComplete;
    }
    
    public boolean getRefillSupplyComplete() {
        return refillSupplyComplete;
    }
    
    public void setRefillSupplyComplete(boolean refillSupplyComplete) {
        this.refillSupplyComplete = refillSupplyComplete;
    }

    public boolean isEmergencyLanding() {
        return emergencyLanding;
    }

    public void setEmergencyLanding(boolean emergencyLanding) {
        this.emergencyLanding = emergencyLanding;
    }
    
    public long getTotalStartTime() {
        return totalStartTime;
    }
    
    public void setTotalStartTime(long totalStartTime) {
        this.totalStartTime = totalStartTime;
    }
    
    public long getTotalEndTime() {
        return totalEndTime;
    }
    
    public void setTotalEndTime(long totalEndTime) {
        this.totalEndTime = totalEndTime;
    }
    
    public long getTotalTime() {
        return totalTime;
    }
    
    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getWaitGateStartTime() {
        return waitGateStartTime;
    }

    public void setWaitGateStartTime(long waitGateStartTime) {
        this.waitGateStartTime = waitGateStartTime;
    }

    public long getWaitGateEndTime() {
        return waitGateEndTime;
    }

    public void setWaitGateEndTime(long waitGateEndTime) {
        this.waitGateEndTime = waitGateEndTime;
    }

    public long getTotalWaitGateTime() {
        return totalWaitGateTime;
    }

    public void setTotalWaitGateTime(long totalWaitGateTime) {
        this.totalWaitGateTime = totalWaitGateTime;
    }

    public long getWaitFuelTruckStartTime() {
        return waitFuelTruckStartTime;
    }

    public void setWaitFuelTruckStartTime(long waitFuelTruckStartTime) {
        this.waitFuelTruckStartTime = waitFuelTruckStartTime;
    }

    public long getWaitFuelTruckEndTime() {
        return waitFuelTruckEndTime;
    }

    public void setWaitFuelTruckEndTime(long waitFuelTruckEndTime) {
        this.waitFuelTruckEndTime = waitFuelTruckEndTime;
    }

    public long getTotalWaitFuelTruckTime() {
        return totalWaitFuelTruckTime;
    }

    public void setTotalWaitFuelTruckTime(long totalWaitFuelTruckTime) {
        this.totalWaitFuelTruckTime = totalWaitFuelTruckTime;
    }
    
    private Gate findGate() {
        if (ATC.gateA.gateLock.tryLock()) {
            setGate(ATC.gateA);
            return ATC.gateA;
        }
        else if (ATC.gateB.gateLock.tryLock()) {
            setGate(ATC.gateB);
            return ATC.gateB;
        }
        else if (ATC.gateC.gateLock.tryLock()) {
            setGate(ATC.gateC);
            return ATC.gateC;
        }
        else {
            return null;
        }
    }
    
    @Override
    public synchronized void run() {
        try {
            this.totalStartTime = System.currentTimeMillis();
            RadarTower.detected(this);
            
            if (emergencyLanding == true) {
                System.out.println("\n---------Plane " + planeID + " EMERGENCY LANDING!!!---------\n");
            }
            
            waitGateStartTime = System.currentTimeMillis();
            gate = findGate();
            
            while (gate == null) {
                System.out.println("Plane " + planeID + " is waiting for an available gate...");
                try {
                    Thread.sleep(8000);
                }
                catch (InterruptedException e) {
                }
                gate = findGate();
            }
            
            System.out.println("Plane " + planeID + " found GATE " + gate.getGateID());
            waitGateEndTime = System.currentTimeMillis();
            totalWaitGateTime = waitGateEndTime - waitGateStartTime;
            
            runway.enter();
            runway.land(this);
            gate.park(this);
            
            Passenger passenger = new Passenger(this, 50, 50);
            passenger.start();
            waitFuelTruckStartTime = System.currentTimeMillis();
            fuelTruck.refill(this);

            while (passengerAllOnBoard != true || fuelComplete != true) {
                Thread.sleep(500);
            }
            
            totalWaitFuelTruckTime = waitFuelTruckEndTime - waitFuelTruckStartTime;
            gate.exit(this);
            runway.depart(this);
            runway.leave();
            this.totalEndTime = System.currentTimeMillis();
            this.totalTime = totalEndTime - totalStartTime;
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex) {
        }
    }
}
