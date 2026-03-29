package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

/**Jégtörő fej megvalósítása*/
public class IceBreakerHead extends PlowHead {
    /** Default konstruktor */
    public IceBreakerHead() {}
    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a jeget takarítjuk
    */
    public void use(RoadSection roadsection){
        /**Megszüntetjük a jeget az útról viszont a hó mennyiségét növeljük */
        int ice = roadsection.getIce();
        roadsection.iceReduce(ice);
        roadsection.snowIncrease(ice);
    }
}
