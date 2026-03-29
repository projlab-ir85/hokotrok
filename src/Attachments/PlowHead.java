package Attachments;

import RoadComponents.RoadSection;

public abstract class PlowHead extends Attachment{
    protected int snowClean;
    protected int iceClean;
    protected int consumableAmountLeft;

    public abstract void use(RoadSection roadsection);

    public void fillConsumable(int amount) {
        consumableAmountLeft += amount;
    }

    public int getConsumableAmountLeft() { return consumableAmountLeft; }
}
