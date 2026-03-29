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

    private static BusMovement createBusMovemement(){
        BusMovement bm = new BusMovement();

        bm.i1.addRoad(bm.road);
        bm.i2.addRoad(bm.road);

        //mind a ket fele teszt esetet le lehet ezzel tesztelni
        //(egyszerre van keresztezodesnel es utszakaszon mert ugyis a tesztelo dont es kulon tesztesetek)

        //keresztezodeshez busz (busz konstruktoraban egybol keresztezodesnel kezd igy forditva itt nem kell)
        bm.i1.addVehicle(bm.bus);

        //utszakaszhoz busz
        bm.road.getFirstRoadSection(bm.i2).accept(bm.bus);

        //buszhoz utszakasz
        bm.bus.setCurrRoadSection(bm.road.getFirstRoadSection(bm.i2));

        //holanc is mindenkepp lesz, ennek a hasznalatarol is a felhasznalo dont ugyis
        bm.bus.addSnowchain(bm.snowchain);

        return bm;
    }

    public static class CarMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);

        public Car car = new Car(i1, i2);
    }

    private static CarMovement createCarMovement(){
        CarMovement cm = new CarMovement();

        cm.i1.addRoad(cm.road);
        cm.i2.addRoad(cm.road);

        //keresztezodeshez auto (auto konstruktoraban egybol keresztezodesnel kezd igy forditva itt nem kell)
        cm.i1.addVehicle(cm.car);

        //utszakaszhoz auto
        cm.road.getFirstRoadSection(cm.i2).accept(cm.car);

        //autohoz utszakasz
        cm.car.setCurrRoadSection(cm.road.getFirstRoadSection(cm.i2));

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
