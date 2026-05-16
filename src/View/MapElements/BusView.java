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
            tile = ImageIO.read(getClass().getResourceAsStream("/images/vehicles/Yellow_BUS_CLEAN_8D_000-sheet.png")).getSubimage(0, 0, 210,210);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.scale(0.75,0.75);

        g2d.drawImage(tile, (int)coord.x, (int)coord.y, null);
    }
}
