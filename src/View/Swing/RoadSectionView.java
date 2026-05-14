package View.Swing;

import RoadComponents.RoadSection;
import Util.Observer;
import View.Interfaces.Animatable;
import View.Util.Point;

import javax.swing.*;

public class RoadSectionView extends JPanel implements Observer, Animatable {
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
    }

    public Point getStartCoord(){return startCoord;}

    public Point getEndCoord(){return endCoord;}

    public void update(){

    }

    public void tick(){

    }
}
