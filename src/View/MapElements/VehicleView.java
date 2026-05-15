package View.MapElements;
import Vehicles.Vehicle;
import View.Interfaces.Animatable;
import Util.Observer;
import View.Util.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public abstract class VehicleView implements Observer, Animatable {
    protected Vehicle vehicle;
    protected Point coord;
    private Point startCoord;
    private Point endCoord;
    private double t;
    private int VIEW_TICKS_PER_GAME_TICK;
    protected BufferedImage image;

    public VehicleView(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    public void update(){

    }

    public void draw(Graphics g){

    }

    public void tick(){

    }
}
