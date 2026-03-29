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

    public void effect(RoadSection roadsection){
        if(timeToLive>0) {
            timeToLive--;
            roadsection.snowReduce(strength);
            roadsection.iceReduce(strength);
        }else{
            //nem emlekszem mit beszeltunk de szol az utszakasznak hogy remove-olja?
        }
    }

}
