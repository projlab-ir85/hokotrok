package RoadComponents;

import java.util.List;

public class Intersection {
    protected int id;
    protected List<Road> roads;

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
}
