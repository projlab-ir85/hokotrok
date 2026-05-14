package Vehicles.GPS;

import RoadComponents.Intersection;
import RoadComponents.Road;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BFS {
    Intersection junction;
    List<BfsData> table;

    public BFS(Intersection junction){
        this.junction = junction;
        table = new ArrayList<>();
    }

    public String getJunctionId() {return junction.getId();}

    public void createTable(List<Intersection> junctions, List<Road> roads){
        if(!table.isEmpty()) return;

        tableInit(junctions);

        algorithm(junctions, roads);
    }

    private void tableInit(List<Intersection> junctions){
        //First it creates the table where it will store the bfsData objects (distance and previous junction for every junction)
        //The first junction is the starting point (distance: 0, no previous)
        table.add(new BfsData(junction.getId(), 0, "*"));

        for(Intersection currI : junctions){
            if(!currI.getId().equals(junction.getId())){ //The starting point of the bfs is already in the table
                table.add(new BfsData(currI.getId(), -1, "*"));
            }
        }
    }

    private void algorithm(List<Intersection> junctions, List<Road> allRoads){
        //toBeVisited list contains which junctions are to be visited by the algorithm
        //The neighbours of these junctions are added to the list
        //Active junction is the currently visitied one

        Intersection active;
        List<Intersection> toBeVisited = new ArrayList<>();
        toBeVisited.add(junction);

        while(!toBeVisited.isEmpty()){
            active = toBeVisited.remove(0);
            String activeId = active.getId();
            BfsData activeTableData = table.stream().filter(data -> data.junctionId.equals(activeId)).findFirst().orElse(null);
            for(Road r : allRoads){
                if(r.getStartIntersectionId().equals(activeId) || r.getEndIntersectionId().equals(activeId)){
                    String rId = r.getStartIntersectionId().equals(active.getId()) ? r.getEndIntersectionId() : r.getStartIntersectionId();
                    BfsData currentJunction = table.stream().filter(data -> data.junctionId.equals(rId)).findFirst().orElse(null);
                    if(currentJunction.distance == -1){
                        toBeVisited.add(junctions.stream().filter(j -> j.getId().equals(currentJunction.junctionId)).findFirst().orElse(null));

                        currentJunction.distance = activeTableData.distance+1;
                        currentJunction.prevId = activeTableData.junctionId;
                    }
                }
            }
        }
    }

    public List<String> makePath(String destinationId){
        List<String> stops = new ArrayList<>();
        BfsData active = table.stream().filter(data -> data.junctionId.equals(destinationId)).findFirst().orElse(null);

        while(!active.junctionId.equals(junction.getId())){
            stops.add(active.junctionId);
            String prevId = active.prevId;
            active = table.stream().filter(data -> data.junctionId.equals(prevId)).findFirst().orElse(null);
        }

        stops.add(active.junctionId);
        Collections.reverse(stops);
        return stops;
    }

}
