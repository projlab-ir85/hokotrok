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

    public RoadSection(Lane lane){
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
    }

    public void SnowReduce(int amount){}
    public void SnowIncrease(int amount){}

    public void IceReduce(int amount){}
    public void IceIncrease(int amount){}

    public void Update(){}

    public boolean Accept(Vehicle v){
        return true;
    }

    public void AddConsumable(Consumable c){}

}
