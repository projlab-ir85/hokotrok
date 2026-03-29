package RoadComponents;

import Consumable.Consumable;
import Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy alsáv legkisebb fizikai egységét, egy útszakaszt reprezentáló osztály.
 * Ez az osztály felel az időjárási elemek (hó, jég) és a balesetek nyilvántartásáért, a rajta tartózkodó járművek és fogyóeszközök kezeléséért, valamint 
 * referenciákat tartalmaz a szomszédos útszakaszokra.
 */
public class RoadSection implements Updateable{
    /** Az útszakaszon lévő hó mennyisége. */
    protected int snowLevel;
    /** Az útszakaszon lévő jég mennyisége. */
    protected int iceLevel;

    /** Jelzi, hogy történt-e baleset ezen az útszakaszon. */
    protected boolean accidentHappened;
    /** A baleset bekövetkeztének ideje. */
    protected int accidentTime;

    /** Az útszakaszon elhelyezett fogyóeszközök (pl. só) listája. */
    protected List<Consumable> consumables;
    /** Az útszakaszon aktuálisan tartózkodó járművek listája. */
    protected List<Vehicle> vehicles;

    /** A haladási irány szerinti következő útszakasz. */
    public RoadSection next;
    /** A haladási irány szerinti előző útszakasz. */
    public RoadSection previous;
    /** A bal oldali szomszédos alsáv. */
    public RoadSection left;
    /** A jobb oldali szomszédos alsáv. */
    public RoadSection right;

    /** A sáv, amelyhez ez az útszakasz tartozik. */
    protected Lane lane;
    /** Az alsáv indexe, amelyben ez az útszakasz elhelyezkedik. */
    protected int sublaneIndex;

    /**
     * Útszakasz konstruktora.
     * Alaphelyzetbe állítja az értékeket, és létrehozza az üres listákat.
     * @param lane A sáv, amibe beletartozik.
     * @param sublaneIndex Az alsáv indexe, ahol elhelyezkedik.
     */
    public RoadSection(Lane lane, int sublaneIndex){
        snowLevel = 0;
        iceLevel = 0;
        accidentHappened = false;
        accidentTime = 0;

        consumables = new ArrayList<>();
        vehicles = new ArrayList<>();

        next = null;
        previous = null;
        left = null;
        right = null;

        this.lane = lane;
        this.sublaneIndex = sublaneIndex;
    }

    /**
     * Csökkenti a hó szintjét a megadott értékkel. 
     * A hó szintje nem mehet 0 alá.
     * @param amount A csökkentés mértéke.
     */
    public void snowReduce(int amount){ snowLevel = Math.max(0, snowLevel - amount); }

    /**
     * Növeli a hó szintjét a megadott értékkel.
     * @param amount A növelés mértéke.
     */
    public void snowIncrease(int amount){ snowLevel += amount; }

    /**
     * Csökkenti a jég szintjét a megadott értékkel.
     * A jég szintje nem mehet 0 alá.
     * @param amount A csökkentés mértéke.
     */
    public void iceReduce(int amount){ iceLevel = Math.max(0, iceLevel - amount); }

    /**
     * Növeli a jég szintjét a megadott értékkel.
     * @param amount A növelés mértéke.
     */
    public void iceIncrease(int amount){ iceLevel += amount; }

    public void update(){
        java.util.Iterator<Consumable> it = consumables.iterator();
        while(it.hasNext()){
            Consumable c = it.next();
            boolean alive = c.effect(this);
            if(!alive) it.remove();
        }
        snowIncrease(1);
    }

    public boolean accept(Vehicle v){
        if(accidentHappened){
            v.setStuck(true);
            return false;
        }
        vehicles.add(v);
        v.setCurrRoadSection(this);
        v.interact(this);
        return true;
    }

    public void addConsumable(Consumable c){ consumables.add(c); }

    public int getSnow() { return snowLevel; }

    public int getIce() { return iceLevel; }

    public int getConsumableCount() { return consumables.size(); }

    public void setAccident(boolean happened){ accidentHappened = happened; }
}
