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
}
