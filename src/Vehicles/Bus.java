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
        stuck = false;
        stuckTime = 0;
        lapsDone = 0;
        accidentTime = 0;
        finishedLap = false;
        hasSnowchain = false;
    }

    public void addSnowchain(Snowchain snowchain){
        this.snowchain = snowchain;
        hasSnowchain = true;
    }

    public void Step(){
        if(stuck) {
            stuckTime--;
            return;
        }
        currRoadSection.Accept(this);
        if(hasSnowchain) {
            snowchain.use(currRoadSection);
        }
        
    }
    public void Interact(RoadSection rs){
        rs.IceIncrease(1);
    }
}
