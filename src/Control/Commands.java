package Control;

import RoadComponents.Intersection;
import RoadComponents.Lane;
import RoadComponents.Road;
import RoadComponents.RoadSection;
import Vehicles.Bus;
import Vehicles.Car;
import Vehicles.Snowplow;
import Vehicles.Vehicle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

//tartalmazza az összes konzolrol hivhato parancsot
public class Commands {
    private final Controller controller;

    public Commands(Controller controller){
        this.controller = controller;
    }

    public void dispatch(String input) throws Exception{
        String[] inputs = input.split(" ");
        String commandName = inputs[0];
        try {
            Method m = this.getClass().getMethod(commandName, String[].class);
            String[] args = Arrays.copyOfRange(inputs, 1, inputs.length);
            m.invoke(this, (Object) args);
        } catch(NoSuchMethodException e){
            System.out.println(Colors.RED+"Unknown command: " + commandName);
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
            writer(args[0]);
        } catch (IOException e){
            System.out.println("Can't write into file!");
            return;
        }
        //write
    }

    private void writer(String filename) throws IOException{
        FileWriter writer = new FileWriter(filename);

        writer.write("newgame");
        writer.write(MessageFormat.format("mode {0}",controller.getDeterministic() ? "deterministic":"random"));

        for(Intersection i : controller.getIntersections()){
            writer.write(MessageFormat.format("create keresztezodes {0}",i.getId()));

            for(Road r : i.getRoads()){
                if(Objects.equals(r.getStartIntersectionId(), i.getId())){
                    writeRoad(writer, r);
                }

                for(Lane l : r.getLanes()){
                    for(RoadSection rs : l.getAllRoadsectionsWithAccidents()){
                        writer.write(MessageFormat.format("setbaleset {0}",rs.getId()));
                    }

                    writeVehicles(writer, l.getAllVehicles());
                }
            }

            writeVehicles(writer, i.getVehicles());
        }

        writer.close();
    }

    private void writeRoad(FileWriter writer, Road r) throws IOException{
        writer.write(MessageFormat.format(
                "create ut {0} {1} {2} {3} {4} {5} {6} {7} {8} {9}",
                r.getId(),
                r.getStartIntersectionId(),
                r.getEndIntersectionId(),
                r.getLaneCount(),
                r.getLength(),
                r.getWay(),
                r.getSnowLevel(),
                r.getIceLevel(),
                r.getRockLevel(),
                r.getType().name().toLowerCase())
        );
    }

    private void writeVehicles(FileWriter writer, List<Vehicle> vehicles) throws IOException{
        for(Vehicle v : vehicles){
            Boolean atIntersection = v.getCurrIntersection() != null;
            String type;
            if(v instanceof Bus){
                type = "busz";
            }else if(v instanceof Car){
                type = "auto";
            }else if(v instanceof Snowplow){
                type = "hokotro";
            }else {
                continue;
            }

            writer.write(MessageFormat.format(
                    "create {0} {1} {2} {3}{4} {5}",
                    type,
                    v.getId(),
                    v.getStartIntersection().getId(),
                    v.getEndIntersection() != null ? v.getEndIntersection().getId()+" " : "",
                    atIntersection,
                    atIntersection ? v.getCurrIntersection().getId() : v.getCurrRoadSection().getId()
            ));

            if(type.equals("busz")){
                writeHolanc(writer, (Bus)v);
            }else if(type.equals("hokotro")){
                writeHokotro(writer, (Snowplow)v);
            }
        }
    }

    private void writeHolanc(FileWriter writer, Bus bus) throws IOException{
        writer.write(MessageFormat.format("attach holanc {0} {1}",
                bus.getId(),
                bus.getSnowchainTTL()));
    }

    private void writeHokotro(FileWriter writer, Snowplow snowplow) throws  IOException{
        //attach fej
        //add consumable
        //setactivefej
    }
    //setroute
    //tick

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
    }

    @CommandInfo(name = "create ut", description = " Két kereszteződés között létrehoz egy utat.", args = "<id> <keresztezodes1> <keresztezodes2> <savok> <hossz> <true|false> <hoszint> <jegszint> <zuzalekszint> <alagut|fout|hid>")
    public void ut(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(name="create busz", description = "Új buszt hoz létre.", args="<id> <startKeresztezodes> <celKeresztezodes>")
    public void busz(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(name="create auto", description = "Új autót hoz létre.", args = " <id> <startKeresztezodes> <celKeresztezodes>")
    public void auto(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(name = "create hokotro", description = "Új hókotrót hoz létre.", args = " <id> <startKeresztezodes>")
    public void hokotro(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    //directs the attach function based on the second word (first in args)
    public void attach(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "attach holanc", description = "Hóláncot rendel a megadott buszhoz.", args = "<buszId> <elettartam>")
    public void holanc(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Megjavítja az adott buszon lévő hóláncot.", args="<buszId>")
    public void fixlanc(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(name = "attach fej", description = "Kotrófejet rendel a hókotróhoz.", args = "<hokotroId> <fejTipus>")
    public void fej(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    //directs the add function based on the second word (first in args)
    public void add(String[] args) throws Exception{
        dispatch(String.join(" ", args));
    }

    @CommandInfo(name = "add consumable", description = "Hozzáad fogyóeszközt a hókotróhoz", args = " <hokotroId> <mennyiseg>")
    public void consumable(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Kiválasztja a hókotró aktív kotrófejét.", args = "<hokotroId> <fejIndex>")
    public void setactivefej(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a hó mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setho(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a jég mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setjeg(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Beállítja, hogy az útszakaszon van-e baleset.", args = "<utszakaszId> <true|false>")
    public void setbaleset(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Beállítja a megadott jármű útvonalát.", args = "<jarmuId> <keresztezodes1> <keresztezodes2> [<keresztezodes3> ...]")
    public void setutvonal(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "A teljes szimulációt egy időegységgel előrelépteti. Minden aktív objektum végrehajtja a saját lépését.")
    public void step(String[] args){
        controller.tick();
    }

    @CommandInfo(description = "Az egész rendszer összefoglaló állapotát írja ki. Ha megvan adva objektum ID akkor egy konkrét objektum részletes állapotát írja ki.",
            args = "[<objektumId>]")
    public void status(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Kilistázza a megadott típusú objektumokat.", args = "<tipus>")
    public void list(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }

    @CommandInfo(description = "Kiírja a jármű aktuálisan tárolt útvonalát vagy következő célpontját.", args = "<jarmuId>")
    public void route(String[] args){
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
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
    }

    @CommandInfo(description = "Leállítja a programot.")
    public void exit(String[] args){
        controller.exit();
        System.out.println(new Object(){}.getClass().getEnclosingMethod().getName());
    }
}
