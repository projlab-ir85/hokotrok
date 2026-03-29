package RoadComponents;

public class Road {
    public enum Way{
        ONEWAY,
        TWOWAY
    }

    protected int length;
    protected Lane[] lanes;
    protected Intersection startPoint;
    protected Intersection endPoint;

    public Road(Intersection start, Intersection end, Way way, int lanes, int length){
        this.length = length;

        if(way == Way.ONEWAY){
            this.lanes = new Lane[1];
            this.lanes[0] = new Lane(lanes, length, start, end);
        }else if(way == Way.TWOWAY){
            this.lanes = new Lane[2];
            this.lanes[0] = new Lane(lanes, length, start, end);
            this.lanes[1] = new Lane(lanes, length, end, start);
        }

        startPoint = start;
        endPoint = end;
    }

    public RoadSection getDriveableRoadSection(Intersection destination){
        for (Lane lane : lanes) {
            if (lane.end.equals(destination)) {
                return lane.getDriveableRoadSection();
            }
        }
        return null;
    }

    public RoadSection getFirstRoadSection(Intersection destination){
        for (Lane lane : lanes) {
            if (lane.end.equals(destination)) {
                return lane.subLanes.getFirst().getFirst();
            }
        }
        return null;
    }
}
