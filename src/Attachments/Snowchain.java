package Attachments;

import RoadComponents.RoadSection;

/** Hólánc megvalósítása */
public class Snowchain extends Attachment {
    protected int timeToLive;
    private int initialTimeToLive;

    /** Konstruktor amiben bele lehet állítani mennyi ideig él a hólánc
     * @param timeToLive hólánc élettartama
     */
    public Snowchain(int timeToLive){
        this.timeToLive = timeToLive;
        this.initialTimeToLive = timeToLive;
    }
    /** Használ függvény megvalósítása, a hólánc élettartama csökken.
     */
    public void use(){
    
    /** Hólánc megjavítása, visszaállítja a hólánc élattartamát
     */
    public void fix(){
        timeToLive = initialTimeToLive;
    }
    /** Megmondja a hólánc maradék élettartamát
     * @return mennyi ideig él még a hólánc
     */
    public int getTimeToLive(){return timeToLive;}
}
