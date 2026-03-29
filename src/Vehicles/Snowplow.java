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
        activePlowHead = plowHeads.get(0);
        stuck = false;
        stuckTime = 0;
    }

    public Snowplow() {
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        activePlowHead = plowHeads.get(0);
        stuck = false;
        stuckTime = 0;
    }

    public void addPlow(PlowHead plow) {
        plowHeads.add(plow);
    }

    public void step(){
        currRoadSection.next.accept(this);
    }

    public void interact(RoadSection rs){
        activePlowHead.use(rs);
    }

    public void fillActiveHead(int amount) {
        if(activePlowHead != null) activePlowHead.fillConsumable(amount);
    }

    public int getPlowHeadCount() { return plowHeads.size(); }

    public PlowHead getActivePlowHead() { return activePlowHead; }

    public void setActivePlowHead(PlowHead head) { activePlowHead = head; }

    public Intersection getCurrIntersection() { return currIntersection; }

    public void setCurrIntersection(Intersection i) { currIntersection = i; }
}
