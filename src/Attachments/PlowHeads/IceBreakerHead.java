package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class IceBreakerHead extends PlowHead {
    public IceBreakerHead() {}

    public void use(RoadSection roadsection){
        int ice = roadsection.getIce();
        roadsection.iceReduce(ice);
        roadsection.snowIncrease(ice);
    }
}
