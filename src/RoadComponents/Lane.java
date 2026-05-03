package RoadComponents;

import Vehicles.Vehicle;

import java.util.*;
import java.util.stream.Collectors;

public class Lane {

    String roadId;
    int indexInRoad;
    /**
     * Az alsávokat és a belőlük felépülő útszakaszokat tartalmazó kétdimenziós lista.
     * Az első dimenzió az alsáv indexe, a második pedig az adott alsávhoz tartozó útszakaszok sorrendje.
     */
    protected List<List<RoadSection>> subLanes;
    /**
     * A nem használható alsávok indexeit tároló halmaz.
     */
    protected Set<Integer> blockedSublanes;

    protected Map<Integer, Integer> roadSectionsWithAccidents;
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
    public Lane(String id, int index, int sublanes, int length, Intersection start, Intersection end, int snowLevel, int iceLevel, int rockLevel, Road.Type type){
        this.roadId = id;
        this.indexInRoad = index;
        this.start = start;
        this.end = end;
        subLanes = new ArrayList<>();
        blockedSublanes = new HashSet<>();

        //creating lanes and filling them
        createLanes(id, sublanes, length, snowLevel, iceLevel, rockLevel, type);

        //setting neighbouring road sections
        setNeighbours(sublanes);
    }

    /**
     * Belső metódus, amely feltölti a subLanes listát a megfelelő számú alsávval és útszakasszal.
     * @param sublanes A létrehozandó alsávok száma.
     * @param length A létrehozandó alsávok hossza (útszakaszokban mérve).
     */
    private void createLanes(String id, int sublanes, int length, int snowLevel, int iceLevel, int rockLevel, Road.Type type){
        for(int i = 0; i < sublanes; i++){
            subLanes.add(new ArrayList<>());

            for(int j = 0; j < length; j++){
                String sectionId = createRoadSectionId(id, sublanes, length, i, j);
                subLanes.get(i).add(new RoadSection(sectionId,this, i, snowLevel, iceLevel, rockLevel, type));
            }
        }
    }

    private String createRoadSectionId(String id, int sublanes, int length, int sublaneIndex, int sectionIndex){
        if(indexInRoad == 0 && sublanes == 1 && length == 1){
            return id;
        }
        return id + "_" + indexInRoad + "_" + sublaneIndex + "_" + sectionIndex;
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

                if(!currLane.getFirst().equals(currSection)){
                    currSection.previous = currLane.get(j-1);
                }

                if(!currLane.getLast().equals(currSection)){
                    currSection.next = currLane.get(j+1);
                }

                if(subLanes.size() != 1){
                    if(!subLanes.getFirst().equals(currLane)){
                        currSection.left = subLanes.get(i-1).get(j);
                    }

                    if(!subLanes.getLast().equals(currLane)){
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

    public List<RoadSection> getAllRoadsectionsWithAccidents(){
        return roadSectionsWithAccidents.entrySet().stream()
                .map(entry -> subLanes.get(entry.getKey()).get(entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<RoadSection> getAllRoadSections(){
        return subLanes.stream()
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
    }

    public void tick(){
        for(List<RoadSection> sublane : subLanes){
            for(RoadSection rs : sublane){
                rs.tick();
            }
        }
    }

    public int getSnowLevel(){return subLanes.getLast().getLast().snowLevel;}
    public int getIceLevel(){return subLanes.getLast().getLast().iceLevel;}
    public int getRockLevel(){return subLanes.getLast().getLast().rockLevel;}
    public Road.Type getType(){return subLanes.getLast().getLast().type;}

    public List<Vehicle> getAllVehicles(){
        return subLanes.stream()
                .flatMap(List::stream)
                .flatMap(roadSection -> roadSection.getVehicles().stream())
                .collect(Collectors.toList());
    }

    public String getRoadId(){return roadId;}
    public int getIndexInRoad(){return indexInRoad;}
    public Intersection getEnd(){return end;}
}
