package Attachments.PlowHeads;

import Attachments.PlowHead;
import Consumable.Consumable;
import RoadComponents.RoadSection;

/**Jégtörő fej megvalósítása*/
public class RockHead extends PlowHead {
    /** Default konstruktor */
    public RockHead() {}
    private Consumable rock = new Consumable(-1, 0);
    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a havat és jeget takarítjuk
    */
    public void use(RoadSection roadsection){
        /** Ha van még fogyóeszközünk akkor hozzáadjuk az útszakaszhoz
         * és csökkentjük a fogyóeszközök mennyiségét
         */
        if(consumableAmountLeft>0) {
        roadsection.addConsumable(rock);
        consumableAmountLeft--;
        }
    }
}