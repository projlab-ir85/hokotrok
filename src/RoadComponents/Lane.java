package RoadComponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lane {
    /**
     * Az alsávokat és a belőlük felépülő útszakaszokat tartalmazó kétdimenziós lista.
     * Az első dimenzió az alsáv indexe, a második pedig az adott alsávhoz tartozó útszakaszok sorrendje.
     */
    protected List<List<RoadSection>> subLanes;
    /**
     * A nem használható alsávok indexeit tároló halmaz.
     */
    protected Set<Integer> blockedSublanes;
    /**
     * A sáv kezdőpontját jelentő kereszteződés.
     */
    protected Intersection start;
    /**
     * A sáv végpontját (célját) jelentő kereszteződés.
     */
    protected Intersection end;

    /**
     * A Lane osztály konstruktora, amely inicializálja a sávot, legenerálja az alsávokat és beállítja az útszakaszok közötti kapcsolatokat.
     * @param sublanes A párhuzamos alsávok száma.
     * @param length A sáv hossza (az egy alsávot alkotó útszakaszok száma).
     * @param start A sáv kiindulási kereszteződése.
     * @param end A sáv végkereszteződése.
     */
    public Lane(int sublanes, int length, Intersection start, Intersection end){
        this.start = start;
        this.end = end;
        subLanes = new ArrayList<>();
        blockedSublanes = new HashSet<>();

        //creating lanes and filling them
        createLanes(sublanes, length);

        //setting neighbouring road sections
        setNeighbours(sublanes);
    }

    /**
     * Belső metódus, amely feltölti a subLanes listát a megfelelő számú alsávval és útszakasszal.
     * @param sublanes A létrehozandó alsávok száma.
     * @param length A létrehozandó alsávok hossza (útszakaszokban mérve).
     */
    private void createLanes(int sublanes, int length){
        for(int i = 0; i < sublanes; i++){
            subLanes.add(new ArrayList<>());

            for(int j = 0; j < length; j++){
                subLanes.get(i).add(new RoadSection(this, i));
            }
        }
    }

    /**
     * Belső metódus, amely minden egyes útszakaszra beállítja a szomszédsági referenciákat (előző, következő, bal, jobb). Ezzel épül fel a navigálható hálózat.
     * @param sublanes Az alsávok száma.
     */
    private void setNeighbours(int sublanes){
        for(int i = 0; i < sublanes; i++){
            List<RoadSection> currLane = subLanes.get(i);

            for(int j = 0; j < currLane.size(); j++){
                RoadSection currSection = currLane.get(j);

                if(!currLane.get(0).equals(currSection)){
                    currSection.previous = currLane.get(j-1);
                }

                if(!currLane.get(currLane.size()-1).equals(currSection)){
                    currSection.next = currLane.get(j+1);
                }

                if(subLanes.size() != 1){
                    if(!subLanes.get(0).equals(currLane)){
                        currSection.left = subLanes.get(i-1).get(j);
                    }

                    if(!subLanes.get(subLanes.size()-1).equals(currLane)){
                        currSection.right = subLanes.get(i+1).get(j);
                    }
                }
            }
        }
    }

    /**
     * Blokkolttá tesz egy alsávot, így arra ideiglenesen nem lehet ráhajtani.
     * @param sublaneIndex A blokkolandó alsáv indexe.
     */
    public void sublaneBlocked(int sublaneIndex){
        blockedSublanes.add(sublaneIndex);
    }

    /**
     * Felszabadít egy korábban blokkolt alsávot.
     * @param sublaneIndex A felszabadítandó alsáv indexe.
     */
    public void sublaneFreed(int sublaneIndex){
        blockedSublanes.remove(sublaneIndex);
    }

    public RoadSection getDriveableRoadSection(){
        for(int i = 0; i < subLanes.size(); i++){
            if(!blockedSublanes.contains(i)){
                return subLanes.get(i).get(0);
            }
        }
        return null;
    }

    public void tick(){
        for(List<RoadSection> sublane : subLanes){
            for(RoadSection rs : sublane){
                rs.tick();
            }
        }
    }
}
