package Control;

import java.lang.reflect.Method;

//tartalmazza az összes konzolrol hivhato parancsot
public class Commands {
    private final Controller controller;

    public Commands(Controller controller){
        this.controller = controller;
    }

    public void dispatch(String input) throws Exception{
        String commandName = input.split(" ")[0];
        try {
            Method m = this.getClass().getMethod(commandName, String[].class);
            String[] args = input.split(" ");
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

    }

    @CommandInfo(description = "Betölti a megadott konfigurációs fájlban leírt kezdőállapotot.", args = "<fajlnev>")
    public void load(String[] args){

    }

    @CommandInfo(description = " Elmenti az aktuális állapotot a megadott fájlba.", args = "<fajlnev>")
    public void save(String[] args){

    }

    @CommandInfo(description = "Beállítja a futási módot. Deterministic módban a prototípus kiszámíthatóan, tesztelhetően működik. Random módban a véletlenelemek engedélyezettek.",
        args="<deterministic|random> ")
    public void mode(String[] args){

    }

    //directs the create function based on the second word (first in args)
    public void create(String[] args){

    }

    @CommandInfo(name = "create keresztezodes", description = "Új kereszteződést hoz létre.", args = "<id>")
    public void keresztezodes(String[] args){

    }

    @CommandInfo(name = "create ut", description = " Két kereszteződés között létrehoz egy utat.", args = "<id> <keresztezodes1> <keresztezodes2> <savok> <hossz> <true|false> <hoszint> <jegszint> <zuzalekszint> <alagut|fout|hid>")
    public void ut(String[] args){

    }

    @CommandInfo(name="create busz", description = "Új buszt hoz létre.", args="<id> <startKeresztezodes> <celKeresztezodes>")
    public void busz(String[] args){

    }

    @CommandInfo(name="create auto", description = "Új autót hoz létre.", args = " <id> <startKeresztezodes> <celKeresztezodes>")
    public void auto(String[] args){

    }

    @CommandInfo(name = "create hokotro", description = "Új hókotrót hoz létre.", args = " <id> <startKeresztezodes>")
    public void hokotro(String[] args){

    }

    //directs the attach function based on the second word (first in args)
    public void attach(String[] args){

    }

    @CommandInfo(name = "attach holanc", description = "Hóláncot rendel a megadott buszhoz.", args = "<buszId> <elettartam>")
    public void holanc(String[] args){

    }

    @CommandInfo(description = "Megjavítja az adott buszon lévő hóláncot.", args="<buszId>")
    public void fixlanc(String[] args){

    }

    @CommandInfo(name = "attach fej", description = "Kotrófejet rendel a hókotróhoz.", args = "<hokotroId> <fejTipus>")
    public void fej(String[] args){

    }

    //directs the add function based on the second word (first in args)
    public void add(String[] args){

    }

    @CommandInfo(name = "add consumable", description = "Hozzáad fogyóeszközt a hókotróhoz", args = " <hokotroId> <mennyiseg>")
    public void consumable(String[] args){

    }

    @CommandInfo(description = "Kiválasztja a hókotró aktív kotrófejét.", args = "<hokotroId> <fejIndex>")
    public void setactivefej(String[] args){

    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a hó mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setho(String[] args){

    }

    @CommandInfo(description = "Beállítja az adott útszakaszon a jég mennyiségét.", args = "<utszakaszId> <mennyiseg>")
    public void setjeg(String[] args){

    }

    @CommandInfo(description = "Beállítja, hogy az útszakaszon van-e baleset.", args = "<utszakaszId> <true|false>")
    public void setbaleset(String[] args){

    }

    @CommandInfo(description = "A teljes szimulációt egy időegységgel előrelépteti. Minden aktív objektum végrehajtja a saját lépését.")
    public void step(String[] args){

    }

    @CommandInfo(description = "Az egész rendszer összefoglaló állapotát írja ki. Ha megvan adva objektum ID akkor egy konkrét objektum részletes állapotát írja ki.",
            args = "[<objektumId>]")
    public void status(String[] args){

    }

    @CommandInfo(description = "Kilistázza a megadott típusú objektumokat.", args = "<tipus>")
    public void list(String[] args){

    }

    @CommandInfo(description = "Kiírja a jármű aktuálisan tárolt útvonalát vagy következő célpontját.", args = "<jarmuId>")
    public void route(String[] args){

    }

    @CommandInfo(description = "Több lépést futtat le egymás után.", args="<db>")
    public void tick(String[] args){
        int n = Integer.parseInt(args[0]);
        for (int i = 0; i < n; i++){
            controller.tick();
        }
    }

    @CommandInfo(description = "Visszaállítja a rendszert az induló állapotra.")
    public void reset(String[] args){

    }

    @CommandInfo(description = "Leállítja a programot.")
    public void exit(String[] args){
        controller.exit();
    }

    @CommandInfo(description = "Beállítja a megadott jármű útvonalát.", args = "<jarmuId> <keresztezodes1> <keresztezodes2> [<keresztezodes3> ...]")
    public void setutvonal(String[] args){

    }
}
