package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

/**Hányófej megvalósítása*/
public class ThrowHead extends PlowHead {
    /** Default konstruktor */
    public ThrowHead() {}

    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a havat takarítjuk
    */
    public void use(RoadSection roadsection){
        /** Csökkentjük a hó mennyiségét */
        roadsection.snowReduce(roadsection.getSnow());
    }
}
