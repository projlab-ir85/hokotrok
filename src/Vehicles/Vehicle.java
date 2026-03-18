package Vehicles;

import RoadComponents.*;
import java.util.List;

public abstract class Vehicle implements Movable{
    protected boolean stuck;
    protected List<Intersection> junctions;
    protected RoadSection currRoadSection;

    public abstract void Interact(RoadSection rs);
}
