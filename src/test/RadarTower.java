package test;

public class RadarTower {
    public static void detected(Plane plane) {
        System.out.println("---------ALERT: Plane " + plane.getPlaneID() + 
                " is detected by the radar tower!---------");
    }
}
