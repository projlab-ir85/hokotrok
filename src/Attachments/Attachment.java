package Attachments;

import RoadComponents.RoadSection;
import Vehicles.Vehicle;

public abstract class Attachment {
    protected int price;
    protected Vehicle vehicle;

    public abstract void use(RoadSection rs);
}
