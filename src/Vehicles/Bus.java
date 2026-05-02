package Vehicles;

import Attachments.Snowchain;
import RoadComponents.Intersection;
import RoadComponents.RoadSection;

public class Bus extends Vehicle{
    protected Snowchain snowchain;
    protected int lapsDone;
    protected int accidentTime;
    protected boolean finishedLap;
    protected Intersection next;
    protected boolean hasSnowchain;

    public Bus(String id, Intersection start, Intersection end) {
        this.id = id;
        this.start = start;
        this.end = end;
        currIntersection = start;
        stuck = false;
        stuckTime = 0;
        lapsDone = 0;
        accidentTime = 0;
        finishedLap = false;
        hasSnowchain = false;
        nextIntersectionIndex = 0;
    }

    public Bus(Intersection start, Intersection end) {
        this(null, start, end);
    }

    public Bus(){}

    /**
     *
     * Hóláncot felszereli a buszra
     * @param snowchain hólánc, amit felrakunk a buszra
     *
     */
    public void addSnowchain(Snowchain snowchain){
        this.snowchain = snowchain;
        hasSnowchain = true;
    }

    /**
     * Busznak lép egyet
     */
    public void step(){
        /* ha el van akadva, akkor csökkenti az elakadási időt, és véget ér a lépés */
        if(stuck) {
            stuckTime--;
            return;
        }
        if(currRoadSection == null && currIntersection != null) {
            Intersection next = getNextIntersection();
            if(next == null) return;
            RoadSection rs = currIntersection.roadSelection(end);
            if(rs != null && rs.accept(this)) {
                currIntersection.getVehicles().remove(this);
                currIntersection = null;
            }
        }

        if(currRoadSection == null) return;
        
        /* amennyiben nincsen elakadva akkor átlép a köbetkező útszakaszra */
        if(currRoadSection.next != null) {
            currRoadSection.next.accept(this);
        } else {
            currRoadSection.getLane().getEnd().addVehicle(this);
        }
        /* amennyiben nincsen elakadva akkor átlép a köbetkező útszakaszra */
        currRoadSection.next.accept(this);
        /* ha van rajta hólánc, akkor azt használja az adott útszakaszon */
        if(hasSnowchain) {
            snowchain.use();
        }
        
    }

    /**
     * Interakcióba lép az adott útszakasszal
     * @param roadsection útszakasz, amivel a busz interaktál
     * 1 egységgel növeli a jégszintet a busz a haladása során
     */
    public void interact(RoadSection roadsection){
        roadsection.iceIncrease(1);
    }

    public Attachments.Snowchain getSnowchain() { return snowchain; }

    public boolean getHasSnowchain() { return hasSnowchain; }

    public int getSnowchainTTL(){return snowchain.getTimeToLive();}

    public Intersection getCurrIntersection() { return currIntersection; }

    public void setCurrIntersection(Intersection i) { currIntersection = i; }

    public Intersection getNext(){return next;}
}
