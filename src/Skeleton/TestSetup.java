package Skeleton;

import Attachments.PlowHead;
import Attachments.PlowHeads.BroomHead;
import Attachments.PlowHeads.DragonHead;
import Attachments.PlowHeads.IceBreakerHead;
import Attachments.PlowHeads.SaltShakerHead;
import Attachments.PlowHeads.ThrowHead;
import Attachments.Snowchain;
import Consumable.Consumable;
import Control.Player;
import Control.Shop;
import RoadComponents.Intersection;
import RoadComponents.Road;
import RoadComponents.RoadSection;
import Vehicles.Bus;
import Vehicles.Car;
import Vehicles.Snowplow;

public class TestSetup {

    // -------------------------------------------------------------------------
    // Busz mozgás
    // -------------------------------------------------------------------------

    public static class BusMovement {
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);
        public Bus bus = new Bus(i1, i2);
        public RoadSection busSection;
        public RoadSection nextSection;
        public Snowchain snowchain;
    }

    private static BusMovement createBusMovementBase() {
        BusMovement bm = new BusMovement();
        bm.i1.addRoad(bm.road);
        bm.i2.addRoad(bm.road);
        bm.busSection = bm.road.getDriveableRoadSection(bm.i2);
        if (bm.busSection != null) {
            bm.nextSection = bm.busSection.next;
            bm.bus.setCurrRoadSection(bm.busSection);
        }
        return bm;
    }

    public static BusMovement createBusMovementIntersection() {
        BusMovement bm = createBusMovementBase();
        bm.bus.setCurrIntersection(bm.i1);
        bm.i1.addVehicle(bm.bus);
        return bm;
    }

    public static BusMovement createBusMovementRoad() {
        return createBusMovementBase();
    }

    // -------------------------------------------------------------------------
    // Busz hólánccal
    // -------------------------------------------------------------------------

    public static class BusWithSnowchain {
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 1, 3);
        public Bus bus = new Bus(i1, i2);
        public Snowchain snowchain;
        public RoadSection busSection;
        public RoadSection nextSection;
    }

    public static BusWithSnowchain createBusWithSnowchainOnRoad() {
        BusWithSnowchain s = new BusWithSnowchain();
        s.snowchain = new Snowchain(10);
        s.bus.addSnowchain(s.snowchain);
        s.i1.addRoad(s.road);
        s.i2.addRoad(s.road);
        s.busSection = s.road.getDriveableRoadSection(s.i2);
        if (s.busSection != null) {
            s.nextSection = s.busSection.next;
            s.bus.setCurrRoadSection(s.busSection);
        }
        return s;
    }

    // -------------------------------------------------------------------------
    // Hólánc javítása
    // -------------------------------------------------------------------------

    public static class SnowchainFix {
        public Bus bus;
        public Snowchain snowchain;
    }

    public static SnowchainFix createSnowchainFix() {
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

    // -------------------------------------------------------------------------
    // Autó mozgás
    // -------------------------------------------------------------------------

    public static class CarMovement {
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);
        public Car car = new Car(i1, i2);
        public RoadSection carSection;
        public RoadSection nextSection;
    }

    private static CarMovement createCarMovementBase() {
        CarMovement cm = new CarMovement();
        cm.i1.addRoad(cm.road);
        cm.i2.addRoad(cm.road);
        cm.carSection = cm.road.getDriveableRoadSection(cm.i2);
        if (cm.carSection != null) {
            cm.nextSection = cm.carSection.next;
            cm.car.setCurrRoadSection(cm.carSection);
        }
        return cm;
    }

    public static CarMovement createCarMovementIntersection() {
        CarMovement cm = createCarMovementBase();
        cm.i1.addVehicle(cm.car);
        return cm;
    }

    public static CarMovement createCarMovementRoad() {
        return createCarMovementBase();
    }

    // -------------------------------------------------------------------------
    // Hókotró mozgás
    // -------------------------------------------------------------------------

    public static class SnowplowMovement {
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 2, 2);
        public Snowplow snowplow = new Snowplow(null);
        public RoadSection snowplowSection;
        public RoadSection nextSection;
    }

    public static SnowplowMovement createSnowplowMovementIntersection() {
        SnowplowMovement sm = new SnowplowMovement();
        sm.i1.addRoad(sm.road);
        sm.i2.addRoad(sm.road);
        sm.snowplow.setCurrIntersection(sm.i1);
        sm.i1.addVehicle(sm.snowplow);
        sm.snowplowSection = sm.road.getDriveableRoadSection(sm.i2);
        if (sm.snowplowSection != null) sm.nextSection = sm.snowplowSection.next;
        return sm;
    }

    public static SnowplowMovement createSnowplowMovementRoad() {
        SnowplowMovement sm = new SnowplowMovement();
        sm.i1.addRoad(sm.road);
        sm.i2.addRoad(sm.road);
        sm.snowplowSection = sm.road.getDriveableRoadSection(sm.i2);
        if (sm.snowplowSection != null) {
            sm.nextSection = sm.snowplowSection.next;
            sm.snowplow.setCurrRoadSection(sm.snowplowSection);
            if (sm.nextSection != null) sm.nextSection.snowIncrease(5);
        }
        return sm;
    }

    // -------------------------------------------------------------------------
    // Kotrófej használat
    // -------------------------------------------------------------------------

    public static class PlowHeadUsage {
        public RoadSection section = new RoadSection(null, 0);
        public RoadSection rightSection = new RoadSection(null, 1);
        public PlowHead head;
    }

    public static PlowHeadUsage createBroomHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new BroomHead();
        p.section.snowIncrease(5);
        p.section.right = p.rightSection;
        return p;
    }

    public static PlowHeadUsage createThrowHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new ThrowHead();
        p.section.snowIncrease(5);
        return p;
    }

    public static PlowHeadUsage createIceBreakerHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new IceBreakerHead();
        p.section.iceIncrease(5);
        return p;
    }

    public static PlowHeadUsage createSaltShakerUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        SaltShakerHead head = new SaltShakerHead();
        head.fillConsumable(10);
        p.head = head;
        return p;
    }

    public static PlowHeadUsage createDragonHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        DragonHead head = new DragonHead();
        head.fillConsumable(10);
        p.head = head;
        return p;
    }

    // -------------------------------------------------------------------------
    // Útszakasz frissítés
    // -------------------------------------------------------------------------

    public static class RoadSectionUpdate {
        public RoadSection section = new RoadSection(null, 0);
        public Consumable consumable;
    }

    public static RoadSectionUpdate createRoadSectionUpdate() {
        RoadSectionUpdate r = new RoadSectionUpdate();
        r.section.snowIncrease(10);
        r.section.iceIncrease(5);
        r.consumable = new Consumable(3, 2);
        r.section.addConsumable(r.consumable);
        return r;
    }

    // -------------------------------------------------------------------------
    // Vásárlás
    // -------------------------------------------------------------------------

    public static class PurchaseSetup {
        public Player player = new Player(3000);
        public Shop shop = new Shop();
        public Snowplow snowplow = new Snowplow();
        public Bus bus = new Bus();
    }

    public static PurchaseSetup createPurchaseSetup() {
        return new PurchaseSetup();
    }

    // -------------------------------------------------------------------------
    // Baleset
    // -------------------------------------------------------------------------

    public static class AccidentSetup {
        public Intersection i1 = new Intersection();
        public Intersection i2 = new Intersection();
        public Road road = new Road(i1, i2, Road.Way.ONEWAY, 1, 3);
        public Bus bus1 = new Bus(i1, i2);
        public Bus bus2 = new Bus(i1, i2);
        public RoadSection section;
    }

    public static AccidentSetup createAccidentSetup() {
        AccidentSetup a = new AccidentSetup();
        a.i1.addRoad(a.road);
        a.i2.addRoad(a.road);
        a.section = a.road.getDriveableRoadSection(a.i2);
        if (a.section != null) {
            a.section.setAccident(true);
            a.bus1.setCurrRoadSection(a.section.previous != null ? a.section.previous : a.section);
            a.bus2.setCurrRoadSection(a.section);
        }
        return a;
    }
}
