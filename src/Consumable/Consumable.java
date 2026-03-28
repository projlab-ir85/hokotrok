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

    public void effect(RoadSection rs){
        if(timeToLive>0) {
            timeToLive--;
            rs.SnowReduce(strength);
            rs.IceReduce(strength);
        }
    }

}
