package Vehicles;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

import java.util.List;

public class Snowplow extends Vehicle{
    protected List<PlowHead> plowHeads;
    protected PlowHead activePlowHead;

    public void Step(){}

    public void Interact(RoadSection rs){}
}
