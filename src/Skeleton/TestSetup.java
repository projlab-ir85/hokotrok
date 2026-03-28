package Skeleton;

import Attachments.Snowchain;
import RoadComponents.Intersection;
import RoadComponents.Road;
import Vehicles.Bus;

public class TestSetup {
    public static class BusMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Bus bus = new Bus(i1, i2);
        public Snowchain snowchain;

        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);
    }

    public static BusMovement createBusMovemement(){
        BusMovement bm = new BusMovement();

        bm.i1.addRoad(bm.road);

        return bm;
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
