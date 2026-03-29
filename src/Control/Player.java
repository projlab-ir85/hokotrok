package Control;
import java.util.List;

import Vehicles.*;

/** A játékos megvalósítása */
public class Player {
    private List<Vehicle> vehicles;
    private int cash;
    /** Konstruktor amiben meg lehet mennyi pénzzel kezd a játékos
     * Létrehozza a járművek listáját és beállítja a kezdő pénzmennyiséget
     * @param startingCash mennyi pénzzel kezd a játékos
     */
    public Player(int startingCash) {
        vehicles = new java.util.ArrayList<>();
        cash = startingCash;
    }
    /** Jármű hozzáadása, hozzáad egy járművet a játékos járműinek listájához
     * @param v a jármű amit odaadunk a játékosnak
     */
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }
    /** Pénz csökkentés, csökkentjük a játékos pénzét ha vásárol a megadott mennyiséggel
     * @param amount a mennyiség amivel a játékos a pénzét csökkentjük
     */
    public void decreaseCash(int amount) {
        cash -= amount;
    }
    /** Visszaadja mennyi pénze van a játékosnak
     * @return a játékos pénzmennyisége
     */
    public int getCash() { return cash; }
    /** Visszaadja mennyi járműve van a játékosnak
     * @return a jármű lista mérete
     */
    public int getVehicleCount() { return vehicles.size(); }
}
