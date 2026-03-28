package Vehicles;

import Attachments.Snowchain;
import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Bus extends Vehicle{
    protected Snowchain snowchain;
    protected int lapsDone;
    protected int accidentTime;
    protected boolean finishedLap;
    protected Intersection start;
    protected Intersection end;
    protected Intersection next;

    public Bus(){}

    public Bus(Intersection start, Intersection end){
        this.start = start;
        this.end = end;
        this.currIntersection = start;
    }

    public void addSnowchain(Snowchain snowchain){
        this.snowchain = snowchain;
    }

    public void Step(){}

    public void Interact(RoadSection rs){}
}
