package RoadComponents;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    protected List<List<RoadSection>> SubLanes;
    protected Intersection start;
    protected Intersection end;

    public Lane(int sublanes, int length, Intersection start, Intersection end){
        this.start = start;
        this.end = end;
        SubLanes = new ArrayList<>();

        //creating lanes and filling them
        for(int i = 0; i < sublanes; i++){
            SubLanes.add(new ArrayList<>());

            for(int j = 0; j < length; j++){
                SubLanes.get(i).add(new RoadSection(this));
            }
        }

        //setting neighbouring road sections
        for(int i = 0; i < sublanes; i++){
            List<RoadSection> currLane = SubLanes.get(i);

            for(int j = 0; j < currLane.size(); j++){
                RoadSection currSection = currLane.get(j);

                if(!currLane.getFirst().equals(currSection)){
                    currSection.previous = currLane.get(j-1);
                }

                if(!currLane.getLast().equals(currSection)){
                    currSection.next = currLane.get(j+1);
                }

                if(SubLanes.size() != 1){
                    if(!SubLanes.getFirst().equals(currLane)){
                        currSection.left = SubLanes.get(i-1).get(j);
                    }

                    if(!SubLanes.getLast().equals(currLane)){
                        currSection.right = SubLanes.get(i+1).get(j);
                    }
                }
            }
        }
    }
}
