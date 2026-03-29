package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

/**sárkányfej megvalósítása*/
public class DragonHead extends PlowHead {
    /** Default konstruktor */
    public DragonHead() {}
    /**létrehozunk egy kerozint*/
    private Consumable kerosene = new Consumable(1, 100);
    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a havat takarítjuk
    */
    public void use(RoadSection roadsection){
        /** Ha van még fogyóeszközünk akkor hozzáadjuk az útszakaszhoz
         * és csökkentjük a fogyóeszközök mennyiségét
         */
        if(consumableAmountLeft>0) {
        roadsection.addConsumable(kerosene);
        consumableAmountLeft--;
        }
    }
}
