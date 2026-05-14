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
        /**csökkentjük a hó mennyiségét és zúzottkő*/
        int snow = roadsection.getSnow();
        roadsection.snowReduce(snow);
        roadsection.rockReduce(roadsection.getRock());
        /**ha van tőle jobbra sáv akkor áttúrja oda a havat és a zúzottkövet*/
        if(roadsection.right != null){
            roadsection.right.snowIncrease(snow);
            roadsection.rockReduce(roadsection.getRock());
        }
    }
}
