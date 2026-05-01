package Control;

import Attachments.Attachment;
import Attachments.PlowHead;
import RoadComponents.*;
import Vehicles.*;
import Attachments.PlowHeads.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//tartalmazza az összes konzolrol hivhato parancsot
public class Commands {
    private final Controller controller;

    public Commands(Controller controller){
        this.controller = controller;
    }

    public void dispatch(String input) throws Exception{
        input = input.trim();
        if(input.isEmpty() || input.startsWith("#")) return;

        String[] inputs = input.split("\\s+");
        String commandName = inputs[0];
        try {
            Method m = this.getClass().getMethod(commandName, String[].class);
            String[] args = Arrays.copyOfRange(inputs, 1, inputs.length);
            m.invoke(this, (Object) args);
        } catch(NoSuchMethodException e){
            System.out.println(Colors.RED+"Unknown command: " + commandName + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Kilistázza az összes támogatott parancsot és azok rövid jelentését.")
    public void help(String[] args){
        for(Method m : this.getClass().getDeclaredMethods()){
            CommandInfo info = m.getAnnotation(CommandInfo.class);
            if(info != null){
                String name = info.name().isEmpty() ? m.getName() : info.name();
                System.out.println(Colors.ORANGE + name + " " + Colors.RESETCOLOR + info.args());
                System.out.println("\tDescription: " + Colors.GREEN + info.description() + "\n" + Colors.RESETCOLOR);
            }
        }
    }

    @CommandInfo(description = "Üres játékkörnyezetet hoz létre, törli az előző állapotot.")
    public void newgame(String[] args){
        controller.clearGame();
    }

    @CommandInfo(description = "Betölti a megadott konfigurációs fájlban leírt kezdőállapotot.", args = "<fajlnev>")
    public void load(String[] args) throws Exception{
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        Scanner scanner;
        try{
            scanner = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e){
            System.out.println("File not found!");
            return;
        }

        while(scanner.hasNextLine()){
            dispatch(scanner.nextLine());
        }

        scanner.close();
    }

    @CommandInfo(description = " Elmenti az aktuális állapotot a megadott fájlba.", args = "<fajlnev>")
    public void save(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        try{
            Writer writer = new Writer(controller, args[0]);
            writer.write();
        } catch (IOException e){
            System.out.println("Can't write into file!");
        }
    }

    @CommandInfo(description = "Beállítja a futási módot. Deterministic módban a prototípus kiszámíthatóan, tesztelhetően működik. Random módban a véletlenelemek engedélyezettek.",
        args="<deterministic|random> ")
    public void mode(String[] args){
        boolean mode = false;
        if(args[0].equals("deterministic")){
            mode = true;
        }else if (args[0].equals("random")){
            mode = false;
        }else{
            System.out.println("Argument must be 'deterministic' or 'random'");
            return;
        }
        controller.setDeterministic(mode);
    }

    //directs the create function based on the second word (first in args)
    public void create(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "create keresztezodes", description = "Új kereszteződést hoz létre.", args = "<id>")
    public void keresztezodes(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        controller.intersections.add(new Intersection(args[0]));
    }

    @CommandInfo(name = "create ut", description = " Két kereszteződés között létrehoz egy utat.", args = "[<id>] <keresztezodes1> <keresztezodes2> <savok> <hossz> <true|false> <hoszint> <jegszint> <zuzalekszint> <alagut|fout|hid>")
    public void ut(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        boolean hasId = controller.findIntersectionById(args[0]) == null;
        String id = hasId ? args[0] : null;
        int offset = hasId ? 1 : 0;

        Intersection start = controller.findIntersectionById(args[offset]);
        Intersection end = controller.findIntersectionById(args[offset + 1]);

        Road.Way way = args[offset + 4].equals("true") ? Road.Way.ONEWAY : Road.Way.TWOWAY;

        int snowLevel = args.length > offset + 5 ? Integer.parseInt(args[offset + 5]) : 0;
        int iceLevel = args.length > offset + 6 ? Integer.parseInt(args[offset + 6]) : 0;
        int rockLevel = args.length > offset + 7 ? Integer.parseInt(args[offset + 7]) : 0;

        Road.Type type = args.length > offset + 8 ? switch(args[offset + 8]){
            case "alagut" -> Road.Type.ALAGUT;
            case "hid" -> Road.Type.HID;
            default -> Road.Type.FOUT;
        } : Road.Type.FOUT;

        Road road = new Road(
                id,
                start,
                end,
                Integer.parseInt(args[offset + 2]),
                Integer.parseInt(args[offset + 3]),
                way,
                snowLevel,
                iceLevel,
                rockLevel,
                type
        );

        controller.roads.add(road);
        start.addRoad(road);
        end.addRoad(road);
    }

    @CommandInfo(name="create busz", description = "Új buszt hoz létre.", args="<id> <startKeresztezodes> <celKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void busz(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Intersection start = controller.findIntersectionById(args[1]);
        Intersection end = controller.findIntersectionById(args[2]);

        if(start == null || end == null) {
            System.out.println(Colors.RED + "Rossz kezdo vagy celpont" + Colors.RESETCOLOR);
            return;
        }

        Bus bus = new Bus(args[0], start, end);

        if(args.length > 3){
            if(args[3].equals("true")){
                Intersection curr = controller.findIntersectionById(args[4]);
                if(curr != null) {
                    curr.addVehicle(bus);
                }
            }else if(args[3].equals("false")){
                RoadSection rs = controller.findRoadSectionById(args[4]);
                if(rs != null) {
                    rs.addVehicle(bus);
                }
            }
        }else{
            start.addVehicle(bus);
        }

        controller.makeRoute(bus);
    }

    @CommandInfo(name="create auto", description = "Új autót hoz létre.", args = " <id> <startKeresztezodes> <celKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void auto(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName()); //konzolra loggol debug miatt

        Intersection start = controller.findIntersectionById(args[1]);
        Intersection end = controller.findIntersectionById(args[2]);
        Car car = new Car(args[0],start, end);

        if(args.length > 3){
            if(args[3].equals("true")){
                controller.findIntersectionById(args[4])
                        .addVehicle(car);
            }else if(args[3].equals("false")){
                controller.findRoadSectionById(args[4]).addVehicle(car);
            }
        }else{
            start.addVehicle(car);
        }
        controller.makeRoute(car);
    }

    @CommandInfo(name = "create hokotro", description = "Új hókotrót hoz létre.", args = " <id> <startKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void hokotro(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Intersection start = controller.findIntersectionById(args[1]);

        if(start == null) {
            System.out.println(Colors.RED + "Rossz kezdopont" + Colors.RESETCOLOR);
            return;
        }

        Snowplow snowplow = new Snowplow(args[0], start);
        
        if(args.length > 2){
             if(args[2].equals("true")){
                Intersection curr = controller.findIntersectionById(args[3]);
                if(curr != null) {
                    curr.addVehicle(snowplow);
                }
            }else if(args[2].equals("false")){
                RoadSection rs = controller.findRoadSectionById(args[3]);
                if(rs != null) {
                    rs.addVehicle(snowplow);
                }
            }
        } else {
             start.addVehicle(snowplow);
        }
    }

    //directs the attach function based on the second word (first in args)
    public void attach(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "attach holanc", description = "Hóláncot rendel a megadott buszhoz.", args = "<buszId> <elettartam>")
    public void holanc(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Bus bus) {
            int durability = Integer.parseInt(args[1]);
            bus.addSnowchain(new Attachments.Snowchain(durability));
            System.out.println("OK Holanc felszerelve a buszra: " + args[0]);
        } else {
            System.out.println(Colors.RED + "A jarmu nem talalhato, vagy nem busz." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Megjavítja az adott buszon lévő hóláncot.", args="<buszId>")
    public void fixlanc(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Bus bus) {
            if(bus.getHasSnowchain()) {
                bus.getSnowchain().fix();
                System.out.println("Holanc megjavitva a buszon: " + args[0]);
            } else {
                System.out.println(Colors.RED + "A buszon nincs holanc." + Colors.RESETCOLOR);
            }
        } else {
            System.out.println(Colors.RED + "A jarmu nem talalhato, vagy nem busz." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(name = "attach fej", description = "Kotrófejet rendel a hókotróhoz.", args = "<hokotroId> <fejTipus>")
    public void fej(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Snowplow snowplow) {
            Attachments.PlowHead head = null;
            
            switch(args[1]) {
                case "BroomHead": head = new BroomHead(); break;
                case "DragonHead": head = new DragonHead(); break;
                case "IceBreakerHead": head = new IceBreakerHead(); break;
                case "SaltShakerHead": head = new SaltShakerHead(); break;
                case "ThrowHead": head = new ThrowHead(); break;
                case "RockHead" : head = new RockHead(); break;
                default: 
                    System.out.println(Colors.RED + "Ismeretlen fej tipus." + Colors.RESETCOLOR);
                    return;
            }
            
            snowplow.addPlow(head);
            System.out.println("A kotrofej felszerelve a hokotrora: " + args[1]);
        } else {
            System.out.println(Colors.RED + "A jarmu nem talalhato, vagy nem hokotro." + Colors.RESETCOLOR);
        }
    }

    //directs the add function based on the second word (first in args)
    public void add(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "add consumable", description = "Hozzáad fogyóeszközt a hókotróhoz", args = " <hokotroId> <mennyiseg> [<fejtipus>]")
    public void consumable(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
        //ha van fejtipus akkor fillPlowHead
        //ha nincs akkor fillActiveHead

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Snowplow snowplow) {
            int amount = Integer.parseInt(args[1]);
            
            if(args.length > 2) {
                // Specifikus fej feltöltése
                Class<? extends Attachments.PlowHead> headClass = switch(args[2]) {
                    case "DragonHead" -> Attachments.PlowHeads.DragonHead.class;
                    case "SaltShakerHead" -> Attachments.PlowHeads.SaltShakerHead.class;
                    case "RockHead" -> Attachments.PlowHeads.RockHead.class;
                    default -> null;
                };
                
                if(headClass != null) {
                    snowplow.fillPlowHead(amount, headClass);
                    System.out.println("Fogyoeszkoz hozzaadva a " + args[2] + " fejhez.");
                } else {
                    System.out.println(Colors.RED + "Ismeretlen vagy nem feltoltheto fejtipus." + Colors.RESETCOLOR);
                }
            } else {
                // Aktív fej feltöltése
                snowplow.fillActiveHead(amount);
                System.out.println("ogyoeszkoz hozzaadva az aktiv fejhez.");
            }
        } else {
            System.out.println(Colors.RED + "A jarmu nem talalhato, vagy nem hokotro." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Kiválasztja a hókotró aktív kotrófejét.", args = "<hokotroId> <fejTipus>")
    public void setactivefej(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Snowplow snowplow) {
            try {
                int index = Integer.parseInt(args[1]);
                if(index >= 0 && index < snowplow.getPlowHeads().size()) {
                    snowplow.setActivePlowHead(snowplow.getPlowHeads().get(index));
                    System.out.println("Aktiv fej beallitva: " + args[1]);
                    return;
                }
            } catch (NumberFormatException ignored) {
            }

            for(PlowHead head : snowplow.getPlowHeads()) {
                if(head.getClass().getSimpleName().equals(args[1])) {
                    snowplow.setActivePlowHead(head);
                    System.out.println("Aktiv fej beallitva: " + args[1]);
                    return;
                }
            }
            System.out.println(Colors.RED + "Ez a hokotro nem rendelkezik ilyen kotrofejjel." + Colors.RESETCOLOR);
        } else {
            System.out.println(Colors.RED + "A jarmu nem talalhato, vagy nem hokotro." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a hó mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setho(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getSnow();
            if(target > current) rs.snowIncrease(target - current);
            else rs.snowReduce(current - target);
            System.out.println("Ho beallitva: " + target);
        } else {
            System.out.println(Colors.RED + "Utszakasz nem talalhato." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a jég mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setjeg(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getIce();
            if(target > current) rs.iceIncrease(target - current);
            else rs.iceReduce(current - target);
            System.out.println("Jeg beallitva: " + target);
        } else {
            System.out.println(Colors.RED + "Utszakasz nem talalhato." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a zuzalék mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setzuzalek(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getRock();
            if(target > current) rs.rockIncrease(target - current);
            else rs.rockReduce(current - target);
            System.out.println("Zuzalek beallitva: " + target);
        } else {
            System.out.println(Colors.RED + "Utszakasz nem talalhato." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Beállítja, hogy az útszakaszon van-e baleset.", args = "<utszakaszId> <true|false>")
    public void setbaleset(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            boolean happened = Boolean.parseBoolean(args[1]);
            rs.setAccident(happened);
            System.out.println("Baleset allapota beallitva: " + happened);
        } else {
            System.out.println(Colors.RED + "Utszakasz nem talalhato." + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Beállítja a megadott jármű útvonalát.", args = "<jarmuId> <keresztezodes1> <keresztezodes2> [<keresztezodes3> ...]")
    public void setutvonal(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        if (v == null) {
            System.out.println(Colors.RED + "A jarmu nem talalhato." + Colors.RESETCOLOR);
            return;
        }

        List<Intersection> route = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            Intersection inter = controller.findIntersectionById(args[i]);
            if (inter != null) {
                route.add(inter);
            }
        }
        v.setRoute(route);
        System.out.println("Utvonal beallitva a jarmuvon: " + args[0]);
    }

    @CommandInfo(description = "A teljes szimulációt egy időegységgel előrelépteti. Minden aktív objektum végrehajtja a saját lépését.")
    public void step(String[] args){
        controller.tick();
    }

    @CommandInfo(description = "Az egész rendszer összefoglaló állapotát írja ki. Ha megvan adva objektum ID akkor egy konkrét objektum részletes állapotát írja ki.",
            args = "[<objektumId>]")
    public void status(String[] args){
        if (args.length == 0) {
            // Globális állapot
            System.out.println("STATE");

            int allVehicles = controller.intersections.stream()
                    .mapToInt(i -> i.getVehicles().size())
                            .sum()
                            + controller.roads.stream()
                            .mapToInt(r -> r.getAllVehicles().size())
                            .sum();
            System.out.println("vehicles="+allVehicles);

            System.out.println("roads=" + controller.getRoads().size());

            int allSegments = controller.roads.stream()
                            .flatMap(r -> r.getLanes().stream())
                            .mapToInt(l -> l.getAllRoadSections().size())
                            .sum();
            System.out.println("segments="+allSegments);

            System.out.println("intersections=" + controller.intersections.size());

            System.out.println("time="+controller.tickCount);

            System.out.println("mode=" + (controller.deterministic ? "deterministic" : "random"));

            System.out.println("END");
        } else {
            // Objektum szintű állapot
            String id = args[0];
            Vehicle v = controller.findVehiclebyId(id);
            
            if (v != null) {
                String typeName = v.getClass().getSimpleName();
                System.out.println("OBJECT " + typeName + " " + id);
                System.out.println("type=" + typeName);
                if (v.getCurrRoadSection() != null) {
                    System.out.println("currentSegment=" + v.getCurrRoadSection().getId());
                    System.out.println("currentRoad=" + v.getCurrRoadSection().getLane().getRoadId());
                    System.out.println("currentLane=" + v.getCurrRoadSection().getLane().getIndexInRoad());
                }else{
                    System.out.println("currentIntersection=" + v.getCurrIntersection().getId());
                }
                System.out.println("stuck=" + v.isStuck());
                System.out.println("target="+v.getEndIntersection().getId());

                if(v instanceof Bus bus){
                    System.out.println("hasSnowChain="+bus.getHasSnowchain());
                    System.out.println("snowChainDurability="+bus.getSnowchainTTL());
                }else if(v instanceof Car car){
                    System.out.println("next="+car.getNextIntersection().getId());
                }else if(v instanceof Snowplow snowplow){
                    System.out.println("activeHead="+snowplow.getActivePlowHead().getClass().getSimpleName());
                    System.out.println("consumableLeft="+snowplow.getActivePlowHead().getConsumableAmountLeft());
                    System.out.println("headCount="+snowplow.getPlowHeads().size());
                }
                System.out.println("END");
                return;
            }

            Intersection intersection = controller.findIntersectionById(id);
            if (intersection != null) {
                System.out.println("OBJECT Keresztezodes " + id);
                System.out.println("type=Keresztezodes");
                System.out.println("roadCount=" + intersection.getRoads().size());
                System.out.println("roads=" + intersection.getRoads().stream()
                        .map(Road::getId)
                        .collect(Collectors.joining(",")));
                System.out.println("vehicleCount=" + intersection.getVehicles().size());
                System.out.println("vehicles=" + intersection.getVehicles().stream()
                        .map(Vehicle::getId)
                        .collect(Collectors.joining(",")));
                System.out.println("END");
                return;
            }

            RoadSection rs = controller.findRoadSectionById(id);
            if (rs != null) {
                System.out.println("OBJECT Utszakasz " + id);
                System.out.println("road="+rs.getLane().getRoadId());
                System.out.println("lane="+rs.getLane().getIndexInRoad());
                System.out.println("snow=" + rs.getSnow());
                System.out.println("ice=" + rs.getIce());
                System.out.println("rock="+rs.getRock());
                System.out.println("accident="+rs.getAccidentHappened());
                System.out.println("occupiedBy=" + rs.getVehicles().stream()
                        .map(Vehicle::getId)
                        .collect(Collectors.joining(",")));
                System.out.println("END");
                return;
            }
            
            System.out.println(Colors.RED + "Az objektum nem talalhato: " + id + Colors.RESETCOLOR);
        }
    }

    @CommandInfo(description = "Kilistázza a megadott típusú objektumokat.", args = "<tipus>")
    public void list(String[] args){
        String type = args[0].toLowerCase();
        System.out.println("LIST " + type);
        
        switch(type) {
            case "keresztezodesek" -> {
                for (Intersection i : controller.intersections) {
                    System.out.println(i.getId() + " Keresztezodes");
                }
            }
            case "utak" -> {
                for (Road r : controller.getRoads()) {
                    System.out.println(r.getId() + " Ut");
                }
            }
            case "jarmuvek" -> {
                for (Intersection i : controller.intersections) {
                    for (Vehicle v : i.getVehicles()) System.out.println(v.getId() + " " + v.getClass().getSimpleName());
                }
                for (Road r : controller.getRoads()) {
                    for (Vehicle v : r.getAllVehicles()) System.out.println(v.getId() + " " + v.getClass().getSimpleName());
                }
            }
            case "utszakaszok" -> {
                for (Road r : controller.getRoads()) {
                    for (Lane l : r.getLanes()) {
                        for (RoadSection rs : l.getAllRoadSections()) {
                            System.out.println(rs.getId() + " Utszakasz");
                        }
                    }
                }
            }
            default -> System.out.println(Colors.RED + "Ismeretlen tipus." + Colors.RESETCOLOR);
        }

        System.out.println("END");
        
    }

    @CommandInfo(description = "Kiírja a jármű aktuálisan tárolt útvonalát vagy következő célpontját.", args = "<jarmuId>")
    public void route(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        Vehicle v = controller.findVehiclebyId(args[0]);
        if(v == null){System.out.println(Colors.RED+"Vehicle not found"+Colors.RESETCOLOR); return;}

        if(v.getJunctions() == null || v.getJunctions().isEmpty()){
            System.out.println("");
            return;
        }

        System.out.println(v.getJunctions().stream()
                .map(Intersection::getId)
                .collect(Collectors.joining(" -> ")));
    }

    @CommandInfo(description = "Több lépést futtat le egymás után.", args="<db>")
    public void tick(String[] args){
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++){
            controller.tick();
        }
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    //mit takar a kiindulo allapot?
    @CommandInfo(description = "Visszaállítja a rendszert az induló állapotra.")
    public void reset(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());

        controller.clearGame();
    }

    @CommandInfo(description = "Leállítja a programot.")
    public void exit(String[] args){
        controller.exit();
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }
}
