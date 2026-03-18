package Vehicles;

import RoadComponents.*;
import java.util.List;

public class Vehicle implements Movable{
    protected boolean stuck;
    protected List<Intersection> junctions;
    protected RoadSection currRoadSection;

}
