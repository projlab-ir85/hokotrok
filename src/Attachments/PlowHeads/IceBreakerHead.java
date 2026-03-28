package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class IceBreakerHead extends PlowHead {
    public IceBreakerHead() {}

    public void use(RoadSection rs){
        rs.IceReduce(rs.getIce());
        rs.SnowIncrease(rs.getIce());
    }
}
