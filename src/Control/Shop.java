package Control;

import Attachments.PlowHead;
import Attachments.Snowchain;
import Vehicles.*;
/**A bolt megvalósítása */
public class Shop {
    /** Default konstruktor
     */
    public Shop() {}
    /** Hókotró vásárlása
     * Csökkenti a játékos pénzét és ad egy új hókotrót
     * @param player melyik játékosnak adjuk a hókotrót
     */
    public void buySnowplow(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Snowplow());
    }
    /** Busz vásárlása
     * Csökkenti a játékos pénzét és ad egy új buszt
     * @param player melyik játékosnak adjuk a buszt
     */
    public void buyBus(Player player){
        player.decreaseCash(2000);
        player.addVehicle(new Bus());
    }
    /** Fogyóeszköz vásárlása
     * Levonjuk a játékostól a pénzt és megtöltjük az adott hókotró kotrófejét
     * @param player melyik játékostól vonjuk le a pénzt
     * @param vehicle melyik hókotrónak adjuk a fogyóeszközt
     * 
     */
    public void buyConsumeable(Player player, Snowplow vehicle){
        player.decreaseCash(100);
        vehicle.fillActiveHead(10);
    }
    /** Busz vásárlása
     * Levonjuk a játékostól a pénzt és felteszzük a buszra a hóláncot
     * @param player melyik játékos veszi a hóláncot
     * @param bus melyik buszra vesszük a hóláncot
     */
    public void buySnowchain(Player player, Bus bus){
        player.decreaseCash(300);
        bus.addSnowchain(new Snowchain(30));
    }
    /** Kotrófej vásárlása
     * Levonjuk a játékostól a pénzt, majd az adott hókotróhoz hozzáadjuk az adott kotrófejet
     * @param player melyik játékos veszi a kotrófejet
     * @param vehicle melyik hókotróra vesszük a kotrófejet
     * @param plow milyen kotrófejet veszünk
     */
    public void buyAttachment(Player player, Snowplow vehicle, PlowHead plow){
        player.decreaseCash(500);
        vehicle.addPlow(plow);
    }
}
