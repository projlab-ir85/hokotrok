package View.Util;

import RoadComponents.Road;
import View.Swing.IntersectionView;
import View.Swing.RoadSectionView;

import java.util.List;

public class RoadView {
    private IntersectionView from;
    private IntersectionView to;
    private List<RoadSectionView> roadSectionViews;

    public RoadView(Road road, IntersectionView from, IntersectionView to){
        this.from = from;
        this.to = to;

    }

    public List<RoadSectionView> getRoadSectionViews(){ return roadSectionViews; }
}
