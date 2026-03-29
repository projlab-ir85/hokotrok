package Control;

import Attachments.PlowHead;
import Vehicles.*;

public class Shop {

    public Shop() {}

    void buySnowplow(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Snowplow());
    }
    void buyBus(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Bus());
    }
    void buyConsumeable(Player player, Snowplow vehicle, String consumeable){
        
    }
    void buyAttachment(Player player, Snowplow vehicle, PlowHead plow){
        player.decreaseCash(500);
        vehicle.addPlow(plow);
    }
}
