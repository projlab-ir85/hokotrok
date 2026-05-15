package View.MapElements;

import RoadComponents.Intersection;
import Util.Observer;
import View.Util.Point;

import javax.swing.*;

public class IntersectionView implements Observer {
    private Point coord;
    private Intersection intersection;

    public IntersectionView(Intersection intersection, Point coord){
        this.coord = coord;
        this.intersection = intersection;
    }

    public Point getCoord(){return coord;}

    public void update(){

    };
}
