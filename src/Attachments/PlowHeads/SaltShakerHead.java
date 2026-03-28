package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

public class SaltShakerHead extends PlowHead {
    private Consumable salt = new Consumable(60, 2);
    public void use(RoadSection rs){
        if(consumableAmountLeft>0) {
        rs.AddConsumable(salt);
        consumableAmountLeft--;
        }
    }
}
