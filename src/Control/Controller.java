package Control;

import RoadComponents.Intersection;
import RoadComponents.Road;
import RoadComponents.RoadSection;
import Vehicles.GPS.BFS;
import Vehicles.Vehicle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Controller {
    private boolean isRunning;
    protected int tickCount;
    private final BufferedReader reader;
    private final boolean interactive;
    private Commands commands;
    protected List<Intersection> intersections;
    protected List<Road> roads;
    protected boolean deterministic;
    protected List<BFS> bfsList;

    public Controller(){
        isRunning = false;
        tickCount = 0;
        reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
       
        interactive = System.console() != null;
        commands = new Commands(this);
        intersections = new ArrayList<>();
        roads = new ArrayList<>();
        bfsList = new ArrayList<>();
    }

    public void start() throws Exception{
        isRunning = true;

        java.io.Console console = interactive ? System.console() : null;

        if(interactive){
            printWelcome();
        }

        String line;
        while(isRunning){
            if(console != null){
                line = console.readLine("hokotrok> ");
            } else {
                line = reader.readLine();
            }
            if(line == null) break;
            if(!line.isEmpty() && line.charAt(0) == '\uFEFF'){
                line = line.substring(1);
            }
            commands.dispatch(line);
        }
    }

    private void printWelcome(){
        System.out.println("===============================================");
        System.out.println("   Hókotrók - prototípus");
        System.out.println("   A parancsok listájához: help");
        System.out.println("   Új játék:            newgame");
        System.out.println("   Kilépés:             exit");
        System.out.println("===============================================");
    }

    private List<Vehicle> getAllVehicles() {
        List<Vehicle> result = new ArrayList<>();

        for(Intersection i : intersections){
            result.addAll(i.getVehicles());
        }

        for(Road r : roads){
            result.addAll(r.getAllVehicles());
        }

        return result;
    }

    public void tick(){
        tickCount++;
        for(Road road : roads) {
            road.updateRoadSections();
        }
        List<Vehicle> vehicles = getAllVehicles();
        for(Vehicle vehicle : vehicles) {
            vehicle.step();
        }
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
        return roads.stream()
                .map(r -> r.findRoadSectionById(id))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}
