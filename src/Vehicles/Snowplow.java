package Vehicles;

import Attachments.PlowHead;
import Attachments.PlowHeads.*;
import RoadComponents.RoadSection;

import java.util.List;

public class Snowplow extends Vehicle{
    protected List<PlowHead> plowHeads;
    protected PlowHead activePlowHead;

    public Snowplow() {
        BroomHead bh = new BroomHead();
        IceBreakerHead ih = new IceBreakerHead();
        plowHeads.add(bh);
        plowHeads.add(ih);
    }

    public void addPlow(PlowHead plow) {
        plowHeads.add(plow);
    }

    public void Step(){
        currRoadSection.next.Accept(this);
    }

    public void Interact(RoadSection rs){
        activePlowHead.use(rs);
    }
}
