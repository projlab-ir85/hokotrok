package View.MapElements;

import RoadComponents.RoadSection;
import Util.Observer;
import View.Interfaces.Animatable;
import View.Util.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.spec.ECField;

public class RoadSectionView implements Observer, Animatable {
    private BufferedImage tile;
    private Point startCoord;
    private Point endCoord;
    private RoadSection roadSection;
    private double t;
    private int VIEW_TICKS_PER_GAME_TICK;
    private double startSnow;
    private double targetSnow;
    private double startIce;
    private double targetIce;
    private double startGravel;
    private double targetGravel;

    public RoadSectionView(RoadSection roadSection, Point startCoord, Point endCoord){
        this.roadSection = roadSection;
        this.startCoord = startCoord;
        this.endCoord = endCoord;

        try{
            tile = ImageIO.read(getClass().getResourceAsStream("/images/roadComponents/roads.png")).getSubimage(512, 256, 256,256);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Point getStartCoord(){return startCoord;}

    public Point getEndCoord(){return endCoord;}

    public void update(){

    }

    public void tick(){

    }

    public void draw(Graphics g){
        g.drawImage(tile, (int)startCoord.x, (int)startCoord.y, null);
    }
}
