package RoadComponents;

import Consumable.Consumable;
import Vehicles.Vehicle;

import java.util.List;

public class RoadSection implements Updateable{
    protected int snowLevel;
    protected int iceLevel;

    protected boolean accidentHappened;
    protected int accidentTime;

    protected List<Consumable> consumables;
    protected List<Vehicle> vehicles;

    protected RoadSection next;
    protected RoadSection previous;
    protected RoadSection left;
    protected RoadSection right;

    protected Lane lane;

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
