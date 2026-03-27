package Consumable;

import RoadComponents.RoadSection;

public class Consumable {
    protected int timeToLive;
    protected int price;
    protected int strength;

    public boolean Effect(RoadSection rs){return true;};
}
