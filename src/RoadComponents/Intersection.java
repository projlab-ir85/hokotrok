package RoadComponents;

import Vehicles.Vehicle;

import java.util.List;

/**
 * Egy kereszteződést reprezentáló osztály a közúti hálózatban.
 * Ez az osztály köti össze a különböző utakat, és nyilvántartja a kereszteződésen áthaladó vagy ott tartózkodó járműveket.
 */
public class Intersection {
    /**
     * A kereszteződés egyedi azonosítója.
     */
    protected String id;
    /**
     * A kereszteződéshez közvetlenül csatlakozó utak listája.
     */
    protected List<Road> roads;
    /**
     * Egy lista a kereszteződésben jelenleg tartózkodó járművekről.
     */
    protected List<Vehicle> vehicles;

    /**
     * Az Intersection osztály alapértelmezett konstruktora.
     * Létrehozáskor inicializálja az utak és a járművek üres listáit.
     */
    public Intersection(String id) {
        this.id = id;
        roads = new java.util.ArrayList<>();
        vehicles = new java.util.ArrayList<>();
    }

    public RoadSection roadSelection(Intersection destination){
        for(Road road : roads){
            if((road.startPoint.equals(this) && road.endPoint.equals(destination)) ||
               (road.startPoint.equals(destination) && road.endPoint.equals(this))){
                return road.getDriveableRoadSection(destination);
            }
        }
        return null;
    }

    /**
     * Hozzáad egy új utat a kereszteződéshez.
     * A hozzáadás előtt ellenőrzi, hogy az átadott út kezdőpontja vagy végpontja valóban ez a kereszteződés-e. Csak akkor fűzi hozzá a listához, ha a kapcsolat fennáll.
     * @param road A hozzáadni kívánt út objektuma.
     */
    public void addRoad(Road road){
        if(road.getStartIntersectionId().equals(this.id)){
            roads.add(road);
        }
    }

    /**
     * Hozzáad egy járművet a kereszteződéshez.
     * Hozzáadja a megadott járművet a kereszteződében tartózkodó járművek listájához.
     * @param vehicle A kereszteződésbe belépő jármű objektuma.
     */
    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public void tick(){
        for(Vehicle v : vehicles){
            v.step();
        }
    }

    public String getId(){return id;}

    public List<Road> getRoads(){return roads;}

    public List<Vehicle> getVehicles(){return vehicles;}
}
