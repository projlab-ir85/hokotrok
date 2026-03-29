package Attachments;

import RoadComponents.RoadSection;

/** Hólánc megvalósítása */
public class Snowchain extends Attachment {
    protected int timeToLive;
    /** Konstruktor amiben bele lehet állítani mennyi ideig él a hólánc
     * @param timeToLive hólánc élettartama
     */
    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
    }
    /** Használ függvény megvalósítása, a hólánc élettartama csökken.
     * @param roadsection itt most nincs használva
     */
    public void use(RoadSection roadsection){
        timeToLive--;
    }
    /** Hólánc megjavítása, visszaállítja a hólánc élattartamát
     */
    public void fix(){
        timeToLive = 30;
    }
    /** Megmondja a hólánc maradék élettartamát
     * @return mennyi ideig él még a hólánc
     */
    public int getTimeToLive(){return timeToLive;}
}
