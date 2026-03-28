package Control;
import java.util.List;

import Vehicles.*;


public class Player {
    private List<Vehicle> vehicles;
    private int cash;

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void decrease(int amount) {
        cash -= amount;
    }
}
