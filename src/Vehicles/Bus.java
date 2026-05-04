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
     * Beállítja a busz következő, a buszvezető játékos által kijelölt
     * kereszteződését. Ha a játékos egy ugyanolyan kereszteződést ad meg,
     * mint ahol a busz most áll, a metódus nem tesz semmit.
     * @param intersection a kijelölt következő kereszteződés
     */
    public void setNext(Intersection intersection){
        if(intersection == null) return;
        if(intersection.equals(currIntersection)) return;
        this.next = intersection;
    }

    public Intersection getPlayerNext(){ return next; }

    public int getLapsDone(){ return lapsDone; }

    public boolean isFinishedLap(){ return finishedLap; }

    /**
     * Busznak lép egyet. A busz a buszvezető játékos által irányított
     * jármű, ezért a mozgás logikája eltér az autóétól:
     *
     * - Ha el van akadva (stuck), csak az elakadás idejét csökkenti.
     * - Kereszteződésben áll: megnézi a player által beállított next mezőt,
     *   ha nincs ilyen, megpróbálja a {@code setutvonal} paranccsal kijelölt
     *   útvonalból kinyerni a soron következő kereszteződést. Ha egyik sem
     *   elérhető, a busz vár (a játékosra).
     * - Útszakaszon halad: továbblép a szakasz next mezőjére. Ha az
     *   útszakaszok sorának végére ért, beáll a célzott kereszteződésbe,
     *   és a next mezőt kiüríti (újabb player-input szükséges a folytatáshoz).
     * - Ha van felszerelt hólánc, akkor minden ténylegesen megtett
     *   útszakasz után használja azt.
     * - A végállomásra érkezéskor növeli a megtett körök számát és
     *   beállítja a finishedLap jelzőt.
     */
    public void step(){
        finishedLap = false;

        if(stuck) {
            stuckTime--;
            return;
        }

        /* 1. Kereszteződésben áll a busz. */
        if(currRoadSection == null && currIntersection != null) {
            /* Ha a játékos nem adott meg még egy következő kereszteződést,
             * megpróbáljuk a setutvonal paranccsal kijelölt útvonal
             * várólistájából kiolvasni a soron következőt. Ha sem a player,
             * sem a várólista nem ad irányt, a busz várakozik. */
            if(next == null && junctions != null && !junctions.isEmpty()) {
                advanceRouteIfAt(currIntersection);
                if(nextIntersectionIndex < junctions.size()) {
                    Intersection queued = junctions.get(nextIntersectionIndex);
                    if(queued != null && !queued.equals(currIntersection)) {
                        next = queued;
                    }
                }
            }
            if(next == null) return;

            Intersection oldIntersection = currIntersection;
            RoadSection rs = currIntersection.roadSelection(next);
            if(rs != null && rs.accept(this)) {
                oldIntersection.getVehicles().remove(this);
            }
            /* A kereszteződésből való elindulás a dokumentáció szerint
             * nem számít megtett útszakasznak a hólánc szempontjából. */
            return;
        }

        if(currRoadSection == null) return;

        /* 2. Útszakaszon halad a busz. */
        boolean sectionAdvanced = false;
        if(currRoadSection.next != null) {
            RoadSection oldRoadSection = currRoadSection;
            if(oldRoadSection.next.accept(this)) {
                oldRoadSection.removeVehicle(this);
                sectionAdvanced = true;
            }
        } else {
            /* 3. Elérte az útszakaszok sorának végét: betér a
             * kereszteződésbe, a next mezőt kiüríti (újabb player-input
             * szükséges a továbbhaladáshoz), kör-számláló frissül. */
            RoadSection oldRoadSection = currRoadSection;
            Intersection arrived = oldRoadSection.getLane().getEnd();
            arrived.addVehicle(this);
            oldRoadSection.removeVehicle(this);
            advanceRouteIfAt(arrived);
            sectionAdvanced = true;

            if(arrived.equals(next)) {
                next = null;
            }
            if(arrived.equals(end)) {
                lapsDone++;
                finishedLap = true;
                /* Teljesített körért járó jmf a buszvezetőnek. */
                creditOwner(200);
                if(owner != null) owner.incrementLapsDone();
            }
        }

        /* Hólánc fogyasztása minden megtett útszakasz után. */
        if(hasSnowchain) {
            snowchain.use();
        }

        /* Megtett útszakaszért járó 10 jmf a buszvezető játékosnak. */
        if(sectionAdvanced){
            creditOwner(10);
            if(owner != null) owner.incrementSectionsDone();
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
