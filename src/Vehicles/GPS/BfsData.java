package Vehicles.GPS;

public class BfsData {
    protected int distance;
    protected String prevId;
    protected String junctionId;

    public BfsData(String junctionId, int distance, String prevId){
        this.junctionId = junctionId;
        this.distance = distance;
        this.prevId = prevId;
    }
}
