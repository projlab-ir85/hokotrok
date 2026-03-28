package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

public class ThrowHead extends PlowHead {
    public void use(RoadSection rs){
        rs.SnowReduce(rs.getSnow());
    }
}
