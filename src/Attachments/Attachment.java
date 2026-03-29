package Attachments;

import Vehicles.Vehicle;

public abstract class Attachment {
    protected int price;
    protected Vehicle vehicle;

    public abstract void use();
}
