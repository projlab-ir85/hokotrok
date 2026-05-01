package Control;

import RoadComponents.Intersection;
import RoadComponents.Road;
import RoadComponents.RoadSection;
import Vehicles.Bus;
import Vehicles.GPS.BFS;
import Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Controller {
    private boolean isRunning;
    protected int tickCount;
    private Scanner scanner;
    private Commands commands;
    protected List<Intersection> intersections;
    protected List<Road> roads;
    protected boolean deterministic;
    protected List<BFS> bfsList;

    public Controller(){
        isRunning = false;
        tickCount = 0;
        scanner = new Scanner(System.in);
        commands = new Commands(this);
        intersections = new ArrayList<>();
        roads = new ArrayList<>();
        bfsList = new ArrayList<>();
    }

    public void start() throws Exception{
        isRunning = true;

        while(isRunning && scanner.hasNextLine()){
            commands.dispatch(scanner.nextLine());
        }
    }

    public void tick(){
        tickCount++;

        for (Road road : roads) {
            road.updateRoadSections();
        }

        for (Vehicle vehicle : getAllVehiclesSnapshot()) {
            moveVehicle(vehicle);
        }
    }

    public void printOutput(String message){
        System.out.println(message);
    }

    public void exit(){
        isRunning = false;
    }

    public void clearGame(){
        intersections.clear();
        roads.clear();
        bfsList.clear();
        tickCount = 0;
    }

    public void setDeterministic(boolean mode){
        deterministic = mode;
    }

    public void makeBFS(String id){
        Intersection i = findIntersectionById(id);
        BFS currBFS = new BFS(i);
        currBFS.createTable(intersections, roads);
        bfsList.add(currBFS);
    }

    public void makeRoute(Vehicle v){
        bfsList.removeIf(b -> b.getJunctionId().equals(v.getStartIntersection().getId()));
        makeBFS(v.getStartIntersection().getId());

        BFS currBFS = bfsList.stream().filter(item -> item.getJunctionId().equals(v.getStartIntersection().getId())).findFirst().orElse(null);
        if(currBFS == null){System.out.println("The starting junction for the route doesn't exist!"); return;}
        List<String> routeString = currBFS.makePath(v.getEndIntersection().getId());

        v.setRoute(routeString.stream().map(id -> intersections.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    public Intersection findIntersectionById(String id){
        return intersections.stream().filter(i -> i.getId().equals(id)).findFirst().orElse(null);
    }

    public Vehicle findVehiclebyId(String id){
        Vehicle v;
        for(Intersection i : intersections){
            v = i.getVehicles().stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
            if(v != null) return v;
        }
        for(Road r : roads){
            v = r.getAllVehicles().stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().orElse(null);
            if(v != null) return v;
        }
        return null;
    }

    public List<Road> getRoads(){return roads;}

    public RoadSection findRoadSectionById(String id){
        RoadSection exactMatch = roads.stream()
                .map(r -> r.findRoadSectionById(id))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);

        if(exactMatch != null){
            return exactMatch;
        }

        return roads.stream()
                .filter(r -> r.getId().equals(id))
                .flatMap(r -> r.getLanes().stream())
                .flatMap(l -> l.getAllRoadSections().stream())
                .findFirst().orElse(null);
    }

    private List<Vehicle> getAllVehiclesSnapshot(){
        LinkedHashSet<Vehicle> vehicles = new LinkedHashSet<>();

        for(Intersection intersection : intersections){
            vehicles.addAll(intersection.getVehicles());
        }

        for(Road road : roads){
            vehicles.addAll(road.getAllVehicles());
        }

        return new ArrayList<>(vehicles);
    }

    private void moveVehicle(Vehicle vehicle){
        if(vehicle.isStuck()){
            vehicle.tickStuck();
            return;
        }

        if(vehicle.getCurrIntersection() != null){
            moveFromIntersection(vehicle);
            return;
        }

        if(vehicle.getCurrRoadSection() != null){
            moveFromRoadSection(vehicle);
        }
    }

    private void moveFromIntersection(Vehicle vehicle){
        Intersection current = vehicle.getCurrIntersection();
        ensureRoute(vehicle);

        Intersection next = vehicle.getNextIntersection();
        if(next == null || next.equals(current)){
            vehicle.advanceRouteIfAt(current);
            next = vehicle.getNextIntersection();
        }

        if(next == null || next.equals(current)){
            return;
        }

        RoadSection firstSection = current.roadSelection(next);
        if(firstSection != null && firstSection.accept(vehicle)){
            current.removeVehicle(vehicle);
            vehicle.setCurrIntersection(null);
            useSnowchainIfNeeded(vehicle);
        }
    }

    private void moveFromRoadSection(Vehicle vehicle){
        RoadSection current = vehicle.getCurrRoadSection();
        RoadSection next = current.next;

        if(next != null){
            if(next.accept(vehicle)){
                current.removeVehicle(vehicle);
                useSnowchainIfNeeded(vehicle);
            }
            return;
        }

        Intersection destination = current.getLane().getEnd();
        current.removeVehicle(vehicle);
        vehicle.setCurrRoadSection(null);
        destination.addVehicle(vehicle);
        vehicle.advanceRouteIfAt(destination);
    }

    private void ensureRoute(Vehicle vehicle){
        if(vehicle.getEndIntersection() == null){
            return;
        }

        if(vehicle.getJunctions() == null || vehicle.getJunctions().isEmpty()){
            makeRoute(vehicle);
        }
    }

    private void useSnowchainIfNeeded(Vehicle vehicle){
        if(vehicle instanceof Bus bus && bus.getHasSnowchain()){
            bus.getSnowchain().use();
        }
    }
}
