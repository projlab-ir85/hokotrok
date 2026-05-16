package View.MapElements;

import Vehicles.Car;
import View.Util.Point;

import javax.imageio.ImageIO;
import java.awt.*;

public class CarView extends VehicleView {
    public CarView(Car car, Point coord){
        super(car, coord);

        try{
            tile = ImageIO.read(getClass().getResourceAsStream("/images/vehicles/300/Red_CIVIC_CLEAN_8D_000-sheet.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
