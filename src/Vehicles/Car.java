package Vehicles;

import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Car extends Vehicle{
    protected Intersection start;
    protected Intersection end;
    protected int stuckTime;

    public void Step(){
        if(stuck) {
            stuckTime--;
            return;
        }
        currRoadSection.next.Accept(this);
    }

    public void Interact(RoadSection rs){
        rs.IceIncrease(1);
    }
}
