package Attachments.PlowHeads;

import Attachments.PlowHead;
import RoadComponents.RoadSection;

/**söprőfej megvalósítása*/
public class BroomHead extends PlowHead {
    /** Default konstruktor */
    public BroomHead() {}
    /** Kotrófej use függvény megvalósítása
    * @param roadsection az adott útszakasz ahol a havat takarítjuk
    */
    public void use(RoadSection roadsection){
        /**csökkentjük a hó mennyiségét*/
        int snow = roadsection.getSnow();
        roadsection.snowReduce(snow);
        /**ha van tőle jobbra sáv akkor áttúrja oda a havat*/
        if(roadsection.right != null){
            roadsection.right.snowIncrease(snow);
        }
    }
}
