package Vehicles;

import Attachments.PlowHead;
import Attachments.PlowHeads.*;
import RoadComponents.Intersection;
import RoadComponents.RoadSection;

import java.util.ArrayList;
import java.util.List;

public class Snowplow extends Vehicle{
    protected List<PlowHead> plowHeads;
    protected PlowHead activePlowHead;
    protected Intersection start;

    public Snowplow(Intersection start) {
        this.start = start;
        currIntersection = start;
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        stuck = false;
        stuckTime = 0;
    }

    public Snowplow() {
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        stuck = false;
        stuckTime = 0;
    }

    public void addPlow(PlowHead plow) {
        plowHeads.add(plow);
    }

    public void Step(){
        currRoadSection.next.accept(this);
    }

    public void Interact(RoadSection rs){
        activePlowHead.use(rs);
    }
}
