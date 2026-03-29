package Attachments;

import Vehicles.Vehicle;

/**Kiegészítő ősosztály deklarálása */
public abstract class Attachment {
    protected int price;
    protected Vehicle vehicle;
    /** Használ függvény, a jármű ezen a metóduson
     *  keresztül szól a kiegészítőnek, hogy használja.
     */
    public abstract void use();
}
