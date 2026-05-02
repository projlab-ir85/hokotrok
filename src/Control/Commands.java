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

import javax.xml.transform.OutputKeys;

//tartalmazza az összes konzolrol hivhato parancsot
public class Commands {
    private final Controller controller;

    public Commands(Controller controller){
        this.controller = controller;
    }

    public void okMessage(String message) {
        System.out.println(Colors.GREEN + "OK! " + Colors.BLUE + message + Colors.RESETCOLOR);
    }
    
    public void statusMessage(String message) {
        System.out.println(Colors.ORANGE + message + Colors.RESETCOLOR);
    }

    public void errorMessage(String message) {
        System.out.println(Colors.RED + "ERROR! " + message + Colors.RESETCOLOR);
    }

    public void message(String message) {
        System.out.println(Colors.BLUE + message + Colors.RESETCOLOR);
    }

    public void dispatch(String input) throws Exception{
        String[] inputs = input.split(" ");
        String commandName = inputs[0];
        try {
            Method m = this.getClass().getMethod(commandName, String[].class);
            String[] args = Arrays.copyOfRange(inputs, 1, inputs.length);
            m.invoke(this, (Object) args);
        } catch(NoSuchMethodException e){
            errorMessage("Unknown command: " + commandName);
        }
    }

    @CommandInfo(description = "Kilistázza az összes támogatott parancsot és azok rövid jelentését.")
    public void help(String[] args){
        for(Method m : this.getClass().getDeclaredMethods()){
            CommandInfo info = m.getAnnotation(CommandInfo.class);
            if(info != null){
                String name = info.name().isEmpty() ? m.getName() : info.name();
                System.out.println(Colors.ORANGE + name + " " + Colors.RESETCOLOR + info.args());
                System.out.println("\tDescription: " + Colors.BLUE + info.description() + "\n" + Colors.RESETCOLOR);
            }
        }
    }

    @CommandInfo(description = "Üres játékkörnyezetet hoz létre, törli az előző állapotot.")
    public void newgame(String[] args){
        controller.clearGame();
        okMessage("Új játék létrehozva");
    }

    @CommandInfo(description = "Betölti a megadott konfigurációs fájlban leírt kezdőállapotot.", args = "<fajlnev>")
    public void load(String[] args) throws Exception{
        Scanner scanner;
        try{
            scanner = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e){
            errorMessage("Nincs ilyen file!");
            return;
        }

        while(scanner.hasNextLine()){
            dispatch(scanner.nextLine());
        }

        scanner.close();
    }

    @CommandInfo(description = " Elmenti az aktuális állapotot a megadott fájlba.", args = "<fajlnev>")
    public void save(String[] args){
        try{
            Writer writer = new Writer(controller, args[0]);
            writer.write();
        } catch (IOException e){
            errorMessage("Nem sikerült a kiírás!");
        }
    }

    @CommandInfo(description = "Beállítja a futási módot. Deterministic módban a prototípus kiszámíthatóan, tesztelhetően működik. Random módban a véletlenelemek engedélyezettek.",
        args="<deterministic|random> ")
    public void mode(String[] args){
        boolean mode = false;
        if(args[0].equals("deterministic")){
            mode = true;
            okMessage("Deterministic mód beállítva!");
        }else if (args[0].equals("random")){
            mode = false;
            okMessage("Random mód beállítva");
        }else{
            errorMessage("Argument must be 'deterministic' or 'random'");
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
        controller.intersections.add(new Intersection(args[0]));
        okMessage("Kereszteződés létrejött: " + args[0]);
    }

    @CommandInfo(name = "create ut", description = " Két kereszteződés között létrehoz egy utat.", args = "[<id>] <keresztezodes1> <keresztezodes2> <savok> <hossz> <true|false> <hoszint> <jegszint> <zuzalekszint> <alagut|fout|hid>")
    public void ut(String[] args){

        Boolean hasId = args.length == 10;
        String id = hasId ? args[0] : null;
        int i = hasId ? 0 : 1;

        Intersection start = controller.findIntersectionById(args[1-i]);
        Intersection end = controller.findIntersectionById(args[2-i]);

        Road.Way way = args[5-1].equals("true") ? Road.Way.ONEWAY : Road.Way.TWOWAY;

        Road.Type type = switch(args[9-i]){
            case "alagut" -> Road.Type.ALAGUT;
            case "hid" -> Road.Type.HID;
            default -> Road.Type.FOUT;
        };

        Road road = new Road(
                id,
                start,
                end,
                Integer.parseInt(args[3-i]),
                Integer.parseInt(args[4-i]),
                way,
                Integer.parseInt(args[6-i]),
                Integer.parseInt(args[7-i]),
                Integer.parseInt(args[8-i]),
                type
        );

        controller.roads.add(road);
        start.addRoad(road);
        okMessage("Út létrejött: " + args[0]);
    }

    @CommandInfo(name="create busz", description = "Új buszt hoz létre.", args="<id> <startKeresztezodes> <celKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void busz(String[] args){

        Intersection start = controller.findIntersectionById(args[1]);
        Intersection end = controller.findIntersectionById(args[2]);

        if(start == null || end == null) {
            System.out.println(Colors.RED + "Rossz kezdo vagy celpont" + Colors.RESETCOLOR);
            return;
        }

        Bus bus = new Bus(start, end);

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
        okMessage("Busz létrejött: " + args[0]);
    }

    @CommandInfo(name="create auto", description = "Új autót hoz létre.", args = " <id> <startKeresztezodes> <celKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void auto(String[] args){

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
        okMessage("Autó létrejött: " + args[0]);
    }

    @CommandInfo(name = "create hokotro", description = "Új hókotrót hoz létre.", args = " <id> <startKeresztezodes> [<true|false> <keresztezodesId|utszakaszId]")
    public void hokotro(String[] args){


        Intersection start = controller.findIntersectionById(args[1]);

        if(start == null) {
            errorMessage("Rossz kezdőpont!");
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
        okMessage("Hókotró létrejött: " + args[0]);
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
            okMessage("Hólánc sikeresen felszerelve: " + args[0]);
        } else {
            errorMessage("A jármű nem található vagy nem busz!");
        }
    }

    @CommandInfo(description = "Megjavítja az adott buszon lévő hóláncot.", args="<buszId>")
    public void fixlanc(String[] args){

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Bus bus) {
            if(bus.getHasSnowchain()) {
                bus.getSnowchain().fix();
                okMessage("Hólánc megjavítva a buszon: " + args[0]);
            } else {
                errorMessage("A buszon nincs hólánc!");
            }
        } else {
            errorMessage("A jármű nem található vagy nem busz!");
        }
    }

    @CommandInfo(name = "attach fej", description = "Kotrófejet rendel a hókotróhoz.", args = "<hokotroId> <fejTipus>")
    public void fej(String[] args){

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
                    errorMessage("Ismeretlen fej típus");
                    return;
            }
            
            snowplow.addPlow(head);
            okMessage("Kotrófej sikeresen felszerelve a hókotróra: " + args[0]);
        } else {
            errorMessage("A jármű nem található vagy nem hókotró!");
        }
    }

    //directs the add function based on the second word (first in args)
    public void add(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "add consumable", description = "Hozzáad fogyóeszközt a hókotróhoz", args = " <hokotroId> <mennyiseg> [<fejtipus>]")
    public void consumable(String[] args){
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
                    okMessage("Fogyoeszkoz hozzaadva a " + args[2] + " fejhez!");
                } else {
                    okMessage("Ismeretlen vagy nem feltoltheto fejtipus!");
                }
            } else {
                // Aktív fej feltöltése
                snowplow.fillActiveHead(amount);
                okMessage("Fogyoeszkoz hozzaadva az aktiv fejhez!");
            }
        } else {
            errorMessage("A jarmu nem talalhato, vagy nem hokotro!");
        }
    }

    @CommandInfo(description = "Kiválasztja a hókotró aktív kotrófejét.", args = "<hokotroId> <fejTipus>")
    public void setactivefej(String[] args){

        Vehicle v = controller.findVehiclebyId(args[0]);
        
        if(v instanceof Snowplow snowplow) {
            for(PlowHead head : snowplow.getPlowHeads()) {
                if(head.getClass().getSimpleName().equals(args[1])) {
                    snowplow.setActivePlowHead(head);
                    okMessage("Aktiv fej beallitva: " + args[1]);
                    return;
                }
            }
            errorMessage("Ez a hokotro nem rendelkezik ilyen kotrofejjel!");
        } else {
            errorMessage("A jarmu nem talalhato, vagy nem hokotro!");
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a hó mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setho(String[] args){

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getSnow();
            if(target > current) rs.snowIncrease(target - current);
            else rs.snowReduce(current - target);
            okMessage("Hó beállítva: " + target);
        } else {
            errorMessage("Útszakasz nem található!" );
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a jég mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setjeg(String[] args){

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getIce();
            if(target > current) rs.iceIncrease(target - current);
            else rs.iceReduce(current - target);
            okMessage("Jég beállítva: " + target);
        } else {
            errorMessage("Útszakasz nem található!");
        }
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a zuzalék mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setzuzalek(String[] args){

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            int target = Integer.parseInt(args[1]);
            int current = rs.getRock();
            if(target > current) rs.rockIncrease(target - current);
            else rs.rockReduce(current - target);
            okMessage("Zúzalék beállítva: " + target);
        } else {
            errorMessage("Útszakasz nem található!");
        }
    }

    @CommandInfo(description = "Beállítja, hogy az útszakaszon van-e baleset.", args = "<utszakaszId> <true|false>")
    public void setbaleset(String[] args){

        RoadSection rs = controller.findRoadSectionById(args[0]);
        if(rs != null) {
            boolean happened = Boolean.parseBoolean(args[1]);
            rs.setAccident(happened);
            okMessage("Baleset állapota beállítva: " + happened);
        } else {
            errorMessage("Útszakasz nem található!");
        }
    }

    @CommandInfo(description = "Beállítja a megadott jármű útvonalát.", args = "<jarmuId> <keresztezodes1> <keresztezodes2> [<keresztezodes3> ...]")
    public void setutvonal(String[] args){

        Vehicle v = controller.findVehiclebyId(args[0]);
        if (v == null) {
            errorMessage("Jármű nem található!");
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
        okMessage("Útvonal sikeresen beállítva a járművön: " + args[0]);
    }

    @CommandInfo(description = "A teljes szimulációt egy időegységgel előrelépteti. Minden aktív objektum végrehajtja a saját lépését.")
    public void step(String[] args){
        controller.tick();
        okMessage("A játék sikeresen lépett egyet!");
    }

    @CommandInfo(description = "Az egész rendszer összefoglaló állapotát írja ki. Ha megvan adva objektum ID akkor egy konkrét objektum részletes állapotát írja ki.",
            args = "[<objektumId>]")
    public void status(String[] args){

        if (args.length == 0) {
            // Globális állapot
            statusMessage("STATE");

            int allVehicles = controller.intersections.stream()
                    .mapToInt(i -> i.getVehicles().size())
                            .sum()
                            + controller.roads.stream()
                            .mapToInt(r -> r.getAllVehicles().size())
                            .sum();
            message("vehicles="+allVehicles);

            message("roads=" + controller.getRoads().size());

            int allSegments = controller.roads.stream()
                            .flatMap(r -> r.getLanes().stream())
                            .mapToInt(l -> l.getAllRoadSections().size())
                            .sum();
            message("segments="+allSegments);

            message("intersections=" + controller.intersections.size());

            message("time="+controller.tickCount);

            message("mode=" + (controller.deterministic ? "deterministic" : "random"));

            statusMessage("END");
        } else {
            // Objektum szintű állapot
            String id = args[0];
            Vehicle v = controller.findVehiclebyId(id);
            
            if (v != null) {
                String typeName = v.getClass().getSimpleName();
                statusMessage("OBJECT " + typeName + " " + id);
                message("type=" + typeName);
                if (v.getCurrRoadSection() != null) {
                    message("currentSegment=" + v.getCurrRoadSection().getId());
                    message("currentRoad=" + v.getCurrRoadSection().getLane().getRoadId());
                    message("currentLane=" + v.getCurrRoadSection().getLane().getIndexInRoad());
                }else{
                    message("currentIntersection=" + v.getCurrIntersection().getId());
                }
                message("stuck=" + v.isStuck());

                if(v instanceof Bus bus){
                    message("target="+v.getEndIntersection().getId());
                    message("hasSnowChain="+bus.getHasSnowchain());
                    message("snowChainDurability="+bus.getSnowchainTTL());
                }else if(v instanceof Car car){
                    message("target="+v.getEndIntersection().getId());
                    message("next="+car.getNextIntersection().getId());
                }else if(v instanceof Snowplow snowplow){
                    message("activeHead="+snowplow.getActivePlowHead().getClass().getSimpleName());
                    message("consumableLeft="+snowplow.getActivePlowHead().getConsumableAmountLeft());
                    message("headCount="+snowplow.getPlowHeads().size());
                }
                statusMessage("END");
                return;
            }

            RoadSection rs = controller.findRoadSectionById(id);
            if (rs != null) {
                statusMessage("OBJECT Utszakasz " + id);
                message("road="+rs.getLane().getRoadId());
                message("lane="+rs.getLane().getIndexInRoad());
                message("snow=" + rs.getSnow());
                message("ice=" + rs.getIce());
                message("rock="+rs.getRock());
                message("accident="+rs.getAccidentHappened());
                statusMessage("END");
                return;
            }
            
            errorMessage("Az objektum nem talalhato: " + id );
        }
    }

    @CommandInfo(description = "Kilistázza a megadott típusú objektumokat.", args = "<tipus>")
    public void list(String[] args){

        String type = args[0].toLowerCase();
        statusMessage("LIST " + type);
        
        switch(type) {
            case "keresztezodesek" -> {
                for (Intersection i : controller.intersections) {
                    message(i.getId() + " Keresztezodes");
                }
            }
            case "utak" -> {
                for (Road r : controller.getRoads()) {
                    message(r.getId() + " Ut");
                }
            }
            case "jarmuvek" -> {
                for (Intersection i : controller.intersections) {
                    for (Vehicle v : i.getVehicles()) message(v.getId() + " " + v.getClass().getSimpleName());
                }
                for (Road r : controller.getRoads()) {
                    for (Vehicle v : r.getAllVehicles()) message(v.getId() + " " + v.getClass().getSimpleName());
                }
            }
            default -> errorMessage("Ismeretlen típus!");
        }
        
    }

    @CommandInfo(description = "Kiírja a jármű aktuálisan tárolt útvonalát vagy következő célpontját.", args = "<jarmuId>")
    public void route(String[] args){

        Vehicle v = controller.findVehiclebyId(args[0]);
        if(v == null){errorMessage("Nincs ilyen jármű!");; return;}

        if(v instanceof Bus bus){
            message(bus.getNext().getId());
        }else{
            message(v.getJunctions().stream()
                    .map(Intersection::getId)
                    .collect(Collectors.joining(" -> ")));
        }
    }

    @CommandInfo(description = "Több lépést futtat le egymás után.", args="<db>")
    public void tick(String[] args){
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++){
            controller.tick();
        }
        okMessage(n + " lépés sikeresen lefutattva!");
    }

    //mit takar a kiindulo allapot?
    @CommandInfo(description = "Visszaállítja a rendszert az induló állapotra.")
    public void reset(String[] args){
        controller.clearGame();
        okMessage("Rendszer sikeresen visszaállítva!");
    }

    @CommandInfo(description = "Leállítja a programot.")
    public void exit(String[] args){
        controller.exit();
        okMessage("Játék leállítása!");
    }
}

