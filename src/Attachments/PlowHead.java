package Attachments;

import RoadComponents.RoadSection;

/** Kotrófej ősosztály */
public abstract class PlowHead extends Attachment{
    protected int snowClean;
    protected int iceClean;
    protected int consumableAmountLeft;
    /** Használ függvény, a jármű ezen a metóduson
     * keresztül szól a kiegészítőnek, hogy használja.
     */
        @Override
    public void use() {}

    /** Kotrófej megtöltése fogyóeszközzel
     * @param amount mennyit töltünk bele
     */
    public void fillConsumable(int amount) {
        consumableAmountLeft += amount;
    }

    /** Megmondja mennyi fogyóeszköz maradt
     * @return mennyi fogyóeszköz maradt még a kotrófejben
     */
    public int getConsumableAmountLeft() { return consumableAmountLeft; }
}
