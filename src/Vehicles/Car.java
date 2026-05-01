package Vehicles;

import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Car extends Vehicle{
    /**
     * Új Car példány létrehozása
     * @param start kezdőpont
     * @param end   végpont
     * inicializálja az útvonalat, illetve beállítja az alapállapotot
     * alapállapotban nincsen elakadva
     */
    public Car(String id, Intersection start, Intersection end) {
        this.id = id;
        this.start = start;
        this.end = end;
        stuck = false;
        stuckTime = 0;
        nextIntersectionIndex = 0;
    }

    /**
     * Autónak lép egyet
     */
    public void step(){
        /* ha el van akadva, akkor csökkenti az elakadási időt, és véget ér a lépés */
        if(stuck) {
            stuckTime--;
            return;
        }
        /* amennyiben nincsen elakadva akkor átlép a köbetkező útszakaszra */
        currRoadSection.next.accept(this);
    }

    /**
     * Interakcióba lép az adott útszakasszal
     * @param rs útszakasz, amivel interaktál
     * 1 egységgel növeli a jégszintet az autó a haladása során
     */
    public void interact(RoadSection rs){
        rs.iceIncrease(1);
    }
}
