package Vehicles;

import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Car extends Vehicle{
    protected Intersection start;
    protected Intersection end;

    public Car(Intersection start, Intersection end) {
        this.start = start;
        this.end = end;
        stuck = false;
        stuckTime = 0;
    }

    public void step(){
        if(stuck) {
            stuckTime--;
            return;
        }
        currRoadSection.next.accept(this);
    }

    public void interact(RoadSection rs){
        rs.iceIncrease(1);
    }
}
