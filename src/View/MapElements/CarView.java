package View.MapElements;

import Vehicles.Car;

import javax.imageio.ImageIO;
import java.awt.*;

public class CarView extends VehicleView {
    public CarView(Car car){
        super(car);

        try{
            image = ImageIO.read(getClass().getResourceAsStream("/images/vehicles/Red_CIVIC_CLEAN_All_000.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){

    }
}
