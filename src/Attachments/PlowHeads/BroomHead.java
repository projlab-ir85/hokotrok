package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class BroomHead extends PlowHead {
    public BroomHead() {}

    public void use(RoadSection rs){
        int snow = rs.getSnow();
        rs.snowReduce(snow);
        rs.right.snowIncrease(snow);
    }
}
