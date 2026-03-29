package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class BroomHead extends PlowHead {
    public BroomHead() {}

    public void use(RoadSection roadsection){
        int snow = roadsection.getSnow();
        roadsection.snowReduce(snow);
        roadsection.right.snowIncrease(snow);
    }
}
