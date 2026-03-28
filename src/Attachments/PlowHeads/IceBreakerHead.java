package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class IceBreakerHead extends PlowHead {
    public IceBreakerHead() {}

    public void use(RoadSection rs){
        int ice = rs.getIce();
        rs.iceReduce(ice);
        rs.snowIncrease(ice);
    }
}
