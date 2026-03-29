package RoadComponents;

import Consumable.Consumable;
import Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RoadSection implements Updateable{
    protected int snowLevel;
    protected int iceLevel;

    protected boolean accidentHappened;
    protected int accidentTime;

    protected List<Consumable> consumables;
    protected List<Vehicle> vehicles;

    public RoadSection next;
    public RoadSection previous;
    public RoadSection left;
    public RoadSection right;

    protected Lane lane;
    protected int sublaneIndex;

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

    public void snowReduce(int amount){ snowLevel = Math.max(0, snowLevel - amount); }

    public void snowIncrease(int amount){ snowLevel += amount; }

    public void iceReduce(int amount){ iceLevel = Math.max(0, iceLevel - amount); }

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
