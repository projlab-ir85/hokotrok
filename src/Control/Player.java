package Control;
import java.util.List;

import Vehicles.*;

/** A játékos megvalósítása. */
public class Player {
    /** A játékos azonosítója (parancssoros hivatkozáshoz). */
    private final String id;
    /** Szerepkör: BUSZVEZETO vagy TAKARITO. */
    private final Role role;
    private final List<Vehicle> vehicles;
    private int cash;
    /** A játékos által megtett útszakaszok száma (pontozási statisztika). */
    private int sectionsDone;
    /** A játékos által megtett körök száma (csak buszvezetőnek releváns). */
    private int lapsDone;

    public enum Role {
        BUSZVEZETO,
        TAKARITO;

        public static Role parse(String s){
            if(s == null) return null;
            switch(s.trim().toLowerCase()){
                case "buszvezeto": case "buszvezető": case "driver": return BUSZVEZETO;
                case "takarito":   case "takarító":   case "cleaner": return TAKARITO;
                default: return null;
            }
        }
    }

    /** Konstruktor. Alapértelmezetten 2000 jmf kezdőpénz.
     * @param id a játékos egyedi azonosítója
     * @param role a játékos szerepköre
     */
    public Player(String id, Role role){
        this(id, role, 2000);
    }

    /** Régi, ID és szerep nélküli konstruktor a Skeleton kompatibilitásért. */
    public Player(int startingCash){
        this(null, null, startingCash);
    }

    /** Konstruktor megadott kezdőpénzzel.
     * @param id a játékos egyedi azonosítója
     * @param role a játékos szerepköre
     * @param startingCash kezdeti jmf
     */
    public Player(String id, Role role, int startingCash) {
        this.id = id;
        this.role = role;
        this.vehicles = new java.util.ArrayList<>();
        this.cash = startingCash;
    }

    public String getId(){ return id; }
    public Role getRole(){ return role; }

    /** Jármű hozzáadása a játékos tulajdonába. */
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public List<Vehicle> getVehicles(){ return vehicles; }

    public int getVehicleCount() { return vehicles.size(); }

    /** Pénzlevonás (vásárláskor).
     * @return true, ha volt elég pénz és sikeresen levonásra került.
     */
    public boolean spend(int amount){
        if(cash < amount) return false;
        cash -= amount;
        return true;
    }

    /** Pénz jóváírása (mozgás, kör után). */
    public void earn(int amount){
        cash += amount;
    }

    /** Régi API megtartása kompatibilitás miatt. */
    public void decreaseCash(int amount) { cash -= amount; }

    public int getCash() { return cash; }

    public int getSectionsDone(){ return sectionsDone; }
    public void incrementSectionsDone(){ sectionsDone++; }

    public int getLapsDone(){ return lapsDone; }
    public void incrementLapsDone(){ lapsDone++; }
}
