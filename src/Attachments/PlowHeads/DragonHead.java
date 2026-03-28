package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

public class DragonHead extends PlowHead {
    private Consumable kerosene = new Consumable(1, 100);
    
    public void use(RoadSection rs){
        if(consumableAmountLeft>0) {
        rs.AddConsumable(kerosene);
        consumableAmountLeft--;
        }
    }
}
