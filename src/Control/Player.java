package Control;
import java.util.List;

import Vehicles.*;


public class Player {
    private List<Vehicle> vehicles;
    private int cash;

    public Player(int startingCash) {
        vehicles = new java.util.ArrayList<>();
        cash = startingCash;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void decreaseCash(int amount) {
        cash -= amount;
    }
}
