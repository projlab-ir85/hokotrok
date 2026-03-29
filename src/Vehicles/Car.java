package Vehicles;

import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Car extends Vehicle{
    protected Intersection start;
    protected Intersection end;

    /**
     * Új Car példány létrehozása
     * @param start kezdőpont
     * @param end   végpont
     * inicializálja az útvonalat, illetve beállítja az alapállapotot
     * alapállapotban nincsen elakadva
     */
    public Car(Intersection start, Intersection end) {
        this.start = start;
        this.end = end;
        stuck = false;
        stuckTime = 0;
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
     * @param rs
     * 1 egységgel növeli a jégszintet az autó a haladása során
     */
    public void interact(RoadSection rs){
        rs.iceIncrease(1);
    }
}
