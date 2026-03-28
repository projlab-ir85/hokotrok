package RoadComponents;

import Vehicles.Vehicle;

import java.util.List;

public class Intersection {
    protected int id;
    protected List<Road> roads;
    protected List<Vehicle> vehicles;

    public RoadSection roadSelection(Intersection destination){
        for(Road road : roads){
            if(road.startPoint.equals(destination) || road.endPoint.equals(destination)){
                return road.getDriveableRoadSection(destination);
            }
        }
        return null;
    }

    public void addRoad(Road road){
        if(road.endPoint.equals(this) || road.startPoint.equals(this)){
            roads.add(road);
        }
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }
}
