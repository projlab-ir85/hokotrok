package RoadComponents;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lane {
    protected List<List<RoadSection>> subLanes;
    protected Set<Integer> blockedSublanes;
    protected Intersection start;
    protected Intersection end;

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

    private void createLanes(int sublanes, int length){
        for(int i = 0; i < sublanes; i++){
            subLanes.add(new ArrayList<>());

            for(int j = 0; j < length; j++){
                subLanes.get(i).add(new RoadSection(this, i));
            }
        }
    }

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

    public void sublaneBlocked(int sublaneIndex){
        blockedSublanes.add(sublaneIndex);
    }

    public void sublaneFreed(int sublaneIndex){
        blockedSublanes.remove(sublaneIndex);
    }

    public RoadSection getDriveableRoadSection(){
        for(int i = 0; i < subLanes.size(); i++){
            if(!blockedSublanes.contains(i)){
                return subLanes.get(i).getFirst();
            }
        }
        return null;
    }
}
