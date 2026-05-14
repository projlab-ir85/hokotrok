package Vehicles;

import Attachments.PlowHead;
import Attachments.PlowHeads.*;
import RoadComponents.Intersection;
import RoadComponents.RoadSection;

import java.util.ArrayList;
import java.util.List;

public class Snowplow extends Vehicle{
    protected List<PlowHead> plowHeads;
    protected PlowHead activePlowHead;

    /**
     * Új hókotró példány létrehozása
     * @param start kezdőkereszteződés, ahonnét indul
     * inicializálja az alapállapotot a hokotrónak
     */
    public Snowplow(String id, Intersection start) {
        this.id = id;
        this.start = start;
        currIntersection = start;
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        activePlowHead = plowHeads.get(0);
        /* nincsen elakadva induláskor */
        stuck = false;
        stuckTime = 0;
        nextIntersectionIndex = 0;
    }

    public Snowplow(Intersection start) {
        this(null, start);
    }

    /**
     * Paraméter nélküli konstruktor
     * inicializálja a hókotrófejek listáját, és felszereli a gépet seprűvel és jégtörővel
     */
    public Snowplow() {
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        activePlowHead = plowHeads.get(0);
        /* nincsen elakadva induláskor */
        stuck = false;
        stuckTime = 0;
        nextIntersectionIndex = 0;
    }

    /**
     * Új kotrófejet felszerel a hókotróra
     * @param plow kotrófej, amit felrak
     */
    public void addPlow(PlowHead plow) {
        plowHeads.add(plow);
    }

    /**
     * Hókotrónak lép egyet
     */
    public void step(){
        /* átlép a következő útszakaszra */
        if(currRoadSection == null && currIntersection != null) {
            Intersection next = getNextIntersection();
            if(next == null) return;
            Intersection oldIntersection = currIntersection;
            RoadSection rs = currIntersection.roadSelection(next);
            if(rs != null && rs.accept(this)) {
                oldIntersection.getVehicles().remove(this);
            }
            return;
        }

        if(currRoadSection == null) return;

        /* Mielőtt ellépünk a szakaszról, megjegyezzük, volt-e rajta hó
         * vagy jég — ha igen és a takarító játékosé ez a hókotró, akkor
         * a dokumentáció szerint 25 jmf jár érte. */
        boolean hadSnowOrIce = currRoadSection.getSnow() > 0 || currRoadSection.getIce() > 0;

        /* amennyiben nincsen elakadva akkor átlép a köbetkező útszakaszra */
        boolean sectionAdvanced = false;
        if(currRoadSection.next != null) {
            RoadSection oldRoadSection = currRoadSection;
            if(oldRoadSection.next.accept(this)) {
                oldRoadSection.removeVehicle(this);
                sectionAdvanced = true;
            }
        } else {
            RoadSection oldRoadSection = currRoadSection;
            Intersection arrived = oldRoadSection.getLane().getEnd();
            currRoadSection.getLane().getEnd().addVehicle(this);
            oldRoadSection.removeVehicle(this);
            advanceRouteIfAt(arrived);
            sectionAdvanced = true;
        }

        if(sectionAdvanced){
            if(owner != null) owner.incrementSectionsDone();
            if(hadSnowOrIce) creditOwner(25);
        }
    }

    /**
     * Interakcióba lép az adott útszakasszal
     * @param rs útszakasz
     */
    public void interact(RoadSection rs){
        /* az éppen aktív kotrófejet használja */
        activePlowHead.use(rs);
    }

    public void fillActiveHead(int amount) {
        if(activePlowHead != null) activePlowHead.fillConsumable(amount);
    }

    public void fillPlowHead(int amount, Class<? extends PlowHead> plowHeadClass){
        for(PlowHead ph : plowHeads){
            if(ph.getClass() == plowHeadClass){
                ph.fillConsumable(amount);
            }
        }
    }

    public int getPlowHeadCount() { return plowHeads.size(); }

    public List<PlowHead> getPlowHeads() {return plowHeads;}

    public PlowHead getActivePlowHead() { return activePlowHead; }

    public void setActivePlowHead(PlowHead head) { activePlowHead = head; }

    public Intersection getCurrIntersection() { return currIntersection; }

    public void setCurrIntersection(Intersection i) { currIntersection = i; }
}
