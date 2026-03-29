package Vehicles;

import RoadComponents.*;
import java.util.List;

public abstract class Vehicle implements Movable{
    protected boolean stuck;
    protected List<Intersection> junctions;
    protected RoadSection currRoadSection;
    protected Intersection currIntersection;
    protected int stuckTime;

    public abstract void interact(RoadSection rs);

    public void setCurrRoadSection(RoadSection roadSection){
        currRoadSection = roadSection;
    }

    public RoadSection getCurrRoadSection() { return currRoadSection; }

    public boolean isStuck() { return stuck; }

    public void setStuck(boolean value) {
        stuck = value;
        if(value) stuckTime = 3;
    }
}
