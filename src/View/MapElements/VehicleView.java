package View.MapElements;
import Vehicles.Vehicle;
import View.Interfaces.Animatable;
import Util.Observer;
import View.Util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class VehicleView implements Observer, Animatable {
    protected Vehicle vehicle;
    protected Point coord;
    private Point startCoord;
    private Point endCoord;
    private double t;
    private int VIEW_TICKS_PER_GAME_TICK;
    protected BufferedImage tile;

    public VehicleView(Vehicle vehicle, Point coord){
        this.vehicle = vehicle;
        this.coord = coord;
    }

    public void update(){

    }

    public void draw(Graphics g){
        g.drawImage(tile, (int)coord.x, (int)coord.y, null);
    }

    public void tick(){

    }
}
