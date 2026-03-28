package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class BroomHead extends PlowHead {
    public BroomHead() {}

    public void use(RoadSection rs){
        rs.SnowReduce(rs.getSnow());
        rs.right.SnowIncrease(rs.getSnow());
    }
}
