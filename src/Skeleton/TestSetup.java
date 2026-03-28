package Skeleton;

import Attachments.Snowchain;
import RoadComponents.Intersection;
import RoadComponents.Road;
import Vehicles.Bus;
import Vehicles.Car;

public class TestSetup {
    public static class BusMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);

        public Bus bus = new Bus(i1, i2);
        public Snowchain snowchain;
    }

    private static BusMovement createBusMovemementBase(){
        BusMovement bm = new BusMovement();

        bm.i1.addRoad(bm.road);
        bm.i2.addRoad(bm.road);

        bm.bus.addSnowchain(bm.snowchain);

        return bm;
    }

    public static BusMovement createBusMovementIntersection(){
        BusMovement bm = createBusMovemementBase();

        bm.i1.addVehicle(bm.bus);

        return bm;
    }

    public static BusMovement createBusMovementRoad(){
        BusMovement bm = createBusMovemementBase();

        //buszt utszakaszra

        return bm;
    }

    public static class CarMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);

        public Car car = new Car(i1, i2);
    }

    private static CarMovement createCarMovementBase(){
        CarMovement cm = new CarMovement();

        cm.i1.addRoad(cm.road);
        cm.i2.addRoad(cm.road);

        return cm;
    }

    public static class SnowchainFix{
        public Bus bus;
        public Snowchain snowchain;
    }

    public static SnowchainFix createSnowchainFix(){
        SnowchainFix s = new SnowchainFix();

        Skeleton.call("Skeleton", "Bus", "new Bus()", false);
        s.bus = new Bus();

        Skeleton.call("Skeleton", "Snowchain", "new Snowchain()", false);
        s.snowchain = new Snowchain(999);

        Skeleton.call("Skeleton", "Bus", "addSnowchain(snowchain)", false);
        s.bus.addSnowchain(s.snowchain);

        System.out.println("\n");
        return s;
    }
}
