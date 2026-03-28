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
        }else{
            this.lanes = new Lane[2];
            this.lanes[0] = new Lane(lanes, length, start, end);
            this.lanes[1] = new Lane(lanes, length, start, end);
        }

        startPoint = start;
        endPoint = end;
    }
}
