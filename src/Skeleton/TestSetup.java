package Skeleton;

import Attachments.PlowHeads.BroomHead;
import Attachments.Snowchain;
import Consumable.Consumable;
import Control.Player;
import Control.Shop;
import RoadComponents.Intersection;
import RoadComponents.Road;
import Vehicles.Bus;
import Vehicles.Car;
import Vehicles.Snowplow;

public class TestSetup {
    public static class BusMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);

        public Bus bus = new Bus(i1, i2);
        public Snowchain snowchain;
    }

    private static BusMovement createBusMovemementSetup(){
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

    private static CarMovement createCarMovementSetup(){
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

    public static class SnowplowMovement{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);

        public Snowplow snowplow = new Snowplow(i1);
        public BroomHead broomHead = new BroomHead();
    }

    public static SnowplowMovement createSnowplowMovementSetup(){
        SnowplowMovement sm = new SnowplowMovement();

        sm.i1.addRoad(sm.road);
        sm.i2.addRoad(sm.road);

        //keresztezodeshez hokotro (hokotro konstruktoraban egybol keresztezodesnel kezd igy forditva itt nem kell)
        sm.i1.addVehicle(sm.snowplow);

        //utszakaszhoz hokotro
        sm.road.getFirstRoadSection(sm.i2).accept(sm.snowplow);

        //hokotrohoz utszakasz
        sm.snowplow.setCurrRoadSection(sm.road.getFirstRoadSection(sm.i2));

        //kotrofej is mindenkeppen lesz legfeljebb keresztezodesnel nem hasznalja
        sm.snowplow.addPlow(sm.broomHead);

        return sm;
    }

    public static class SnowchainFix{
        public Bus bus;
        public Snowchain snowchain;
    }

    public static SnowchainFix createSnowchainFixSetup(){
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

    public static class ConsumableUsage{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 1, 1);

        public Consumable consumable = new Consumable(60, 2);
    }

    public static ConsumableUsage createConsumableUsageSetup(){
        ConsumableUsage cu = new ConsumableUsage();

        cu.i1.addRoad(cu.road);
        cu.i2.addRoad(cu.road);

        cu.road.getFirstRoadSection(cu.i2).addConsumable(cu.consumable);

        return cu;
    }

    public static class ShopUsage{
        public Shop shop = new Shop();
        public Player player = new Player(6942067);
    }

    public static ShopUsage createShopUsageSetup(){
        ShopUsage su = new ShopUsage();

        return su;
    }

    public static class AccidentSim{
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 1, 2);

        public Car c1 = new Car(i1, i2);
        public Car c2 = new Car(i1, i2);
    }

    public static AccidentSim createAccidentSimSetup(){
        AccidentSim as = new AccidentSim();

        as.i1.addRoad(as.road);
        as.i2.addRoad(as.road);

        //demo
        as.road.setIceshield();

        as.road.getFirstRoadSection(as.i2).accept(as.c1);
        as.road.getFirstRoadSection(as.i2).accept(as.c2);

        return as;
    }
}
