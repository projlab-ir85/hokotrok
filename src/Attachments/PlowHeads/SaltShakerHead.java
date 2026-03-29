package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

/**Sószóró fej megvalósítása*/
public class SaltShakerHead extends PlowHead {
    /** Default konstruktor */
    public SaltShakerHead() {}
    /**létrehozunk egy sót*/
    private Consumable salt = new Consumable(60, 2);
    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a havat és jeget takarítjuk
    */
    public void use(RoadSection roadsection){
        /** Ha van még fogyóeszközünk akkor hozzáadjuk az útszakaszhoz
         * és csökkentjük a fogyóeszközök mennyiségét
         */
        if(consumableAmountLeft>0) {
        roadsection.addConsumable(salt);
        consumableAmountLeft--;
        }
    }
}
