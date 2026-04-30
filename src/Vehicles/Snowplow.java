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
    public Snowplow(Intersection start) {
        this.start = start;
        currIntersection = start;
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
        activePlowHead = plowHeads.get(0);
        /* nincsen elakadva induláskor */
        stuck = false;
        stuckTime = 0;
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
        currRoadSection.next.accept(this);
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
