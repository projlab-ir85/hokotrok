package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class ThrowHead extends PlowHead {
    public ThrowHead() {}

    public void use(RoadSection roadsection){
        roadsection.snowReduce(roadsection.getSnow());
    }
}
