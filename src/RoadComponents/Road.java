package RoadComponents;

import java.util.List;

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
