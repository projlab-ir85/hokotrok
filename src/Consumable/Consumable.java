package Consumable;

import RoadComponents.RoadSection;

public class Consumable {
    protected int timeToLive;
    protected int price;
    protected int strength;

    public Consumable(int ttl, int strength) {
        timeToLive = ttl;
        this.strength = strength;
    }

    public boolean effect(RoadSection roadsection){
        if(timeToLive > 0) {
            timeToLive--;
            roadsection.snowReduce(strength);
            roadsection.iceReduce(strength);
            return true;
        }
        return false;
    }

    public int getTimeToLive() { return timeToLive; }
}
