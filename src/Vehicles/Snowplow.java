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
    protected Intersection start;

    /**
     * Új hókotró példány létrehozása
     * @param start
     * inicializálja az alapállapotot a hokotrónak
     */
    public Snowplow(Intersection start) {
        this.start = start;
        currIntersection = start;
        plowHeads = new ArrayList<>();
        plowHeads.add(new BroomHead());
        plowHeads.add(new IceBreakerHead());
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
        /* nincsen elakadva induláskor */
        stuck = false;
        stuckTime = 0;
    }

    /**
     * Új kotrófejet felszerel a hókotróra
     * @param plow
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
     * @param rs
     */
    public void interact(RoadSection rs){
        /* az éppen aktív kotrófejet használja */
        activePlowHead.use(rs);
    }
}
