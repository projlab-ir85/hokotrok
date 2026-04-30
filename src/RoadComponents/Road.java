package RoadComponents;

import Vehicles.Vehicle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Két kereszteződést összekötő utat reprezentáló osztály.
 * Egy út lehet egyirányú vagy kétirányú.
 */
public class Road {
    /**
     * Az út irányultságát meghatározó felsorolás (enum).
     */
    public enum Way{
        /** Egyirányú út. */
        ONEWAY,
        /** Kétirányú út. */
        TWOWAY
    }

    public enum Type{
        ALAGUT,
        FOUT,
        HID
    }

    protected String id;

    /**
     * Az út hossza (az útszakaszok számában kifejezve).
     */
    protected int length;
    /**
     * Az utat alkotó sávokat tartalmazó tömb. Egyirányú út esetén 1, kétirányú esetén 2 sávot tartalmaz.
     */
    protected Lane[] lanes;
    /**
     * Az út egyik végpontját (kezdőpontját) jelentő kereszteződés.
     */
    protected Intersection startPoint;
    /**
     * Az út másik végpontját jelentő kereszteződés.
     */
    protected Intersection endPoint;

    /**
     * A Road osztály konstruktora.
     * Létrehozza az utat a két megadott kereszteződés között, beállítja az irányultságot, és legenerálja a megfelelő számú sávot.
     * @param start Az út kezdőpontját jelentő kereszteződés.
     * @param end Az út végpontját jelentő kereszteződés.
     * @param way Az út irányultsága (egyirányú vagy kétirányú).
     * @param lanes A sávon belüli párhuzamos alsávok száma.
     * @param length Az út hossza.
     */
    public Road(String id, Intersection start, Intersection end, int lanes, int length, Way way, int snowLevel, int iceLevel, int rockLevel, Type type){
        if(id == null){
            id = start.id+end.id;
        }
        this.id = id;
        this.length = length;

        if(way == Way.ONEWAY){
            this.lanes = new Lane[1];
            this.lanes[0] = new Lane(id,lanes, length, start, end, snowLevel, iceLevel, rockLevel, type);
        }else if(way == Way.TWOWAY){
            this.lanes = new Lane[2];
            this.lanes[0] = new Lane(id,lanes, length, start, end, snowLevel, iceLevel, rockLevel, type);
            this.lanes[1] = new Lane(id,lanes, length, end, start, snowLevel, iceLevel, rockLevel, type);
        }

        startPoint = start;
        endPoint = end;
    }

    /**
     * Kikeresi a célkereszteződés felé vezető sávból az első szabad (vezethető) útszakaszt.
     * @param destination A kereszteződés, ahová a jármű tart.
     * @return Az első szabad RoadSection, amire rá lehet hajtani, vagy null, ha a sáv teljesen blokkolt.
     */
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

    public void tick(){
        for(Lane l : lanes){
            l.tick();
        }
    }

    public String getId(){return id;}
    public String getStartIntersectionId(){return startPoint.id;}
    public String getEndIntersectionId(){return endPoint.id;}
    public int getLaneCount(){return lanes[0].subLanes.size();}
    public int getLength(){return length;}
    public boolean getWay(){return lanes.length == 1;}
    public int getSnowLevel(){return lanes[0].getSnowLevel();}
    public int getIceLevel(){return lanes[0].getIceLevel();}
    public int getRockLevel(){return lanes[0].getRockLevel();}
    public Type getType(){return lanes[0].getType();}
    public List<Lane> getLanes(){return Collections.unmodifiableList(Arrays.asList(lanes));}

    public List<Vehicle> getAllVehicles(){
        return Arrays.stream(lanes)
                .flatMap(lane -> lane.getAllVehicles().stream())
                .collect(Collectors.toUnmodifiableList());
    }

    public RoadSection findRoadSectionById(String id){
            return Arrays.stream(lanes)
                    .flatMap(lane -> lane.getAllRoadSections().stream())
                    .filter(rs -> rs.getId().equals(id))
                    .findFirst().orElse(null);
    }

    //demo
    public void setIceshield(){
        for(Lane lane : lanes){
            for(List<RoadSection> sublane : lane.subLanes){
                for(RoadSection rs : sublane){
                    rs.iceIncrease(100);
                }
            }
        }
    }
}
