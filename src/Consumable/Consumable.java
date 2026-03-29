package Consumable;

import RoadComponents.RoadSection;

/**Fogyóeszköz megvalósítása */
public class Consumable {
    protected int timeToLive;
    protected int price;
    protected int strength;
    /** Konstruktor amiben az élettartamot és erősséget állítunk be
     * @param ttl mennyi ideig tart
     * @param strength másodpercenként mennyi százalékot csökkent
     */
    public Consumable(int ttl, int strength) {
        timeToLive = ttl;
        this.strength = strength;
    }

    /** Fogyóeszköz hatását fejti ki az adott útszakaszon, egyúttal az élettartamát csökkenti.
     * @param roadsection az adott útszakasz ahol a hatását kifejti a fogyóeszköz 
     * @return true ha van az útszakaszon fogyóeszköz
     * @return false ha nincs az útszakaszon semmi
     */
    public boolean effect(RoadSection roadsection){
        if(timeToLive > 0) {
            timeToLive--;
            roadsection.snowReduce(strength);
            roadsection.iceReduce(strength);
            return true;
        }
        return false;
    }
    /** Visszaadja mennyi élettartama van még a fogyóeszköznek
     * @return a fogyóeszköz hátralévő élettartama
     */
    public int getTimeToLive() { return timeToLive; }
}
