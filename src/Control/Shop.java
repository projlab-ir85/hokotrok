package Control;

import Attachments.PlowHead;
import Attachments.Snowchain;
import Vehicles.*;

public class Shop {

    public Shop() {}

    public void buySnowplow(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Snowplow());
    }
    public void buyBus(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Bus());
    }
    public void buyConsumeable(Player player, Snowplow vehicle, String type){
        player.decreaseCash(100);
        vehicle.fillActiveHead(10);
    }

    public void buySnowchain(Player player, Bus bus){
        player.decreaseCash(300);
        bus.addSnowchain(new Snowchain(30));
    }
    public void buyAttachment(Player player, Snowplow vehicle, PlowHead plow){
        player.decreaseCash(500);
        vehicle.addPlow(plow);
    }
}
