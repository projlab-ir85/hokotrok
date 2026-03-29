package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

public class SaltShakerHead extends PlowHead {
    public SaltShakerHead() {}

    private Consumable salt = new Consumable(60, 2);
    public void use(RoadSection roadsection){
        if(consumableAmountLeft>0) {
        roadsection.addConsumable(salt);
        consumableAmountLeft--;
        }
    }
}
