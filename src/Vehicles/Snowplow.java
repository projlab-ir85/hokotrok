package Vehicles;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

import java.util.List;

public class Snowplow extends Vehicle{
    protected List<PlowHead> plowHeads;
    protected PlowHead activePlowHead;

    public void Step(){
        currRoadSection.next.Accept(this);
    }

    public void Interact(RoadSection rs){
        activePlowHead.use(rs);
    }
}
