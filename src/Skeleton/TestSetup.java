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

    /**
     * Adatstruktúra a busz mozgásával kapcsolatos tesztekhez.
     * Tartalmaz két kereszteződést, egy utat, egy buszt,
     * valamint az aktuális és következő útszakasz referenciákat.
     */
    public static class BusMovement {
        public Intersection i1 = new Intersection("a");
        public Intersection i2 = new Intersection("b");
        public Road road = new Road("ab",i1, i2, 2, 2, Road.Way.ONEWAY, 0, 0, 0, Road.Type.FOUT);
        public Bus bus = new Bus(i1, i2);
        public RoadSection busSection;
        public RoadSection nextSection;
        public Snowchain snowchain;
    }

    /**
     * Alap busz mozgás setup: létrehozza az úthálózatot és a buszt,
     * és beállítja a busz kezdeti pozícióját az első járható útszakaszra.
     */
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

    /**
     * Teszt1 setup: a busz a kereszteződésnél áll, készen a kereszteződés kezelésére.
     * A busz az i1 kereszteződéshez van rendelve.
     */
    public static BusMovement createBusMovementIntersection() {
        BusMovement bm = createBusMovementBase();
        bm.bus.setCurrIntersection(bm.i1);
        bm.i1.addVehicle(bm.bus);
        return bm;
    }

    /**
     * Teszt2 és Teszt3 setup: a busz úton áll, következő útszakasz is elérhető.
     */
    public static BusMovement createBusMovementRoad() {
        return createBusMovementBase();
    }

    // -------------------------------------------------------------------------
    // Busz hólánccal
    // -------------------------------------------------------------------------

    /**
     * Adatstruktúra a hóláncos busz tesztjéhez.
     * Tartalmaz egy buszt egy rá felszerelt hólánccal, valamint az aktuális útszakasz referenciát.
     */
    public static class BusWithSnowchain {
        public Intersection i1 = new Intersection("a");
        public Intersection i2 = new Intersection("b");
        public Road road = new Road("ab",i1, i2, 1, 3, Road.Way.ONEWAY,0,0,0, Road.Type.FOUT);
        public Bus bus = new Bus(i1, i2);
        public Snowchain snowchain;
        public RoadSection busSection;
        public RoadSection nextSection;
    }

    /**
     * Teszt4 setup: busz hólánccal felszerelve, útszakaszon állva.
     * A hólánc élettartama 10, a busz az első járható útszakaszon van.
     */
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

    /**
     * Adatstruktúra a hólánc javítás tesztjéhez.
     * Tartalmaz egy buszt és egy hóláncot.
     */
    public static class SnowchainFix {
        public Bus bus;
        public Snowchain snowchain;
    }

    /**
     * Teszt5 setup: busz 999-es élettartamú hólánccal.
     * A fix() metódus helyességét ellenőrzi, visszaállítja-e az eredeti értéket.
     */
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

    /**
     * Adatstruktúra az autó mozgásával kapcsolatos tesztekhez.
     * Tartalmaz két kereszteződést, egy utat, egy autót,
     * valamint az aktuális és következő útszakasz referenciákat.
     */
    public static class CarMovement {
        public Intersection i1 = new Intersection("a");
        public Intersection i2 = new Intersection("b");
        public Road road = new Road("ab",i1, i2, 2, 2, Road.Way.ONEWAY, 0,0,0, Road.Type.FOUT);
        public Car car = new Car("c",i1, i2);
        public RoadSection carSection;
        public RoadSection nextSection;
    }

    /**
     * Alap autó mozgás setup: létrehozza az úthálózatot és az autót,
     * és beállítja az autó kezdeti pozícióját az első járható útszakaszra.
     */
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

    /**
     * Teszt6 setup: az autó kereszteződésnél áll, készen a kereszteződés kezelésére.
     */
    public static CarMovement createCarMovementIntersection() {
        CarMovement cm = createCarMovementBase();
        cm.i1.addVehicle(cm.car);
        return cm;
    }

    /**
     * Teszt7 és Teszt8 setup: az autó az úton áll, a következő útszakasz is elérhető.
     */
    public static CarMovement createCarMovementRoad() {
        return createCarMovementBase();
    }

    // -------------------------------------------------------------------------
    // Hókotró mozgás
    // -------------------------------------------------------------------------

    /**
     * Adatstruktúra a hókotró mozgásával kapcsolatos tesztekhez.
     * Tartalmaz két kereszteződést, egy utat, egy hókotrót,
     * valamint az aktuális és következő útszakasz referenciákat.
     */
    public static class SnowplowMovement {
        public Intersection i1 = new Intersection("a");
        public Intersection i2 = new Intersection("b");
        public Road road = new Road("ab",i1, i2, 2, 2, Road.Way.ONEWAY,0,0,0, Road.Type.FOUT);
        public Snowplow snowplow = new Snowplow(null);
        public RoadSection snowplowSection;
        public RoadSection nextSection;
    }

    /**
     * Teszt9 setup: a hókotró kereszteződésnél áll, készen a kereszteződés kezelésére.
     */
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

    /**
     * Teszt10 és Teszt11 setup: a hókotró az úton áll, a következő útszakaszon 5 egység hó van.
     * Az aktív söprőfej elvégzi a takarítást lépéskor.
     */
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

    /**
     * Adatstruktúra a kotrófej használati tesztekhez.
     * Tartalmaz egy útszakaszt, egy szomszédos (jobb) útszakaszt, és egy kotrófejet.
     */
    public static class PlowHeadUsage {
        public RoadSection section = new RoadSection("a",null, 0,0,0,0, Road.Type.FOUT);
        public RoadSection rightSection = new RoadSection("b",null, 1,0,0,0, Road.Type.FOUT);
        public PlowHead head;
    }

    /**
     * Teszt12 setup: söprőfej teszthez.
     * Az útszakaszon 5 egység hó van, a jobb szomszéd üres, a söprőfej ide tolja a havat.
     */
    public static PlowHeadUsage createBroomHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new BroomHead();
        p.section.snowIncrease(5);
        p.section.right = p.rightSection;
        return p;
    }

    /**
     * Teszt13 setup: hányófej teszthez.
     * Az útszakaszon 5 egység hó van, a hányófej egyszerűen eltávolítja.
     */
    public static PlowHeadUsage createThrowHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new ThrowHead();
        p.section.snowIncrease(5);
        return p;
    }

    /**
     * Teszt14 setup: jégtörő fej teszthez.
     * Az útszakaszon 5 egység jég van, a jégtörő nullára csökkenti.
     */
    public static PlowHeadUsage createIceBreakerHeadUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        p.head = new IceBreakerHead();
        p.section.iceIncrease(5);
        return p;
    }

    /**
     * Teszt15 setup: sószóró fej teszthez.
     * A sószóró 10 egységnyi sókészlettel van feltöltve.
     */
    public static PlowHeadUsage createSaltShakerUsage() {
        PlowHeadUsage p = new PlowHeadUsage();
        SaltShakerHead head = new SaltShakerHead();
        head.fillConsumable(10);
        p.head = head;
        return p;
    }

    /**
     * Teszt16 setup: sárkányfej teszthez.
     * A sárkányfej 10 egységnyi kerozinnal van feltöltve.
     */
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

    /**
     * Adatstruktúra az útszakasz frissítés tesztjéhez.
     * Tartalmaz egy útszakaszt és egy rajta lévő fogyóeszközt.
     */
    public static class RoadSectionUpdate {
        public RoadSection section = new RoadSection("a",null, 0,0,0,0, Road.Type.FOUT);
        public Consumable consumable;
    }

    /**
     * Teszt17 setup: útszakasz frissítés teszthez.
     * Az útszakaszon 10 egység hó, 5 egység jég és egy 3 fordulóig élő fogyóeszköz van.
     */
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

    /**
     * Adatstruktúra a vásárlási tesztekhez (Teszt18–22).
     * Tartalmaz egy 3000 egyenlegű játékost, egy boltot, egy hókotrót és egy buszt.
     */
    public static class PurchaseSetup {
        public Player player = new Player(3000);
        public Shop shop = new Shop();
        public Snowplow snowplow = new Snowplow();
        public Bus bus = new Bus();
    }

    /**
     * Teszt18–22 közös setup: játékos 3000 egyenleggel, üres bolt, hókotró és busz készen.
     */
    public static PurchaseSetup createPurchaseSetup() {
        return new PurchaseSetup();
    }

    // -------------------------------------------------------------------------
    // Baleset
    // -------------------------------------------------------------------------

    /**
     * Adatstruktúra a baleset tesztjéhez.
     * Tartalmaz két buszt ugyanazon az úton, balesetes útszakasszal.
     */
    public static class AccidentSetup {
        public Intersection i1 = new Intersection("a");
        public Intersection i2 = new Intersection("b");
        public Road road = new Road("ab",i1, i2, 1, 3, Road.Way.ONEWAY, 0,0,0, Road.Type.FOUT);
        public Bus bus1 = new Bus(i1, i2);
        public Bus bus2 = new Bus(i1, i2);
        public RoadSection section;
    }

    /**
     * Teszt23 setup: két busz ugyanazon az útszakaszon, az útszakasz balesetre van állítva.
     * Ha jeges az út és mindkét busz ott van, mindkettő elakad.
     */
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
