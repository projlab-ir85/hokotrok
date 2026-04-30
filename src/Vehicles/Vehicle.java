package Vehicles;

import RoadComponents.*;
import java.util.List;

public abstract class Vehicle implements Movable{
    protected boolean stuck;
    protected List<Intersection> junctions;
    protected RoadSection currRoadSection;
    protected Intersection currIntersection;
    protected int stuckTime;
    protected String id;
    protected Intersection start;
    protected Intersection end;

    public String getId(){return id;}

    public Intersection getStartIntersection(){return start;}

    public Intersection getEndIntersection(){return end;}

    /**
     * Interakcióba lép az adott útszakasszal
     * @param rs útszakasz
     * absztrakt metódus, leszármazott osztályok definiálják
     */
    public abstract void interact(RoadSection rs);

    /**
     * beállítja a jármú aktuális útszakaszát
     * @param roadSection útszakasz
     */
    public void setCurrRoadSection(RoadSection roadSection){
        /* frissíti a jelenlegi útszakaszt az újra */
        currRoadSection = roadSection;
    }

    public RoadSection getCurrRoadSection() { return currRoadSection; }

    public Intersection getCurrIntersection(){return currIntersection;}

    public boolean isStuck() { return stuck; }

    public void setStuck(boolean value) {
        stuck = value;
        if(value) stuckTime = 3;
    }

    public List<Intersection> getJunctions(){return junctions;}

    public void setRoute(List<Intersection> route){
        junctions = route;
    }
}
