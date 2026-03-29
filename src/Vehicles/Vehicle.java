package Vehicles;

import RoadComponents.*;
import java.util.List;

public abstract class Vehicle implements Movable{
    protected boolean stuck;
    protected List<Intersection> junctions;
    protected RoadSection currRoadSection;
    protected Intersection currIntersection;
    protected int stuckTime;

    /**
     * Interakcióba lép az adott útszakasszal
     * @param rs
     * absztrakt metódus, leszármazott osztályok definiálják
     */
    public abstract void interact(RoadSection rs);

    /**
     * beállítja a jármú aktuális útszakaszát
     * @param roadSection
     */
    public void setCurrRoadSection(RoadSection roadSection){
        /* frissíti a jelenlegi útszakaszt az újra */
        currRoadSection = roadSection;
    }
}
