import java.time.LocalDateTime;
import java.util.*;


class User{
    private String id;
    private String name;
    private String contact;

    public User(String id, String name, String contact){
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getName(){
        return name;
    }

    public String getContact(){
        return contact;
    }

}


class Rider extends User{
   private Ride currentRide;
    
    

    public Rider(String id, String name, String contact) {
        super(id, name, contact);
    }

   public void assignRide(Ride ride){
    this.currentRide = ride;
   }

   public Ride getCurrentRide(){
    return currentRide;
   }

}

class Driver extends User{

    private Ride ride;
    private Vehicle vehicle;


    public Driver(String id, String name, String contact, Vehicle vehicle) {
        super(id, name, contact);
        this.vehicle = vehicle;
        
    }
    
    public void accept(Ride ride){
        this.ride = ride;
    }

    public Ride getCurrentRide(){
        return ride;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }
}

class Location{
    private String id;
    private String name;

    public Location(String id, String name){
        this.id = id;
        this.name = name;
    }
}

class Ride{
    private String id;
    private LocalDateTime time;
    private rideStatus status;
    private Location pick;
    private Location drop;

    private Driver driver;
    private Rider rider;


    public Ride(String id, LocalDateTime time, Driver driver, Rider rider, Location pick, Location drop){
        this.id = id;
        this.time = time;
        this.driver = driver;
        this.rider = rider;
        this.pick = pick;
        this.drop = drop;
        this.status = rideStatus.CREATED;
    }

    public void cancel(){
        this.status = rideStatus.CANCELLED;
    }
    public void complete(){
        this.status = rideStatus.COMPLETED;
    }

    public Driver getDriver(){
        return driver;
    }
    
}

enum rideStatus{
    CREATED,
    ONGOING,
    COMPLETED,
    CANCELLED
}

class RideManager{
    private List<Driver> drivers;

    public RideManager(){
        this.drivers = new ArrayList<>();
    }

    public void registerDriver(Driver driver){
        drivers.add(driver);
    }

    public Ride createRide(Rider rider, Location pick, Location drop){
        Driver availableDriver = findAvailableDriver();

        if(availableDriver == null){
            System.out.println("No drivers available");
            return null;
        }
        String id = UUID.randomUUID().toString();
        Ride ride = new Ride(id, LocalDateTime.now(), availableDriver, rider, pick, drop);
            availableDriver.accept(ride);
            rider.assignRide(ride);


            return ride;
        }

        private Driver findAvailableDriver(){
            for(Driver d : drivers){
                if(d.getCurrentRide() == null){
                    return d;
                }
            }
            return null;
        
    }

}

class Vehicle{
    private String id;
    private String name;

    public Vehicle(String id, String name){
        this.id = id;
        this.name = name;
    }
}


public class cabBooking{
    public static void main(String[] args){
         Vehicle v1 = new Vehicle("V1", "Sedan");
        Driver d1 = new Driver("D1", "Alex", "12345", v1);

        Rider r1 = new Rider("R1", "Emma", "99999");
        Location pick = new Location("L1", "Airport");
        Location drop = new Location("L2", "Downtown");

        
        RideManager manager = new RideManager();
        manager.registerDriver(d1);
        Ride myRide = manager.createRide(r1,pick,drop);
         

        Driver dr = myRide.getDriver();

        String a = dr.getName();

        System.out.println(a);

        
    }
}