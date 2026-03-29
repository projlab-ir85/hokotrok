package Attachments;

import RoadComponents.RoadSection;
import Vehicles.Vehicle;

/**Kiegészítő ősosztály deklarálása */
public abstract class Attachment {
    protected int price;
    protected Vehicle vehicle;
    /** Használ függvény, a jármű ezen a metóduson keresztül szól a kiegészítőnek, hogy használja.
     * @param roadsection az az útszakasz amin használjuk az adott kiegészítőt
     */
    public abstract void use(RoadSection roadsection);
}
