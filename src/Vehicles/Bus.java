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
    protected boolean hasSnowchain;

    public Bus(Intersection start, Intersection end) {
        this.start = start;
        this.end = end;
        currIntersection = start;
        stuck = false;
        stuckTime = 0;
        lapsDone = 0;
        accidentTime = 0;
        finishedLap = false;
        hasSnowchain = false;
    }

    public Bus(){}

    public void addSnowchain(Snowchain snowchain){
        this.snowchain = snowchain;
        hasSnowchain = true;
    }

    public void step(){
        if(stuck) {
            stuckTime--;
            return;
        }
        currRoadSection.next.accept(this);
        if(hasSnowchain) {
            snowchain.use(currRoadSection);
        }
        
    }

    public void interact(RoadSection roadsection){
        roadsection.iceIncrease(1);
    }

    public boolean getHasSnowchain() { return hasSnowchain; }

    public Intersection getCurrIntersection() { return currIntersection; }

    public void setCurrIntersection(Intersection i) { currIntersection = i; }
}
