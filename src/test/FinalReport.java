package test;

import java.util.ArrayList;
import java.util.Collections;

public class FinalReport {
    public static long startTotalExecutionTime;
    public static long endTotalExecutionTime;
    
    public static ArrayList<Plane> planeList;
    private static final ArrayList<Long> totalTimeList = new ArrayList<>();
    private static final ArrayList<Long> waitGateTimeList = new ArrayList<>();
    private static final ArrayList<Long> waitFuelTruckTimeList = new ArrayList<>();
    private static long sumTotalTime = 0;
    private static long sumWaitGateTime = 0;
    private static long sumWaitFuelTruckTime = 0;
    
    public static void displayFinalReport() {
        System.out.println("");
        System.out.println("================================================================");
        System.out.println("|                         FINAL REPORT                         |");
        System.out.println("================================================================");
        System.out.println("TOTAL SYSTEM EXECUTION TIME >>> " + (endTotalExecutionTime - startTotalExecutionTime) + " milliseconds");
        System.out.println("================================================================");
        
        for (Plane plane : planeList) {
            System.out.println("TOTAL RUNTIME FOR PLANE " + plane.getPlaneID() + " >>> " + plane.getTotalTime() + " milliseconds");
            totalTimeList.add(plane.getTotalTime());
            sumTotalTime += plane.getTotalTime();
        }
        
        System.out.println("================================================================");
        System.out.println("MIN TOTAL RUNTIME >>> " + Collections.min(totalTimeList) + " milliseconds");
        System.out.println("MAX TOTAL RUNTIME >>> " + Collections.max(totalTimeList) + " milliseconds");
        System.out.println("AVG TOTAL RUNTIME >>> " + (sumTotalTime / totalTimeList.size()) + " milliseconds");
        System.out.println("================================================================");
        
        for (Plane plane : planeList) {
            System.out.println("TOTAL WAITING GATE TIME FOR PLANE " + plane.getPlaneID() + " >>> " + plane.getTotalWaitGateTime() + " milliseconds");
            waitGateTimeList.add(plane.getTotalWaitGateTime());
            sumWaitGateTime += plane.getTotalWaitGateTime();
        }
        
        System.out.println("================================================================");
        System.out.println("MIN TOTAL WAITING GATE TIME >>> " + Collections.min(waitGateTimeList) + " milliseconds");
        System.out.println("MAX TOTAL WAITING GATE TIME >>> " + Collections.max(waitGateTimeList) + " milliseconds");
        System.out.println("AVG TOTAL WAITING GATE TIME >>> " + (sumWaitGateTime / waitGateTimeList.size()) + " milliseconds");
        System.out.println("================================================================");
    
        for (Plane plane : planeList) {
            System.out.println("TOTAL WAITING FUEL TRUCK TIME FOR PLANE " + plane.getPlaneID() + " >>> " + plane.getTotalWaitFuelTruckTime() + " milliseconds");
            waitFuelTruckTimeList.add(plane.getTotalWaitFuelTruckTime());
            sumWaitFuelTruckTime += plane.getTotalWaitFuelTruckTime();
        }
        
        System.out.println("================================================================");
        System.out.println("MIN TOTAL WAITING FUEL TRUCK TIME >>> " + Collections.min(waitFuelTruckTimeList) + " milliseconds");
        System.out.println("MAX TOTAL WAITING FUEL TRUCK TIME >>> " + Collections.max(waitFuelTruckTimeList) + " milliseconds");
        System.out.println("AVG TOTAL WAITING FUEL TRUCK TIME >>> " + (sumWaitFuelTruckTime / waitFuelTruckTimeList.size()) + " milliseconds");
        System.out.println("================================================================");
    }
    
    public static void checkGateEmpty() {
        if (ATC.gateA.planeInGateCount.get() == 0 && 
                ATC.gateB.planeInGateCount.get() == 0 && 
                ATC.gateC.planeInGateCount.get() == 0) {
            System.out.println("\nAll gates are empty!");
        }
    }
}
