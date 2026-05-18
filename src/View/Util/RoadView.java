package View.Util;

import RoadComponents.Lane;
import RoadComponents.Road;
import RoadComponents.RoadSection;
import View.MapElements.IntersectionView;
import View.MapElements.RoadSectionView;

import java.util.ArrayList;
import java.util.List;

public class RoadView {
    private List<RoadSectionView> roadSectionViews;

    public RoadView(Road road, IntersectionView from, IntersectionView to, int viewTicksPerGameTick) {
        this.roadSectionViews = new ArrayList<>();

        Point fromCoord = from.getCoord();
        Point toCoord = to.getCoord();

        boolean horizontal = Math.abs(toCoord.x - fromCoord.x) > Math.abs(toCoord.y - fromCoord.y);

        List<Lane> lanes = road.getLanes();

        int globalSublaneIndex = 0; // összesített alsáv index az eltoláshoz

        for (Lane lane : lanes) {
            for (int s = 0; s < lane.getSubLanes().size(); s++) {
                List<RoadSection> sublane = lane.getSubLanes().get(s);
                int sectionCount = sublane.size();

                for (int j = 0; j < sectionCount; j++) {
                    RoadSection rs = sublane.get(j);

                    double startT = (double) j / sectionCount;
                    double endT = (double) (j + 1) / sectionCount;

                    Point sectionStart, sectionEnd;

                    if (horizontal) {
                        double startX = fromCoord.x + startT * (toCoord.x - fromCoord.x);
                        double endX = fromCoord.x + endT * (toCoord.x - fromCoord.x);
                        double offsetY = globalSublaneIndex * RoadSectionView.LANE_WIDTH;
                        sectionStart = new Point(startX, fromCoord.y + offsetY);
                        sectionEnd = new Point(endX, fromCoord.y + offsetY);
                    } else {
                        double startY = fromCoord.y + startT * (toCoord.y - fromCoord.y);
                        double endY = fromCoord.y + endT * (toCoord.y - fromCoord.y);
                        double offsetX = globalSublaneIndex * RoadSectionView.LANE_WIDTH;
                        sectionStart = new Point(fromCoord.x + offsetX, startY);
                        sectionEnd = new Point(fromCoord.x + offsetX, endY);
                    }

                    RoadSectionView rsv = new RoadSectionView(rs, sectionStart, sectionEnd, viewTicksPerGameTick);
                    roadSectionViews.add(rsv);
                }

                globalSublaneIndex++;
            }
        }
    }

    public List<RoadSectionView> getRoadSectionViews(){ return roadSectionViews; }
}
