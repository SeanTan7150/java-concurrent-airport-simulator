package test;

public class AircraftStaff {
    private final Plane plane;
    
    public AircraftStaff(Plane plane) {
        this.plane = plane;
    }
    
    public void cleanAndRefillSupply() {
        Cleaner cleaner = new Cleaner(plane);
        Supplier supplier = new Supplier(plane);
        
        cleaner.start();
        supplier.start();
    }
}
