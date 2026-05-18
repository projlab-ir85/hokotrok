package View.MapElements;

import Vehicles.Bus;
import View.Util.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BusView extends VehicleView {
    public BusView(Bus bus, Point coord){
        super(bus, coord);

        try{
            tile = ImageIO.read(getClass().getResourceAsStream("/images/vehicles/32x48_cars_snowplow.png")).getSubimage(0*48, 6*48, 32,48);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(tile, (int)coord.x, (int)coord.y, null);
    }
}
